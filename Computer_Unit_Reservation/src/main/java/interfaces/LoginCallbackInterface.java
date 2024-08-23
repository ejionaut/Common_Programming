package interfaces;

import shared.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoginCallbackInterface extends Remote {
    Account getAccount() throws RemoteException;
}
