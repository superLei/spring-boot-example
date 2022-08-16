package com.example.service.qa.thread.pool;

import com.example.service.qa.thread.request.RequestQueue;
import com.example.service.qa.thread.request.RequestThread;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.example.service.qa.thread.IRequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.*;

/**
 * @author 
 * @version V1.0.0
 * @ClassName: RequestProcessorThreadPool
 * @Description: 请求执行线程池
 * @date 2020-05-27-10:43
 */
@Slf4j
public class RequestProcessorThreadPool {

	private ExecutorService threadPool;
	private int capacity = 10240;
	private int threadNum = 20;

	private RequestProcessorThreadPool() {
		threadPool = initThreadPools(threadNum);

		RequestQueue queue = RequestQueue.getInstance();
		for (int i = 0; i < threadNum; i++) {
			ArrayBlockingQueue<IRequestHandler> requests = Queues.newArrayBlockingQueue(capacity);
			queue.addQueue(requests);

			threadPool.submit(new RequestThread(requests));
		}
	}

	private ExecutorService initThreadPools(int corePoolSize) {
		long keepAliveTime = BigDecimal.ZERO.longValue();
		BlockingQueue<Runnable> queue = Queues.newLinkedBlockingQueue(capacity);
		ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("requestProcessor-pool-%d").build();

		return new ThreadPoolExecutor(corePoolSize, corePoolSize, keepAliveTime, TimeUnit.MILLISECONDS, queue, factory);
	}

	public static RequestProcessorThreadPool getInstance() {
		return SingletonHolder.getPool();
	}

	public static void init() {
		getInstance();
	}

	private static class SingletonHolder {

		private static final RequestProcessorThreadPool POOL;
		static {
			POOL = new RequestProcessorThreadPool();
		}

		static RequestProcessorThreadPool getPool() {
			return POOL;
		}
	}
}

