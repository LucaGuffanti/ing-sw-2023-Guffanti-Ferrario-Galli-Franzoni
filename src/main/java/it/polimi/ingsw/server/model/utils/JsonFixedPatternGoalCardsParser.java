package it.polimi.ingsw.server.model.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.server.model.cards.ObjectTypeEnum;
import it.polimi.ingsw.server.model.cards.goalCards.CommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.FixedPatternCommonGoalCard;
import it.polimi.ingsw.server.model.cards.goalCards.PersonalGoalCard;
import it.polimi.ingsw.server.model.cards.Pattern;
import it.polimi.ingsw.server.model.cards.PatternCell;
import it.polimi.ingsw.server.model.cards.goalCards.FixedPatternShapedCard;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;


/**
 * A class to load FixedPatternShapedCards in memory from a json file (so every PersonalGoalCard and most of CommonGoalCards)
 *
 * @see FixedPatternShapedCard
 * @see Gson
 * @author Daniele Ferrario
 */
public class JsonFixedPatternGoalCardsParser {

    /**
     * This method parses a json file and builds a list of common goal cards
     * @param path path to the json file
     * @return the arraylist of common goal cards
     * @throws IOException thrown if some error occurs during the parsing of the file
     */
    public static ArrayList<CommonGoalCard> parseFixedPatternCommonGoals(String path) throws IOException {

        // Writing a GSON deserializer ad hoc
        JsonDeserializer<ArrayList<CommonGoalCard>> deserializer = (json, typeOfT, context) -> {
            JsonArray jsonArray= json.getAsJsonArray();
            // list of common goals
            ArrayList<CommonGoalCard> result = new ArrayList<>();

            for (JsonElement ob: jsonArray) {
                // the specific json object from which we extract the id ...
                JsonObject goalCardObject = ob.getAsJsonObject();
                String id = goalCardObject.get("id").getAsString();
                // ... and the pattern
                JsonObject subPatternObject = goalCardObject.get("pattern").getAsJsonObject();
                JsonArray subPatternCellsArray = subPatternObject.get("coveredCells").getAsJsonArray();

                // the pattern is built by combining pattern cells
                List<PatternCell> patternCells = subPatternCellsArray.asList().stream().
                        map((coordinatesPairArray) -> coordinatesPairArray.getAsJsonArray().asList())
                        .map((coordinatesPair) -> new PatternCell(coordinatesPair.get(0).getAsInt(), coordinatesPair.get(1).getAsInt(), Optional.empty()))
                        .toList();
                // and additional info is also fetched
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

        // after building a custom deserializer, the json file is read

        BufferedReader reader = new BufferedReader(new InputStreamReader(JsonFixedPatternGoalCardsParser.class.getResourceAsStream(path)));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String jsonData = stringBuilder.toString();
        GsonBuilder gsonBuilder = new GsonBuilder();

        Type classType = new TypeToken<ArrayList<CommonGoalCard>>(){}.getType();


        gsonBuilder.registerTypeAdapter(classType, deserializer);
        Gson customGson = gsonBuilder.create();
        reader.close();
        return customGson.fromJson(jsonData, classType);
    }

    private static final Map<String, ObjectTypeEnum> map = Map.of(
            "G", ObjectTypeEnum.CAT,
            "B", ObjectTypeEnum.FRAME,
            "W", ObjectTypeEnum.BOOK,
            "Y", ObjectTypeEnum.TOY,
            "C", ObjectTypeEnum.TROPHY,
            "M", ObjectTypeEnum.PLANT
    );

    /**
     * This method builds a list of personal goal cards starting from data contained in a json file
     * @param path path to the json file
     * @return a list of personal goal cards obtained from the file
     * @throws IOException thrown if some error occurs during the parsing of the file
     */
    public static ArrayList<PersonalGoalCard> parsePersonalGoalCard(String path) throws IOException {

        // Writing a GSON deserializer ad hoc
        JsonDeserializer<ArrayList<PersonalGoalCard> >deserializer = (json, typeOfT, context) -> {
            JsonArray jsonArray = json.getAsJsonArray();
            ArrayList<PersonalGoalCard> result = new ArrayList<>();
            /*
            For each element in the list of cards, a personal goal card is
            built with the id and list of tiles that make up the pattern
             */
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
                        new HashSet<>(personalGoalPattern),
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
        // then the json file is read
        BufferedReader reader = new BufferedReader(new InputStreamReader(JsonFixedPatternGoalCardsParser.class.getResourceAsStream(path)));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }

        String jsonData = stringBuilder.toString();
        Type classType = new TypeToken<ArrayList<PersonalGoalCard>>() {
        }.getType();


        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(classType, deserializer);
        Gson customGson = gsonBuilder.create();
        reader.close();
        return customGson.fromJson(jsonData, classType);
    }
}
