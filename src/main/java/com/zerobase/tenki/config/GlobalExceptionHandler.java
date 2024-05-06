package com.zerobase.tenki.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.zerobase.tenki.TenkiApplication;
import com.zerobase.tenki.exception.DiaryInUseException;
import com.zerobase.tenki.exception.ErrorCode;
import com.zerobase.tenki.exception.ErrorResponseDto;
import com.zerobase.tenki.exception.InvalidDateException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(TenkiApplication.class);
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidDateException.class)
	public ErrorResponseDto handleInvalidDateException(InvalidDateException e) {
		logger.error(e.getErrorCode().toString());
		return ErrorResponseDto.builder()
				.errorCode(e.getErrorCode())
				.errorMessage(e.getErrorMessage())
				.build();
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DiaryInUseException.class)
	public ErrorResponseDto handleDiaryInUseException(DiaryInUseException e) {
		logger.error(e.getErrorCode().toString());
		return ErrorResponseDto.builder()
				.errorCode(e.getErrorCode())
				.errorMessage(e.getErrorMessage())
				.build();
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorResponseDto handleException(Exception e) {
		logger.error("Error: ", e);
		return ErrorResponseDto.builder()
				.errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
				.errorMessage(ErrorCode.INTERNAL_SERVER_ERROR.getErrorDescription())
				.build();
	}
}
