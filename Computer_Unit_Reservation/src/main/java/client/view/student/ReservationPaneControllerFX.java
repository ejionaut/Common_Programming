package client.view.student;

import client.model.ReservationModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import shared.Constants;
import shared.Reservation;

import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Objects;

public class ReservationPaneControllerFX implements Serializable {
    @FXML
    public BorderPane bp;
    @FXML
    public TextField searchTextField;
    @FXML
    public Button searchButton;
    @FXML
    public Button createReservation;
    @FXML
    public Button loadReservation;
    @FXML
    public VBox reservationWidgetBox;
    Registry registry;
    ReservationModel reservationModel;

    public ReservationPaneControllerFX () {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            reservationModel= new ReservationModel(registry);
        } catch (Exception _) {}
    }

    @FXML
    public void displayForm() {
        loadFXML();
    }

    private void loadFXML() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/student/CreateReservation.fxml")));
            bp.setCenter(root);
            bp.setBottom(null);
        } catch (Exception _) {
        }
    }

    public void load() {
        try {
            List<Reservation> reservationList = reservationModel.loadReservations();
            reservationWidgetBox.getChildren().clear();
            for(Reservation reservation : reservationList) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/student/ReservationWidget.fxml"));
                Parent widgetLayout = fxmlLoader.load();
                ReservationWidgetFX reservationWidgetFX = fxmlLoader.getController();
                reservationWidgetFX.setData(reservation, String.valueOf(reservationList.indexOf(reservation)));
                reservationWidgetBox.getChildren().add(widgetLayout);
            }
        } catch (Exception _) {
        }
    }

    public void search() {
        try {
            String searchText = searchTextField.getText();
            if (!searchText.isEmpty() && !Double.isNaN(Double.parseDouble(searchText))) {
                int searchNumber = Integer.parseInt(searchText);
                Reservation reservationSearch = reservationModel.searchReservation(searchNumber);
                reservationWidgetBox.getChildren().clear();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/student/ReservationWidget.fxml"));
                Parent widgetLayout = fxmlLoader.load();
                ReservationWidgetFX reservationWidgetFX = fxmlLoader.getController();
                reservationWidgetFX.setData(reservationSearch, String.valueOf(reservationSearch));
                reservationWidgetBox.getChildren().add(widgetLayout);
            }
        } catch (Exception _) {
        }
    }

}
