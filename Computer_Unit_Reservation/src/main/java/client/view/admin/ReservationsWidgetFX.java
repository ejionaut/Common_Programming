package client.view.admin;

import client.model.AccountModel;
import javafx.scene.control.Label;
import shared.Constants;
import shared.Reservation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ReservationsWidgetFX {
    public Label idNumberText;
    public Label unitIdText;
    public Label dateTextField;
    public Label startTimeText;
    public Label endTimeText;
    public Label reservationNum;
    Registry registry;
    AccountModel accountModel;

    public ReservationsWidgetFX () {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            accountModel= new AccountModel(registry);
        } catch (Exception _) {}
    }

    public void setReservationData(Reservation reservation){
        idNumberText.setText(String.valueOf(reservation.getUserID()));
        unitIdText.setText(String.valueOf(reservation.getUnitID()));
        reservationNum.setText(String.valueOf(reservation.getReservationID()));
        dateTextField.setText(String.valueOf(reservation.getDate()));
        startTimeText.setText(String.valueOf(reservation.getStartTime()));
        endTimeText.setText(String.valueOf(reservation.getEndTime()));
    }
}
