# Project description

Create an application which reads an input file of city names and produces and output file with weather information about those cities:
- Main details:
    - City name
    - Coordinates in the format latitude, longitude: "59.44, 24.75"
    - Temperature unit
- Current weather report:
    - weather report date (DD-MM-YYYY)
    - temperature in Celsius
    - humidity
    - pressure
- Following 3-day forecast (not counting current date) in ascending order:
    - forecast date (DD-MM-YYYY)
    - average temperature in Celsius
    - average humidity
    - average pressure

Use the [OpenWeatherMap API](https://openweathermap.org/api):
- [current weather data](https://openweathermap.org/current)
- [5-day weather forecast](https://openweathermap.org/forecast5)

## Technical Part

### What stack is used:

1. Java 17
2. Gradle

### Installation, compilation and building in the command line:

- Download project

#### By IntelliJ:

- Open project
- Build Gradle
- Run

#### By CommandLine in project:

If you have gradle: gradle

If you haven't: gradlew

##### To build project:

``` gradlew build ```

##### To run app with stdin and stdout:

``` gradlew runApp --args 1 ```

After this log: `[main] INFO org.app.FullWeatherReport - Enter city:`\
Enter a city, example: Tallinn

Run with default cityName:

``` gradlew runApp --args 3 ```

##### To run app read from file and write to file:

``` gradlew runApp --args 2 ```

After this log: `[main] INFO org.app.FullWeatherReport - Enter file name:`\
Enter a file name, example: myInput.txt

Run with default file:

``` gradlew runApp --args 4 ```

##### Note:

- All inputs file must be in project in this directory: ``` src/main/resources/input/ ```
- All output generated files will be located in : ``` src/main/resources/output/ ```

### Run tests by command line:

##### Run all tests:

``` gradlew test ```

#### Run tests from specific package:

```gradlew test --tests "tests_package.*" -i```

Example:

```gradlew test --tests "unit_test.*" -i```

#### Run tests from specific class:

```gradlew test --tests "tests_package.testClass.*" -i```


Example:

```gradlew test --tests "unit_test.BasicFunctionTest.*" -i```


#### Run single test:

```gradlew test --tests "tests_package.testClass.testMethod" -i```


Example:

```gradlew test --tests "unit_test.BasicFunctionTest.outputShouldContainsCoordinates" -i```
