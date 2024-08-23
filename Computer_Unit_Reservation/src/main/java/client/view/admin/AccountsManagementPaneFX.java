package client.view.admin;

import client.model.AccountModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import shared.Account;
import shared.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Objects;

public class AccountsManagementPaneFX {
    public BorderPane bp;
    public Button createAccountButton;
    public Button loadAccountButton;
    public VBox accountsWidgetBox;

    Registry registry;
    AccountModel accountModel;

    public AccountsManagementPaneFX() {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            accountModel= new AccountModel(registry);
        } catch (Exception _) {}
    }

    public void load() {
        try {
            List<Account> accountList = accountModel.loadAccounts();
            accountsWidgetBox.getChildren().clear();
            for(Account account : accountList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/admin/AccountsWidget.fxml"));
                Parent widgetLayout = fxmlLoader.load();
                AccountsWidgetFX accountsWidgetFX = fxmlLoader.getController();
                accountsWidgetFX.setAccountsData(account, String.valueOf(accountList.indexOf(account)));
                accountsWidgetBox.getChildren().add(widgetLayout);
            }
        } catch (Exception _) {
        }
    }

    public void displayForm() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/admin/CreateAccountPane.fxml")));
            bp.setCenter(root);
            bp.setBottom(null);
        } catch (Exception _) {
        }
    }
}
