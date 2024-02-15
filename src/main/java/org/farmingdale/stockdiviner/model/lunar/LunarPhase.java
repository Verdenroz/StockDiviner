package org.farmingdale.stockdiviner.model.lunar;

public enum LunarPhase {
    NEW_MOON("New Moon"),
    FIRST_QUARTER("First Quarter"),
    FULL_MOON("Full Moon"),
    LAST_QUARTER("Last Quarter");

    private final String phase;

    LunarPhase(String phase) {
        this.phase = phase;
    }

    public String getPhase() {
        return this.phase;
    }
}