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
 * @since 2017年5月14日 上午11:11:23
 * @version v 0.1
 */
public class InvalidMessageException extends Exception {

	private static final long serialVersionUID = -851963659547885319L;

	public InvalidMessageException() {
		super();
	}

	public InvalidMessageException(String message) {
		super(message);
	}

	public InvalidMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMessageException(Throwable cause) {
		super(cause);
	}

	protected InvalidMessageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
