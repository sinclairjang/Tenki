package com.zerobase.tenki.util;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import com.zerobase.tenki.exception.DiaryInUseException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LockUtil {
	private final RedissonClient redissonClient;
	
	public void lock(String key) throws Exception {
		RLock lock = redissonClient.getLock(getLockKey(key));
		
		boolean isLock = lock.tryLock(1, 15, TimeUnit.SECONDS);
		if (!isLock) {
			throw new DiaryInUseException();
		} 
			
	}
	
	public void unlock(String key) {
		redissonClient.getLock(getLockKey(key)).unlock();
	}

	private String getLockKey(String key) {
		return "Lock-key: " + key;
	}
}
