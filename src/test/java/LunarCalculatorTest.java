import org.farmingdale.stockdiviner.model.lunar.ImplLunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarPhase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;


public class LunarCalculatorTest {
    @Test
    public void testGetLunarPhase() throws IOException {
        ImplLunarCalculatorAPI api = new ImplLunarCalculatorAPI();
        //These are known dates for the lunar phases in 2021
        LunarPhase fullMoon = api.getLunarPhase(LocalDate.of(2021, 1, 28));
        LunarPhase newMoon = api.getLunarPhase(LocalDate.of(2021, 1, 13));
        LunarPhase firstQuarter = api.getLunarPhase(LocalDate.of(2021, 2, 19));
        LunarPhase lastQuarter = api.getLunarPhase(LocalDate.of(2021, 2, 4));
        System.out.println(fullMoon);
        System.out.println(newMoon);
        System.out.println(firstQuarter);
        System.out.println(lastQuarter);

        assert fullMoon.getPhase().equals("Full Moon");
        assert newMoon.getPhase().equals("New Moon");
        assert firstQuarter.getPhase().equals("First Quarter");
        assert lastQuarter.getPhase().equals("Last Quarter");
    }

}
