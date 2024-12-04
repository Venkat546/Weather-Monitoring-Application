package com.example.WeatherApplication.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String main;  // Weather condition (e.g., "clear", "rain")
    private double temp;  // Temperature in Celsius
    private double feelsLike;  // Temperature feeling in Celsius
    private long timestamp;  // Time of data retrieval in Unix timestamp format
    private String date; // Added date field (YYYY-MM-DD format)

    private String temperatureAlert;  // Store temperature alert message
    private String weatherConditionAlert;  // Store weather condition alert message


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperatureAlert() {
        return temperatureAlert;
    }

    public void setTemperatureAlert(String temperatureAlert) {
        this.temperatureAlert = temperatureAlert;
    }

    public String getWeatherConditionAlert() {
        return weatherConditionAlert;
    }

    public void setWeatherConditionAlert(String weatherConditionAlert) {
        this.weatherConditionAlert = weatherConditionAlert;
    }
}
