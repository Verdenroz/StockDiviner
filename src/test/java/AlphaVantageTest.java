import org.farmingdale.stockdiviner.model.alphavantage.AlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;
import org.farmingdale.stockdiviner.model.alphavantage.MonthlyStockData;
import org.farmingdale.stockdiviner.model.alphavantage.WeeklyStockData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AlphaVantageTest {
    private final AlphaVantageAPI api = ImplAlphaVantageAPI.getInstance();
    private final String symbol = "AAPL";

    @Test
    void getMonthlyTimeSeries() {
        MonthlyStockData result = null;
        try {
            result = api.getMonthlyTimeSeries(symbol);
        } catch (IOException e) {
            System.out.println(e.getMessage());
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
        WeeklyStockData result = null;
        try {
            result = api.getWeeklyTimeSeries(symbol);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            fail("IOException was thrown");
        }
        Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> timeSeries = result.getWeeklyTimeSeries();

        for (Map.Entry<LocalDate, WeeklyStockData.WeeklyTimeSeries> entry : timeSeries.entrySet()) {
            LocalDate date = entry.getKey();
            String closingPrice = entry.getValue().getClose();

            System.out.println("Date: " + date + ", Closing Price: " + closingPrice);
        }

        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getWeeklyTimeSeries(), "Weekly time series should not be null");
        assertFalse(result.getWeeklyTimeSeries().isEmpty(), "Weekly time series should not be empty");
    }
}