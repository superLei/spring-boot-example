package com.example.service.qa.thread.processor;
/**
 * @ClassName: BaseRequestProcessor
 * @Description: 请求执行基类
 * @author yangpengbing
 * @date 2020-10-13-11:56
 * @version V1.0.0
 *
 */
public class BaseRequestProcessor {

	/**
	 * 获取索引index
	 * @param routingKey
	 * @param queueSize
	 * @return
	 */
	protected int getIndex(String routingKey, int queueSize) {
		int h;
		int hash = (routingKey == null) ? 0 : (h = routingKey.hashCode()) ^ (h >>> 16);

		return (queueSize - 1) & hash;
	}
}
