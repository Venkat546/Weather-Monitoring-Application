package com.example.WeatherApplication.service;

import com.example.WeatherApplication.exception.CityNotFoundException;
import com.example.WeatherApplication.model.WeatherData;
import com.example.WeatherApplication.repository.WeatherDataRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WeatherService {

    @Value("${weather.api.base-url}")
    private String baseUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private final List<String> cityList;
    private final WeatherDataRepository weatherDataRepository;

    public WeatherService(
            @Value("${weather.api.cities}") String cities,
            WeatherDataRepository weatherDataRepository
    ) {
        this.cityList = Arrays.asList(cities.split(","));
        this.weatherDataRepository = weatherDataRepository;
    }

    public WeatherData getWeatherData(String city) {
        if (city == null || city.isEmpty()) {
            throw new CityNotFoundException("City name is required");
        }

        try {
            String url = String.format("%s?q=%s&appid=%s", baseUrl, city, apiKey);
            String response = restTemplate.getForObject(url, String.class);

            if (response == null) {
                throw new CityNotFoundException("City not found: " + city);
            }

            JSONObject json = new JSONObject(response);

            if (json.has("cod") && json.getInt("cod") == 404) {
                throw new CityNotFoundException("City not found: " + city);
            }

            JSONObject main = json.getJSONObject("main");

            WeatherData data = new WeatherData();
            data.setCity(city);
            data.setMain(json.getJSONArray("weather").getJSONObject(0).getString("main"));
            data.setTemp(main.getDouble("temp") - 273.15);
            data.setFeelsLike(main.getDouble("feels_like") - 273.15);
            data.setTimestamp(json.getLong("dt"));
            data.setDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(data.getTimestamp() * 1000L)));

            if (data.getTemp() > 35.0) {
                data.setTemperatureAlert("High temperature alert: " + data.getTemp() + "°C");
            } else if (data.getTemp() < 15.0) {
                data.setTemperatureAlert("Low temperature alert: " + data.getTemp() + "°C");
            } else {
                data.setTemperatureAlert("Moderate temperature");
            }

            if (data.getMain().equalsIgnoreCase("Rain")) {
                data.setWeatherConditionAlert("Rain alert for " + city);
            } else if (data.getMain().equalsIgnoreCase("Clear")) {
                data.setWeatherConditionAlert("Clear skies in " + city);
            } else if (data.getMain().equalsIgnoreCase("Haze")) {
                data.setWeatherConditionAlert("Haze alert for " + city);
            } else {
                data.setWeatherConditionAlert("Moderate");
            }

            return data;

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                throw new CityNotFoundException("City not found: " + city);
            } else {
                throw new RuntimeException("Error fetching weather data for city: " + city, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error fetching weather data for city: " + city, e);
        }
    }

    public void calculateDailySummary(String city, String date) {
        List<WeatherData> dailyData = weatherDataRepository.findByCityAndDate(city, date);

        if (dailyData.isEmpty()) {
            return;
        }

        double totalTemp = 0;
        double maxTemp = Double.MIN_VALUE;
        double minTemp = Double.MAX_VALUE;
        Map<String, Integer> weatherConditionCount = new HashMap<>();

        for (WeatherData data : dailyData) {
            totalTemp += data.getTemp();
            maxTemp = Math.max(maxTemp, data.getTemp());
            minTemp = Math.min(minTemp, data.getTemp());

            weatherConditionCount.put(data.getMain(), weatherConditionCount.getOrDefault(data.getMain(), 0) + 1);
        }

        double averageTemp = totalTemp / dailyData.size();
        String dominantCondition = weatherConditionCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Clear");

        System.out.println("Daily Summary for " + city + " on " + date);
        System.out.println("Average Temp: " + averageTemp + "°C");
        System.out.println("Max Temp: " + maxTemp + "°C");
        System.out.println("Min Temp: " + minTemp + "°C");
        System.out.println("Dominant Weather: " + dominantCondition);
    }

    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void fetchWeatherUpdates() {
        for (String city : cityList) {
            try {
                WeatherData data = getWeatherData(city);
                weatherDataRepository.save(data);

                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date(data.getTimestamp() * 1000L));
                calculateDailySummary(city, date);

                System.out.println("Saved weather data for " + city + ": " + data);

            } catch (Exception e) {
                System.err.println("Failed to process weather data for city: " + city);
                e.printStackTrace();
            }
        }
    }
}
