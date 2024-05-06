package com.zerobase.tenki.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "diary")
public class Diary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String weather;
	private String icon;
	private double temperature;
	private String text;
	private LocalDate date;
	
	public static Diary from(WeatherHistory wh, String text) {
		Diary newDiary = new Diary();
		newDiary.setWeather(wh.getWeather());
		newDiary.setIcon(wh.getIcon());
		newDiary.setTemperature(wh.getTemperature());
		newDiary.setText(text);
		newDiary.setDate(wh.getDate());
		return newDiary;
	}
}
