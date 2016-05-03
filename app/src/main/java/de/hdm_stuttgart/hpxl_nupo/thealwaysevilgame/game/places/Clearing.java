package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import java.util.ArrayList;
import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Game;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Navigator;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager;

/**
 * Created by nerd on 26/04/16.
 */
public class Clearing extends Place {
//region Constants
//endregion

//region Properties & Members
//endregion

//region Constructors
//endregion

//region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return PlaceManager.PlaceIdentifier.COUNTRYSIDE;
    }

    @Override
    public PlaceManager.PlaceIdentifier goEast() {
        return null;
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
        // catch iventory command
        if(wordlist.contains(Game.LIST_INVENTORY_TOKEN)){
            List<String> inventorySoundList = mInventoryManager.getInventorySoundList();
            soundList.addAll(inventorySoundList);
            // tutorial info
            soundList.add("clearing/clearing_01.ogg");
        } else if(wordlist.contains(PlaceManager.TOKEN_WEST)|| wordlist.contains(PlaceManager.TOKEN_SOUTH)|| wordlist.contains(PlaceManager.TOKEN_EAST)){
            soundList.add("clearing/clearing_02.ogg");
        }

        return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        return "clearing/clearing_00.ogg";
    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
