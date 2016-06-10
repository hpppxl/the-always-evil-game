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
public class Tavern extends Place{
    //region Constants
    private static final String LOG_TAG = Tavern.class.getSimpleName();
    public static final String TOKEN_BARTENDER = LancasterStemmer.stem("bartender");
    public static final String TOKEN_BARKEEPER = LancasterStemmer.stem("barkeeper");
//endregion

    //region Properties & Members
    public boolean bartenderAlive = true;
//endregion

//region Constructors
//endregion

//region Methods
//region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return PlaceManager.PlaceIdentifier.TRAVLING_SALESMAN;
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
        return PlaceManager.PlaceIdentifier.ENTRANCE;
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
        } else if(((wordlist.contains(Game.TOKEN_TALK) || wordlist.contains(Game.TOKEN_SPEAK)) && (wordlist.contains(TOKEN_BARKEEPER) || wordlist.contains(TOKEN_BARTENDER))) && bartenderAlive) {
            soundList.add("tavern/tavern_02.ogg");
        } else if(((wordlist.contains(Game.TOKEN_USE) || wordlist.contains(Game.TOKEN_GIVE)) && (wordlist.contains(TOKEN_BARKEEPER) || wordlist.contains(TOKEN_BARTENDER))) && bartenderAlive && (wordlist.contains(InventoryManager.TOKEN_COKE) || wordlist.contains(InventoryManager.TOKEN_POISON)) && mInventoryManager.contains(InventoryItem.RAT_POISON_AND_COKE)) {
            soundList.add("tavern/tavern_03.ogg");
            bartenderAlive = false;
        }else if(((wordlist.contains(Game.TOKEN_KILL) && (wordlist.contains(TOKEN_BARTENDER) || wordlist.contains(TOKEN_BARKEEPER))) || (wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_SWORD) && (wordlist.contains(TOKEN_BARTENDER) || wordlist.contains(TOKEN_BARKEEPER)))) && bartenderAlive){
            soundList.add("tavern/tavern_04.ogg");
            bartenderAlive = false;
        }else if(((wordlist.contains(Game.TOKEN_KILL) && (wordlist.contains(TOKEN_BARTENDER) || wordlist.contains(TOKEN_BARKEEPER))) || (wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_SWORD) && (wordlist.contains(TOKEN_BARTENDER) || wordlist.contains(TOKEN_BARKEEPER)))) && !bartenderAlive){
            soundList.add(Game.getRandomAlreadyDidThatMonsterSound());
        }
        return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        if(bartenderAlive) {
            return "tavern/tavern_00.ogg";
        }
        else{
            return "tavern/tavern_01.ogg";
        }
    }
//endregion
//endregion

//region Inner Classes / Interfaces
//endregion
}
