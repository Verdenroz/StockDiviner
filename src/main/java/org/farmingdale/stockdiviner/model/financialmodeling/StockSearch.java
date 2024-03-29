package org.farmingdale.stockdiviner.model.financialmodeling;

/**
 * Represents the results from searching for a stock
 */
public class StockSearch {
    private String symbol;
    private String name;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
