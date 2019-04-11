/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.message;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:11:57
 * @version v 0.1
 */
public class InvalidMessageParamException extends InvalidMessageException {

	private static final long serialVersionUID = 2772320844451892354L;

	public InvalidMessageParamException() {
		super();
	}

	public InvalidMessageParamException(String message) {
		super(message);
	}

	public InvalidMessageParamException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMessageParamException(Throwable cause) {
		super(cause);
	}

	protected InvalidMessageParamException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
