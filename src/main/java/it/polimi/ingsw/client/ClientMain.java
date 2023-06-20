package it.polimi.ingsw.client;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.constants.UIModesEnum;
import it.polimi.ingsw.network.utils.NetworkConfigurationData;

import java.io.IOException;
import java.util.Scanner;

/**
 * The client-side entry point of the game.
 * Once opened the client is asked to insert a selection for the interface (graphical or command line)
 * and for the technology used to connect to the server (Socket or RMI). Then, the client is activated.
 */
public class ClientMain {


    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        NetworkConfigurationData networkConfigurationData = new NetworkConfigurationData().get();
        String choiceString;
        int choice = 0;
        UIModesEnum chosenUI;

        System.out.println("Welcome, please choose between GUI technology and CLI technology");
        System.out.println("[1] GUI");
        System.out.println("[2] CLI");
        do {
            System.out.print("Your Choice: ");
            choiceString = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(choiceString);
            } catch (NumberFormatException e) {
                System.out.println("Oops, you inserted a string!");
            }
            if (!(choice == 1 || choice == 2)) {
                System.out.println("Please, type [1] GUI or [2] CLI");
            }
        } while(!(choice == 1 || choice == 2));
        chosenUI = choice== 1 ? UIModesEnum.GUI : UIModesEnum.CLI;

        choice = 0;
        System.out.println("Now please choose between Socket technology and RMI technology");
        System.out.println("[1] Socket");
        System.out.println("[2] RMI");
        do {
            System.out.print("Your Choice: ");
            choiceString = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(choiceString);
            } catch (NumberFormatException e) {
                System.out.println("Oops, you inserted a string!");
            }
            if (!(choice == 1 || choice == 2)) {
                System.out.println("Please, type [1] Socket or [2] RMI");
            }
        } while(!(choice == 1 || choice == 2));

        ClientManager clientManager;

        if (choice == 1) {
            clientManager = new ClientManager(chosenUI, networkConfigurationData.getServerIP(), networkConfigurationData.getServerPort());
        } else {
            clientManager = new ClientManager(chosenUI, networkConfigurationData.getServerIP(), networkConfigurationData.getServerRMIRegistry(), networkConfigurationData.getRmiPort());
        }

        clientManager.runUI();
    }
}
