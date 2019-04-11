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
 * @since 2017年5月14日 上午11:12:47
 * @version v 0.1
 */
public class InvalidMessageCommandException extends InvalidMessageException {

	private static final long serialVersionUID = 2772320844451892354L;

	public InvalidMessageCommandException() {
		super();
	}

	public InvalidMessageCommandException(String message) {
		super(message);
	}

	public InvalidMessageCommandException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMessageCommandException(Throwable cause) {
		super(cause);
	}

	protected InvalidMessageCommandException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
