package com.example.service.qa.thread.request;

import com.example.service.qa.thread.IRequestHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @ClassName: RequestThread
 * @Description: 请求线程
 * @author yangpengbing
 * @date 2020-10-13-11:52
 * @version V1.0.0
 *
 */
@Slf4j
public class RequestThread implements Runnable {

	private final BlockingQueue<IRequestHandler> queue;

	public RequestThread(ArrayBlockingQueue<IRequestHandler> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				IRequestHandler request = queue.take();
				request.process();

				request.clearResource();
			} catch (InterruptedException e) {
				log.debug(e.getMessage(), e);
			}
		}
	}
}
