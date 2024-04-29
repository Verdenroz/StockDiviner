import org.farmingdale.stockdiviner.model.analysis.*;
import org.junit.jupiter.api.Test;


public class StockAnalysisTest {
    @Test
    public void testLunarAnalysis() {
        try {
            LunarAnalysis lunarAnalysis = new LunarAnalysis("META");
            System.out.println("LunarAnalysis: " + lunarAnalysis.getBestIndicator() + " " + lunarAnalysis.getBestStat());
            System.out.println("LunarAnalysis: " + lunarAnalysis.getWorstIndicator() + " " + lunarAnalysis.getWorstStat());
            System.out.println("LunarAnalysis: " + lunarAnalysis.getAnalyses());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAnimalAnalysis() {
        try {
            AnimalAnalysis animalAnalysis = new AnimalAnalysis("META");
            System.out.println("AnimalAnalysis: " + animalAnalysis.getBestIndicator() + " " + animalAnalysis.getBestStat());
            System.out.println("AnimalAnalysis: " + animalAnalysis.getWorstIndicator() + " " + animalAnalysis.getWorstStat());
            System.out.println("AnimalAnalysis: " + animalAnalysis.getAnalyses());
            System.out.println("AnimalAnalysis: " + animalAnalysis.getAnimalDates());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testZodiacAnalysis() {
        try {
            ZodiacAnalysis zodiacAnalysis = new ZodiacAnalysis("META");
            System.out.println("ZodiacAnalysis: " + zodiacAnalysis.getBestIndicator() + " " + zodiacAnalysis.getBestStat());
            System.out.println("ZodiacAnalysis: " + zodiacAnalysis.getWorstIndicator() + " " + zodiacAnalysis.getWorstStat());
            System.out.println("ZodiacAnalysis: " + zodiacAnalysis.getAnalyses());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
