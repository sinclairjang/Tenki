package com.zerobase.tenki.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zerobase.tenki.annotation.DiaryLock;
import com.zerobase.tenki.domain.Diary;
import com.zerobase.tenki.service.DiaryService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DiaryController {
	private final DiaryService diaryService;
	
	@ApiOperation("일기 텍스트와 날씨를 함께 DB에 저장합니다.")
	@PostMapping("/create/diary")
	void createDiary(
			@RequestParam(name = "date") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			@ApiParam(value = "날짜 형식 : yyyy-MM-dd", example = "2024-05-05") 
			LocalDate date,
			
			@RequestBody String text
	) {
		diaryService.createDiary(date, text);
	}
	
	@ApiOperation("선택한 날짜의 모든 일기 데이터를 가져옵니다.")
	@GetMapping("/read/diary")
	List<Diary> readDiary(
			@RequestParam(name = "date") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			@ApiParam(value = "날짜 형식 : yyyy-MM-dd", example = "2024-05-05") 
			LocalDate date
	) {
		return diaryService.readDiary(date);
	}
	
	@ApiOperation("선택한 기간의 모든 일기 데이터를 가져옵니다.")
	@GetMapping("/read/diaries")
	List<Diary> readDiaries(
			@RequestParam(name = "startDate") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			@ApiParam(value = "조회할 기간의 첫번째 날", example = "2024-05-05")  
			LocalDate startDate,
			
			@RequestParam(name = "endDate") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			@ApiParam(value = "조회할 기간의 마지막 날", example = "2024-05-05")  
			LocalDate endDate
	) {
		return diaryService.readDiaries(startDate, endDate);
	}
	
	@ApiOperation("선택한 날짜의 첫번째 일기 데이터를 수정합니다.")
	@PutMapping("/update/diary")
	@DiaryLock
	void updateDiary(
			@RequestParam(name = "date") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			@ApiParam(value = "날짜 형식 : yyyy-MM-dd", example = "2024-05-05") 
			LocalDate date,
			
			@RequestBody String text
	) {
		diaryService.updateDiary(date, text);
	}
	
	@ApiOperation("선택한 날짜의 모든 일기 데이터를 삭제합니다.")
	@DeleteMapping("/delete/diary")
	void deleteDiary(
			@RequestParam(name = "date") 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) 
			@ApiParam(value = "날짜 형식 : yyyy-MM-dd", example = "2024-05-05")  
			LocalDate date
	) {
		diaryService.deleteDiary(date);
	}
}