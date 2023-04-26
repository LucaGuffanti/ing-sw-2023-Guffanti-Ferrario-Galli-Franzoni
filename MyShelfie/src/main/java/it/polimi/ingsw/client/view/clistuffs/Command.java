package it.polimi.ingsw.client.view.clistuffs;

import it.polimi.ingsw.network.messages.Message;

import java.util.Optional;
import java.util.Set;

public abstract class Command {
    private Set<ClientStatusEnum> availableStatues;
    private String commandLabel;
    private String commandDescription;
    private Cli cli;

    public Command(Set<ClientStatusEnum> availableStatues, String commandLabel, String commandDescription, Cli cli) {
        this.availableStatues = availableStatues;
        this.commandLabel = commandLabel;
        this.commandDescription = commandDescription;
        this.cli = cli;
    }

    /*public Optional<Message> evaluate(String commandInput){
        return ;
    }
    */

    protected boolean areParametersValid(String userInput){
        return true;
    }
}
