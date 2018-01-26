/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.net.util;

/**
 * Desc:TODO
 * 
 * @author wei.zw
 * @since 2017年5月14日 上午11:25:20
 * @version v 0.1
 */
public class InvalidPortException extends Exception {

	private static final long serialVersionUID = -4888932452832860111L;

	public InvalidPortException() {
		super();
	}

	public InvalidPortException(String message) {
		super(message);
	}

	public InvalidPortException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidPortException(Throwable cause) {
		super(cause);
	}

	protected InvalidPortException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
