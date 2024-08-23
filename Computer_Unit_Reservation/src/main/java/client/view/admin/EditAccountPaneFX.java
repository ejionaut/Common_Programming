package client.view.admin;

import client.model.AccountModel;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import shared.Account;

public class EditAccountPaneFX {
    public TextField editIdNumberTextField;
    public TextField newPasswordTextField;
    public TextField confirmPasswordTextField;
    public Label errorPrompt;
    public Button confirmButton;
    public Button backButton;
    public BorderPane bd;
    AccountModel accountModel;

    public void cancelEdit() {
        Stage stage = (Stage) bd.getScene().getWindow();
        stage.close();
    }

    public void submit() {
        try {
            String userIdText = editIdNumberTextField.getText().trim();
            String password = newPasswordTextField.getText();
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
