package server.model;

import interfaces.LoginCallbackInterface;
import interfaces.LoginManagerInterface;
import server.util.GSONModifier;
import shared.Account;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class LoginManagerImpl extends UnicastRemoteObject implements LoginManagerInterface {
    private final GSONModifier gsonModifier = new GSONModifier();

    public LoginManagerImpl() throws RemoteException {}

    @Override
    public List<Account> loadStudents() throws RemoteException {
        return gsonModifier.accountReader();
    }

    @Override
    public List<Account> loadAdmin() throws RemoteException {
        return gsonModifier.adminReader();
    }

    @Override
    public boolean studentChecker(LoginCallbackInterface loginCallbackInterface) throws RemoteException {
        Account account = loginCallbackInterface.getAccount();
        List<Account> accounts = loadStudents();
        for (Account acc : accounts) {
            if (acc.getUserID().equals(account.getUserID()) && acc.getPassword().equals(account.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean adminChecker(LoginCallbackInterface loginCallbackInterface) throws RemoteException {
        Account account = loginCallbackInterface.getAccount();
        List<Account> accounts = loadAdmin();
        for (Account acc : accounts) {
            if (acc.getUserID().equals(account.getUserID()) && acc.getPassword().equals(account.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void userWriter(LoginCallbackInterface loginCallbackInterface) throws RemoteException {
        Account account = loginCallbackInterface.getAccount();
        if (!account.getUserID().equals("admin")) {
            gsonModifier.loggedInWriter(loginCallbackInterface.getAccount());
        }
    }
}
