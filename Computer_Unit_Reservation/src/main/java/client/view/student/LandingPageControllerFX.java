package client.view.student;

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

public class LandingPageControllerFX extends SceneSwitch {
    public VBox reservationSideBar;
    public VBox homeSideBar;
    public Pane homePanel;
    public Button signOutButton;
    public BorderPane bp;
    public Label userIDText;

    @FXML
    protected void isSelected(){
        if(homeSideBar.isHover()){
            homeSideBar.setStyle("-fx-background-color: #001D3D");
            reservationSideBar.setStyle("-fx-background-color: #003566");
            bp.setCenter(homePanel);
        } else if (reservationSideBar.isHover()){
            reservationSideBar.setStyle("-fx-background-color: #001D3D");
            homeSideBar.setStyle("-fx-background-color: #003566");
            swapPages();
        }
    }

    private void swapPages(){
        Parent root = null;
        try{
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/student/ReservationPane.fxml")));
        } catch (Exception _) {
        }
        bp.setCenter(root);
    }
}
