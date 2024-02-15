package org.farmingdale.stockdiviner.model.alphavantage;

import java.io.IOException;

public interface AlphaVantageAPI {
    MonthlyStockData getMonthlyTimeSeries(String symbol) throws IOException;
}
