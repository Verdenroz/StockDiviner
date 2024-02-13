import org.farmingdale.stockdiviner.model.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.StockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

public class AlphaVantageTest {

    @BeforeEach
    public void setup() {
        // Load properties and set system property
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Unable to find config.properties");
            }
            properties.load(input);
            String apiKey = properties.getProperty("alphaVantage.apiKey");
            System.setProperty("alphaVantage.apiKey", apiKey);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    void getMonthlyTimeSeries() {
        // Arrange
        ImplAlphaVantageAPI api = new ImplAlphaVantageAPI();
        String symbol = "META"; // Use a valid symbol for testing

        // Act
        StockData result = null;
        try {
            result = api.getMonthlyTimeSeries(symbol);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException was thrown");
        }
        Map<String, StockData.MonthlyTimeSeries> timeSeries = result.getMonthlyTimeSeries();

        for (Map.Entry<String, StockData.MonthlyTimeSeries> entry : timeSeries.entrySet()) {
            String date = entry.getKey();
            String closingPrice = entry.getValue().getClose();

            System.out.println("Date: " + date + ", Closing Price: " + closingPrice);
        }
        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getMonthlyTimeSeries(), "Monthly time series should not be null");
        assertFalse(result.getMonthlyTimeSeries().isEmpty(), "Monthly time series should not be empty");
    }
}