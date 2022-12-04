package org.app;

import lombok.extern.java.Log;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Log
public class FullWeatherReport {

    public JSONObject getFullWeatherReport(String city) {
        Report report = new Report(city);
        log.info(report.getOutput().toString());
        return report.getOutput();
    }

    public JSONObject getReportFromStdin() {
        log.info("Enter city: ");
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
            log.info("THE FILE WILL BE OVERWRITTEN");
        }
    }

    public void getReportFromFileToFile(String file) throws Exception {
        isValidFile(!getFileExtension(file).equals("txt"), "UNSUPPORTED FILE TYPE! ACCEPTED ONLY .TXT .\n");
        List<String> cities = readFromFile(file);
        for (String city : cities) {
            JSONObject output = getFullWeatherReport(city);
            createFile(city, output);
        }
    }

    private void createFile(String city, JSONObject output) {
        if (!Objects.equals(output.toString(), "{}")) {
            writeToFile(output, city);
        } else {
            log.info("INVALID CITY NAME");
        }
    }

    private void isValidFile(boolean file, String msg) throws Exception {
        if (file) {
            log.info(msg);
            throw new Exception(msg);
        }
    }

    public String getFileExtension(String fullName) throws Exception {
        checkNotNull(fullName);
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    private void checkNotNull(String fullName) throws Exception {
        isValidFile(fullName.isEmpty(), "FILE NAME IS EMPTY!\n");
    }

    public static void main(String[] args) throws Exception {
        new FullWeatherReport().getReportFromFileToFile("input.txt");
    }
}