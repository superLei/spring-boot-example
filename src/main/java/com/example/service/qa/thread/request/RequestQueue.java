package com.example.service.qa.thread.request;

import com.google.common.collect.Lists;
import com.example.service.qa.thread.IRequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName: RequestQueue
 * @Description: 请求队列
 * @author yangpengbing
 * @date 2020-05-27-10:49
 * @version V1.0.0
 *
 */
@Slf4j
public class RequestQueue {

	private List<BlockingQueue<IRequestHandler>> queues;

	private RequestQueue() {
		queues = Lists.newArrayList();
	}

	public static RequestQueue getInstance() {
		return SingletonHolder.getQueue();
	}

	public void addQueue(ArrayBlockingQueue<IRequestHandler> requests) {
		queues.add(requests);
	}

	public int queueSize() {
		return queues.size();
	}

	public BlockingQueue<IRequestHandler> getIndex(int index) {
		return queues.get(index);
	}

	private static class SingletonHolder {

		private static final RequestQueue QUEUE;
		static {
			QUEUE = new RequestQueue();
		}

		static RequestQueue getQueue() {
			return QUEUE;
		}
	}
}
