package it.polimi.ingsw.network.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Class that retrieves information regarding the network-related configuration of the client (
 * socket of the server and rmi interface)
 * @author Luca Guffanti
 */
public class NetworkConfigurationData {
    private String serverIP;
    private int serverPort;
    private String serverRMIRegistry;
    private int rmiPort;

    public NetworkConfigurationData(NetworkConfigurationData data) {
        this.serverIP = data.getServerIP();
        this.serverPort = data.getServerPort();
        this.serverRMIRegistry = data.getServerRMIRegistry();
        this.rmiPort = data.getRmiPort();
    }

    public NetworkConfigurationData(){
    }

    public NetworkConfigurationData get() {
        final String path = "config/serverInfo.json";
        Gson g = new Gson();
        String data = null;
        try {
            data = Files.readString(Path.of(path));
            NetworkConfigurationData d = g.fromJson(data, NetworkConfigurationData.class);
            if (data != null) {
                System.out.println("Found existing network configuration data");
                System.out.println("> SERVER IP     : "+ d.getServerIP());
                System.out.println("> SOCKER PORT   : "+ d.getServerPort());
                System.out.println("> REGISTRY NAME : "+ d.getServerRMIRegistry());
                System.out.println("> RMI PORT      : "+ d.getRmiPort());
                System.out.println("\n\nWould you like to use this configuration?");
                System.out.println("[1] Yes");
                System.out.println("[2] No");
                System.out.print("Choice: ");
                Scanner sc = new Scanner(System.in);
                int choice = sc.nextInt();
                if (choice != 1) {
                    return requestNetworkData(path);
                } else {
                    return d;
                }

            }
        } catch (Exception e) {
            System.out.println("It seems like you don't have a network configuration file. Let me prepare it" +
                    "for you");
            return requestNetworkData(path);
        }
        return null;
    }

    private NetworkConfigurationData requestNetworkData(String path) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Please choose the ip of the server               : ");
        serverIP = scanner.nextLine().trim();
        System.out.print("Please choose the name of the rmi registry       : ");
        serverRMIRegistry = scanner.nextLine();
        System.out.print("Please choose the port of the socket connection  : ");
        serverPort = scanner.nextInt();
        System.out.print("Please choose the port of the rmi registry       : ");
        rmiPort = scanner.nextInt();

        System.out.println("==========RECAP==========");
        System.out.println("> SERVER IP     : "+ serverIP);
        System.out.println("> SOCKER PORT   : "+ serverPort);
        System.out.println("> REGISTRY NAME : "+ serverRMIRegistry);
        System.out.println("> RMI PORT      : "+ rmiPort);
        System.out.println("=========================");


        File f = new File(path);
        try {
            FileWriter writer = new FileWriter(f);
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(this));
            writer.close();
        } catch (IOException e) {
            System.out.println("COULD NOT SAVE THIS INFORMATION TO A FILE.");
        }

        return this;
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

    public int getRmiPort() {
        return rmiPort;
    }
}
