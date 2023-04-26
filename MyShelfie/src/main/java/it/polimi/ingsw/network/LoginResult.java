package it.polimi.ingsw.network;

/**
 * This object refers to the possible results of the login of a client.
 * A client that's trying to log in can either be new or known
 * @author Luca Guffanti
 */
public class LoginResult {
    private boolean logged;
    private boolean reconnecting;

    public LoginResult(boolean logged, boolean reconnecting) {
        this.logged = logged;
        this.reconnecting = reconnecting;
    }

    public boolean isLogged() {
        return logged;
    }

    public boolean isReconnecting() {
        return reconnecting;
    }
}
