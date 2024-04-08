package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Map;

/**
 * Class to represent the stock data returned from the AlphaVantage API
 */
public class StockData {
    /**
     * Map of dates to prices
     */
    @SerializedName("Monthly Time Series")
    private Map<LocalDate, MonthlyTimeSeries> monthlyTimeSeries;

    public Map<LocalDate, MonthlyTimeSeries> getMonthlyTimeSeries() {
        return monthlyTimeSeries;
    }

    public void setMonthlyTimeSeries(Map<LocalDate, MonthlyTimeSeries> monthlyTimeSeries) {
        this.monthlyTimeSeries = monthlyTimeSeries;
    }

    public static class MonthlyTimeSeries {
        @SerializedName("1. open")
        private String open;

        @SerializedName("2. high")
        private String high;

        @SerializedName("3. low")
        private String low;

        @SerializedName("4. close")
        private String close;

        @SerializedName("5. volume")
        private String volume;

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getClose() {
            return close;
        }

        public void setClose(String close) {
            this.close = close;
        }

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

    }
}