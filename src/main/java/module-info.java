module org.farmingdale.stockdiviner {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.google.gson;


    opens org.farmingdale.stockdiviner to javafx.fxml;
    exports org.farmingdale.stockdiviner;
    exports org.farmingdale.stockdiviner.model.alphavantage;
    exports org.farmingdale.stockdiviner.model.lunar;
    exports org.farmingdale.stockdiviner.model.animals;
}