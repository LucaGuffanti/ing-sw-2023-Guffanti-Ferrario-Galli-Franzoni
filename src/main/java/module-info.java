module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.rmi;
    requires java.desktop;
    requires jdk.jfr;
    requires com.google.gson;
    requires com.opencsv;

    opens it.polimi.ingsw.client.view.gui to javafx.fxml, javafx.media;
    exports it.polimi.ingsw.client.view.gui;
    opens it.polimi.ingsw.client.view.gui.controllers to javafx.fxml;
    exports it.polimi.ingsw.client.view.gui.controllers;
    opens it.polimi.ingsw.client to java.rmi;
    exports it.polimi.ingsw.client;
    opens it.polimi.ingsw.client.controller.stateController to java.desktop;
    exports it.polimi.ingsw.client.controller.stateController;
    opens it.polimi.ingsw.client.view.cli to jdk.jfr;
    exports it.polimi.ingsw.client.view.cli;
    opens it.polimi.ingsw.network.utils to com.google.gson;
    exports it.polimi.ingsw.network.utils;
    opens it.polimi.ingsw.network.rmi to java.rmi;
    exports it.polimi.ingsw.network.rmi;
    opens it.polimi.ingsw.server.model.utils to com.opencsv;
    exports it.polimi.ingsw.server.model.utils;

    opens it.polimi.ingsw.server.model to com.google.gson;
    exports it.polimi.ingsw.server.model;
    opens it.polimi.ingsw.server.model.player to com.google.gson;
    exports it.polimi.ingsw.server.model.player;
    opens it.polimi.ingsw.server.model.cells to com.google.gson;
    exports it.polimi.ingsw.server.model.cells;
    opens it.polimi.ingsw.server.model.cards to com.google.gson;
    exports it.polimi.ingsw.server.model.cards;
    opens it.polimi.ingsw.server.model.cards.goalCards to com.google.gson;
    exports it.polimi.ingsw.server.model.cards.goalCards;
    opens it.polimi.ingsw.server.controller.save to com.google.gson;
    exports it.polimi.ingsw.server.controller.save;
    opens it.polimi.ingsw.server.controller.utils to com.google.gson;
    exports it.polimi.ingsw.server.controller.utils;

}