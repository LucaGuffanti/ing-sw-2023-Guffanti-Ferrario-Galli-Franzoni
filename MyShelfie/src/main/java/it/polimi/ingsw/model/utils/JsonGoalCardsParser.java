package it.polimi.ingsw.model.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.model.cards.goalCards.FixedPatternCommonGoalCard;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongPointCardsValueGivenException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class JsonGoalCardsParser {

    public static ArrayList<CommonGoalCard> parseCommonGoals(String path) throws IOException, WrongNumberOfPlayersException, WrongPointCardsValueGivenException{

        // change serialization for specific types
        JsonDeserializer<ArrayList<CommonGoalCard>> deserializer = (json, typeOfT, context) -> {
            JsonArray jsonArray= json.getAsJsonObject().get("test").getAsJsonArray();
            ArrayList<CommonGoalCard> result = new ArrayList<>();

            for (JsonElement ob: jsonArray) {

                JsonObject goalCardObject = ob.getAsJsonObject();
                String id = goalCardObject.get("id").getAsString();

                JsonObject subPatternObject = goalCardObject.get("pattern").getAsJsonObject();

                JsonArray subPatternCellsArray = subPatternObject.get("coveredCells").getAsJsonArray();



                List<PatternCell> patternCells = subPatternCellsArray.asList().stream().
                        map((coordinatesPairArray) -> coordinatesPairArray.getAsJsonArray().asList())
                        .map((coordinatesPair) -> new PatternCell(coordinatesPair.get(0).getAsInt(), coordinatesPair.get(1).getAsInt(), Optional.empty()))
                        .toList();

                Pattern pattern = new Pattern(
                        subPatternObject.get("height").getAsInt(),
                        subPatternObject.get("length").getAsInt(),
                        (Set) new HashSet<>(patternCells),
                        subPatternObject.get("minDifferentTypes").getAsInt(),
                        subPatternObject.get("maxDifferentTypes").getAsInt());

                FixedPatternCommonGoalCard goalCard = new FixedPatternCommonGoalCard(
                        id,
                        pattern,
                        goalCardObject.get("minNumberOfOccurrences").getAsInt(),
                        goalCardObject.get("shouldRotate").getAsBoolean(),
                        goalCardObject.get("admitsAdjacency").getAsBoolean(),
                        goalCardObject.get("patternsShareSameColor").getAsBoolean()
                );

                result.add(goalCard);


            }

            return result;
        };

        String jsonData = Files.readString(Path.of(path));
        GsonBuilder gsonBuilder = new GsonBuilder();

        Type classType = new TypeToken<ArrayList<CommonGoalCard>>(){}.getType();


        gsonBuilder.registerTypeAdapter(classType, deserializer);
        Gson customGson = gsonBuilder.create();
        return customGson.fromJson(jsonData, classType);
    }
}
