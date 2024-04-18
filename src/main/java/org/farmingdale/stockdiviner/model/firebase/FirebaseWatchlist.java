package org.farmingdale.stockdiviner.model.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;
import org.farmingdale.stockdiviner.model.financialmodeling.ImplFinancialModelingAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Handles watchlist operations in Firestore
 */
public class FirebaseWatchlist {
    private static volatile FirebaseWatchlist instance;
    private static FirebaseFirestore db;

    private FirebaseWatchlist() {
        db = FirebaseFirestore.getInstance();
    }

    public static FirebaseWatchlist getInstance() {
        if (instance == null) {
            synchronized (FirebaseWatchlist.class) {
                if (instance == null) {
                    instance = new FirebaseWatchlist();
                }
            }
        }
        return instance;
    }

    /**
     * Get the watchlist for a user
     * @param email email of the user
     */
    public List<FullQuoteData> getWatchlist(String email) {
        FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();
        CollectionReference watchlist = db.getWatchlistCollection(email);

        List<String> symbols = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = watchlist.get().get().getDocuments();
            for (DocumentSnapshot document : documents) {
                symbols.add(document.getId());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }

        try {
            return api.getBulkQuotes(symbols.toArray(new String[0]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Add a stock to the watchlist
     * @param email email of the user
     * @param symbol stock symbol
     * @param name stock name
     */
    public void addToWatchlist(String email, String symbol, String name) {
        CollectionReference watchlist = db.getWatchlistCollection(email);
        Map<String, String> data = new HashMap<>();
        data.put("name", name);
        ApiFuture<WriteResult> writeResult = watchlist.document(symbol).set(data);

        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFromWatchlist(String email, String symbol) {
        CollectionReference watchlist = db.getWatchlistCollection(email);
        ApiFuture<WriteResult> writeResult = watchlist.document(symbol).delete();

        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInsideWatchList(String email, String symbol) {
        CollectionReference watchlist = db.getWatchlistCollection(email);
        List<String> symbols = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = watchlist.get().get().getDocuments();
            for (DocumentSnapshot document : documents) {
                symbols.add(document.getId());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
        return symbols.contains(symbol);
    }
}
