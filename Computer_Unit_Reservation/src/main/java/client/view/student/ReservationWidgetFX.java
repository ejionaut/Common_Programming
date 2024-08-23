package client.view.student;

import client.model.ReservationModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import shared.Constants;
import shared.Reservation;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@SuppressWarnings("SameParameterValue")
public class ReservationWidgetFX {
    @FXML
    public Label unitIDText;
    @FXML
    public Label reservationNum;
    @FXML
    public Label startTimeText;
    @FXML
    public Label endTimeText;
    @FXML
    public Label userIDText;
    @FXML
    public Button editBtn;
    @FXML
    public Label dateTimeText;
    @FXML
    public Button deleteBtn;
    Registry registry;
    ReservationModel reservationModel;

    public ReservationWidgetFX () {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            reservationModel= new ReservationModel(registry);
        } catch (Exception _) {}
    }

    public void setData(Reservation reservation, String buttonVal){
        userIDText.setText(String.valueOf(reservation.getUserID()));
        unitIDText.setText(String.valueOf(reservation.getUnitID()));
        reservationNum.setText(String.valueOf(reservation.getReservationID()));
        startTimeText.setText(String.valueOf(reservation.getStartTime()));
        endTimeText.setText(String.valueOf(reservation.getEndTime()));
        dateTimeText.setText(String.valueOf(reservation.getDate()));
        editBtn.setId(buttonVal);
        deleteBtn.setId(buttonVal);
    }

    @FXML
    public void delete() {
        int userID = Integer.parseInt(userIDText.getText());
        int unitID = Integer.parseInt(unitIDText.getText());
        int reservationID = Integer.parseInt(reservationNum.getText());
        LocalDate dateTime = LocalDate.parse(dateTimeText.getText());
        LocalTime startTime = LocalTime.parse(startTimeText.getText());
        LocalTime endTime = LocalTime.parse(endTimeText.getText());
        try {
            reservationModel.deleteReservation(new Reservation(userID, unitID, reservationID, dateTime, startTime, endTime));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        deleteBtn.setStyle("-fx-background-color: #939393");
        deleteBtn.setDisable(true);
        editBtn.setStyle("-fx-background-color:#939393");
        editBtn.setDisable(true);
    }

    @FXML
    public void edit() {
        int userID = Integer.parseInt(userIDText.getText());
        int unitID = Integer.parseInt(unitIDText.getText());
        int reservationID = Integer.parseInt(reservationNum.getText());
        LocalDate dateTime = LocalDate.parse(dateTimeText.getText());
        LocalTime startTime = LocalTime.parse(startTimeText.getText());
        LocalTime endTime = LocalTime.parse(endTimeText.getText());
        try {
            reservationModel.setReservation(new Reservation(userID, unitID, reservationID, dateTime, startTime, endTime));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        loadFXML("/fxml/student/EditReservation.fxml");
    }

    private void loadFXML(String fileName) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fileName)));
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 732, 641));
            stage.show();
        } catch (Exception _) {
        }
    }
}
