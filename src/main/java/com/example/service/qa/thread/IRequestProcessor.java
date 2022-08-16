package com.example.service.qa.thread;

/**
 * @author yangpengbing
 * @version V1.0.0
 * @ClassName: IRequestProcessor
 * @Description: 请求执行器接口
 * @date 2020-05-27-11:13
 */
@FunctionalInterface
public interface IRequestProcessor {

	/**
	 * 执行请求
	 */
	void process(IRequestHandler request);
}
