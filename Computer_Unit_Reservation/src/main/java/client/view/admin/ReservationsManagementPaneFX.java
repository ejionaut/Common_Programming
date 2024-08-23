package client.view.admin;

import client.model.AccountModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import shared.Constants;
import shared.Reservation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ReservationsManagementPaneFX {
    @FXML
    BorderPane bp;
    public VBox reservationsStorageBox;
    public Button loadReservationButton;
    Registry registry;
    AccountModel accountModel;

    public ReservationsManagementPaneFX () {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            accountModel= new AccountModel(registry);
        } catch (Exception _) {}
    }

    public void load() {
        try {
            List<Reservation> reservationList = accountModel.loadReservations();
            reservationsStorageBox.getChildren().clear();
            for(Reservation reservation : reservationList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/admin/ReservationsWidget.fxml"));
                Parent widgetLayout = fxmlLoader.load();
                ReservationsWidgetFX reservationsWidgetFX = fxmlLoader.getController();
                reservationsWidgetFX.setReservationData(reservation);
                reservationsStorageBox.getChildren().add(widgetLayout);
            }
        } catch (Exception _) {
        }
    }
}
