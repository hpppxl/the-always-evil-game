package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import android.location.LocationManager;
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
public class MarketPlace extends Place{
    //region Constants
    private static final String LOG_TAG = MarketPlace.class.getSimpleName();
    public static final String TOKEN_WELL = LancasterStemmer.stem("well");
//endregion

    //region Properties & Members
    private boolean oldManAlive = true;
    private boolean talkedToOldMan = false;
    private boolean bucketAttachedToWell = false;

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
        return PlaceManager.PlaceIdentifier.TRAVLING_SALESMAN;
    }

    @Override
    public PlaceManager.PlaceIdentifier goSouth() {
        return PlaceManager.PlaceIdentifier.ENTRANCE;
    }

    @Override
    public PlaceManager.PlaceIdentifier goWest() {
        return PlaceManager.PlaceIdentifier.BLACKSMITH;
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
        } else if((wordlist.contains(Game.TOKEN_TALK) || wordlist.contains(Game.TOKEN_SPEAK)) && oldManAlive && !talkedToOldMan && ((Tavern)(PlaceManager.PlaceIdentifier.TAVERN.getPlace())).bartenderAlive) {
            talkedToOldMan = true;
            soundList.add("market/market_02.ogg");
        } else if((wordlist.contains(Game.TOKEN_TALK) || wordlist.contains(Game.TOKEN_SPEAK)) && oldManAlive && !talkedToOldMan && !((Tavern)(PlaceManager.PlaceIdentifier.TAVERN.getPlace())).bartenderAlive) {
            talkedToOldMan = true;
            soundList.add("market/market_03.ogg");
        } else if((wordlist.contains(Game.TOKEN_TALK) || wordlist.contains(Game.TOKEN_SPEAK)) && oldManAlive && talkedToOldMan) {
            talkedToOldMan = true;
            soundList.add("market/market_04.ogg");
        } else if(wordlist.contains(InventoryManager.TOKEN_BUCKET) && wordlist.contains(TOKEN_WELL) && mInventoryManager.contains(InventoryItem.BUCKET) && !bucketAttachedToWell){
            bucketAttachedToWell = true;
            mInventoryManager.remove(InventoryItem.BUCKET);
            soundList.add("market/market_05.ogg");
        } else if (((wordlist.contains(Game.TOKEN_USE) || wordlist.contains(Game.TOKEN_GIVE)) && wordlist.contains(mInventoryManager.TOKEN_BUCKET)) && oldManAlive && bucketAttachedToWell){
            soundList.add("market/market_06.ogg");
            // TODO: DUN-DIN DUN-DUN
            soundList.add("market/market_07.ogg");
            oldManAlive = false;
        }
        return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        if(oldManAlive) {
            return "market/market_00.ogg";
        } else {
            return "market/market_01.ogg";
        }

    }
//endregion
//endregion

//region Inner Classes / Interfaces
//endregion
}
