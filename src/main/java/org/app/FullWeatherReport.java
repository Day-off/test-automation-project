package org.app;

import lombok.extern.java.Log;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Level;

@Log
public class FullWeatherReport {

    public JSONObject getFullWeatherReport(String city) {
        Report report = new Report(city);
        return report.getOutput();
    }

    public JSONObject getReportFromStdin() {
        log.log(Level.INFO, "Enter city: ");
        Scanner scanner = new Scanner(System.in);
        String city = scanner.next();
        scanner.close();
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
            log.log(Level.INFO, "THE FILE WILL BE OVERWRITTEN");
        }
    }

    public void getReportFromFileToFile(String file) {
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
        if (!Objects.equals(output.toString(), "{}")) {
            writeToFile(output, city);
        } else {
            log.log(Level.WARNING, "INVALID CITY NAME");
        }
    }

    private boolean isValidFile(boolean file, String msg) {
        if (file) {
            log.log(Level.WARNING, msg);
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
        new FullWeatherReport().getReportFromFileToFile("input.css");
    }
}