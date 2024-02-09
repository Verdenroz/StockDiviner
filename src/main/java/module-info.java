module org.farmingdale.stockdiviner {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.farmingdale.stockdiviner to javafx.fxml;
    exports org.farmingdale.stockdiviner;
}