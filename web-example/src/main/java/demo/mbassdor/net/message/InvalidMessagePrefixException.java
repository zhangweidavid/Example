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
 * @since 2017年5月14日 上午11:13:28
 * @version v 0.1
 */
public class InvalidMessagePrefixException extends InvalidMessageException {

	private static final long serialVersionUID = 2772320844451892354L;

	public InvalidMessagePrefixException() {
		super();
	}

	public InvalidMessagePrefixException(String message) {
		super(message);
	}

	public InvalidMessagePrefixException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMessagePrefixException(Throwable cause) {
		super(cause);
	}

	protected InvalidMessagePrefixException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
