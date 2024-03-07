module org.farmingdale.stockdiviner {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.google.gson;
    requires com.google.auth;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires google.cloud.core;
    requires firebase.admin;
    requires com.google.api.apicommon;


    opens org.farmingdale.stockdiviner to javafx.fxml;
    exports org.farmingdale.stockdiviner;
    exports org.farmingdale.stockdiviner.model.alphavantage;
    exports org.farmingdale.stockdiviner.model.lunar;
    exports org.farmingdale.stockdiviner.model.animals;
    exports org.farmingdale.stockdiviner.model.firebase;

}