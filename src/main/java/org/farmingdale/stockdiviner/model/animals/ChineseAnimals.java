package org.farmingdale.stockdiviner.model.animals;

/**
 * Enum for the Chinese Zodiac animals
 * @see ChineseNewYears
 */
public enum ChineseAnimals {
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

    public String getDisplayName() {
        return displayName;
    }
}