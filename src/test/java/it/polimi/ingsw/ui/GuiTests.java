package it.polimi.ingsw.ui;

import it.polimi.ingsw.client.controller.ClientManager;
import it.polimi.ingsw.client.controller.ClientPhasesEnum;
import it.polimi.ingsw.client.controller.constants.UIModesEnum;
import it.polimi.ingsw.client.controller.stateController.ClientState;
import it.polimi.ingsw.client.controller.stateController.StateContainer;
import it.polimi.ingsw.network.utils.NetworkConfigurationData;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.PointCard;
import it.polimi.ingsw.server.model.cards.PointEnumeration;
import it.polimi.ingsw.server.model.cards.goalCards.SimplifiedCommonGoalCard;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.*;

public class GuiTests {


    public void scene4_test() throws RemoteException {
        // Network
        NetworkConfigurationData networkConfigurationData = new NetworkConfigurationData().get();
        String serverIp = networkConfigurationData.getServerIP();
        int serverPort = networkConfigurationData.getServerPort();

        // State
        StateContainer sc = new StateContainer(createPickFromBoardState());

        ClientManager cm = new ClientManager(UIModesEnum.GUI, serverIp, serverPort, sc);

        cm.runUI();

    }

    private ClientState createPickFromBoardState(){

        String user1 = "TesterUser1";
        String user2 = "TesterUser2";
        String user3 = "TesterUser3";
        String user4 = "TesterUser4";

        ClientState state = new ClientState();
        state.setUsername(user1);
        HashMap<String, Integer> nameToPointMap = new HashMap<>();
        nameToPointMap.put(user1, 0);
        nameToPointMap.put(user2, 0);
        nameToPointMap.put(user3, 0);
        nameToPointMap.put(user4, 0);
        state.setNameToPointMap(nameToPointMap);
        state.setActivePlayer(user1);
        state.setOrderedPlayersNames(Arrays.stream(new String []{user1, user2, user3, user4}).toList());
        state.setActivePlayer(user1);
        state.setCurrentPhase(ClientPhasesEnum.FINAL_RESULTS_SHOW); // phase's scene to render
        ArrayList<PointCard> pointCards = new ArrayList<>();
        HashMap<String, PointCard> nickToPointCard = new HashMap<>();
        nickToPointCard.put(user1, null);
        nickToPointCard.put(user2, null);
        nickToPointCard.put(user3, null);
        nickToPointCard.put(user4, null);
        pointCards.add(new PointCard(PointEnumeration.FOUR_POINTS, 4));
        pointCards.add(new PointCard(PointEnumeration.TWO_POINTS, 2));
        SimplifiedCommonGoalCard sc1 = new SimplifiedCommonGoalCard("1", pointCards, nickToPointCard);
        SimplifiedCommonGoalCard sc2 = new SimplifiedCommonGoalCard("1", pointCards, nickToPointCard);
        state.setCommonGoalCards(Arrays.stream(new SimplifiedCommonGoalCard[]{sc1, sc2}).toList());
        state.setBoard(createFakeBoard(9));
        state.setShelves(createFakeShelves(4));
        return state;
    }

    private ObjectTypeEnum[][] createFakeBoard(int dimensions){
        ObjectTypeEnum[][] board = new ObjectTypeEnum[dimensions][dimensions];
        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                board[j][i] = ObjectTypeEnum.TROPHY;
            }
        }

        return board;
    }

    private List<ObjectTypeEnum[][]> createFakeShelves(int players){
        List<ObjectTypeEnum[][]> shelves = new ArrayList<>();

        for (int k = 0; k < players; k++) {
            ObjectTypeEnum[][] shelf = new ObjectTypeEnum[6][5];
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 6; j++) {
                    shelf[j][i] = ObjectTypeEnum.TROPHY;
                }
            }
            shelves.add(shelf);
        }


        return shelves;
    }

}
