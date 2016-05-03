package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.LancasterStemmer;

/**
 * Created by nerd on 26/04/16.
 */
public class Game {

//region Constants
private static final String LOG_TAG = Game.class.getSimpleName();
    public static final String LIST_INVENTORY_TOKEN = LancasterStemmer.stem("inventory");
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

        }
        return returnValue;
    }
//endregion

//region Inner Classes / Interfaces
//endregion

}
