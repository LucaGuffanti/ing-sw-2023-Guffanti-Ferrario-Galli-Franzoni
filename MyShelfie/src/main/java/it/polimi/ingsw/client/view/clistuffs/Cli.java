package it.polimi.ingsw.client.view.clistuffs;


import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.network.ClientNetworkHandler;
import it.polimi.ingsw.network.messages.Message;

import java.util.Map;
public class Cli {
        private StateContainer stateContainer;
        private ClientNetworkHandler networkHandler;
        private Map<String, Command> inputCommandMap;
        private Map<ClientStatusEnum, CliView> defaultViewPerStatusMap;

        public Cli(StateContainer stateContainer, ClientNetworkHandler networkHandler, Map<String, Command> inputCommandMap, Map<ClientStatusEnum, CliView> defaultViewPerStatusMap) {
            this.stateContainer = stateContainer;
            this.networkHandler = networkHandler;
            this.inputCommandMap = inputCommandMap;
            this.defaultViewPerStatusMap = defaultViewPerStatusMap;
        }

        public StateContainer getStateContainer() {
            return stateContainer;
        }

        public void setStateContainer(StateContainer stateContainer) {
            this.stateContainer = stateContainer;
        }

        public ClientNetworkHandler getNetworkHandler() {
            return networkHandler;
        }

        public void setNetworkHandler(ClientNetworkHandler networkHandler) {
            this.networkHandler = networkHandler;
        }

        public Map<String, Command> getInputCommandMap() {
            return inputCommandMap;
        }

        public void setInputCommandMap(Map<String, Command> inputCommandMap) {
            this.inputCommandMap = inputCommandMap;
        }

        public Map<ClientStatusEnum, CliView> getDefaultViewPerStatusMap() {
            return defaultViewPerStatusMap;
        }

        public void setDefaultViewPerStatusMap(Map<ClientStatusEnum, CliView> defaultViewPerStatusMap) {
            this.defaultViewPerStatusMap = defaultViewPerStatusMap;
        }

        private void evaluateUserInput(String userInput) {
            return;
        }

        public void onViewChangeRequest(CliView view) {
            return;
        }

        public void onDispatchMessageToServerRequest(Message m) {
            return;
        }

}
