package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.network.utils.ServerConfigurationData;

/**
 * <h1>ServerStart</h1>
 * The main entry point of the server. Network interface information is automatically retrieved
 * @apiNote <b>IN CASE OF PRESENCE OF DIFFERENT NETWORK INTERFACES IN THE SYSTEM RUNNING THE SERVER,
 * THE METHOD THAT RETRIEVES NETWORK INFORMATION MAY BEHAVE IN UNEXPECTED WAYS (due to the jdk implementation
 * of {@code getLocalHost()}), ACTIVATING THE SERVER
 * ON THE WRONG IP ADDRESS.</b>
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
