package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.constants.UIModesEnum;
import it.polimi.ingsw.network.utils.ClientNetworkConfigurationData;

import java.rmi.RemoteException;

public class ClientMain {

    public static void main(String[] args) throws RemoteException {

        ClientNetworkConfigurationData clientNetworkConfigurationData = new ClientNetworkConfigurationData().get();
        ClientManager clientManager = new ClientManager(UIModesEnum.CLI, clientNetworkConfigurationData.getServerIP(), clientNetworkConfigurationData.getServerPort());
        clientManager.runUI();
    }
}
