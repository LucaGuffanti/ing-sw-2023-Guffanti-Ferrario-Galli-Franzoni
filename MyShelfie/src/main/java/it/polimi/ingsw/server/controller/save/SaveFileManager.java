package it.polimi.ingsw.server.controller.save;

import com.google.gson.Gson;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Sack;
import it.polimi.ingsw.server.model.SimplifiedGameInfo;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.player.SimplifiedPlayer;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * This object contains methods that save and load a game state to and from disk
 */
public class SaveFileManager {

    public static void saveGameState(Game g, GameController controller, String pathToFile) {
        try {
            Logger.controllerInfo("SAVING GAME STATE");

            SimplifiedPlayer[] players = GameObjectConverter.fromPlayersToSimplifiedPlayers(g.getPlayers());
            ObjectTypeEnum[][] board = GameObjectConverter.fromBoardToMatrix(g.getBoard());
            SimplifiedGameInfo gameInfo = GameObjectConverter.fromGameInfoToSimplifiedGameInfo(g.getGameInfo());
            Sack sack = new Sack(g.getSack());
            List<String> orderedPlayers = controller.getOrderedPlayersNicks();
            int activePlayer = controller.getActivePlayerIndex();

            SaveFileData saveFileData = new SaveFileData(
                    players,
                    board,
                    gameInfo,
                    sack,
                    orderedPlayers,
                    activePlayer
            );

            Gson gson = new Gson();
            String jsonString = gson.toJson(saveFileData);
            File saveFile = new File(pathToFile);
            FileWriter fileWriter = new FileWriter(saveFile, false);
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (Exception e) {
            Logger.networkCritical("COULD NOT SAVE GAME STATE");
        }

    }

    public static SaveFileData loadGameState(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        Scanner scanner = new Scanner(in);

        Gson gson = new Gson();

        String savedJson = scanner.nextLine();
        in.close();

        SaveFileData s = gson.fromJson(savedJson, SaveFileData.class);
        return s;
    }
}