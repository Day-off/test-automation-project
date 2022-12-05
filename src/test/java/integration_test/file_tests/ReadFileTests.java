package integration_test.file_tests;

import org.app.FullWeatherReport;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ReadFileTests {

    private final String RIGHT_NAME = "input.txt";

    @Test
    void canReadProperNameFile() {
        List<String> cities = new FullWeatherReport().readFromFile(RIGHT_NAME);
        assertFalse(cities.isEmpty());
    }

    @Test
    void readerReturnsListWithProperCities() {
        List<String> cities = new FullWeatherReport().readFromFile(RIGHT_NAME);
        assertThat(cities.get(0)).isEqualTo("Tallinn");
    }

}
