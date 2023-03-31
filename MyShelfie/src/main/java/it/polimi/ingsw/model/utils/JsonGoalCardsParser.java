package it.polimi.ingsw.model.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongPointCardsValueGivenException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;



public class JsonGoalCardsParser {

    public static Map<String, CommonGoalCard> parseCommonGoals(String path, int playersNumber) throws IOException, WrongNumberOfPlayersException, WrongPointCardsValueGivenException{

        // change serialization for specific types
        JsonDeserializer<Map<String, CommonGoalCard>> deserializer = (json, typeOfT, context) -> {
            JsonArray jsonArray= json.getAsJsonObject().get("test").getAsJsonArray();
            Map<String, CommonGoalCard> result = new HashMap<>();

            for (JsonElement ob: jsonArray) {

                JsonObject goalCardObject = ob.getAsJsonObject();
                String id = goalCardObject.get("id").getAsString();

                JsonObject patternRulesObject = goalCardObject.get("patternRules").getAsJsonObject();
                JsonObject subPatternObject = patternRulesObject.get("subPattern").getAsJsonObject();

                JsonArray subPatternCellsArray = subPatternObject.get("coveredCells").getAsJsonArray();



                List<SubPatternCell> subPatternCells = subPatternCellsArray.asList().stream().
                        map((coordinatesPairArray) -> coordinatesPairArray.getAsJsonArray().asList())
                        .map((coordinatesPair) -> new SubPatternCell(coordinatesPair.get(0).getAsInt(), coordinatesPair.get(1).getAsInt(), Optional.empty()))
                        .toList();

                SubPattern subPattern = new SubPattern(
                        subPatternObject.get("height").getAsInt(),
                        subPatternObject.get("length").getAsInt(),
                        (Set) new HashSet<>(subPatternCells),
                        subPatternObject.get("minDifferentTypes").getAsInt(),
                        subPatternObject.get("maxDifferentTypes").getAsInt());

                CommonPatternRules rules = new CommonPatternRules(
                        subPattern,
                        patternRulesObject.get("minNumberOfOccurrences").getAsInt(),
                        patternRulesObject.get("shouldRotate").getAsBoolean(),
                        patternRulesObject.get("admitsAdjacency").getAsBoolean(),
                        patternRulesObject.get("subPatternsSameColor").getAsBoolean()
                        );


                try {
                    CommonGoalCard goalCard = new CommonGoalCard(id, CardBuilder.generatePointsCards(playersNumber), rules);
                    result.put(id, goalCard);
                } catch (WrongNumberOfPlayersException | WrongPointCardsValueGivenException e) {
                    throw new RuntimeException(e);
                }


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
