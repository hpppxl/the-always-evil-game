package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager;

/**
 * Created by nerd on 26/04/16.
 */
public class TravelingSalesman extends Place{
//region Constants
//endregion

//region Properties & Members
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
        return null;
    }

    @Override
    public String getWelcomeMediaFile() {
        return "welcomeMessages/location_travelingsalesman.ogg";
    }
//endregion
//endregion

//region Inner Classes / Interfaces
//endregion
}
