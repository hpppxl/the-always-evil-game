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
public class Blacksmith extends Place {
    //region Constants
    private static final String LOG_TAG = Blacksmith.class.getSimpleName();
    public static final String TOKEN_BLACKSMITH = LancasterStemmer.stem("blacksmith");
//endregion

    //region Properties & Members
    private boolean blacksmithAlive = true;
    private boolean talkedToBlacksmith = false;
//endregion

//region Constructors
//endregion


    //region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return null;
    }

    @Override
    public PlaceManager.PlaceIdentifier goEast() {
        return PlaceManager.PlaceIdentifier.MARKET_PLACE;
    }

    @Override
    public PlaceManager.PlaceIdentifier goSouth() {
        return PlaceManager.PlaceIdentifier.CARPENTER;
    }

    @Override
    public PlaceManager.PlaceIdentifier goWest() {
        return null;
    }

    @Override
    public List<String> parseSpeechInput(List<String> wordlist) {
        List<String> soundList = new ArrayList<>();
        for (String word : wordlist) {
            Log.d(LOG_TAG, word);
        }
        // catch iventory command
        if (wordlist.contains(PlaceManager.TOKEN_LOOK_AROUND)) {
            soundList.add(getWelcomeMediaFile());
        } else if (((wordlist.contains(Game.TOKEN_TALK) || wordlist.contains(Game.TOKEN_SPEAK)) && wordlist.contains(TOKEN_BLACKSMITH)) && blacksmithAlive && !talkedToBlacksmith) {
            talkedToBlacksmith = true;
            soundList.add("blacksmith/blacksmith_04.ogg");
        } else if (((wordlist.contains(Game.TOKEN_TALK) || wordlist.contains(Game.TOKEN_SPEAK)) && wordlist.contains(TOKEN_BLACKSMITH)) && blacksmithAlive && talkedToBlacksmith) {
            soundList.add("blacksmith/blacksmith_05.ogg");
        } else if (((wordlist.contains(Game.TOKEN_KILL) && wordlist.contains(TOKEN_BLACKSMITH)) || (wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_SWORD) && wordlist.contains(TOKEN_BLACKSMITH))) && blacksmithAlive) {
            blacksmithAlive = false;
            soundList.add("blacksmith/blacksmith_06.ogg");
        }else if (((wordlist.contains(Game.TOKEN_KILL) && wordlist.contains(TOKEN_BLACKSMITH)) || (wordlist.contains(Game.TOKEN_USE) && wordlist.contains(InventoryManager.TOKEN_SWORD) && wordlist.contains(TOKEN_BLACKSMITH))) && !blacksmithAlive) {
            soundList.add(Game.getRandomAlreadyDidThatMonsterSound());
        } else if (wordlist.contains(Game.TOKEN_TAKE) && wordlist.contains(InventoryManager.TOKEN_BUCKET) && blacksmithAlive && !mInventoryManager.contains(InventoryItem.BUCKET)) {
            mInventoryManager.add(InventoryItem.BUCKET);
            blacksmithAlive = false;
            soundList.add("blacksmith/blacksmith_07.ogg");
            soundList.add("globalSounds/start_listening.ogg");
            soundList.add("globalSounds/no_input.ogg");
            soundList.add("blacksmith/blacksmith_08.ogg");
        } else if (wordlist.contains(Game.TOKEN_TAKE) && wordlist.contains(InventoryManager.TOKEN_BUCKET) && !blacksmithAlive && !mInventoryManager.contains(InventoryItem.BUCKET)) {
            mInventoryManager.add(InventoryItem.BUCKET);
            soundList.add("blacksmith/blacksmith_09.ogg");
        } else if (wordlist.contains(Game.TOKEN_TAKE) && wordlist.contains(InventoryManager.TOKEN_BUCKET) && !blacksmithAlive && mInventoryManager.contains(InventoryItem.BUCKET)) {
            soundList.add(Game.getRandomAlreadyDidThatSound());
        } else if (wordlist.contains(Game.TOKEN_TAKE) && wordlist.contains(InventoryManager.TOKEN_COAL) && !mInventoryManager.contains(InventoryItem.BURNING_COAL)) {
            mInventoryManager.add(InventoryItem.BURNING_COAL);
            soundList.add("blacksmith/blacksmith_10.ogg");
        }else if (wordlist.contains(Game.TOKEN_TAKE) && wordlist.contains(InventoryManager.TOKEN_COAL) && mInventoryManager.contains(InventoryItem.BURNING_COAL)) {
            soundList.add(Game.getRandomAlreadyDidThatSound());
        }
        return soundList;
    }

    @Override
    public String getWelcomeMediaFile() {
        if (!mInventoryManager.contains(InventoryItem.BURNING_COAL) && !mInventoryManager.contains(InventoryItem.BUCKET) && blacksmithAlive) {
            return "blacksmith/blacksmith_00.ogg";
        } else if (mInventoryManager.contains(InventoryItem.BURNING_COAL) && !mInventoryManager.contains(InventoryItem.BUCKET) && blacksmithAlive) {
            return "blacksmith/blacksmith_01.ogg";
        } else if (!mInventoryManager.contains(InventoryItem.BURNING_COAL) && mInventoryManager.contains(InventoryItem.BUCKET) && !blacksmithAlive) {
            return "blacksmith/blacksmith_02.ogg";
        } else if (!mInventoryManager.contains(InventoryItem.BURNING_COAL) && !mInventoryManager.contains(InventoryItem.BUCKET) && !blacksmithAlive) {
            return "blacksmith/blacksmith_12.ogg";
        } else if (mInventoryManager.contains(InventoryItem.BURNING_COAL) && mInventoryManager.contains(InventoryItem.BUCKET) && !blacksmithAlive) {
            return "blacksmith/blacksmith_03.ogg";
        } else if (mInventoryManager.contains(InventoryItem.BURNING_COAL) && !mInventoryManager.contains(InventoryItem.BUCKET) && !blacksmithAlive) {
            return "blacksmith/blacksmith_11.ogg";
        } else {
            throw new RuntimeException("blacksmith missing state");
        }
    }
//endregion


//region Inner Classes / Interfaces
//endregion
}
