package client.model;

import interfaces.AccountManagerInterface;
import shared.Account;
import shared.Reservation;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.List;

public class AccountModel {
    AccountManagerInterface accountManager;

    public AccountModel(Registry registry) {
        try {
            accountManager = (AccountManagerInterface) registry.lookup("Account");
        } catch (Exception _) {}
    }

    public boolean createAccount(Account account) throws RemoteException {
        return accountManager.createAccount(account);
    }

    public void deleteAccount(String userID) throws RemoteException {
        accountManager.deleteAccount(userID);
    }

    public List<Account> loadAccounts() throws RemoteException {
        return accountManager.loadAccounts();
    }

    public List<Reservation> loadReservations() throws RemoteException {
        return  accountManager.loadReservations();
    }
}
