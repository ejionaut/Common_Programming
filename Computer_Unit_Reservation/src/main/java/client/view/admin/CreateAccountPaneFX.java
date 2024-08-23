package client.view.admin;

import client.model.AccountModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import shared.Account;
import shared.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;

public class CreateAccountPaneFX {
    public TextField idNumberTextField;
    public TextField passwordTextField;
    public TextField confirmPasswordTextField;
    public Label errorPrompt;
    public Button submitButton;
    public Button backButton;
    public BorderPane bp;
    Registry registry;
    AccountModel accountModel;

    public CreateAccountPaneFX() {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            accountModel= new AccountModel(registry);
        } catch (Exception _) {}
    }

    public void back() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/admin/AccountsManagementPane.fxml")));
            bp.setCenter(root);
        } catch (Exception _) {}
    }

    public void submit() {
        try {
            String userIdText = idNumberTextField.getText().trim();
            String password = passwordTextField.getText();
            String confirmPassword = confirmPasswordTextField.getText();

            if (!isNumeric(userIdText)) {
                errorPrompt.setText("User ID must be a number");
                errorPrompt.setStyle("-fx-text-fill: #720808");
                return;
            }

            if (password.equals(confirmPassword) && !userIdText.isBlank() && !password.isBlank()) {
                if (accountModel.createAccount(new Account(userIdText, password))) {
                    errorPrompt.setText("Account created successfully");
                    errorPrompt.setStyle("-fx-text-fill: #07ff00");
                } else {
                    errorPrompt.setText("Account already exists");
                    errorPrompt.setStyle("-fx-text-fill: #720808");
                }
            } else {
                errorPrompt.setText("Invalid input or passwords do not match");
                errorPrompt.setStyle("-fx-text-fill: #720808");
            }
        } catch (Exception e) {
            errorPrompt.setText("Error occurred");
            errorPrompt.setStyle("-fx-text-fill: #720808");
        }
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
