import org.farmingdale.stockdiviner.model.analysis.StockAnalysis;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StockAnalysisTest {
    @Test
    public void testAnalysis() throws IOException {
        StockAnalysis stockAnalysis = new StockAnalysis("META");

        System.out.println("Best Animal " + stockAnalysis.getBestAnimal() + " " + stockAnalysis.getBestAnimalAvg());
        System.out.println("Worst Animal " + stockAnalysis.getWorstAnimal() + " " + stockAnalysis.getWorstAnimalAvg());
        System.out.println("Best Zodiac " + stockAnalysis.getBestZodiacSign() + " " + stockAnalysis.getBestZodiacPercent());
        System.out.println("Worst Zodiac " + stockAnalysis.getWorstZodiacSign() + " " + stockAnalysis.getWorstZodiacPercent());
        System.out.println("Best Lunar " + stockAnalysis.getBestLunarPhase() + " " + stockAnalysis.getBestLunarAvg());
        System.out.println("Worst Lunar " + stockAnalysis.getWorstLunarPhase() + " " + stockAnalysis.getWorstLunarAvg());

        assertNotNull(stockAnalysis.getBestAnimal());
        assertNotNull(stockAnalysis.getWorstAnimal());
        assertNotNull(stockAnalysis.getBestZodiacSign());
        assertNotNull(stockAnalysis.getWorstZodiacSign());
        assertNotNull(stockAnalysis.getBestLunarPhase());
        assertNotNull(stockAnalysis.getWorstLunarPhase());
    }
}
