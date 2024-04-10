import org.farmingdale.stockdiviner.model.alphavantage.AlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.MonthlyStockData;
import org.farmingdale.stockdiviner.model.alphavantage.WeeklyStockData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
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
        AlphaVantageAPI api = ImplAlphaVantageAPI.getInstance();
        String symbol = "META"; // Use a valid symbol for testing

        // Act
        MonthlyStockData result = null;
        try {
            result = api.getMonthlyTimeSeries(symbol);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException was thrown");
        }
        Map<LocalDate, MonthlyStockData.MonthlyTimeSeries> timeSeries = result.getMonthlyTimeSeries();

        for (Map.Entry<LocalDate, MonthlyStockData.MonthlyTimeSeries> entry : timeSeries.entrySet()) {
            LocalDate date = entry.getKey();
            String closingPrice = entry.getValue().getClose();

            System.out.println("Date: " + date + ", Closing Price: " + closingPrice);
        }
        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getMonthlyTimeSeries(), "Monthly time series should not be null");
        assertFalse(result.getMonthlyTimeSeries().isEmpty(), "Monthly time series should not be empty");
    }

    @Test
    void getWeeklyTimeSeries() {
        // Arrange
        AlphaVantageAPI api = ImplAlphaVantageAPI.getInstance();
        String symbol = "META"; // Use a valid symbol for testing

        // Act
        WeeklyStockData result = null;
        try {
            result = api.getWeeklyTimeSeries(symbol);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException was thrown");
        }
        Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> timeSeries = result.getWeeklyTimeSeries();

        for (Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries> entry : timeSeries.entrySet()) {
            LocalDate date = entry.getKey();
            String closingPrice = entry.getValue().getClose();

            System.out.println("Date: " + date + ", Closing Price: " + closingPrice);
        }
        // Assert
        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getWeeklyTimeSeries(), "Weekly time series should not be null");
        assertFalse(result.getWeeklyTimeSeries().isEmpty(), "Weekly time series should not be empty");
    }
}