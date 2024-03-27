package org.farmingdale.stockdiviner.model.financialmodeling;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface for the FinancialModelingAPI
 * @see <a href="https://site.financialmodelingprep.com/developer/docs">FinancialModelingPrep API Documentation</a>
 */
public interface FinancialModelingAPI {
    /**
     * Get the full quote data for a given stock symbol
     * @param symbol the symbol of the stock
     * @return FullQuoteData object containing all the data for the stock
     */
    FullQuoteData getFullQuoteData(String symbol) throws IOException;

    /**
     * Search for a stock by name or symbol
     * @param input the search input from the user
     * @return List of StockSearch objects containing the search results
     */
    List<StockSearch> searchStock(String input) throws IOException;

    /**
     * Get the end of day price for a given stock symbol on a given date.
     * If the date is a weekend, it will automatically adjust to the next weekday
     * @param symbol the symbol of the stock
     * @param date the date to get the end of day price for or nearest weekday if a weekend
     * @return EOD object containing the historical data as a list for the stock on the given date
     */
    EOD getEOD(String symbol, LocalDate date) throws IOException;

    /**
     * Get the full quote data for a list of stock symbols in a given exchange
     * @param exchange the exchange to get the list of symbols from
     * @return List of FullQuoteData objects containing all the data for the stocks
     */
    List<FullQuoteData> getSymbolList(String exchange) throws IOException;
}
