package it.polimi.ingsw.server.controller.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.network.utils.Logger;
import it.polimi.ingsw.server.controller.GameController;
import it.polimi.ingsw.server.controller.utils.GameObjectConverter;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Sack;
import it.polimi.ingsw.server.model.SimplifiedGameInfo;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.player.SimplifiedPlayer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

/**
 * This object contains methods that save and load a game state to and from disk
 */
public class SaveFileManager {

    public static void saveGameState(Game g, GameController controller, String pathToDir, String pathToFile) {
        try {
            Logger.controllerInfo("SAVING GAME STATE");

            Path path = Path.of(pathToDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

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

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File saveFile = new File(pathToFile);
            FileWriter fileWriter = new FileWriter(saveFile, false);
            fileWriter.write(gson.toJson(saveFileData));
            fileWriter.close();
            // Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
            // System.out.println(gson1.toJson(saveFileData));
        } catch (Exception e) {
            Logger.networkCritical("COULD NOT SAVE GAME STATE");
        }

    }

    public static SaveFileData loadGameState(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(in);
        while(scanner.hasNextLine()) {
            builder.append(scanner.nextLine().trim());
        }
        String savedJson = builder.toString();
        System.out.println(savedJson);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        in.close();

        SaveFileData s = gson.fromJson(savedJson, SaveFileData.class);
        return s;
    }
}
