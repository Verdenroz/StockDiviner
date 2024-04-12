package org.farmingdale.stockdiviner;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.financialmodeling.FinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.FullQuoteData;
import org.farmingdale.stockdiviner.model.financialmodeling.ImplFinancialModelingAPI;
import org.farmingdale.stockdiviner.model.financialmodeling.StockSearch;
import org.farmingdale.stockdiviner.model.lunar.ImplLunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.zodiac.ZodiacCalculator;

import java.io.IOException;
import java.util.*;

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

    private SharedService sharedService ;

    public SearchController() {
        this.sharedService = SharedService.getInstance();
    }


    FinancialModelingAPI api = ImplFinancialModelingAPI.getInstance();

    public void setTheUserNameLabel(String userName){
        userNameLabel.setText(userName);
    }

//populate a list of stock symbols
    public void populateListWithStocksWhenType(){
        searchResultsListView.setVisible(true);
        ObservableList<String> stockSymbols = searchResultsListView.getItems();

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
        if(isTheStockNameEmpty(stockNameLabel)!=false){
            stockNameLabel.setVisible(false);
        }
        else {
            // isTheStockNameEmpty(stockNameLabel);
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

                    sharedService.setData(result);
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions or show error messages if necessary
            }
        }
    }
    public boolean isTheStockNameEmpty(Label label){
        if(label.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Stock Not Selected");
            alert.setContentText("Please select a stock before proceeding");
            alert.showAndWait();
            return true;
        }
        else{
            makeTheToggleButtonsAvailable();
        }

        return false;

    }

    public void onLogOutButtonClicked(ActionEvent event) throws IOException{
        changeView.logout(event);
    }
//if Lunar button is selected, set the shared service to the LunarCalculatorAPI
//if Chinese button is selected, set the shared service to the ChineseNewYears
//if Zodiac button is selected, set the shared service to the ZodiacCalculator

public void makeTheToggleButtonsAvailable(){
        ChineseNewYearsButton.setDisable(false);
        lunarPhasesButton.setDisable(false);
        zodiacSignsButton.setDisable(false);
}

public void whatIsSelected(ActionEvent event)throws IOException{
    if(ChineseNewYearsButton.isSelected()){
        System.out.println("Chinese New Years is selected");
        onChineseToggleSelected(event);
    }
    if(lunarPhasesButton.isSelected()){
        System.out.println("Lunar Phases is selected");
        onLunarToggleSelected(event);
    }
    if(zodiacSignsButton.isSelected()){
        System.out.println("Zodiac Signs is selected");
        onZodiacToggleSelected(event);
    }
}



    public void onChineseToggleSelected(ActionEvent event) throws IOException {
           ChineseNewYearsButton.setDisable(false);
            resetButtonStyles();
            changeColorSelected(ChineseNewYearsButton);
            sharedService.setChineseNewYears(ChineseNewYears.getInstance());
            changeView.changeViewTo("info-screen", event);
    }

    public void onLunarToggleSelected(ActionEvent event) throws IOException {
        resetButtonStyles();
        changeColorSelected(lunarPhasesButton);
        sharedService.setLunarPhanses(ImplLunarCalculatorAPI.getInstance());
        changeView.changeViewTo("info-screen", event);
    }

    public void onZodiacToggleSelected(ActionEvent event) throws IOException {
        resetButtonStyles();
        changeColorSelected(zodiacSignsButton);
        sharedService.setZodiacCalulator(ZodiacCalculator.getInstance());
        changeView.changeViewTo("info-screen", event);
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
