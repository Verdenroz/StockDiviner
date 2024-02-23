package org.farmingdale.stockdiviner.model.animals;

/**
 * Class whose only purpose is to return a chinese new year animal given a year
 */
public class ChineseNewYears {
    public ChineseAnimals getChineseZodiac(int year) {
        int zodiac = year % 12;
        return switch (zodiac) {
            case 0 -> ChineseAnimals.MONKEY;
            case 1 -> ChineseAnimals.ROOSTER;
            case 2 -> ChineseAnimals.DOG;
            case 3 -> ChineseAnimals.PIG;
            case 4 -> ChineseAnimals.RAT;
            case 5 -> ChineseAnimals.OX;
            case 6 -> ChineseAnimals.TIGER;
            case 7 -> ChineseAnimals.RABBIT;
            case 8 -> ChineseAnimals.DRAGON;
            case 9 -> ChineseAnimals.SNAKE;
            case 10 -> ChineseAnimals.HORSE;
            case 11 -> ChineseAnimals.SHEEP;
            default -> null;
        };
    }
}