package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

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
//endregion
//endregion

//region Inner Classes / Interfaces
//endregion
}
