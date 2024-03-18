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

/**
 * Handles user authentication and creation in Firebase Authentication and Firestore
 */
public class FirebaseAuthentication {
    private static volatile FirebaseAuthentication instance;
    private static FirebaseAuth auth;
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    private FirebaseAuthentication() {
        auth = FirebaseAuth.getInstance();
    }

    /**
     * Get instance of FirebaseAuthentication
     */
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

    /**
     * Create a new user in Firebase Authentication and Firestore
     * @param email the email of the user
     * @param username the username of the user
     * @param password the password of the user
     * @return the UserRecord of the created user or null if email or username already used
     */
    public UserRecord createUser(String email, String username, String password){
        // Check if a username or email already exists in Firestore
        if (db.usernameExists(username) || db.emailExists(email)) {
            return null;
        }
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
            return null;
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

    /**
     * Authenticate a user by matching inputs with the stored user records in FireStore
     * @param username the username of the user
     * @param password the password of the user
     * @return the UserRecord of the authenticated user or null if not found
     */
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
