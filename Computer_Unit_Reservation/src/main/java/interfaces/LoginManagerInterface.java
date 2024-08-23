package interfaces;

import shared.Account;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LoginManagerInterface extends Remote {
    List<Account> loadStudents() throws RemoteException;
    List<Account> loadAdmin() throws RemoteException;
    boolean studentChecker(LoginCallbackInterface loginCallbackInterface) throws RemoteException;
    boolean adminChecker(LoginCallbackInterface loginCallbackInterface) throws RemoteException;
    void userWriter(LoginCallbackInterface loginCallbackInterface) throws RemoteException;
}
