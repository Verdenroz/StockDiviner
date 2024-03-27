package org.farmingdale.stockdiviner.model.financialmodeling;

import java.util.List;

public class EOD {
    private List<HistoricalData> historical;

    public List<HistoricalData> getHistorical() {
        return historical;
    }

    public void setHistorical(List<HistoricalData> historical) {
        this.historical = historical;
    }

    public double getClose() {
        if (historical != null && !historical.isEmpty()) {
            return historical.getFirst().getClose();
        }
        return 0.0;
    }

}
