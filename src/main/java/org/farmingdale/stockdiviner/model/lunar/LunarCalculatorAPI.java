package org.farmingdale.stockdiviner.model.lunar;

import java.io.IOException;
import java.time.LocalDate;

public interface LunarCalculatorAPI {
    LunarPhase getLunarPhase(LocalDate date) throws IOException;
}
