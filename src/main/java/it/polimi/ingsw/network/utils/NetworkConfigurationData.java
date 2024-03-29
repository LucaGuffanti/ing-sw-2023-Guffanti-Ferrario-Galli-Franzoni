package it.polimi.ingsw.network.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Class that retrieves information regarding the network-related configuration of the client (
 * socket of the server and rmi interface) by either reading an apposite file or by requesting the user to insert
 * network data
 * @author Luca Guffanti
 */
public class NetworkConfigurationData {
    /**
     * Ip address of the server
     */
    private String serverIP;
    /**
     * Port of the server
     */
    private int serverPort;
    /**
     * The rmi registry service name
     */
    private String serverRMIRegistry;
    /**
     * The rmi port
     */
    private int rmiPort;

    public NetworkConfigurationData(NetworkConfigurationData data) {
        this.serverIP = data.getServerIP();
        this.serverPort = data.getServerPort();
        this.serverRMIRegistry = data.getServerRMIRegistry();
        this.rmiPort = data.getRmiPort();
    }

    public NetworkConfigurationData(){
    }

    /**
     * This method generates network configuration data
     * @return data regarding a network configuration
     */
    public NetworkConfigurationData get() {
        final String directoryPath = "./MyShelfieClientData/";
        final String configFilePath = "./MyShelfieClientData/config.json";

        Gson g = new Gson();
        String data = null;

        // checking if the directory exists
        try {
            if (!Files.exists(Path.of(directoryPath))) {
                Files.createDirectories(Path.of(directoryPath));
            }
        } catch (IOException e) {
            System.out.println("Could not create directory for the configuration file");
            System.exit(1);
        }

        try {
            data = Files.readString(Path.of(configFilePath));
            NetworkConfigurationData d = g.fromJson(data, NetworkConfigurationData.class);
            if (data != null) {
                System.out.println("Found existing network configuration data");
                System.out.println("> SERVER IP     : "+ d.getServerIP());
                System.out.println("> SOCKER PORT   : "+ d.getServerPort());
                System.out.println("> REGISTRY NAME : "+ d.getServerRMIRegistry());
                System.out.println("> RMI PORT      : "+ d.getRmiPort());
                System.out.println("\nWould you like to use this configuration?");
                System.out.println("[1] Yes");
                System.out.println("[2] No");
                int choice = 0;
                String choiceString;
                Scanner sc = new Scanner(System.in);
                do {
                    System.out.print("Your Choice: ");
                    choiceString = sc.nextLine().trim();
                    try {
                        choice = Integer.parseInt(choiceString);
                    } catch (NumberFormatException e) {
                        System.out.println("Oops, you inserted a string!");
                    }
                    if (!(choice == 1 || choice == 2)) {
                        System.out.println("Please, type [1] Yes or [2] No");
                    }
                } while(!(choice == 1 || choice == 2));
                if (choice == 1) {
                    return d;
                } else {
                    return requestNetworkData(configFilePath);
                }

            }
        } catch (Exception e) {
            System.out.println("It seems like you don't have a network configuration file. Let me prepare it " +
                    "for you");
            return requestNetworkData(configFilePath);
        }
        return null;
    }

    /**
     * This method asks the user to insert network-related data (ip, ports, service name) and saves them to a file.
     * @param path path to a file in which to save the data requested to the user
     * @return the network data
     */
    private NetworkConfigurationData requestNetworkData(String path) {
        Scanner scanner = new Scanner(System.in);
        boolean flag;
        System.out.print("Please choose the ip of the server (192.168.xxx.xxx)         : ");
        serverIP = scanner.nextLine().trim();
        do {
            System.out.print("Please choose the port of the socket connection (5000)       : ");
            flag = false;
            String serverPortString = scanner.nextLine().trim();
            try {
                serverPort = Integer.parseInt(serverPortString);
            } catch (NumberFormatException e) {
                System.out.println("Oops, you inserted a string!");
                flag = true;
            }
        } while (flag);
        System.out.print("Please choose the name of the rmi registry (MyShelfieServer) : ");
        serverRMIRegistry = scanner.nextLine();
        do {
            System.out.print("Please choose the port of the rmi registry (1099)            : ");
            flag = false;
            String rmiPortString = scanner.nextLine().trim();
            try {
                rmiPort = Integer.parseInt(rmiPortString);
            } catch (NumberFormatException e) {
                System.out.println("Oops, you inserted a string!");
                flag = true;
            }
        } while (flag);

        System.out.println("==============RECAP==============");
        System.out.println("> SERVER IP     : "+ serverIP);
        System.out.println("> SOCKER PORT   : "+ serverPort);
        System.out.println("> REGISTRY NAME : "+ serverRMIRegistry);
        System.out.println("> RMI PORT      : "+ rmiPort);
        System.out.println("=================================");


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
