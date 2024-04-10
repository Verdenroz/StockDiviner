package org.farmingdale.stockdiviner.model.lunar;

public class PhaseData {
    private int day;

    private int month;

    private int year;

    private String phase;

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public int getDay() { return day;}

    public void setDay(int day) { this.day = day; }

    public int getMonth() { return month; }

    public void setMonth(int month) { this.month = month; }

    public int getYear() { return year; }

    public void setYear(int year) { this.year = year; }
}