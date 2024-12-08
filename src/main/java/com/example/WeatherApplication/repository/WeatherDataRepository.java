package com.example.WeatherApplication.repository;

import com.example.WeatherApplication.model.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    List<WeatherData> findByCityAndDate(String city, String date);

    List<WeatherData> findByCityAndTimestampBetween(String city, long startTimestamp, long endTimestamp);
}