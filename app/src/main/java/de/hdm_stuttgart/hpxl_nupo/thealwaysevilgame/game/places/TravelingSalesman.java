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
public class TravelingSalesman extends Place{
    //region Constants
    private static final String LOG_TAG = VillageGate.class.getSimpleName();
    public static final String TOKEN_KEEPER = LancasterStemmer.stem("keeper");
//endregion

    //region Properties & Members
    private boolean salesmanAlive = true;
    private boolean visited = false;
//endregion

//region Constructors
//endregion

//region Methods
//region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return null;
    }
    @Override
    public PlaceManager.PlaceIdentifier goEast() {
        return null;
    }

    @Override
    public PlaceManager.PlaceIdentifier goSouth() {
        return PlaceManager.PlaceIdentifier.TAVERN;
    }

    @Override
    public PlaceManager.PlaceIdentifier goWest() {
        return PlaceManager.PlaceIdentifier.MARKET_PLACE;
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
        } else if(((wordlist.contains(Game.TOKEN_USE) && wordlist.contains(mInventoryManager.TOKEN_SWORD)) || wordlist.contains(Game.TOKEN_KILL)) && salesmanAlive) {
            salesmanAlive = false;
            soundList.add("salesman/salesman_04.ogg");
            mInventoryManager.add(InventoryItem.RAT_POISON_AND_COKE);
        }
        else if(((wordlist.contains(Game.TOKEN_USE) && wordlist.contains(mInventoryManager.TOKEN_SWORD)) || wordlist.contains(Game.TOKEN_KILL)) && !salesmanAlive){
            soundList.add(Game.getRandomAlreadyDidThatMonsterSound());
        }
        else if(((wordlist.contains(Game.TOKEN_BUY) && (wordlist.contains(mInventoryManager.TOKEN_POISON) || wordlist.contains(mInventoryManager.TOKEN_COKE))) || ((wordlist.contains(Game.TOKEN_USE) || wordlist.contains(Game.TOKEN_GIVE)) && wordlist.contains(mInventoryManager.TOKEN_COIN))) && mInventoryManager.contains(InventoryItem.GOLDEN_COIN)){
            mInventoryManager.remove(InventoryItem.GOLDEN_COIN);
            mInventoryManager.add(InventoryItem.RAT_POISON_AND_COKE);
            soundList.add("salesman/salesman_02.ogg");
        }
        else if(((wordlist.contains(Game.TOKEN_BUY) && (wordlist.contains(mInventoryManager.TOKEN_POISON) || wordlist.contains(mInventoryManager.TOKEN_COKE))) || ((wordlist.contains(Game.TOKEN_USE) || wordlist.contains(Game.TOKEN_GIVE)) && wordlist.contains(mInventoryManager.TOKEN_COIN))) && !mInventoryManager.contains(InventoryItem.GOLDEN_COIN)){
            soundList.add("salesman/salesman_03.ogg");
        }
        else if(((wordlist.contains(Game.TOKEN_BUY)) || ((wordlist.contains(Game.TOKEN_USE) || wordlist.contains(Game.TOKEN_GIVE)) && wordlist.contains(mInventoryManager.TOKEN_COIN))) && !mInventoryManager.contains(InventoryItem.GOLDEN_COIN)){
            soundList.add("salesman/salesman_06.ogg");
        }else if((wordlist.contains(Game.TOKEN_TALK) || wordlist.contains(Game.TOKEN_SPEAK)) && salesmanAlive){
            soundList.add("salesman/salesman_07.ogg");
        }
            return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        if(salesmanAlive && !visited) {
            visited = true;
            return "salesman/salesman_00.ogg";
        }
        else if(salesmanAlive && visited){
            return "salesman/salesman_09.ogg";
        }
        else{
            return "salesman/salesman_01.ogg";
        }
    }
//endregion
//endregion

//region Inner Classes / Interfaces
//endregion
}
