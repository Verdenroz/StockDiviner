package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class to represent the stock data returned from the AlphaVantage API
 */
public class MonthlyStockData {
    /**
     * Map of dates to prices
     */
    @SerializedName("Monthly Adjusted Time Series")
    private Map<LocalDate, MonthlyTimeSeries> monthlyTimeSeries;

    public Map<LocalDate, MonthlyTimeSeries> getMonthlyTimeSeries() {
        return monthlyTimeSeries;
    }

    public void setMonthlyTimeSeries(Map<LocalDate, MonthlyTimeSeries> monthlyTimeSeries) {
        this.monthlyTimeSeries = monthlyTimeSeries;
    }

    public static class MonthlyTimeSeries {
        @SerializedName("5. adjusted close")
        private String close;

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }
    }
}