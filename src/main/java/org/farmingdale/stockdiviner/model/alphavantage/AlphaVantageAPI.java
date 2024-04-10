package org.farmingdale.stockdiviner.model.alphavantage;

import java.io.IOException;

/**
 * Interface for the AlphaVantageAPI
 * @see <a href="https://www.alphavantage.co/documentation/">AlphaVantage API Documentation</a>
 */
public interface AlphaVantageAPI {
    /**
     * Get the monthly time series for a given stock symbol
     * @param symbol symbol of the stock
     * @return MonthlyStockData object containing dates with associated prices
     * @throws IOException when there is an error with the request
     */
    MonthlyStockData getMonthlyTimeSeries(String symbol) throws IOException;

    /**
     * Get the weekly time series for a given stock symbol
     * @param symbol symbol of the stock
     * @return WeeklyStockData object containing dates with associated prices
     * @throws IOException when there is an error with the request
     */
    WeeklyStockData getWeeklyTimeSeries(String symbol) throws IOException;
}
