package interfaces;

import shared.Account;
import shared.Reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AccountManagerInterface extends Remote {
    boolean createAccount(Account account) throws RemoteException;
    void deleteAccount(String userID) throws RemoteException;
    List<Account> loadAccounts() throws RemoteException;
    List<Reservation> loadReservations() throws RemoteException;
}
