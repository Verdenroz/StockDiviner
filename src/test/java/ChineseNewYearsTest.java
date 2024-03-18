import org.farmingdale.stockdiviner.model.animals.ChineseAnimals;
import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChineseNewYearsTest {

    @Test
    public void getChineseZodiac() {
        // Arrange
        ChineseNewYears chineseNewYears = ChineseNewYears.getInstance();

        // Act and Assert
        assertEquals(ChineseAnimals.MONKEY, chineseNewYears.getChineseZodiac(2016), "2016 is the year of the Monkey");
        assertEquals(ChineseAnimals.ROOSTER, chineseNewYears.getChineseZodiac(2017), "2017 is the year of the Rooster");
        assertEquals(ChineseAnimals.DOG, chineseNewYears.getChineseZodiac(2018), "2018 is the year of the Dog");
        assertEquals(ChineseAnimals.PIG, chineseNewYears.getChineseZodiac(2019), "2019 is the year of the Pig");
        assertEquals(ChineseAnimals.RAT, chineseNewYears.getChineseZodiac(2020), "2020 is the year of the Rat");
        assertEquals(ChineseAnimals.OX, chineseNewYears.getChineseZodiac(2021), "2021 is the year of the Ox");
        assertEquals(ChineseAnimals.TIGER, chineseNewYears.getChineseZodiac(2022), "2022 is the year of the Tiger");
        assertEquals(ChineseAnimals.RABBIT, chineseNewYears.getChineseZodiac(2023), "2023 is the year of the Rabbit");
        assertEquals(ChineseAnimals.DRAGON, chineseNewYears.getChineseZodiac(2024), "2024 is the year of the Dragon");
        assertEquals(ChineseAnimals.SNAKE, chineseNewYears.getChineseZodiac(2025), "2025 is the year of the Snake");
        assertEquals(ChineseAnimals.HORSE, chineseNewYears.getChineseZodiac(2026), "2026 is the year of the Horse");
        assertEquals(ChineseAnimals.SHEEP, chineseNewYears.getChineseZodiac(2027), "2027 is the year of the Sheep");
    }
}