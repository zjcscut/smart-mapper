package org.throwable.mapper.exception;

/**
 * @author throwable
 * @version v1.0
 * @since 2017/3/30 12:44
 * @description 不支持操作类型异常
 */
public class UnsupportedOpertationException extends RuntimeException {

	public UnsupportedOpertationException(String message) {
		super(message);
	}

	public UnsupportedOpertationException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsupportedOpertationException(Throwable cause) {
		super(cause);
	}
}
