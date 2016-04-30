package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager;

/**
 * Created by nerd on 26/04/16.
 */
public class VillageGate extends Place {
//region Constants
//endregion

//region Properties & Members
//endregion

//region Constructors
//endregion

//region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return PlaceManager.PlaceIdentifier.TOWN_HALL;
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
    public String parseSpeechInput(List<String> wordlist) {
        return null;
    }

    @Override
    public String getWelcomeMediaFile() {
        return "welcomeMessages/location_villagegate.ogg";
    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
