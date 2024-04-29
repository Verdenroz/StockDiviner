package org.farmingdale.stockdiviner.model.analysis;

import org.farmingdale.stockdiviner.model.Indicator;
import org.farmingdale.stockdiviner.model.alphavantage.ImplAlphaVantageAPI;

import java.util.HashMap;
import java.util.Map;

public abstract class Analysis {
    protected String stockSymbol;
    protected Indicator bestIndicator;
    protected Indicator worstIndicator;
    protected double bestStat;
    protected double worstStat;
    protected Map<Indicator, Double> analyses;
    protected static ImplAlphaVantageAPI api;

    public Analysis(String stockSymbol) {
        this.stockSymbol = stockSymbol;
        this.analyses = new HashMap<>();
        api = ImplAlphaVantageAPI.getInstance();
    }

    abstract void analyze() throws Exception;

    public Indicator getBestIndicator() {
        return this.bestIndicator;
    }

    public Indicator getWorstIndicator() {
        return this.worstIndicator;
    }

    public double getBestStat() {
        return this.bestStat;
    }

    public double getWorstStat() {
        return this.worstStat;
    }

    public Map<Indicator, Double> getAnalyses() {
        return this.analyses;
    }
}
