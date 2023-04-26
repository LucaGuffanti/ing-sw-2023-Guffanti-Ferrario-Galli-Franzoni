package it.polimi.ingsw.network.utils;

import com.google.gson.Gson;
import it.polimi.ingsw.network.ClientConnection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class that retrieves information regarding the network-related configuration of the client (
 * socket of the server and rmi interface)
 * @author Luca Guffanti
 */
public class ClientNetworkConfigurationData {
    private String serverIP;
    private int serverPort;
    private String serverRMIRegistry;
    private int rmiPort;

    public ClientNetworkConfigurationData(ClientNetworkConfigurationData data) {
        this.serverIP = data.getServerIP();
        this.serverPort = data.getServerPort();
        this.serverRMIRegistry = data.getServerRMIRegistry();
        this.rmiPort = data.getRMIPort();
    }

    public int getRMIPort() {
        return rmiPort;
    }

    public ClientNetworkConfigurationData(){
    }

    public ClientNetworkConfigurationData get() {
        final String path = "config/serverInfo.json";
        Gson g = new Gson();
        String data = null;
        try {
            data = Files.readString(Path.of(path));
            return new ClientNetworkConfigurationData(g.fromJson(data, ClientNetworkConfigurationData.class));
        } catch (IOException e) {
            System.out.println("The file was not found");
        }
        return null;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerRMIRegistry() {
        return serverRMIRegistry;
    }

}
