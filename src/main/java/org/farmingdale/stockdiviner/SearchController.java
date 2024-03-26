package org.farmingdale.stockdiviner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.farmingdale.stockdiviner.model.animals.ChineseNewYears;
import org.farmingdale.stockdiviner.model.lunar.ImplLunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarCalculatorAPI;
import org.farmingdale.stockdiviner.model.lunar.LunarPhase;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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
    private String[] possibleSuggestions = {"Apple APPL",
                                            "Microsof MSFT",
            "Google GOOGL",
            "Amazon AMZN",
            "Facebook FB",
            "Tesla TSLA",
            "Netflix NFLX",
            "Alphabet GOOGL",
            "Alphabet GOOG",
            "Nvidia NVDA",
            "Paypal PYPL",
            "Adobe ADBE","Shopify SHOP","Salesforce CRM","Zoom ZM","Microsoft MSFT","Apple AAPL","Amazon AMZN","Facebook FB","Tesla TSLA","Netflix NFLX","Alphabet GOOGL","Alphabet GOOG","Nvidia NVDA","Paypal PYPL","Adobe ADBE","Shopify SHOP","Salesforce CRM","Zoom ZM","Microsoft MSFT","Apple AAPL","Amazon AMZN","Facebook FB","Tesla TSLA","Netflix NFLX","Alphabet GOOGL","Alphabet GOOG","Nvidia NVDA","Paypal PYPL","Adobe ADBE","Shopify SHOP","Salesforce CRM","Zoom ZM","Microsoft MSFT","Apple AAPL","Amazon AMZN","Facebook FB","Tesla TSLA","Netflix NFLX","Alphabet GOOGL","Alphabet GOOG","Nvidia NVDA","Paypal PYPL","Adobe ADBE","Shopify SHOP","Salesforce CRM","Zoom ZM","Microsoft MSFT","Apple AAPL","Amazon AMZN","Facebook FB","Tesla TSLA","Netflix NFLX","Alphabet GOOGL","Alphabet GOOG","Nvidia NVDA","Paypal PYPL","Adobe ADBE","Shopify SHOP","Salesforce CRM","Zoom ZM","Microsoft MSFT","Apple AAPL","Amazon AMZN","Facebook FB","Tesla TSLA","Netflix NFLX","Alphabet GOOGL","Alphabet GOOG","Nvidia NVDA","Paypal PYPL","Adobe ADBE","Shopify SHOP","Salesforce CRM","Zoom ZM","Microsoft MSFT","Apple AAPL","Amazon AMZN","Facebook FB","Tesla TSLA","Netflix NFLX","Alphabet GOOGL","Alphabet GOOG","Nvidia NVDA","Paypal PYPL","Adobe ADBE","Shopify SHOP","Salesforce CRM","Zoom ZM","Microsoft MSFT","Apple AAPL","Amazon AMZN","Facebook FB","Tesla TSLA","Netflix NFLX","Alphabet GOOGL","Alphabet GOOG","Nvidia NVDA","Paypal PYPL","Adobe ADBE","Shopify SHOP","Salesforce CRM","Zoom ZM","Microsoft MSFT",};

    private Set<String> possibleSuggestionsSet = new HashSet<>(Arrays.asList(possibleSuggestions));

    public void printMessage() {

        TextFields.bindAutoCompletion(searchBarTextField, possibleSuggestionsSet).setMaxWidth(searchBarTextField.getWidth());
    }
    public void onChineseToggleSelected(ActionEvent event) {
        resetButtonStyles();
        changeColorSelected(ChineseNewYearsButton);
        ChineseNewYears chineseNewYears = ChineseNewYears.getInstance();
        SharedModel.getInstance().setChineseNewYears(chineseNewYears);

    }

    public void onLunarToggleSelected(ActionEvent event) throws IOException {
        resetButtonStyles();
        changeColorSelected(lunarPhasesButton);
        LunarCalculatorAPI lunarPhases = ImplLunarCalculatorAPI.getInstance();
        SharedModel.getInstance().setLunarPhases(lunarPhases);
       // LunarPhase fullMoon = lunarPhases.getLunarPhase(LocalDate.of(2021, 1, 28));
       // System.out.println(fullMoon);
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
