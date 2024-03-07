import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;
import org.farmingdale.stockdiviner.model.financialmodeling.ImplFinancialModelingAPI;
import org.junit.Test;

import java.io.IOException;
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
