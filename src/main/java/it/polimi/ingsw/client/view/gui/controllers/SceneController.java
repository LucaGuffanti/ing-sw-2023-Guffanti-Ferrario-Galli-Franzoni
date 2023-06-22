package it.polimi.ingsw.client.view.gui.controllers;

/**
 * The interface of scene controllers.
 * @author Marco Galli
 */
public interface SceneController {
    /**
     * This method sets the error message in the label for error messages
     * @param message the error message
     */
    public abstract void setLabelErrorMessage(String message);

    /**
     * This method allows the volume slider to be set
     * @param volume the media player volume
     */
    public abstract void setSliderVolume(double volume);
}
