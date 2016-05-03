package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

import android.location.LocationManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.GameActivity;

/**
 * Created by nerd on 26/04/16.
 */
public class Game {

//region Constants
private static final String LOG_TAG = Game.class.getSimpleName();
//endregion

//region Properties & Members
private final List<InventoryItem> mInventoryItems = new ArrayList<InventoryItem>();
private PlaceManager placeManager = new PlaceManager();
//endregion

//region Constructors
//endregion

//region Methods
    public List<String> parseSpeechInput(List<String> wordList){
        List<String> returnValue;
        returnValue = placeManager.getCurrentPlace().parseSpeechInput(wordList);
        if (returnValue == null){
            returnValue = placeManager.parseSpeechInput(wordList);
        }
        if (returnValue == null){

        }
        return returnValue;
    }
//endregion

//region Inner Classes / Interfaces
//endregion

}
