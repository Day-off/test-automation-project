package integration_test.file_tests;

import org.app.FullWeatherReport;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FullWeatherReportTests {


    @BeforeEach
    void initializeCurrentWeatherHandler() throws Exception {
        FullWeatherReport fullWeatherReport = new FullWeatherReport();
        fullWeatherReport.getReportFromFileToFile("input.txt");
    }

    @Test
    void inputFileNotExist() {
        try {
            new FullWeatherReport().readFromFile("src/main/resources/input/Tallinn_output.txt");
        } catch (Exception e) {
            assertThat(e)
                    .isInstanceOf(IOException.class);
        }
    }

    @Test
    void reportShouldCreateNewOutPutFile() {
        File result = new File("src/main/resources/output/Tallinn_output.json");
        assertTrue(result.exists());
    }


    @Test
    void reportShouldBeJsonFile() {
        File result = new File("src/main/resources/output/Tallinn_output.json");
        String fileName = result.getName();
        int dotIndex = fileName.lastIndexOf('.');
        String type = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        assertThat(type).isEqualTo("json");
    }

    @Test
    void reportShouldNotBeEmpty() {
        File result = new File("src/main/resources/output/Tallinn_output.json");
        assertThat(result).isNotEmpty();
    }

    @AfterEach
    void deleteGeneratedFiles() {
        File result = new File("src/main/resources/output/Tallinn_output.json");
        result.delete();
    }

}
