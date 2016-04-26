package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager;

/**
 * Created by nerd on 26/04/16.
 */
public class Blacksmith extends Place{
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
//endregion
//endregion

//region Inner Classes / Interfaces
//endregion
}
