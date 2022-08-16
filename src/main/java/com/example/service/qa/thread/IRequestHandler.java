package com.example.service.qa.thread;

/**
 * @ClassName: IRequestHandler
 * @Description: 封装请求
 * @author yangpengbing
 * @date 2020-05-27-10:51
 * @version V1.0.0
 *
 */
public interface IRequestHandler {

	/**
	 * 处理请求
	 */
	void process();

	/**
	 * 路由的key
	 * @return
	 */
	String routingKey();

	/**
	 * 清除资源
	 */
	default void clearResource() {}
}
