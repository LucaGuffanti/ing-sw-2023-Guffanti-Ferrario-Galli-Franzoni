package it.polimi.ingsw.client.view.gui.controllers;

import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.network.ClientNetworkHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public interface GuiController{
    void render(ClientState state, ClientNetworkHandler clientNetworkHandler, Stage stage, Scene scene) throws IOException;
}
