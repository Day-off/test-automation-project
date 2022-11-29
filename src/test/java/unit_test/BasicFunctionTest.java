package unit_test;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BasicFunctionTest {

    private static String result;

    @Test
    void enterCity() throws JSONException {
        Main app = new Main();
        String city = "Tallinn";
        System.setIn(new ByteArrayInputStream(city.getBytes()));

        JSONObject cityData = app.getInfoAboutCity();
        result = cityData.toString();
    }

    @Test
    void outputShouldContainsMainDetails(){
        assertTrue(result.contains("mainDetails"));
    }
}
