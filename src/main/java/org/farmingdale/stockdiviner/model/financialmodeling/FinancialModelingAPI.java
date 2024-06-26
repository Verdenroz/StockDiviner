package org.farmingdale.stockdiviner.model.financialmodeling;

import java.io.IOException;
import java.util.List;

/**
 * Interface for the FinancialModelingAPI
 * @see <a href="https://site.financialmodelingprep.com/developer/docs">FinancialModelingPrep API Documentation</a>
 */
public interface FinancialModelingAPI {
    /**
     * Get the full quote data for a given stock symbol
     * @param symbols the symbol of the stock
     * @return FullQuoteData object containing all the data for the stock
     */
    FullQuoteData getFullQuoteData(String symbols) throws IOException;

    /**
     * Get full quote data for multiple stocks
     * @param symbols the symbols of the stocks
     * @return List of FullQuoteData objects containing the data for the stocks
     */
    List<FullQuoteData> getBulkQuotes(String... symbols) throws IOException;

    /**
     * Search for a stock by name or symbol
     * @param input the search input from the user
     * @return List of StockSearch objects containing the search results
     */
    List<StockSearch> searchStock(String input) throws IOException;
}
