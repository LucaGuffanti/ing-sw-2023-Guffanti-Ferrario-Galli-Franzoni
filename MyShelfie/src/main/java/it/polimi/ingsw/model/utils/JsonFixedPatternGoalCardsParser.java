package it.polimi.ingsw.model.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.model.cards.goalCards.FixedPatternCommonGoalCard;
import it.polimi.ingsw.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.model.utils.exceptions.WrongNumberOfPlayersException;
import it.polimi.ingsw.model.utils.exceptions.WrongPointCardsValueGivenException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


/**
 * A class to load FixedPatternShapedCards in memory from a json file (so every PersonalGoalCard and most of CommonGoalCards)
 *
 * @see it.polimi.ingsw.model.cards.goalCards.FixedPatternShapedCard
 * @see Gson
 * @author Daniele Ferrario
 */
public class JsonFixedPatternGoalCardsParser {

    public static ArrayList<CommonGoalCard> parseFixedPatternCommonGoals(String path) throws IOException, WrongNumberOfPlayersException, WrongPointCardsValueGivenException{

        // Writing a GSON deserializer ad hoc
        JsonDeserializer<ArrayList<CommonGoalCard>> deserializer = (json, typeOfT, context) -> {
            JsonArray jsonArray= json.getAsJsonArray();
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

    private static final Map<String, ObjectTypeEnum > map = Map.of(
            "G", ObjectTypeEnum.CAT,
            "B", ObjectTypeEnum.FRAME,
            "W", ObjectTypeEnum.BOOK,
            "Y", ObjectTypeEnum.TOY,
            "C", ObjectTypeEnum.TROPHY,
            "M", ObjectTypeEnum.PLANT
    );
    public static ArrayList<PersonalGoalCard> parsePersonalGoalCard(String path) throws IOException, WrongNumberOfPlayersException, WrongPointCardsValueGivenException {

        // Writing a GSON deserializer ad hoc
        JsonDeserializer<ArrayList<PersonalGoalCard> >deserializer = (json, typeOfT, context) -> {
            JsonArray jsonArray = json.getAsJsonArray();
            ArrayList<PersonalGoalCard> result = new ArrayList<>();

            for (JsonElement ob : jsonArray) {

                JsonObject goalCardObject = ob.getAsJsonObject();
                String id = goalCardObject.get("id").getAsString();

                JsonArray subPatternArray = goalCardObject.get("coveredCells").getAsJsonArray();

                List<PatternCell> personalGoalPattern = new ArrayList<>();

                for (JsonElement ob2 : subPatternArray) {

                    JsonObject goalCardObjectInfo = ob2.getAsJsonObject();

                    JsonArray goalCardPosition = goalCardObjectInfo.get("coordinates").getAsJsonArray();

                    String admittedType = goalCardObjectInfo.get("admittedType").getAsString();

                    personalGoalPattern.add(new PatternCell(goalCardPosition.get(0).getAsInt(),
                            goalCardPosition.get(1).getAsInt(),
                            Optional.of(map.get(admittedType))));

                }

                Pattern pattern = new Pattern(
                        6,
                        5,
                        (Set) new HashSet<>(personalGoalPattern),
                        0,0
                );
                PersonalGoalCard goalCard = new PersonalGoalCard(
                        id,
                        pattern
                );

                result.add(goalCard);

            }

            return result;
        };

        String jsonData = Files.readString(Path.of(path));
        GsonBuilder gsonBuilder = new GsonBuilder();

        Type classType = new TypeToken<ArrayList<PersonalGoalCard>>() {
        }.getType();


        gsonBuilder.registerTypeAdapter(classType, deserializer);
        Gson customGson = gsonBuilder.create();
        return customGson.fromJson(jsonData, classType);
    }


}
