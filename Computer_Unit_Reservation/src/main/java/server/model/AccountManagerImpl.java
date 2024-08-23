package server.model;

import interfaces.AccountManagerInterface;
import server.util.GSONModifier;
import shared.Account;
import shared.Reservation;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AccountManagerImpl extends UnicastRemoteObject implements AccountManagerInterface {
    GSONModifier gsonModifier = new GSONModifier();

    public AccountManagerImpl() throws RemoteException {}

    @Override
    public boolean createAccount(Account account) throws RemoteException {
        if (!isDuplicate(account)) {
            gsonModifier.accountWriter(account);
            return true;
        }
        return false;
    }

    @Override
    public void deleteAccount(String userID) throws RemoteException {
        List<Account> existingAccounts = loadAccounts();
        for (Account existingAccount : existingAccounts) {
            if (existingAccount.getUserID().equals(userID)) {
                gsonModifier.deleteAccount(userID);
                return;
            }
        }
    }

    @Override
    public List<Account> loadAccounts() throws RemoteException {
        return gsonModifier.accountReader();
    }

    @Override
    public List<Reservation> loadReservations() throws RemoteException {
        return gsonModifier.reservationReader();
    }

    public boolean isDuplicate(Account account) throws RemoteException {
        List<Account> existingAccounts = loadAccounts();
        for (Account existingAccount : existingAccounts) {
            if (existingAccount.getUserID().equals(account.getUserID())) {
                return true;
            }
        }
        return false;
    }
}
