package com.zerobase.tenki.exception;

public class DiaryInUseException extends RuntimeException {
	private static final long serialVersionUID = 403871244149865824L;
	
	private static final ErrorCode errorCode = ErrorCode.TRANSACTION_IN_USE;

	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMessage() {
		return errorCode.getErrorDescription();
	}
}
