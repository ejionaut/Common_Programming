package server.model;

import interfaces.ReservationManagerInterface;
import server.util.GSONModifier;
import shared.Account;
import shared.Reservation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationManagerImpl extends UnicastRemoteObject implements ReservationManagerInterface {
    GSONModifier gsonModifier = new GSONModifier();
    Reservation reservation;

    public ReservationManagerImpl() throws RemoteException {}

    @Override
    public boolean createReservation(int unitID, LocalDate date, LocalTime startTime, LocalTime endTime) throws RemoteException {
        Account account = gsonModifier.loggedInReader();
        if (isDuplicate(unitID, date, startTime)) {
            Reservation reservation = new Reservation(Integer.parseInt(account.getUserID()), unitID, createReservationID(), date, startTime, endTime);
            gsonModifier.reservationWriter(reservation);
            return true;
        }
        return false;
    }

    @Override
    public List<Reservation> loadReservations() throws RemoteException {
        List<Reservation> reservationList = gsonModifier.reservationReader();
        Account loggedIn = gsonModifier.loggedInReader();
        reservationList.removeIf(reservation -> reservation.getUserID() != Integer.parseInt(loggedIn.getUserID()));
        return reservationList;
    }

    public List<Reservation> loadAllReservations() throws RemoteException {
        return gsonModifier.reservationReader();
    }

    @Override
    public boolean updateReservation(Reservation reservation) throws RemoteException {
        Reservation selectedReservation = getReservation();
        if (isDuplicate(reservation.getUnitID(), reservation.getDate(), reservation.getStartTime())) {
            if (selectedReservation.getReservationID() == reservation.getReservationID()) {
                gsonModifier.updateReservation(reservation);
                return true;
            }
        }
        return false;
    }

    @Override
    public void deleteReservation(Reservation reservation) throws RemoteException {
        List<Reservation> reservationList = loadReservations();
        for (Reservation tempReservation : reservationList) {
            if (tempReservation.getReservationID() == reservation.getReservationID()) {
                gsonModifier.deleteReservation(reservation.getReservationID());
                return;
            }
        }
    }

    @Override
    public Reservation searchReservation(int reservationID) throws RemoteException {
        List<Reservation> existingReservations = loadReservations();
        for (Reservation reservation : existingReservations) {
            if (reservation.getReservationID() == reservationID) {
                return reservation;
            }
        }
        return null;
    }

    @Override
    public void setReservation(Reservation reservation) throws RemoteException {
        this.reservation = reservation;
    }

    @Override
    public Reservation getReservation() throws RemoteException {
        return reservation;
    }

    public boolean isDuplicate (int unitID, LocalDate date, LocalTime startTime) throws RemoteException {
        List<Reservation> existingReservations = loadAllReservations();
        if (existingReservations == null) {
            return false;
        }
        for (Reservation tempReservation : existingReservations) {
            if (tempReservation.getUnitID() == unitID
            && tempReservation.getDate().equals(date)
            && tempReservation.getStartTime().equals(startTime)) {
                return false;
            }
        }
        return true;
    }

    public int createReservationID() throws RemoteException {
        List<Reservation> existingReservations = loadAllReservations();
        int maxReservationID = 0;
        for (Reservation reservation : existingReservations) {
            maxReservationID = Math.max(maxReservationID, reservation.getReservationID());
        }
        return maxReservationID + 1;
    }
}
