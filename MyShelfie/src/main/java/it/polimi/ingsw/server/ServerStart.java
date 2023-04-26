package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.utils.ServerConfigurationData;

/**
 * <h1>ServerStart</h1>
 * The main entry point of the server.
 * @author Luca Guffanti
 */
public class ServerStart {
    public static final String serverConfigPath = "src/config/serverConfig.json";
    public static void main(String[] args) {

        ServerConfigurationData data = new ServerConfigurationData();
        ServerNetworkHandler serverNetworkHandler = new ServerNetworkHandler(data);
        serverNetworkHandler.activateHandler();
    }
}
