package com.zerobase.tenki.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.zerobase.tenki.TenkiApplication;
import com.zerobase.tenki.domain.Diary;
import com.zerobase.tenki.domain.WeatherHistory;
import com.zerobase.tenki.exception.InvalidDateException;
import com.zerobase.tenki.repository.DiaryRepository;
import com.zerobase.tenki.repository.WeatherHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {
	private final DiaryRepository diaryRepository;
	private final WeatherHistoryRepository weatherHistoryRepository;
	private static final Logger logger = LoggerFactory.getLogger(TenkiApplication.class);
	
	@Value("${openweathermap.key}")
	private String apiKey;
	
	@Transactional(readOnly = false)
	public void createDiary(LocalDate date, String text) {
		diaryRepository.save(Diary.from(getWeatherData(date), text));
	}

	private WeatherHistory getWeatherData(LocalDate date) {
		List<WeatherHistory> weatherList = weatherHistoryRepository.findAllByDate(date);
		if (weatherList.size() > 0) {
			return weatherList.get(0);
		}
		return getWeatherFromApi(date);
	}

	
	private String sendRequestForWeatherData() {
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=seoul&appid=" + apiKey;
		
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}
			
			String inputLine;
			StringBuilder response = new StringBuilder();
			while((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			br.close();
			
			return response.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return "failed to get weather data.";
		} catch (IOException e) {
			e.printStackTrace();
			return "failed to get weather data.";
		}
	}
	
	private Map<String, Object> parseWeatherData(String jsonString) {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject;
		
		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		Map<String, Object> resultMap = new HashMap<>();
		
		JSONObject mainData = (JSONObject) jsonObject.get("main");
		resultMap.put("temp", mainData.get("temp"));
		JSONArray _weatherData = (JSONArray) jsonObject.get("weather");
		JSONObject weatherData = (JSONObject) _weatherData.get(0);
		resultMap.put("main", weatherData.get("main"));
		resultMap.put("icon", weatherData.get("icon"));
		return resultMap;
		
	}

	public List<Diary> readDiary(LocalDate date) {
		if (date.isAfter(LocalDate.now())) {
			 throw new InvalidDateException();
		}
		return diaryRepository.findAllByDate(date);
	}

	public List<Diary> readDiaries(LocalDate startDate, LocalDate endDate) {
		return diaryRepository.findAllByDateBetween(startDate, endDate);
	}

	@Transactional(readOnly = false)
	public void updateDiary(LocalDate date, String text) {
		Diary nowDiary = diaryRepository.getFirstByDate(date);
		nowDiary.setText(text);
		diaryRepository.save(nowDiary);
	}

	@Transactional(readOnly = false)
	public void deleteDiary(LocalDate date) {
		diaryRepository.deleteAllByDate(date);
		
	}
	
	@Scheduled(cron = "0 0 1 * * *")
	@Transactional(readOnly = false, isolation = Isolation.REPEATABLE_READ)
	public void saveWeatherHistory() {
		logger.info("logging weather history");
		weatherHistoryRepository.save(makeWeatherHistory());
	}
	
	private WeatherHistory makeWeatherHistory() {
		return getWeatherFromApi(LocalDate.now());
	}
	
	private WeatherHistory getWeatherFromApi(LocalDate date) {
		String weatherData = sendRequestForWeatherData();
		Map<String, Object> parsedWeatherData = parseWeatherData(weatherData);
		
		WeatherHistory wh = new WeatherHistory();
		wh.setWeather(parsedWeatherData.get("main").toString());
		wh.setIcon(parsedWeatherData.get("icon").toString());
		wh.setTemperature((Double) parsedWeatherData.get("temp"));
		wh.setDate(date);
		
		return wh;
	}
}
