package org.app;

import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Slf4j
public class FullWeatherReport {

    public JSONObject getFullWeatherReport(String city) {
        Report report = new Report(city);
        return report.getOutput();
    }

    public JSONObject getReportFromStdin() {
        log.info("Enter city: ");
        Scanner scanner = new Scanner(System.in);
        String city = scanner.next();
        scanner.close();
        return getFullWeatherReport(city);
    }

    public JSONObject getReportFromStdin(String city) {
        log.info("Default city: " + city);
        return getFullWeatherReport(city);
    }

    public List<String> readFromFile(String fileName) {
        List<String> cities = new ArrayList<>();
        try {
            File file = new File("src/main/resources/input/" + fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                cities.add(line);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public void writeToFile(JSONObject output, String city) {
        try {
            isFileExists(city);
            FileWriter fileWriter = new FileWriter("src/main/resources/output/" + city + "_" + "output.json");
            fileWriter.write(output.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void isFileExists(String city) {
        File file = new File("src/main/resources/output/" + city + "_" + "output.json");
        if (file.exists()) {
            log.info("THE FILE WILL BE OVERWRITTEN");
        }
    }

    public void getReportFromFileToFile() {
        log.info("Enter file name: ");
        Scanner scanner = new Scanner(System.in);
        String file = scanner.next();
        scanner.close();
        List<String> cities = validateFile(file);
        for (String city : cities) {
            JSONObject output = getFullWeatherReport(city);
            createFile(city, output);
        }
    }

    public void getReportFromFileToFile(String file) {
        log.info("Default file: " + file);
        List<String> cities = validateFile(file);
        for (String city : cities) {
            JSONObject output = getFullWeatherReport(city);
            createFile(city, output);
        }
    }

    private List<String> validateFile(String file) {
        List<String> cities;
        if (isValidFile(!getFileExtension(file).equals("txt"), "UNSUPPORTED FILE TYPE! ACCEPTED ONLY .TXT .\n")) {
            cities = readFromFile(file);
        } else {
            cities = new ArrayList<>();
        }
        return cities;
    }

    private void createFile(String city, JSONObject output) {
        if (isEmpty(output)) {
            log.error("INVALID CITY NAME");
        } else {
            writeToFile(output, city);
        }
    }

    private static boolean isEmpty(JSONObject output) {
        return Objects.equals(output.toString(), "{}");
    }

    private boolean isValidFile(boolean file, String msg) {
        if (file) {
            log.error(msg);
            return false;
        } else {
            return true;
        }
    }

    public String getFileExtension(String fullName) {
        checkNotNull(fullName);
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private void checkNotNull(String fullName) {
        isValidFile(fullName.isEmpty(), "FILE NAME IS EMPTY!\n");
    }

    public static void main(String[] args) {
        for (String arg : args) {
            switch (arg) {
                case "1" -> log.info(new FullWeatherReport().getReportFromStdin().toString());
                case "2" -> new FullWeatherReport().getReportFromFileToFile();
                case "3" -> log.info(new FullWeatherReport().getReportFromStdin("Tallinn").toString());
                case "4" -> new FullWeatherReport().getReportFromFileToFile("input.txt");
            }
        }
    }

}