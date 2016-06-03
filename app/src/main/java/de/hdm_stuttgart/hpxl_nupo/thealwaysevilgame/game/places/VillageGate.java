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
public class VillageGate extends Place {
//region Constants
private static final String LOG_TAG = VillageGate.class.getSimpleName();
    public static final String TOKEN_GUARD = LancasterStemmer.stem("guard");
    public static final String TOKEN_KEEPER = LancasterStemmer.stem("keeper");
//endregion

//region Properties & Members
private boolean guardAlive = true;
    private boolean guardBribed = false;
    private boolean visited = false;
//endregion

//region Constructors
//endregion

//region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return PlaceManager.PlaceIdentifier.ENTRANCE;
    }
    @Override
    public PlaceManager.PlaceIdentifier goEast() {
        return null;
    }

    @Override
    public PlaceManager.PlaceIdentifier goSouth() {
        return PlaceManager.PlaceIdentifier.COUNTRYSIDE;
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
        } else if(wordlist.contains(PlaceManager.TOKEN_WEST)|| wordlist.contains(PlaceManager.TOKEN_EAST)) {
            // TODO: replace with global
            soundList.add("countryside/countryside_06.ogg");
        } else if(((wordlist.contains(Game.TOKEN_TALK) || wordlist.contains(Game.TOKEN_SPEAK)) && (wordlist.contains(TOKEN_GUARD) || wordlist.contains(TOKEN_KEEPER))) && guardAlive) {
            soundList.add("gate/gate_01.ogg");
        }
        else if(wordlist.contains(PlaceManager.TOKEN_NORTH) && !guardBribed && guardAlive){
            soundList.add("gate/gate_02.ogg");
            soundList.add("gate/gate_01.ogg");
        }
        else if(wordlist.contains(PlaceManager.TOKEN_NORTH) && guardBribed && guardAlive){
            soundList.add("gate/gate_02.ogg");
            soundList.add("gate/gate_03.ogg");
        }
        else if(wordlist.contains(PlaceManager.TOKEN_NORTH) && !guardAlive){
            return null;
        }
        else if((wordlist.contains(Game.TOKEN_GIVE) || wordlist.contains(Game.TOKEN_USE) || wordlist.contains(Game.TOKEN_HAND)) && wordlist.contains(InventoryManager.TOKEN_COIN) && !guardBribed && mInventoryManager.contains(InventoryItem.GOLDEN_COIN)) {
            guardBribed = true;
            soundList.add("gate/gate_05.ogg");
            mInventoryManager.remove(InventoryItem.GOLDEN_COIN);
        }
        else if(((wordlist.contains(Game.TOKEN_KILL) && wordlist.contains(TOKEN_GUARD)) || (wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_SWORD) && wordlist.contains(TOKEN_GUARD))) && !guardBribed && guardAlive) {
            soundList.add("gate/gate_06.ogg");
            guardAlive = false;
        }
        else if(((wordlist.contains(Game.TOKEN_KILL) && wordlist.contains(TOKEN_GUARD)) || (wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_SWORD) && wordlist.contains(TOKEN_GUARD))) && guardBribed && guardAlive) {
            soundList.add("gate/gate_06.ogg");
            soundList.add("gate/gate_07.ogg");
            guardAlive = false;
            mInventoryManager.add(InventoryItem.GOLDEN_COIN);
        }
        return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        if(guardAlive && !visited) {
            visited = true;
            return "gate/gate_00.ogg";
        }
        else if(guardAlive && visited){
            return "gate/gate_09.ogg";
        }
        else{
            return "gate/gate_08.ogg";
        }
    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
