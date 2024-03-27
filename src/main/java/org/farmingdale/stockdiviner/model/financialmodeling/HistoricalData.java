package org.farmingdale.stockdiviner.model.financialmodeling;

import com.google.gson.annotations.SerializedName;

public class HistoricalData {
    @SerializedName("close")
    private double close;

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }
}
