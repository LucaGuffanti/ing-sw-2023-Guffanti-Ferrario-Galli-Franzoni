package it.polimi.ingsw.network.utils;

import it.polimi.ingsw.network.ServerNetworkHandler;
import it.polimi.ingsw.server.controller.GameController;

/**
 * This helper class implements static methods that log on the standard output a required message
 * @author Luca Guffanti
 */
public class Logger {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";

    private static final String UNDERLINED = "\u001B[4m";
    private static final String BOLD = "\033[1m";
    public static void networkInfo(String msg) {
        System.out.println(ANSI_GREEN+ ServerNetworkHandler.HOSTNAME +": "+msg+ANSI_RESET);
    }
    public static void networkWarning(String msg) {
        System.out.println(ANSI_YELLOW+ ServerNetworkHandler.HOSTNAME+": "+msg+ANSI_RESET);
    }
    public static void networkCritical(String msg) {
        System.out.println(ANSI_RED+ ServerNetworkHandler.HOSTNAME+": "+msg+ANSI_RESET);
    }

    public static void controllerInfo(String msg) {
        System.out.println(ANSI_CYAN+UNDERLINED+ GameController.NAME +": "+msg+ANSI_RESET);
    }
    public static void controllerWarning(String msg) {
        System.out.println(ANSI_YELLOW+UNDERLINED+ GameController.NAME+": "+msg+ANSI_RESET);
    }
    public static void controllerError(String msg) {
        System.out.println(ANSI_RED+UNDERLINED+ GameController.NAME+": "+msg+ANSI_RESET);
    }

    public static void externalInjection(String msg) {
        System.out.println(ANSI_PURPLE+BOLD+"EXTERNAL: "+msg+ANSI_RESET);
    }

    public static void pingerInfo(String msg) {
        System.out.println(ANSI_YELLOW+BOLD+"PINGER: "+msg+ANSI_RESET);
    }
}
