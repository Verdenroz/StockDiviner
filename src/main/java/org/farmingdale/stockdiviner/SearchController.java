package org.farmingdale.stockdiviner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.ImplFinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.StockSearch;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SearchController {

    @FXML
    private Button ChineseNewYearsButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button lunarPhasesButton;

    @FXML
    private TextField searchBarTextField;

    @FXML
    private Button zodiacSignsButton;
    private AutoCompletionBinding<String> autoCompletionBinding;

    FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();


    public void updateAutoCompletionSuggestions(String input) {
        // Call the stock search API asynchronously
        CompletableFuture<List<StockSearch>> futureResult = CompletableFuture.supplyAsync(() -> {
            try {
                return api.searchStock(input);
            } catch (IOException e) {
                e.printStackTrace();
                return Collections.emptyList();
            }
        });

        // Update the auto-completion suggestions when the API call completes
        futureResult.thenAccept(result -> {
            Set<String> suggestions = result.stream()
                    .map(StockSearch::getName)
                    .collect(Collectors.toSet());
            // Ensure this part runs on the GUI thread
            Platform.runLater(() -> {
                TextFields.bindAutoCompletion(searchBarTextField, suggestions)
                        .setMaxWidth(searchBarTextField.getWidth());
            });
        });
    }
    public void printMessage() {
        // Bind key listener to searchBarTextField to update suggestions as the user types
        searchBarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            updateAutoCompletionSuggestions(newValue);
        });
    }



//cannot use initialize method in this class because it is not a subclass of Application


}
