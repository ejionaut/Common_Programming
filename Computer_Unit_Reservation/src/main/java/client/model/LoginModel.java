package client.model;

import interfaces.LoginManagerInterface;
import shared.Account;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class LoginModel {
    private LoginManagerInterface loginManager;
    private LoginCallbackImpl loginCallback = null;
    
    public LoginModel(Registry registry) {
        try {
            loginManager = (LoginManagerInterface) registry.lookup("Login");
        } catch (Exception _) {
        }
    }
    
    public boolean studentChecker(Account account) throws RemoteException {
        loginCallback = new LoginCallbackImpl(account);
        return loginManager.studentChecker(loginCallback);
    }

    public boolean adminChecker(Account account) throws RemoteException {
        loginCallback = new LoginCallbackImpl(account);
        return loginManager.adminChecker(loginCallback);
    }

    public void setUserID(Account account) throws RemoteException {
        loginCallback = new LoginCallbackImpl(account);
        loginManager.userWriter(loginCallback);
    }
}
