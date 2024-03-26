package org.farmingdale.stockdiviner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

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


//cannot use initialize method in this class because it is not a subclass of Application


}
