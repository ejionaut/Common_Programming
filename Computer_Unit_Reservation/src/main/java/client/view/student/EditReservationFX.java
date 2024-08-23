package client.view.student;

import client.model.ReservationModel;
import client.model.StudentModel;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import shared.Constants;
import shared.Reservation;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class EditReservationFX {
    public VBox editReservationForm;
    public BorderPane bd;
    public DatePicker datePicker;
    public ChoiceBox<LocalTime> startTimeChoiceBox;
    public ChoiceBox<LocalTime> endTimeChoiceBox;
    public Label unitPrompt;
    public Label errorPrompt;
    public Button saveButton;
    public Button cancelButton;
    public TextField newUnitNumberText;
    public Button selectAvailUnitButton;
    Registry registry;
    ReservationModel reservationModel;
    StudentModel studentModel = new StudentModel();

    public void initialize() {
        selectAvailUnitButton.setOnAction(_ -> setUnitID());
        datePicker.setValue(LocalDate.now());
        startTimeChoiceBox.setItems(FXCollections.observableArrayList(getAvailableStartTimes()));
        startTimeChoiceBox.getSelectionModel().selectedItemProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                endTimeChoiceBox.setItems(FXCollections.observableArrayList(studentModel.getAvailableEndTimes(newValue)));
            }
        });
        datePicker.setDayCellFactory(_ -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
        if (setUnitID()) {
            saveButton.setOnAction(_ -> confirmEdit());
        }
    }

    public EditReservationFX() {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            reservationModel= new ReservationModel(registry);
        } catch (Exception _) {}
    }

    public void confirmEdit() {
        try {
            Reservation tempReservation = reservationModel.getReservation();
            int unitID = Integer.parseInt(newUnitNumberText.getText());
            LocalDate date = datePicker.getValue();
            LocalTime startTime = startTimeChoiceBox.getSelectionModel().getSelectedItem();
            LocalTime endTime = endTimeChoiceBox.getSelectionModel().getSelectedItem();
            if (reservationModel.updateReservation(new Reservation(tempReservation.getUserID(), unitID,
                    tempReservation.getReservationID(), date, startTime, endTime))) {
                errorPrompt.setText("Edited successfully");
                errorPrompt.setStyle("-fx-text-fill: #07ff00");
            } else {
                errorPrompt.setText("Reservation is already existing");
                errorPrompt.setStyle("-fx-text-fill: #720808");
            }
        } catch (Exception _) {
            errorPrompt.setText("Please enter a valid unit number");
            unitPrompt.setStyle("-fx-text-fill: #720808");
        }
    }

    public void cancelEdit() {
        Stage stage = (Stage) bd.getScene().getWindow();
        stage.close();
    }

    protected boolean setUnitID() {
        try {
            String unitNumber = newUnitNumberText.getText();
            if (studentModel.isAvailableUnit(Integer.parseInt(unitNumber))) {
                unitPrompt.setText("Valid Unit Number");
                saveButton.setDisable(false);
                unitPrompt.setStyle("-fx-text-fill: #07ff00");
                saveButton.setStyle("-fx-background-color: #FFD60A");
                return false;
            } else {
                unitPrompt.setText("Invalid Unit Number");
                unitPrompt.setStyle("-fx-text-fill: #720808");
                saveButton.setDisable(true);
                saveButton.setStyle("-fx-background-color:#939393");
                return true;
            }
        } catch(Exception e) {
            unitPrompt.setText("Units: 1 - 50");
            unitPrompt.setStyle("-fx-text-fill: #720808");
            saveButton.setDisable(true);
            saveButton.setStyle("-fx-background-color:#939393");
            return true;
        }
    }

    private List<LocalTime> getAvailableStartTimes() {
        List<LocalTime> availableStartTimes = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate selectedDate = datePicker.getValue();

        if (selectedDate.isEqual(currentDate)) {
            LocalTime earliestStartTime = LocalTime.of(7, 0); // 7 AM
            availableStartTimes.add(earliestStartTime);
            for (int i = earliestStartTime.getHour() + 1; i <= 17; i++) {
                availableStartTimes.add(LocalTime.of(i, 0));
            }
        } else {
            LocalTime currentTime = LocalTime.now();
            for (int i = currentTime.getHour(); i <= 17; i++) {
                availableStartTimes.add(LocalTime.of(i, 0));
            }
        }
        return availableStartTimes;
    }
}
