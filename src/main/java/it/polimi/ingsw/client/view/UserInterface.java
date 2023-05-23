package it.polimi.ingsw.client.view;

import java.util.Observable;
import java.util.Observer;

public interface UserInterface {
    public abstract void run();

    void onGameAborted();
}
