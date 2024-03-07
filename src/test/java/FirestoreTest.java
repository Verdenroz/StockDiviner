import org.farmingdale.stockdiviner.model.Exchanges;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.ImplFinancialModelingAPI;
import org.farmingdale.stockdiviner.model.firebase.FirebaseFirestore;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

public class FirestoreTest {
    private FirebaseFirestore db;

    @Before
    public void setUp() {
        FinancialModelingAPI financialModelingAPI = new ImplFinancialModelingAPI();
        db = new FirebaseFirestore(financialModelingAPI);
    }

    @Test
    public void testStoreStockData() throws ExecutionException, InterruptedException {
        db.storeStockNames(Exchanges.AMEX);
    }

    @Test
    public void test(){
        System.out.println(db.getStock("BE"));
    }
}