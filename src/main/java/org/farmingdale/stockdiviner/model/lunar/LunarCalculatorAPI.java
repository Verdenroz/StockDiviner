package org.farmingdale.stockdiviner.model.lunar;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

/**
 * Interface for the LunarCalculatorAPI
 */
public interface LunarCalculatorAPI {
    /**
     * Returns the lunar phase for a given date
     */
    Map<LocalDate, LunarPhase> getLunarPhase(LocalDate date) throws IOException;
}
