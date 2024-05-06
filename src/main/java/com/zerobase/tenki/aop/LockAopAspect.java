package com.zerobase.tenki.aop;

import java.time.LocalDate;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.zerobase.tenki.exception.DiaryInUseException;
import com.zerobase.tenki.util.LockUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LockAopAspect {
	private final LockUtil lockUtil;
	
	@Around("@annotation(com.zerobase.tenki.annotation.DiaryLock) && args(date, ..)")
	public void aroundMethod(ProceedingJoinPoint pjp, LocalDate date) throws Throwable {
		try {
			log.info("try locking {}", date);
			lockUtil.lock(date.toString());
			pjp.proceed();
			log.info("releasing lock {}", date);
			lockUtil.unlock(date.toString());
		} catch (DiaryInUseException e) {
			log.error("failed to obtain lock {}", date);
			throw e;
		} catch (Exception e) {
			log.error("failed to obtaimn lock {}", date);
			throw e;
		}
	}
}
