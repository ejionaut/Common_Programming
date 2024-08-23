package client.model;

import interfaces.ReservationManagerInterface;
import shared.Reservation;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationModel {
    private ReservationManagerInterface reservationManager;

    public ReservationModel(Registry registry) {
        try {
            reservationManager = (ReservationManagerInterface) registry.lookup("Reservation");
        } catch (Exception _) {
        }
    }

    public boolean createReservation(int unitID, LocalDate date, LocalTime startTime, LocalTime endTime) throws RemoteException {
        return reservationManager.createReservation(unitID, date, startTime, endTime);
    }

    public List<Reservation> loadReservations() throws RemoteException {
        return reservationManager.loadReservations();
    }

    public boolean updateReservation(Reservation reservation) throws RemoteException {
        return reservationManager.updateReservation(reservation);
    }

    public void deleteReservation(Reservation reservation) throws RemoteException {
        reservationManager.deleteReservation(reservation);
    }

    public Reservation searchReservation(int reservationID) throws RemoteException {
        return reservationManager.searchReservation(reservationID);
    }

    public void setReservation(Reservation reservation) throws RemoteException {
        reservationManager.setReservation(reservation);
    }

    public Reservation getReservation() throws RemoteException {
        return reservationManager.getReservation();
    }
}
