package org.farmingdale.stockdiviner.model.zodiac;

public class ZodiacCalulator {
    public static volatile ZodiacCalulator instance;

    private ZodiacCalulator() {
    }

    public static ZodiacCalulator getInstance() {
        if (instance == null) {
            synchronized (ZodiacCalulator.class) {
                if (instance == null) {
                    instance = new ZodiacCalulator();
                }
            }
        }
        return instance;
    }

    public ZodiacSigns getZodiacSign(int month, int day) {
        // checks month and date within the
        // valid range of a specified zodiac
        if (month == 12) {
            if (day < 22)
                return ZodiacSigns.SAGITTARIUS;
            else
                return ZodiacSigns.CAPRICORN;
        } else if (month == 1) {
            if (day < 20)
                return ZodiacSigns.CAPRICORN;
            else
                return ZodiacSigns.AQUARIUS;
        } else if (month == 2) {
            if (day < 19)
                return ZodiacSigns.AQUARIUS;
            else
                return ZodiacSigns.PISCES;
        } else if (month == 3) {
            if (day < 21)
                return ZodiacSigns.PISCES;
            else
                return ZodiacSigns.ARIES;
        } else if (month == 4) {
            if (day < 20)
                return ZodiacSigns.ARIES;
            else
                return ZodiacSigns.TAURUS;
        } else if (month == 5) {
            if (day < 21)
                return ZodiacSigns.TAURUS;
            else
                return ZodiacSigns.GEMINI;
        } else if (month == 6) {
            if (day < 21)
                return ZodiacSigns.GEMINI;
            else
                return ZodiacSigns.CANCER;
        } else if (month == 7) {
            if (day < 23)
                return ZodiacSigns.CANCER;
            else
                return ZodiacSigns.LEO;
        } else if (month == 8) {
            if (day < 23)
                return ZodiacSigns.LEO;
            else
                return ZodiacSigns.VIRGO;
        } else if (month == 9) {
            if (day < 23)
                return ZodiacSigns.VIRGO;
            else
                return ZodiacSigns.LIBRA;
        } else if (month == 10) {
            if (day < 23)
                return ZodiacSigns.LIBRA;
            else
                return ZodiacSigns.SCORPIO;
        } else if (month == 11) {
            if (day < 22)
                return ZodiacSigns.SCORPIO;
            else
                return ZodiacSigns.SAGITTARIUS;
        } else {
            throw new IllegalArgumentException("Invalid date");
        }
    }
}
