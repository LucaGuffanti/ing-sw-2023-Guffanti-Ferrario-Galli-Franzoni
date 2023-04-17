package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.client.controller.messagesHandlers.LoginHandler;
import it.polimi.ingsw.client.view.UserInterface;

/**
 * The ClientManager acts as a controller layer above the UserInterface. Here
 * there are the methods to initialize the different UIs.
 */
public class ClientManager {
    private StateContainer stateContainer;
    private NetworkHandler networkHandler;
    private UserInterface userInterface;


}
