# Weather Monitoring Application

This is a Spring Boot application that monitors weather conditions in multiple cities, fetches weather data from the OpenWeatherMap API, stores it in an H2 database, and provides real-time alerts for temperature and weather conditions. The application calculates daily summaries of weather data and alerts users about extreme temperatures or conditions such as rain or haze.

## Features
- Fetches real-time weather data for multiple cities.
- Provides detailed weather data (temperature, weather condition, etc.).
- Alerts for high and low temperatures, and extreme weather conditions.
- Scheduled fetching of weather data every 5 minutes.
- Daily weather summaries for each city.
- Data persistence with H2 database.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- H2 In-memory Database
- OpenWeatherMap API
- RESTful API
- JSON Processing (using `org.json.JSONObject`)

## Installation

### Prerequisites
- Java 23
- Maven
- Spring Boot

### Setup

1. **Clone the repository**:
    ```bash
    git clone https://github.com/Venkat546/Weather-Monitoring-Application.git
    cd Weather-Monitoring-Application
    ```

2. **Configure your environment**:
    - Make sure to replace the API key in the `application.properties` file.
    - You can add your cities to the `weather.api.cities` property in `application.properties` as a comma-separated list.

    ```properties
    weather.api.base-url=https://api.openweathermap.org/data/2.5/weather
    weather.api.key=1baffee776ffba73a46ec877ce9fef94
    weather.api.cities=Delhi,Mumbai,Chennai,Bangalore,Kolkata,Hyderabad
    ```

3. **Run the application locally**:

To run the Spring Boot application, use the following command:

mvn spring-boot:run
The application will start on port 8080 by default.

## Dockerization
1. **Build the Docker image**:

Ensure Docker is installed and running on your system. Use the following command to build the Docker image:

```docker build -t weather-monitoring-app .```


2. **Run the Docker container**:

Start a container from the built image:

```docker run -d -p 8080:8080 --name weather-app weather-monitoring-app```

The application will now be accessible at http://localhost:8080.

3. Using a Persistent Database (Optional):

By default, the application uses an in-memory H2 database. To persist data, integrate an external database like MySQL:  

- Update the application.properties file with the MySQL configuration.
- Include MySQL as a service in a docker-compose.yml file for multi-container setup.

## API Endpoints

### 1. Get Current Weather Data

- **URL**: `/weather`
- **Method**: `GET`
- **Query Parameters**:
    - `city` (required): The name of the city to fetch weather data for.

- **Example Request**:
    ```
    GET /weather?city=Delhi
    ```

- **Response**:
    Returns the weather data for the requested city, including:
    - Temperature in Celsius.
    - Weather conditions (e.g., Clear, Rain).
    - Temperature alerts (high/low).
    - Weather condition alerts (Rain, Clear, Haze).

### 2. Fetch Weather Updates (Scheduled Task)
This operation runs every 5 minutes to fetch weather data for the configured cities and store it in the database.

## Configuration

### application.properties
This file contains the necessary configuration for the application.

- `weather.api.base-url`: Base URL of the OpenWeatherMap API.
- `weather.api.key`: Your API key from OpenWeatherMap.
- `weather.api.cities`: Comma-separated list of cities to monitor.
- Database configuration for H2 in-memory database.

```properties
weather.api.base-url=https://api.openweathermap.org/data/2.5/weather
weather.api.key=1baffee776ffba73a46ec877ce9fef94
weather.api.cities=Delhi,Mumbai,Chennai,Bangalore,Kolkata,Hyderabad
