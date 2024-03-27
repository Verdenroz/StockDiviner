import org.farmingdale.stockdiviner.model.financialmodeling.*;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FinancialModelAPITest {
    @Test
    public void getFullQuoteTest() {
        // Arrange
        FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();
        String symbol = "AAPL"; // Use a valid symbol for testing

        // Act
        FullQuoteData result = null;
        try {
            result = api.getFullQuoteData(symbol);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException was thrown");
        }

        System.out.println(result.getName());
        System.out.println(result.getChange());
        System.out.println(result.getChangesPercentage());
        System.out.println(result.getPreviousClose());
        System.out.println(result.getAvgVolume());
        // Assert
        assertNotNull(result, "The returned FullQuoteData object should not be null");
    }

    @Test
    public void searchStockTest() {
        // Arrange
        FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();
        String input = "Apple"; // Use a valid input for testing

        // Act
        List<StockSearch> result = null;
        try {
            result = api.searchStock(input);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException was thrown");
        }

        for(StockSearch data : result) {
            System.out.println(data.getName());
        }
        // Assert
        assertNotNull(result, "The returned list should not be null");
        assertFalse(result.isEmpty(), "The returned list should not be empty");
    }

    @Test
    public void getEODTest(){
        // Arrange
        FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();
        String symbol = "AAPL"; // Use a valid symbol for testing
        LocalDate date = LocalDate.of(2024, 3, 23);

        // Act
        EOD result = null;
        try {
            result = api.getEOD(symbol, date);
            //HistoricalData is a single element list with only the closing price
            System.out.println(result.getClose());
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException was thrown");
        }

        // Assert
        assertNotNull(result, "The returned list should not be null");
        assertFalse(result.getHistorical().isEmpty(), "The returned list should not be empty");
    }

    @Test
    public void getSymbolListTest() {
        // Arrange
        FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();
        String exchange = "NASDAQ"; // Use a valid exchange for testing

        // Act
        List<FullQuoteData> result = null;
        try {
            result = api.getSymbolList(exchange);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IOException was thrown");
        }

        for(FullQuoteData data : result) {
            System.out.println(data.getName());
        }
        // Assert
        assertNotNull(result, "The returned list should not be null");
        assertFalse(result.isEmpty(), "The returned list should not be empty");
    }
}
