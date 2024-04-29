import com.google.firebase.auth.UserRecord;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;
import org.farmingdale.stockdiviner.model.firebase.FirebaseAuthentication;
import org.farmingdale.stockdiviner.model.firebase.FirebaseWatchlist;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FirebaseTest {

    @Test
    public void testCreateUser(){
        FirebaseAuthentication auth = FirebaseAuthentication.getInstance();
        UserRecord user = auth.createUser("df@tt.com", "df", "password");
        assert user.getEmail().equals("df@tt.com");
        System.out.println(user.getDisplayName());

        UserRecord duplicateUser = auth.createUser("df@tt.com", "df", "password");
        assert duplicateUser == null;

        UserRecord duplicateUsername = auth.createUser("df@s.com", "df", "password2");
        assert duplicateUsername == null;

        UserRecord duplicateEmail = auth.createUser("df@tt.com", "df2", "password3");
        assert duplicateEmail == null;
    }

    @Test
    public void testAuthenticateUser() {
        FirebaseAuthentication auth = FirebaseAuthentication.getInstance();
        UserRecord user = auth.authenticateUser("df", "password");
        assert user.getEmail().equals("df@tt.com");
        System.out.println(user.getDisplayName());

        UserRecord falseUser = auth.authenticateUser("df@tt.com", "wrong");
        assert falseUser == null;

        UserRecord falseUser2 = auth.authenticateUser("wrong", "password");
        assert falseUser2 == null;
    }

    @Test
    public void testAddToWatchlist() {
        FirebaseWatchlist watchlist = FirebaseWatchlist.getInstance();
        watchlist.addToWatchlist("harveytseng@gmail.com", "TSLA", "Apple Inc.");
    }

    @Test
    public void testGetWatchList(){
        FirebaseWatchlist watchlist = FirebaseWatchlist.getInstance();
        try {
            List<FullQuoteData> list = watchlist.getWatchlist("harveytseng@gmail.com");
            for (FullQuoteData data : list) {
                System.out.println(data.getName());
                System.out.println(data.getChange());
                System.out.println(data.getChangesPercentage());
                System.out.println(data.getPreviousClose());
                System.out.println(data.getAvgVolume());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}