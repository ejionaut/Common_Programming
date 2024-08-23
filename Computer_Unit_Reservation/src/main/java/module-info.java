module client{
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;
    requires java.rmi;
    requires java.base;

    opens client to javafx.fxml;
    exports client;

    opens client.model to javafx.fxml;
    exports client.model;

    opens client.controller to javafx.fxml;
    exports client.controller;

    opens client.view.login to javafx.fxml;
    exports client.view.login;

    opens client.view.student to javafx.fxml;
    exports client.view.student;

    opens client.view.admin to javafx.fxml;
    exports client.view.admin;

    opens shared;
    exports shared;

    opens interfaces;
    exports interfaces;
}