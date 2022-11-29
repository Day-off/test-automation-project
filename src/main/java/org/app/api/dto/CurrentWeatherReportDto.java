package org.app.api.dto;


import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrentWeatherReportDto {

    private String date;

    private MainDto main;
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(new Date());
    }
}
