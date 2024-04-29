import org.farmingdale.stockdiviner.model.zodiac.ZodiacCalculator;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacSigns;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZodiacCalculatorTest {
    @Test
    public void testZodiacCalculator() {
        ZodiacCalculator zodiacCalculator = ZodiacCalculator.getInstance();

        ZodiacSigns[] signs = ZodiacSigns.values();
        int[][] dates = {
                {4, 1}, {5, 1}, {6, 1}, {7, 1}, {8, 1}, {9, 1}, {10, 1}, {11, 1}, {12, 1}, {1, 1}, {2, 1}, {3, 1}
        };

        for (int i = 0; i < signs.length; i++) {
            assertEquals(signs[i], zodiacCalculator.getZodiacSign(dates[i][0], dates[i][1]));
        }
    }
}