package server;

import interfaces.LoginManagerInterface;
import server.controller.ServerController;
import server.model.AccountManagerImpl;
import server.model.LoginManagerImpl;
import server.model.ReservationManagerImpl;
import server.view.ServerView;
import shared.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main() {
        try {
            Registry registry = LocateRegistry.createRegistry(Constants.PORT);
            LoginManagerInterface loginManager = new LoginManagerImpl();
            AccountManagerImpl accountManager = new AccountManagerImpl();
            ReservationManagerImpl reservationManager = new ReservationManagerImpl();
            ServerView serverView = new ServerView();
            ServerController serverController = new ServerController(serverView);

            registry.rebind("Login", loginManager);
            registry.rebind("Account", accountManager);
            registry.rebind("Reservation", reservationManager);
            serverController.status();
        } catch (Exception _) {}
    }
}
