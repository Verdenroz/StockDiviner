package org.farmingdale.stockdiviner.model;

/**
 * Represents an indicator that can be used to analyze a stock
 * Currently includes LunarPhase, ZodiacSign, and ChineseAnimals
 */
public interface Indicator {
    /**
     * Returns the display name of the indicator
     */
    String getDisplayName();
}
