package it.polimi.ingsw.client.view;

/**
 * Interface of the view. Implementations are CLI or GUI
 * @author Luca Guffanti, Daniele Ferrario
 */
public interface UserInterface {
    /**
     * This method runs the view
     */
    public abstract void run();

    /**
     * This method is called if the game is aborted
     */
    void onGameAborted();

    /**
     * This method displays the error message
     * @param msg the error message
     */
    void printErrorMessage(String msg);
}
