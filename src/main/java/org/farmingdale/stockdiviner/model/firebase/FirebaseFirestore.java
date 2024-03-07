package org.farmingdale.stockdiviner.model.firebase;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.farmingdale.stockdiviner.model.Exchanges;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirebaseFirestore {
    private static Firestore db = null;
    private FinancialModelingAPI financialModelingAPI;

    public FirebaseFirestore(FinancialModelingAPI financialModelingAPI) {
        db = getFirestore();
        this.financialModelingAPI = financialModelingAPI;
    }

    private static Firestore getFirestore() {

        FileInputStream serviceAccount = null;

        try {
            serviceAccount = new FileInputStream(".\\service.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        Firestore db = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return db;
    }

    private static CollectionReference getCollection(String collection) {
        return db.collection(collection);
    }

    private static DocumentReference getCollectionDocument(String collection, String document) {
        return db.collection(collection).document(document);
    }

    private static CollectionReference getStockCollection() {
        return getCollection("stockList");
    }

    public void storeStockNames(Exchanges exchange) throws ExecutionException, InterruptedException {
        CollectionReference stockCollection = getStockCollection();
        try {
            List<FullQuoteData> stockList = financialModelingAPI.getSymbolList(exchange.name());
            for (FullQuoteData quoteData : stockList) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", quoteData.getName());
                data.put("symbol", quoteData.getSymbol());
                stockCollection.document(quoteData.getSymbol()).set(data);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // asynchronously retrieve all users
        ApiFuture<QuerySnapshot> query = db.collection("stockList").get();
        // query.get() blocks on response
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println("Stock: " + document.getString("symbol"));
            System.out.println("Name: " + document.getString("name"));
        }
    }

    public ArrayList<String> getStock(String input) {
        ArrayList<String> stockList = new ArrayList<>();
        // Create two separate queries
        Query query = db.collection("stockList")
                .orderBy("symbol")
                .orderBy("name")
                .startAt(input)
                .endAt(input + "\uf8ff")
                .limit(5);
        try {
            // Execute the queries and add the results to the stockList
            ApiFuture<QuerySnapshot> future = query.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                if (!stockList.contains(document.getString("symbol"))) {
                    stockList.add(document.getString("symbol"));
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return stockList;
    }

}