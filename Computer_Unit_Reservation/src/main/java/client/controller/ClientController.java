package client.controller;

import client.model.LoginModel;
import client.view.login.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import shared.Account;
import shared.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientController extends SceneSwitch {
    public TextField userIDField;
    public PasswordField userPasswordField;
    public Button loginButton;
    public Label forgotPasswordText;
    public AnchorPane ap;
    public Label loginErrorPrompt;
    Registry registry;
    LoginModel loginModel;

    public ClientController() {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            loginModel= new LoginModel(registry);
        } catch (Exception _) {}
    }

    public void onLoginClicked(ActionEvent event){
        Border border = new Border(new BorderStroke(Color.RED, BorderStrokeStyle.DOTTED, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        Account user = new Account(getUsername(), getPassword());
        try {
            if (loginModel.adminChecker(user)) {
                loginModel.setUserID(user);
                switchToAdminLanding(event);
            } else if (loginModel.studentChecker(user)){
                loginModel.setUserID(user);
                switchToStudentLanding(event);
            } else if(getUsername().isBlank() || getPassword().isBlank()){
                loginErrorPrompt.setText("Blank fields");
                if(getUsername().isBlank()){
                    userIDField.setBorder(border);
                } else {
                    userPasswordField.setBorder(border);
                }
                loginErrorPrompt.setVisible(true);
            } else {
                loginErrorPrompt.setText("Wrong credentials");
                loginErrorPrompt.setVisible(true);
            }
        } catch (Exception _) {
            loginErrorPrompt.setText("Could not connect to the server");
            loginErrorPrompt.setVisible(true);
        }
    }

    public String getUsername(){return userIDField.getText();}

    public String getPassword(){return userPasswordField.getText();}
}
