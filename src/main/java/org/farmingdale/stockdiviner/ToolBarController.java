package org.farmingdale.stockdiviner;

import javafx.event.ActionEvent;

import java.io.IOException;

public class ToolBarController {
    ChangeView changeView = ChangeView.getInstance();

    public void onLogOutButtonClicked(ActionEvent event) throws IOException {
        changeView.logout(event);
    }
}
