package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Game;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.InventoryItem;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.InventoryManager;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Navigator;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.LancasterStemmer;

/**
 * Created by nerd on 26/04/16.
 */
public class Countryside extends Place{
//region Constants
private static final String LOG_TAG = Countryside.class.getSimpleName();


    public static final String TOKEN_DONKEY = LancasterStemmer.stem("donkey");

//endregion

//region Properties & Members
    private boolean donkeyAlive = true;
    private boolean coinTaken = false;
    private boolean visited = false;
//endregion

//region Constructors
//endregion

//region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return PlaceManager.PlaceIdentifier.VILLAGE_GATE;
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
        for(String word : wordlist) {
            Log.d(LOG_TAG, word);
        }
        // catch iventory command
        if(wordlist.contains(PlaceManager.TOKEN_LOOK_AROUND)){
            soundList.add(getWelcomeMediaFile());
        } else if(wordlist.contains(PlaceManager.TOKEN_WEST)|| wordlist.contains(PlaceManager.TOKEN_SOUTH)|| wordlist.contains(PlaceManager.TOKEN_EAST)) {
            soundList.add("countryside/countryside_06.ogg");
        } else if(((wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_SWORD) && wordlist.contains(TOKEN_DONKEY)) || (wordlist.contains(Game.TOKEN_KILL) && wordlist.contains(TOKEN_DONKEY))) && donkeyAlive){
            donkeyAlive = false;
            soundList.add("countryside/countryside_04.ogg");
        } else if(((wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_SWORD) && wordlist.contains(TOKEN_DONKEY)) || (wordlist.contains(Game.TOKEN_KILL) && wordlist.contains(TOKEN_DONKEY))) && !donkeyAlive){
            soundList.add(Game.getRandomAlreadyDidThatMonsterSound());
        } else if(wordlist.contains(Game.TOKEN_TAKE) && wordlist.contains(InventoryManager.TOKEN_COIN) && !donkeyAlive && !coinTaken){
            coinTaken = true;
            mInventoryManager.add(InventoryItem.GOLDEN_COIN);
            soundList.add("countryside/countryside_05.ogg");
        }

        return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        if(!visited) {
            visited = true;
            return "countryside/countryside_00.ogg";
        } else if(donkeyAlive){
            return "countryside/countryside_01.ogg";
        } else if(!donkeyAlive && coinTaken){
            return "countryside/countryside_02.ogg";
        } else {
            return "countryside/countryside_03.ogg";
        }
    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
