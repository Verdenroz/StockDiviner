package org.farmingdale.stockdiviner.model.lunar;

import org.farmingdale.stockdiviner.model.Indicator;

public enum LunarPhase implements Indicator {
    NEW_MOON("New Moon"),
    FIRST_QUARTER("First Quarter"),
    FULL_MOON("Full Moon"),
    LAST_QUARTER("Last Quarter");

    private final String phase;

    LunarPhase(String phase) {
        this.phase = phase;
    }

    @Override
    public String getDisplayName() {
        return this.phase;
    }
}