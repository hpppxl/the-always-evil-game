package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Game;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.InventoryItem;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.InventoryManager;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.LancasterStemmer;

/**
 * Created by nerd on 26/04/16.
 */
public class Carpenter extends Place{

    //region Constants
    private static final String LOG_TAG = Carpenter.class.getSimpleName();
//endregion

    //region Properties & Members
    private boolean burnedDown = false;

//endregion

//region Constructors
//endregion

//region Methods
//region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return PlaceManager.PlaceIdentifier.BLACKSMITH;
    }
    @Override
    public PlaceManager.PlaceIdentifier goEast() {
        return PlaceManager.PlaceIdentifier.ENTRANCE;
    }

    @Override
    public PlaceManager.PlaceIdentifier goSouth() {
        return null;
    }

    @Override
    public PlaceManager.PlaceIdentifier goWest() {
        return null;
    }

    @Override
    public List<String> parseSpeechInput(List<String> wordlist) {
        List<String> soundList = new ArrayList<>();
        for(String word : wordlist) {
            Log.d(LOG_TAG, word);
        }
        // catch iventory command
        if(wordlist.contains(PlaceManager.TOKEN_LOOK_AROUND)){
            soundList.add(getWelcomeMediaFile());
        }
        return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        if(!burnedDown && !mInventoryManager.contains(InventoryItem.BURNING_TORCH)) {
            return "carpenter/carpenter_00.ogg";
        } else if(!burnedDown && mInventoryManager.contains(InventoryItem.BURNING_TORCH)) {
            burnedDown = true;
            return "carpenter/carpenter_01.ogg";
        } else{
            return "carpenter/carpenter_02.ogg";
        }

    }
//endregion
//endregion

//region Inner Classes / Interfaces
//endregion
}
