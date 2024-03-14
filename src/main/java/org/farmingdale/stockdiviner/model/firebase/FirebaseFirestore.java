package org.farmingdale.stockdiviner.model.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

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

    public CollectionReference getUsersCollection(){
        return db.collection("users");
    }

    public DocumentSnapshot getUserRecord(String username){
        try{
            return db.collection("users").document(username).get().get();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

}