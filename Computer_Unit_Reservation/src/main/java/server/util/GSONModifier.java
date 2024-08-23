package server.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import shared.Account;
import shared.Reservation;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GSONModifier {

    private final Gson gson;

    public GSONModifier() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter())
                .registerTypeAdapter(Account.class, new AccountTypeAdapter()); // Register AccountTypeAdapter
        this.gson = gsonBuilder.create();
    }

    public void reservationWriter(Reservation reservation) {
        List<Reservation> reservations = reservationReader();
        reservations.add(reservation);
        writeReservationsToFile(reservations);
    }

    public List<Reservation> reservationReader() {
        List<Reservation> reservations = new ArrayList<>();
        try (Reader reader = new FileReader("res/reservations.json")) {
            Type reservationListType = new TypeToken<List<Reservation>>() {}.getType();
            reservations = gson.fromJson(reader, reservationListType);
        } catch (IOException _) {
        }
        return reservations;
    }

    public Account loggedInReader() {
        Account account = null;
        try (Reader reader = new FileReader("res/logged.json")) {
            Type accountType = new TypeToken<Account>() {}.getType();
            account = gson.fromJson(reader, accountType);
        } catch (IOException _) {
        }
        return account;
    }

    public void loggedInWriter(Account account) {
        try (Writer writer = new FileWriter("res/logged.json")) {
            gson.toJson(account, writer);
        } catch (IOException _) {
        }
    }

    public void accountWriter(Account account) {
        List<Account> accounts = accountReader();
        accounts.add(account);
        writeAccountsToFile(accounts);
    }

    public List<Account> accountReader() {
        List<Account> accounts = new ArrayList<>();
        try (Reader reader = new FileReader("res/accounts.json")) {
            Type accountListType = new TypeToken<List<Account>>() {}.getType();
            accounts = gson.fromJson(reader, accountListType);
        } catch (IOException _) {
        }
        return accounts;
    }

    public List<Account> adminReader() {
        List<Account> accounts = new ArrayList<>();
        try (Reader reader = new FileReader("res/admin.json")) {
            Type accountListType = new TypeToken<List<Account>>() {}.getType();
            accounts = gson.fromJson(reader, accountListType);
        } catch (IOException _) {
        }
        return accounts;
    }

    public void deleteAccount(String userID) {
        List<Account> accounts = accountReader();
        boolean removed = accounts.removeIf(account -> account.getUserID().equals(userID));
        if (removed) {
            writeAccountsToFile(accounts);
        }
    }

    public void deleteReservation(int reservationID) {
        List<Reservation> reservations = reservationReader();
        boolean removed = reservations.removeIf(reservation -> reservation.getReservationID() == reservationID);
        if (removed) {
            writeReservationsToFile(reservations);
        }
    }

    private void writeAccountsToFile(List<Account> accounts) {
        try (Writer writer = new FileWriter("res/accounts.json")) {
            gson.toJson(accounts, writer);
        } catch (IOException _) {
        }
    }

    private void writeReservationsToFile(List<Reservation> reservations) {
        try (Writer writer = new FileWriter("res/reservations.json")) {
            gson.toJson(reservations, writer);
        } catch (IOException _) {
        }
    }

    public void updateReservation(Reservation updatedReservation) {
        List<Reservation> reservations = reservationReader();
        for (int i = 0; i < reservations.size(); i++) {
            Reservation reservation = reservations.get(i);
            if (reservation.getReservationID() == updatedReservation.getReservationID()) {
                reservations.set(i, updatedReservation); // Replace the old reservation with the updated one
                writeReservationsToFile(reservations);
                return;
            }
        }
    }
}
