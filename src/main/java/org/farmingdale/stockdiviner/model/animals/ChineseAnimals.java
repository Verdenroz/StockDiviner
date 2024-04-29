package org.farmingdale.stockdiviner.model.animals;

import org.farmingdale.stockdiviner.model.Indicator;

/**
 * Enum for the Chinese Zodiac animals
 * @see ChineseNewYears
 */
public enum ChineseAnimals implements Indicator {
    RAT("Rat"),
    OX("Ox"),
    TIGER("Tiger"),
    RABBIT("Rabbit"),
    DRAGON("Dragon"),
    SNAKE("Snake"),
    HORSE("Horse"),
    SHEEP("Sheep"),
    MONKEY("Monkey"),
    ROOSTER("Rooster"),
    DOG("Dog"),
    PIG("Pig");

    private final String displayName;

    ChineseAnimals(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}