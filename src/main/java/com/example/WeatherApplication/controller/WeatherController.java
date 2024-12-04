package com.example.WeatherApplication.controller;

import com.example.WeatherApplication.exception.CityNotFoundException;
import com.example.WeatherApplication.model.WeatherData;
import com.example.WeatherApplication.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        try {
            WeatherData weatherData = weatherService.getWeatherData(city);
            return new ResponseEntity<>(weatherData, HttpStatus.OK);
        } catch (CityNotFoundException e) {
            // Return a custom error message for CityNotFoundException
            return new ResponseEntity<>("City not found: " + city, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Return a generic error message for other exceptions
            return new ResponseEntity<>("An error occurred while retrieving weather data.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}