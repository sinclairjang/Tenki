package com.zerobase.tenki.exception;

public class InvalidDateException extends RuntimeException {
	private static final long serialVersionUID = -8729805735680597354L;
	
	private static final ErrorCode errorCode = ErrorCode.INVALID_DATE;

	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMessage() {
		return errorCode.getErrorDescription();
	}
}
