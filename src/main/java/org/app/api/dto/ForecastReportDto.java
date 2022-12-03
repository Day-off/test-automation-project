package org.app.api.dto;

import lombok.Data;
import org.app.api.dto.details.WeatherReport;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastReportDto {

    private WeatherReport[] list;

}
