package org.farmingdale.stockdiviner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.TextFields;

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


    public void printMessage(ActionEvent event) {
        System.out.println("Hello World");
       // TextFields.bindAutoCompletion(searchBarTextField, "AAPL", "GOOGL", "MSFT", "TSLA", "AMZN", "FB", "NFLX", "NVDA", "INTC", "AMD");
    }
//cannot use initialize method in this class because it is not a subclass of Application


}
