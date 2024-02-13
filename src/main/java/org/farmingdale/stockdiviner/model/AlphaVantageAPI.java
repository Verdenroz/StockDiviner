package org.farmingdale.stockdiviner.model;

import java.io.IOException;

public interface AlphaVantageAPI {
    StockData getMonthlyTimeSeries(String symbol) throws IOException;
}
