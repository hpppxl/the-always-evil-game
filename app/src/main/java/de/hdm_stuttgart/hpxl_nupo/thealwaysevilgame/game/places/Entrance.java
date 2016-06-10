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
public class Entrance extends Place {
    //region Constants
    private static final String LOG_TAG = Entrance.class.getSimpleName();
    public static final String TOKEN_HAYSTACK = LancasterStemmer.stem("haystack");
    public static final String TOKEN_FIRE = LancasterStemmer.stem("fire");
//endregion
// region Properties & Members
//endregion

//region Constructors
//endregion

    //region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() { return PlaceManager.PlaceIdentifier.MARKET_PLACE; }

    @Override
    public PlaceManager.PlaceIdentifier goEast() {
        return PlaceManager.PlaceIdentifier.TAVERN;
    }

    @Override
    public PlaceManager.PlaceIdentifier goSouth() {
        return null;
    }

    @Override
    public PlaceManager.PlaceIdentifier goWest() {
        return PlaceManager.PlaceIdentifier.CARPENTER;
    }

    @Override
    public List<String> parseSpeechInput(List<String> wordlist) {
        List<String> soundList = new ArrayList<>();
        // catch iventory command
        if(wordlist.contains(PlaceManager.TOKEN_LOOK_AROUND)){
            soundList.add(getWelcomeMediaFile());
        } else if(((wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_TORCH)) || wordlist.contains(Game.TOKEN_LIGHT) || wordlist.contains(TOKEN_FIRE) ) && wordlist.contains(TOKEN_HAYSTACK) && mInventoryManager.contains(InventoryItem.UNLIT_TORCH)) {
            soundList.add("entrance/entrance_02.ogg");
        } else if(((wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_TORCH)) || wordlist.contains(Game.TOKEN_LIGHT) || wordlist.contains(TOKEN_FIRE) ) && wordlist.contains(TOKEN_HAYSTACK) && mInventoryManager.contains(InventoryItem.BURNING_COAL)) {
            soundList.add("entrance/entrance_05.ogg");
        } else if(((wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_TORCH)) || wordlist.contains(Game.TOKEN_LIGHT) || wordlist.contains(TOKEN_FIRE) ) && wordlist.contains(TOKEN_HAYSTACK) && mInventoryManager.contains(InventoryItem.BURNING_TORCH)) {
            soundList.add("entrance/entrance_03.ogg");
        } else if((wordlist.contains(Game.TOKEN_TAKE) && wordlist.contains(InventoryManager.TOKEN_TORCH)) && !mInventoryManager.contains(InventoryItem.UNLIT_TORCH)) {
            mInventoryManager.add(InventoryItem.UNLIT_TORCH);
            soundList.add("entrance/entrance_04.ogg");
        } else if((wordlist.contains(Game.TOKEN_TAKE) && wordlist.contains(InventoryManager.TOKEN_TORCH)) && mInventoryManager.contains(InventoryItem.UNLIT_TORCH)) {
            soundList.add(Game.getRandomAlreadyDidThatSound());
        }
        return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        if(!mInventoryManager.contains(InventoryItem.UNLIT_TORCH) && !mInventoryManager.contains(InventoryItem.BURNING_TORCH)) {
            return "entrance/entrance_00.ogg";
        }
        else{
            return "entrance/entrance_01.ogg";
        }
    }
//endregion
//endregion

//region Inner Classes / Interfaces
//endregion
}
