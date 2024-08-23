package client.view.admin;

import client.view.login.SceneSwitch;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.Objects;

public class AdminLandingPageControllerFX extends SceneSwitch {
    public Label userIDText;
    public Button signOutButton;
    public BorderPane bp;
    public Pane homePanel;
    public VBox homeSideBar;
    public VBox reservationSideBar;
    public VBox accountSideBar;

    @FXML
    protected void isSelected(){
        if(homeSideBar.isHover()){
            homeSideBar.setStyle("-fx-background-color: #001D3D");
            reservationSideBar.setStyle("-fx-background-color: #003566");
            accountSideBar.setStyle("-fx-background-color: #003566");
            bp.setCenter(homePanel);
        } else if (reservationSideBar.isHover()){
            reservationSideBar.setStyle("-fx-background-color: #001D3D");
            homeSideBar.setStyle("-fx-background-color: #003566");
            accountSideBar.setStyle("-fx-background-color: #003566");
            toReservation();
        } else if (accountSideBar.isHover()){
            accountSideBar.setStyle("-fx-background-color: #001D3D");
            reservationSideBar.setStyle("-fx-background-color: #003566");
            homeSideBar.setStyle("-fx-background-color: #003566");
            toAccount();
        }
    }

    private void toReservation(){
        Parent root = null;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/admin/ReservationsManagementPane.fxml")));
        } catch (Exception _) {
        }
        bp.setCenter(root);
    }

    public void toAccount() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/admin/AccountsManagementPane.fxml")));
            bp.setCenter(root);
        } catch (Exception _) {}
    }
}
