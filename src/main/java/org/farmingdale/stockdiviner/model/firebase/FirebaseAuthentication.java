package org.farmingdale.stockdiviner.model.firebase;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class FirebaseAuthentication {
    private static volatile FirebaseAuthentication instance;
    private static FirebaseAuth auth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuthentication() {
        auth = FirebaseAuth.getInstance();
    }

    public static FirebaseAuthentication getInstance() {
        if (instance == null) {
            synchronized (FirebaseAuthentication.class) {
                if (instance == null) {
                    instance = new FirebaseAuthentication();
                }
            }
        }
        return instance;
    }

    public UserRecord createUser(String email, String username, String password){
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setEmailVerified(false)
                .setDisplayName(username)
                .setPassword(password)
                .setDisabled(false);
        UserRecord userRecord;
        try {
            userRecord = auth.createUser(request);
        } catch (FirebaseAuthException e) {
            throw new RuntimeException(e);
        }

        CollectionReference userCollection = db.getUsersCollection();
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("email", email);
        data.put("password", password);
        ApiFuture<WriteResult> writeResult = userCollection.document(username).set(data);

        try {
            writeResult.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return userRecord;
    }

    public UserRecord authenticateUser(String username, String password) {
        try {
            DocumentSnapshot document = db.getUserRecord(username);
            if (document.exists()) {
                String storedPassword = (String) document.get("password");
                if (password.equals(storedPassword)) {
                    return auth.getUserByEmail(document.get("email").toString());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
