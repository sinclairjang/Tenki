package com.zerobase.tenki.exception;

public enum ErrorCode {
	INVALID_DATE("허용되지 않은 날짜입니다."),
	INTERNAL_SERVER_ERROR("내부 서버 에러가 발생했습니다."), 
	TRANSACTION_IN_USE("다른 트랜젝션이 진행 중입니다.");
	
	private final String errorDescription;
	
	private ErrorCode(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorDescription() {
		return errorDescription;
	}
	
	public String toString() {
		return this.name() + ": " + this.errorDescription;
	}
}
