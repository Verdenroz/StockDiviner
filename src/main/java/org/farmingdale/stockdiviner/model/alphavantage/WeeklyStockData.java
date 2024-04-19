package org.farmingdale.stockdiviner.model.alphavantage;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Map;

public class WeeklyStockData {
    /**
     * Map of dates to prices
     */
    @SerializedName("Weekly Adjusted Time Series")
    private Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> weeklyTimeSeries;

    public Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> getWeeklyTimeSeries() {
        return weeklyTimeSeries;
    }

    public void setWeeklyTimeSeries(Map<LocalDate, WeeklyStockData.WeeklyTimeSeries> weeklyTimeSeries) {
        this.weeklyTimeSeries = weeklyTimeSeries;
    }

    public static class WeeklyTimeSeries {
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
