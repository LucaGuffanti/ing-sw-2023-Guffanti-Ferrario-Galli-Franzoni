package it.polimi.ingsw.network.utils;

import com.google.gson.Gson;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Object containing the configuration of the server, taken from the serverConfig json in src/config.
 * This object contains the ip address of the server and the
 * @author Luca Guffanti
 */
public class ServerConfigurationData {
    /**
     * The ip address of the server
     */
    private String ipAddress;
    /**
     * The socket port
     */
    private int socketPort;
    /**
     * The RMI service name
     */
    private String RMIRegistryServiceName;
    /**
     * The RMI port
     */
    private int rmiPort;
    public ServerConfigurationData() {
        try {
            ipAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            Logger.networkCritical("Couldn't get the IP Address");
            throw new RuntimeException(e);
        }

        socketPort = 5000;
        rmiPort = 1099;
        RMIRegistryServiceName = "MyShelfieServer";
    }

    public ServerConfigurationData(String ipAddress, int socketPort, String RMIRegistryServiceName) {
        this.ipAddress = ipAddress;
        this.socketPort = socketPort;
        this.RMIRegistryServiceName = RMIRegistryServiceName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public int getSocketPort() {
        return socketPort;
    }

    public String getRMIRegistryServiceName() {
        return RMIRegistryServiceName;
    }

    public int getRmiPort() {
        return rmiPort;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
