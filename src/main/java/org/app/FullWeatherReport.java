package org.app;

import lombok.extern.java.Log;
import org.codehaus.jettison.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
            FileWriter file = new FileWriter("src/main/resources/output/" + city + "_" + "output.json");
            file.write(output.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getReportFromFileToFile(String file) throws Exception {
        isValidFile(!getFileExtension(file).equals("txt"), "UNSUPPORTED FILE TYPE! ACCEPTED ONLY .TXT .\n");
        List<String> cities = readFromFile(file);
        for (String city : cities) {
            JSONObject output = getFullWeatherReport(city);
            writeToFile(output, city);
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

}