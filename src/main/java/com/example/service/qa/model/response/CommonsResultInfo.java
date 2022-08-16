package com.example.service.qa.model.response;

import com.example.service.qa.model.ResultInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @ClassName: CommonsServiceResultInfo
 * @Description: service公共的返回值
 * @author sulei
 * @date 2020-04-10-15:37
 * @version V1.0.0
 *
 */
public class CommonsResultInfo<T> extends ResultInfo implements Serializable {


	private static final long serialVersionUID = 3546770907818804925L;

	/**
	 * 返回公共对象的数据
	 */
	@Setter
	@Getter
	@Accessors(chain = true)
	private T data;

	protected CommonsResultInfo<T> data(T data) {
		setData(data);
		return this;
	}


}
