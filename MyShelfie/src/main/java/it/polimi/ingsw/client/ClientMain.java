package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.constants.UIModesEnum;
import it.polimi.ingsw.network.utils.ClientNetworkConfigurationData;

import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws RemoteException {

        Scanner sc = new Scanner(System.in);
        ClientNetworkConfigurationData clientNetworkConfigurationData = new ClientNetworkConfigurationData().get();
        System.out.println("Welcome, please choose between Socket technology and RMI technology");
        String choiceString;
        int choice = 0;

        do {
            System.out.println("[1] Socket");
            System.out.println("[2] RMI");
            System.out.print("Your Choice: ");
            choiceString = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(choiceString);
            } catch (NumberFormatException e) {
                System.out.println("You inserted a string... Goodbye");
                System.exit(1);
            }
        } while(!(choice == 1 || choice == 2));

        ClientManager clientManager;

        if (choice == 1) {
            clientManager = new ClientManager(UIModesEnum.CLI, clientNetworkConfigurationData.getServerIP(), clientNetworkConfigurationData.getServerPort());
        } else {
             clientManager = new ClientManager(UIModesEnum.CLI, clientNetworkConfigurationData.getServerIP(), clientNetworkConfigurationData.getServerRMIRegistry(), clientNetworkConfigurationData.getRMIPort());
        }

        clientManager.runUI();
    }
}
