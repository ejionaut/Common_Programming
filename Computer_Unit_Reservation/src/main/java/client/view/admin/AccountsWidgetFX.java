package client.view.admin;

import client.model.AccountModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import shared.Account;
import shared.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

@SuppressWarnings("SameParameterValue")
public class AccountsWidgetFX {
    public Label idNumberText;
    public Button removeButton;
    Registry registry;
    AccountModel accountModel;

    public AccountsWidgetFX () {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            accountModel= new AccountModel(registry);
        } catch (Exception _) {}
    }

    public void setAccountsData(Account account, String buttonVal){
        idNumberText.setText(String.valueOf(account.getUserID()));
        removeButton.setId(buttonVal);
    }

    @FXML
    public void delete() {
        String userID = idNumberText.getText();
        try {
            accountModel.deleteAccount(userID);
        } catch (Exception _) {}
        removeButton.setStyle("-fx-background-color: #939393");
        removeButton.setDisable(true);
    }
}
