package com.example.service.qa.thread.processor;

import com.example.service.qa.thread.request.RequestQueue;
import com.example.service.qa.thread.IRequestHandler;
import com.example.service.qa.thread.IRequestProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: CustomerChannelRequestProcessor
 * @Description: 修复会员渠道信息请求执行器
 * @date 2020-10-13-12:11
 */
@Slf4j
@Service("requestProcessor")
public class RequestProcessorImpl extends BaseRequestProcessor implements IRequestProcessor {

	@Override
	public void process(IRequestHandler request) {
		String routingKey = request.routingKey();

		RequestQueue queue = RequestQueue.getInstance();

		int queueSize = queue.queueSize();
		int index = getIndex(routingKey, queueSize);

		if (log.isDebugEnabled()) {
			log.debug("routingKey {}, index {}, queueSize {}", routingKey, index, queueSize);
		}

		BlockingQueue<IRequestHandler> requests = queue.getIndex(index);
		try {
			requests.put(request);
		} catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}
	}
}
