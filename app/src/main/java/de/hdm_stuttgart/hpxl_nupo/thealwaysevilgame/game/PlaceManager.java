package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

import java.util.ArrayList;
import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.LancasterStemmer;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.Blacksmith;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.Carpenter;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.Clearing;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.Countryside;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.MarketPlace;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.Place;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.Tavern;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.TownHall;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.TravelingSalesman;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places.VillageGate;

import static de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager.PlaceIdentifier.CLEARING;

/**
 * Created by nerd on 26/04/16.
 */
public class PlaceManager {
//region Constants
    public enum PlaceIdentifier{
        CLEARING (new Clearing()),
        COUNTRYSIDE (new Countryside()),
        VILLAGE_GATE (new VillageGate()),
        TOWN_HALL (new TownHall()),
        TAVERN (new Tavern()),
        CARPENTER (new Carpenter()),
        BLACKSMITH (new Blacksmith()),
        MARKET_PLACE (new MarketPlace()),
        TRAVLING_SALESMAN (new TravelingSalesman());

        private Place mPlace;

        PlaceIdentifier(Place place){
            this.mPlace = place;
        }

        public Place getPlace(){
            return this.mPlace;
        }
    }

    private static final String TOKEN_NORTH = LancasterStemmer.stem("north");
    private static final String TOKEN_EAST = LancasterStemmer.stem("east");
    private static final String TOKEN_SOUTH = LancasterStemmer.stem("south");
    private static final String TOKEN_WEST = LancasterStemmer.stem("west");

//endregion

    public Place getCurrentPlace() {
        return mCurrentPlace.getPlace();
    }

    //region Properties & Members
    private PlaceIdentifier mCurrentPlace = CLEARING;
//endregion

//region Constructors
//endregion

//region Methods
    public List<String> parseSpeechInput(List<String> wordlist){
        List<String> returnList = new ArrayList<>();
        if(wordlist.contains(TOKEN_NORTH)){
            goNorth();
            returnList.add(getCurrentPlace().getWelcomeMediaFile());
        }
        else if(wordlist.contains(TOKEN_EAST)){
            goEast();
            returnList.add( getCurrentPlace().getWelcomeMediaFile());
        }
        else if(wordlist.contains(TOKEN_SOUTH)){
            goSouth();
            returnList.add( getCurrentPlace().getWelcomeMediaFile());
        }
        else if(wordlist.contains(TOKEN_WEST)){
            goWest();
            returnList.add( getCurrentPlace().getWelcomeMediaFile());
        }
        return returnList;
    }
    public PlaceIdentifier goNorth(){
        PlaceIdentifier newLocation = mCurrentPlace.getPlace().goNorth();
        if(newLocation != null){
            mCurrentPlace = newLocation;
            return newLocation;
        }
        return null;
    }
    public PlaceIdentifier goEast(){
        PlaceIdentifier newLocation = mCurrentPlace.getPlace().goEast();
        if(newLocation != null){
            mCurrentPlace = newLocation;
            return newLocation;
        }
        return null;
    }
    public PlaceIdentifier goSouth(){
        PlaceIdentifier newLocation = mCurrentPlace.getPlace().goSouth();
        if(newLocation != null){
            mCurrentPlace = newLocation;
            return newLocation;
        }
        return null;
    }
    public PlaceIdentifier goWest(){
        PlaceIdentifier newLocation = mCurrentPlace.getPlace().goWest();
        if(newLocation != null){
            mCurrentPlace = newLocation;
            return newLocation;
        }
        return null;
    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
