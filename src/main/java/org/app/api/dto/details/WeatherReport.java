package org.app.api.dto.details;


import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WeatherReport {

    @JsonProperty("dt_txt")
    private String dt;

    private MainDto main;

    public String getDt() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d = formatter2.parse(dt);
            return formatter.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
