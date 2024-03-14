module org.farmingdale.stockdiviner {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires com.google.gson;
    requires org.controlsfx.controls;


    opens org.farmingdale.stockdiviner to javafx.fxml;
    opens org.farmingdale.stockdiviner.model.financialmodeling to com.google.gson;
    exports org.farmingdale.stockdiviner;
    exports org.farmingdale.stockdiviner.model.alphavantage;
    exports org.farmingdale.stockdiviner.model.lunar;
    exports org.farmingdale.stockdiviner.model.animals;
    exports org.farmingdale.stockdiviner.model.financialmodeling;
}