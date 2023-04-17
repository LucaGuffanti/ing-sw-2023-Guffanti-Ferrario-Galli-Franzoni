package it.polimi.ingsw.client.view;

import java.util.Observable;
import java.util.Observer;

public abstract class UserInterface implements Observer {
    public abstract void run();

    @Override
    public abstract void update(Observable o, Object arg);

}
