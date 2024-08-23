package shared;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Reservation implements Serializable {
    private int userID;
    private int reservationID;
    private int unitID;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public Reservation(int userID, int unitID, int reservationID, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.userID = userID;
        this.unitID = unitID;
        this.reservationID = reservationID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getUserID() {
        return userID;
    }

    public int getReservationID() {
        return reservationID;
    }

    public int getUnitID() {
        return unitID;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
