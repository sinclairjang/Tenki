package com.zerobase.tenki.domain;

import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "weather_history")
@NoArgsConstructor
public class WeatherHistory {
	@Id
	private LocalDate date;
	private String weather;
	private String icon;
	private double temperature;
}
