package client.view.student;

import client.model.ReservationModel;
import client.model.StudentModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import shared.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateReservationFormFX {
    public BorderPane bp;
    public Button submitButton;
    public ChoiceBox<LocalTime> startTimeChoiceBox;
    public ChoiceBox<LocalTime> endTimeChoiceBox;
    public DatePicker datePicker;
    public Label errorPrompt;
    public Label unitPrompt;
    public ChoiceBox<String> subjectTextBox;
    public ChoiceBox<String> purposeTextBox;
    public TextField unitNumberBox;
    public Button selectAvailUnitButton;
    public Button backButton;
    Registry registry;
    ReservationModel reservationModel;
    private final StudentModel studentModel = new StudentModel();

    public CreateReservationFormFX () {
        try {
            registry = LocateRegistry.getRegistry(Constants.HOST, Constants.PORT);
            reservationModel= new ReservationModel(registry);
        } catch (Exception _) {}
    }

    public void initialize() {
        subjectTextBox.setItems(FXCollections.observableArrayList(studentModel.getSubjects()));
        purposeTextBox.setItems(FXCollections.observableArrayList(studentModel.getPurpose()));
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
        selectAvailUnitButton.setOnAction(_ -> setUnitID());
        if (setUnitID()) {
            submitButton.setOnAction(_ -> setReservation());
        }
    }

    public void toReservationPane() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/student/reservationPane.fxml")));
            bp.setCenter(root);
            bp.setBottom(null);
        } catch (Exception _) {
        }
    }

    protected void setReservation() {
        try{
            String subject = subjectTextBox.getSelectionModel().getSelectedItem();
            String purpose = purposeTextBox.getSelectionModel().getSelectedItem();
            String unitNumber = unitNumberBox.getText();
            LocalDate date = datePicker.getValue();
            LocalTime startTime = startTimeChoiceBox.getSelectionModel().getSelectedItem();
            LocalTime endTime = endTimeChoiceBox.getSelectionModel().getSelectedItem();
            if (subject == null || purpose == null || unitNumber == null || date == null || startTime == null || endTime == null) {
                errorPrompt.setText("Please fill out all fields");
                errorPrompt.setStyle("-fx-text-fill: #720808");
            } else {
                if(reservationModel.createReservation(Integer.parseInt(unitNumber), date, startTime, endTime)) {
                    errorPrompt.setText("Reservation created successfully");
                    errorPrompt.setStyle("-fx-text-fill: #07ff00");
                } else {
                    errorPrompt.setText("Reservation already exists");
                    errorPrompt.setStyle("-fx-text-fill: #720808");
                }
                submitButton.setStyle("-fx-background-color: #FFD60A");
            }
        } catch (Exception _) {
        }
    }

    protected boolean setUnitID() {
        try {
            String unitNumber = unitNumberBox.getText();
            if (studentModel.isAvailableUnit(Integer.parseInt(unitNumber))) {
                unitPrompt.setText("Valid Unit Number");
                submitButton.setDisable(false);
                unitPrompt.setStyle("-fx-text-fill: #07ff00");
                submitButton.setStyle("-fx-background-color: #FFD60A");
                return false;
            } else {
                unitPrompt.setText("Invalid Unit Number");
                unitPrompt.setStyle("-fx-text-fill: #720808");
                submitButton.setDisable(true);
                submitButton.setStyle("-fx-background-color:#939393");
                return true;
            }
        } catch(Exception e) {
            unitPrompt.setText("Units: 1 - 50");
            unitPrompt.setStyle("-fx-text-fill: #720808");
            submitButton.setDisable(true);
            submitButton.setStyle("-fx-background-color:#939393");
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
