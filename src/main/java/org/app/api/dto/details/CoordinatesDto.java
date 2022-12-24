package org.app.api.dto.details;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoordinatesDto {
    private double lat;
    private double lon;

    public String getLatAndLon() {
        return lat + ", " + lon;
    }
}
