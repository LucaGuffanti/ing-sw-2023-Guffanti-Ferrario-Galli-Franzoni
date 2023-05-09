package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.constants.UIModesEnum;
import it.polimi.ingsw.client.view.gui.Gui;
import it.polimi.ingsw.network.utils.ClientNetworkConfigurationData;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * The client-side entry point of the game.
 * Once opened the client is asked to insert a selection for the interface (graphical or command line)
 * and for the technology used to connect to the server (Socket or RMI). Then, the client is activated.
 */
public class ClientMain {


    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        ClientNetworkConfigurationData clientNetworkConfigurationData = new ClientNetworkConfigurationData().get();
        String choiceString;
        int choice = 0;
        UIModesEnum chosenUI;

        System.out.println("Welcome, please choose between GUI technology and CLI technology");
        do {
            System.out.println("[1] GUI");
            System.out.println("[2] CLI");
            System.out.print("Your Choice: ");
            choiceString = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(choiceString);
            } catch (NumberFormatException e) {
                System.out.println("You inserted a string... Goodbye");
                System.exit(1);
            }
        } while(!(choice == 1 || choice == 2));
        chosenUI = choice== 1 ? UIModesEnum.GUI : UIModesEnum.CLI;

        System.out.println("Now please choose between Socket technology and RMI technology");
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
            clientManager = new ClientManager(chosenUI, clientNetworkConfigurationData.getServerIP(), clientNetworkConfigurationData.getServerPort());
        } else {
            clientManager = new ClientManager(chosenUI, clientNetworkConfigurationData.getServerIP(), clientNetworkConfigurationData.getServerRMIRegistry(), clientNetworkConfigurationData.getRMIPort());
        }

        clientManager.runUI();
    }
}
