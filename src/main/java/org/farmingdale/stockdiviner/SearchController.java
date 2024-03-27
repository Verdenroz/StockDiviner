package org.farmingdale.stockdiviner;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.ImplFinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.StockSearch;
import org.farmingdale.stockdiviner.model.lunar.ImplLunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarPhase;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SearchController {

    @FXML
    private ToggleButton ChineseNewYearsButton;

    @FXML
    private Button logOutButton;

    @FXML
    private ToggleButton lunarPhasesButton;

    @FXML
    private TextField searchBarTextField;

    @FXML
    private ToggleButton zodiacSignsButton;
    private AutoCompletionBinding<String> autoCompletionBinding;

    FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();

    private PauseTransition pause = new PauseTransition(Duration.seconds(1)); // 1 second delay


    private String lastQueriedText = "";
    private Timeline timeline = new Timeline();

    private static final int MIN_CHAR_THRESHOLD = 3;

    public void setupSearchBar() {
        searchBarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < MIN_CHAR_THRESHOLD) {
                clearAutoCompletionBinding();
                return;
            }

            if (timeline != null) {
                timeline.stop();
            }
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (!newValue.equals(lastQueriedText)) {
                    fetchAndDisplaySuggestions(newValue);
                }
            }));
            timeline.playFromStart();
        });
    }

    private void fetchAndDisplaySuggestions(String input) {
        lastQueriedText = input;

        new Thread(() -> {
            try {
                List<StockSearch> result = api.searchStock(input);
                Set<String> suggestions = result.stream().map(StockSearch::getName).collect(Collectors.toSet());

                Platform.runLater(() -> {
                    // Clear existing binding before creating a new one
                    clearAutoCompletionBinding();

                    // Create a new binding with the latest suggestions
                    autoCompletionBinding = TextFields.bindAutoCompletion(searchBarTextField, suggestions);
                    autoCompletionBinding.setMaxWidth(searchBarTextField.getWidth());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private synchronized void clearAutoCompletionBinding() {
        if (autoCompletionBinding != null) {
            autoCompletionBinding.dispose(); // Dispose of the previous binding
            autoCompletionBinding = null; // Ensure the reference is cleared
        }
    }


    public void onChineseToggleSelected(ActionEvent event) {
        resetButtonStyles();
        changeColorSelected(ChineseNewYearsButton);
        SharedModel.getInstance().selectApi(ApiType.CHINESE_NEW_YEARS);

    }

    public void onLunarToggleSelected(ActionEvent event) throws IOException {
        resetButtonStyles();
        changeColorSelected(lunarPhasesButton);

    }

    public void onZodiacToggleSelected(ActionEvent event) {
        resetButtonStyles();
        changeColorSelected(zodiacSignsButton);
        // Additional functionality for Zodiac can be added here
    }

    public void changeColorSelected(ToggleButton button) {
        // No need to check if button is selected here as this method is called after a button is selected
        button.setStyle("-fx-background-color: #2cc579");
    }

    public void resetButtonStyles() {
        // Reset all buttons to default style
        ChineseNewYearsButton.setStyle(".black-background");
        lunarPhasesButton.setStyle(".black-background");
        zodiacSignsButton.setStyle(".black-background");
    }
//cannot use initialize method in this class because it is not a subclass of Application


}
