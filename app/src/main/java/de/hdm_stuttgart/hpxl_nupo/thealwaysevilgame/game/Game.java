package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

import java.util.ArrayList;
import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.LancasterStemmer;

/**
 * Created by nerd on 26/04/16.
 */
public class Game {

    //region Constants
    private static final String LOG_TAG = Game.class.getSimpleName();
    public static final String LIST_INVENTORY_TOKEN = LancasterStemmer.stem("inventory");
    public static final String TOKEN_USE = LancasterStemmer.stem("use");
    public static final String TOKEN_TAKE = LancasterStemmer.stem("take");
    public static final String TOKEN_TALK = LancasterStemmer.stem("talk");
    public static final String TOKEN_SPEAK = LancasterStemmer.stem("speak");
    public static final String TOKEN_GIVE = LancasterStemmer.stem("give");
    public static final String TOKEN_HAND = LancasterStemmer.stem("hand");
    public static final String TOKEN_KILL = LancasterStemmer.stem("kill");
    public static final String TOKEN_LIGHT = LancasterStemmer.stem("light");
    public static final String TOKEN_BUY = LancasterStemmer.stem("buy");
    public static final String TOKEN_MYSELF = LancasterStemmer.stem("myself");
    public static final String TOKEN_SUICIDE = LancasterStemmer.stem("suicide");
    //endregion

    //region Properties & Members
    private final InventoryManager mInventoryManager = new InventoryManager();
    private PlaceManager placeManager = new PlaceManager();
    //endregion

    //region Constructors
    public Game(){
        placeManager.setInventoryManager(mInventoryManager);
    }
    //endregion

    //region Methods
    public List<String> parseSpeechInput(List<String> wordList){
        List<String> returnValue;
        returnValue = placeManager.getCurrentPlace().parseSpeechInput(wordList);
        if (returnValue == null || returnValue.isEmpty()){
            returnValue = placeManager.parseSpeechInput(wordList);
        }
        if (returnValue == null || returnValue.isEmpty()){
            returnValue = parseGlobalFunctions(wordList);
        }
        if (returnValue == null || returnValue.isEmpty()){
            returnValue = new ArrayList<>(1);
            returnValue.add(getRandomCantDoThatSound());
        }
        return returnValue;
    }

    public List<String> parseGlobalFunctions(List<String> wordList){
        List<String> returnValue = new ArrayList<>();
        if(wordList.contains(LIST_INVENTORY_TOKEN)) {
            return mInventoryManager.getInventorySoundList();
        }else if(((wordList.contains(Game.TOKEN_KILL) && wordList.contains(TOKEN_MYSELF)) || (wordList.contains(Game.TOKEN_USE) && wordList.contains(InventoryManager.TOKEN_SWORD) && wordList.contains(TOKEN_MYSELF))) || wordList.contains(TOKEN_SUICIDE)) {
            returnValue.add("globalSounds/anywhere_00.ogg");
        } else if((wordList.contains(TOKEN_USE) ||wordList.contains(TOKEN_LIGHT)) && wordList.contains(mInventoryManager.TOKEN_TORCH) && wordList.contains(mInventoryManager.TOKEN_COAL) && mInventoryManager.contains(InventoryItem.BURNING_COAL) && mInventoryManager.contains(InventoryItem.UNLIT_TORCH)){
            mInventoryManager.remove(InventoryItem.UNLIT_TORCH);
            mInventoryManager.remove(InventoryItem.BURNING_COAL);
            mInventoryManager.add(InventoryItem.BURNING_TORCH);
            returnValue.add("globalSounds/anywhere_01.ogg");
        }
        return returnValue;
    }

    public InventoryManager getInventoryManager() {
        return mInventoryManager;
    }

    public PlaceManager getPlaceManager() {
        return placeManager;
    }

    public static String getRandomCantDoThatSound() {
        return "globalSounds/cant_do_that_0" + (int) ((Math.random() * 7)) + ".ogg";
    }

    public static String getRandomAlreadyDidThatSound() {
        return "globalSounds/did_that_0" + (int) ((Math.random() * 2)) + ".ogg";
    }

    public static String getRandomAlreadyDidThatMonsterSound() {
        return "globalSounds/did_that_monster_0" + (int) ((Math.random() * 3)) + ".ogg";
    }
    //endregion

    //region Inner Classes / Interfaces
    //endregion

}
