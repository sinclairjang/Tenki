package com.zerobase.tenki.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zerobase.tenki.domain.WeatherHistory;

public interface WeatherHistoryRepository 
	extends JpaRepository<WeatherHistory, LocalDate> {
	
	List<WeatherHistory> findAllByDate(LocalDate date);
	List<WeatherHistory> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
	WeatherHistory getFirstByDate(LocalDate date);
	void deleteAllByDate(LocalDate date);
}
