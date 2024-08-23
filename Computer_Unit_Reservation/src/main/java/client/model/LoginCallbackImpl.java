package client.model;

import interfaces.LoginCallbackInterface;
import shared.Account;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class LoginCallbackImpl extends UnicastRemoteObject implements LoginCallbackInterface, Serializable {
    Account account;

    public LoginCallbackImpl(Account account) throws RemoteException {
        this.account = account;
    }

    @Override
    public Account getAccount() throws RemoteException {
        return account;
    }
}
