package interfaces;

import shared.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationManagerInterface extends Remote {
    boolean createReservation(int unitID, LocalDate date, LocalTime startTime, LocalTime endTime) throws RemoteException;
    List<Reservation> loadReservations() throws RemoteException;
    boolean updateReservation(Reservation reservation) throws RemoteException;
    void deleteReservation(Reservation reservation) throws RemoteException;
    Reservation searchReservation(int reservationID) throws RemoteException;
    void setReservation(Reservation reservation) throws RemoteException;
    Reservation getReservation() throws RemoteException;
}
