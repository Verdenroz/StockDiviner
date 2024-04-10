import org.farmingdale.stockdiviner.model.lunar.ImplLunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarPhase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;


public class LunarCalculatorTest {
    @Test
    public void testGetLunarPhase() throws IOException {
        LunarCalculatorAPI api = ImplLunarCalculatorAPI.getInstance();

        Map<LocalDate, LunarPhase> lunarPhaseMap = api.getLunarPhase(LocalDate.now().minusYears(2));

        for (Map.Entry<LocalDate, LunarPhase> entry : lunarPhaseMap.entrySet()) {
            System.out.println("Date: " + entry.getKey() + " Phase: " + entry.getValue());
        }
    }

}
