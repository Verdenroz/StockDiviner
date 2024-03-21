package org.farmingdale.stockdiviner.model.zodiac;

public enum ZodiacSigns {
    ARIES("Aries"),
    TAURUS("Taurus"),
    GEMINI("Gemini"),
    CANCER("Cancer"),
    LEO("Leo"),
    VIRGO("Virgo"),
    LIBRA("Libra"),
    SCORPIO("Scorpio"),
    SAGITTARIUS("Sagittarius"),
    CAPRICORN("Capricorn"),
    AQUARIUS("Aquarius"),
    PISCES("Pisces");

    private final String displayName;

    ZodiacSigns(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
