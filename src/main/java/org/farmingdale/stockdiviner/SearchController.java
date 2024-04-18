package org.farmingdale.stockdiviner;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;
import org.farmingdale.stockdiviner.model.financialmodeling.ImplFinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.StockSearch;
import org.farmingdale.stockdiviner.model.firebase.FirebaseWatchlist;

import java.io.IOException;
import java.util.List;

public class SearchController {
    private final SharedService sharedService;

    private static final FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();

    ChangeView changeView = ChangeView.getInstance();

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField searchBarTextField;

    @FXML
    private ListView<StockSearch> searchResultsListView;

    @FXML
    private TextFlow stockInfoArea;

    @FXML
    private Button analysisButton;

    @FXML
    private ListView<FullQuoteData> watchListView;

    private static final FirebaseWatchlist watchlist = FirebaseWatchlist.getInstance();

    private final PauseTransition pause = new PauseTransition(Duration.seconds(0.25));

    public SearchController() {
        this.sharedService = SharedService.getInstance();
    }

    public void initialize() {
        userNameLabel.setText(sharedService.getUser().getDisplayName());
        List<FullQuoteData> stocks = watchlist.getWatchlist(sharedService.getUser().getEmail());

        // Create a new ObservableList for the ListView
        ObservableList<FullQuoteData> observableList = FXCollections.observableArrayList(stocks);

        // Set the cell factory of the ListView
        watchListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(FullQuoteData item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a VBox to hold the symbol and name
                    VBox vbox = new VBox(50);
                    vbox.setAlignment(Pos.CENTER);
                    vbox.setSpacing(10);
                    // Create a Label for the symbol and add it to the VBox
                    Label symbolLabel = new Label(item.getName() + " ( " + item.getSymbol() + " )");
                    vbox.getChildren().add(symbolLabel);

                    Button removeButton = new Button("Remove from watchlist");
                    removeButton.setOnAction(event -> {
                        watchlist.deleteFromWatchlist(sharedService.getUser().getEmail(), item.getSymbol());
                        //refresh watchlist
                        initialize();
                    });
                    removeButton.setPadding(new Insets(10, 10, 10, 10));
                    vbox.getChildren().add(removeButton);

                    vbox.setOnMouseClicked(event -> setStockInfoArea(item));
                    setGraphic(vbox);
                }
            }
        });
        // Set the items of the ListView
        watchListView.setItems(observableList);
    }

    //populate a list of stock symbols
    public void populateListWithStocksWhenType() {
        pause.setOnFinished(event -> {
            FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();
            searchResultsListView.setVisible(true);
            ObservableList<StockSearch> stockSymbols = searchResultsListView.getItems();

            stockSymbols.clear(); // Clear the list before adding new items
            String input = searchBarTextField.getText();

            // Execute the search only if the input is not empty
            if (!input.isEmpty()) {
                List<StockSearch> result;
                try {
                    result = api.searchStock(input);
                    if (result != null) {
                        stockSymbols.addAll(result);
                    }
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }

            // Set the cell factory of the ListView
            searchResultsListView.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(StockSearch item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        HBox hbox = new HBox();
                        hbox.setAlignment(Pos.CENTER);
                        hbox.setPadding(new Insets(0, 10, 0, 10));

                        Label symbolLabel = new Label(item.getName() + " ( " + item.getSymbol() + " )");
                        HBox.setHgrow(symbolLabel, Priority.ALWAYS); // Make the symbolLabel grow to take up all available space
                        symbolLabel.setMaxWidth(Double.MAX_VALUE); // Allow the symbolLabel to grow without limit
                        hbox.getChildren().add(symbolLabel);

                        // Create a Button and add it to the HBox
                        Button actionButton = new Button();
                        if (watchlist.isInsideWatchList(sharedService.getUser().getEmail(), item.getSymbol())) {
                            actionButton.setText("-");
                        } else {
                            actionButton.setText("+");
                        }
                        actionButton.setOnAction(event -> {
                            if (watchlist.isInsideWatchList(sharedService.getUser().getEmail(), item.getSymbol())) {
                                watchlist.deleteFromWatchlist(sharedService.getUser().getEmail(), item.getSymbol());
                                actionButton.setText("+");
                            } else {
                                watchlist.addToWatchlist(sharedService.getUser().getEmail(), item.getSymbol(), item.getName());
                                actionButton.setText("-");
                            }
                            //refresh watchlist
                            initialize();
                        });
                        hbox.getChildren().add(actionButton);
                        setGraphic(hbox);
                    }
                }
            });
        });
        pause.playFromStart();
    }

    public void fromListCellToTextField() {
        searchResultsListView.setOnMouseClicked(event -> {
            StockSearch selectedItem = searchResultsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                searchBarTextField.setText(selectedItem.getName());
                searchResultsListView.getItems().clear();
                searchResultsListView.setVisible(false);
                try {
                    setStockInfoArea(api.getFullQuoteData(selectedItem.getSymbol()));
                } catch (IOException e) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setHeaderText("Unable to retrieve stock information");
                    alert.setContentText("Please try to again.");
                    alert.showAndWait();
                }
            }
        });
    }


    public void onPickStockButtonClicked() {
        String symbol = searchBarTextField.getText();
        if (isTheStockNameEmpty(new TextArea(symbol))) {
            return;
        }
        try {
            FullQuoteData data = api.getFullQuoteData(symbol);
            setStockInfoArea(data);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Unable to retrieve stock information");
            alert.setContentText("Please try to enter a valid stock symbol.");
            alert.showAndWait();
        }
    }

    public boolean isTheStockNameEmpty(TextArea label) {
        if (label.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Stock Not Selected");
            alert.setContentText("Please select a stock before proceeding");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Set the stock information area with the data from the FullQuoteData object
     */
    public void setStockInfoArea(FullQuoteData data) {
        sharedService.setData(data);
        stockInfoArea.setVisible(true);
        analysisButton.setVisible(true);
        stockInfoArea.getChildren().clear();
        stockInfoArea.getChildren().add(new Text(data.getName() + " ( " + data.getSymbol() + " )"));
        stockInfoArea.getChildren().add(new Text("\n\n"));
        stockInfoArea.getChildren().add(new Text("Current Price: " + data.getPrice()));
        stockInfoArea.getChildren().add(new Text("\n\n"));
        stockInfoArea.getChildren().add(new Text("Exchange: " + data.getExchange()));
        stockInfoArea.getChildren().add(new Text("\n\n"));
        stockInfoArea.getChildren().add(new Text("Average Volume: " + data.getAvgVolume()));
    }

    public void onLogOutButtonClicked(ActionEvent event) throws IOException {
        changeView.logout(event);
    }

    public void onAnalysisButtonClicked(ActionEvent event) throws IOException {
        stockInfoArea.setVisible(false);
        analysisButton.setVisible(false);
        changeView.changeViewTo("info-screen", event);
    }

}
