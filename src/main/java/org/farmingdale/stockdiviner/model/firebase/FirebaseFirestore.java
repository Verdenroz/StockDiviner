package org.farmingdale.stockdiviner.model.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Handles Firestore database connection and operations
 */
public class FirebaseFirestore {
    private static volatile FirebaseFirestore instance;
    private static Firestore db;

    private FirebaseFirestore() {
        FileInputStream serviceAccount = null;

        try {
            serviceAccount = new FileInputStream(".\\service.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get instance of FirebaseFirestore
     */
    public static FirebaseFirestore getInstance() {
        if (instance == null) {
            synchronized (FirebaseFirestore.class) {
                if (instance == null) {
                    instance = new FirebaseFirestore();
                }
            }
        }
        return instance;
    }

    /**
     * Get the users collection from Firestore
     */
    public CollectionReference getUsersCollection(){
        return db.collection("users");
    }

    /**
     * Get the watchlist collection for a user from Firestore
     * @param email user email
     */
    public CollectionReference getWatchlistCollection(String email){
        return db.collection("watchlist").document(email).collection("stocks");
    }

    /**
     * Check if a username already exists in Firestore
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        try {
            DocumentSnapshot document = db.collection("users").document(username).get().get();
            return document.exists();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if an email already exists in Firestore
     * @param email the email to check
     * @return true if the email exists, false otherwise
     */
    public boolean emailExists(String email) {
        try {
            QuerySnapshot document = db.collection("users").whereEqualTo("email", email).get().get();
            return !document.isEmpty();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the user record from Firestore
     * @param username the username of the user
     * @return the DocumentSnapshot of the user record or null if user does not exist
     */
    public DocumentSnapshot getUserRecord(String username){
        try{
            return db.collection("users").document(username).get().get();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}