package server.controller;

import server.view.ServerView;

public class ServerController {
    ServerView serverView;

    public ServerController(ServerView serverView) {
        this.serverView = serverView;
    }

    public void status() {
        serverView.status();
    }
}
