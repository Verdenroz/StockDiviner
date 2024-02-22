package org.farmingdale.stockdiviner.model;

import java.io.IOException;

/**
 * Interface for the AlphaVantageAPI
 * @see <a href="https://www.alphavantage.co/documentation/">AlphaVantage API Documentation</a>
 */
public interface AlphaVantageAPI {
    /**
     * Get the monthly time series for a given stock symbol
     * @param symbol symbol of the stock
     * @return StockData object containing dates with associated prices
     * @throws IOException when there is an error with the request
     */
    StockData getMonthlyTimeSeries(String symbol) throws IOException;
}
