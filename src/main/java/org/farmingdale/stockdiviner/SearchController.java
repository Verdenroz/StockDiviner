package org.farmingdale.stockdiviner;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;
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
    ChangeView changeView = ChangeView.getInstance();
    @FXML
    private ToggleButton ChineseNewYearsButton;

    @FXML
    private Label userNameLabel;

    @FXML
    private Button logOutButton;

    @FXML
    private ToggleButton lunarPhasesButton;

    @FXML
    private TextField searchBarTextField;

    @FXML
    private ToggleButton zodiacSignsButton;

    @FXML
    private ListView<String> searchResultsListView;

    @FXML
     private Label stockNameLabel;


    FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();

    public void setTheUserNameLabel(String userName){
        userNameLabel.setText(userName);
    }

//populate a list of stock symbols
    public void populateListWithStocksWhenType(){
        searchResultsListView.setVisible(true);
        ObservableList<String> stockSymbols = searchResultsListView.getItems();

        //searchBarTextField.addEventHandler(KeyEvent.KEY_RELEASED, event -> populateListWithStocksWhenType());

        stockSymbols.clear(); // Clear the list before adding new items
        String input = searchBarTextField.getText();


        // Execute the search only if the input is not empty
        if (!input.isEmpty()) {
            List<StockSearch> result = null;
            try {
                result = api.searchStock(input);
                if (result != null) {
                    for (StockSearch data : result) {
                        stockSymbols.add(data.getName());
                        System.out.println(data.getName());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions or show error messages if necessary
            }
        }// assertFalse(result.isEmpty(), "The returned list should not be empty");

    }

    public void fromListCellToTextField(){

        searchResultsListView.setOnMouseClicked(event -> {
            String selectedItem = searchResultsListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && !selectedItem.isEmpty()) {
                searchBarTextField.setText(selectedItem);
                searchResultsListView.getItems().clear();
                searchResultsListView.setVisible(false);// Clear the ListView after selection
            }
        });

    }



public void onPickStockButtonClicked(ActionEvent event) throws IOException {
        String symbol = searchBarTextField.getText();

        stockNameLabel.setText(symbol);
        searchBarTextField.clear();
        stockNameLabel.setVisible(true);
        searchResultsListView.setVisible(false);

        FullQuoteData result = null;

        try {
            result = api.getFullQuoteData(api.searchStock(symbol).get(0).getSymbol());
            if (result != null) {
                System.out.println(result.getName());
                System.out.println(result.getChange());
                System.out.println(result.getChangesPercentage());
                System.out.println(result.getPreviousClose());
                System.out.println(result.getAvgVolume());
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exceptions or show error messages if necessary
        }
    }

    public void onLogOutButtonClicked(ActionEvent event) throws IOException{
        changeView.logout(event);
    }


    public void onChineseToggleSelected(ActionEvent event) throws IOException {
        resetButtonStyles();
        changeColorSelected(ChineseNewYearsButton);
        SharedModel.getInstance().selectApi(ApiType.CHINESE_NEW_YEARS);

        changeView.changeViewTo("Test", event);

    }

    public void onLunarToggleSelected(ActionEvent event) throws IOException {
        resetButtonStyles();
        changeColorSelected(lunarPhasesButton);
        SharedModel.getInstance().selectApi(ApiType.LUNAR_PHASES);
        changeView.changeViewTo("Test", event);
    }

    public void onZodiacToggleSelected(ActionEvent event) throws IOException {
        resetButtonStyles();
        changeColorSelected(zodiacSignsButton);
        SharedModel.getInstance().selectApi(ApiType.ZODIAC_SIGNS);
        changeView.changeViewTo("Test", event);
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
