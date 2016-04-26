package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Navigator;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.PlaceManager;

/**
 * Created by nerd on 26/04/16.
 */
public class Clearing extends Place {
//region Constants
//endregion

//region Properties & Members
//endregion

//region Constructors
//endregion

//region Methods
    @Override
    public PlaceManager.PlaceIdentifier goNorth() {
        return PlaceManager.PlaceIdentifier.COUNTRYSIDE;
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
//endregion

//region Inner Classes / Interfaces
//endregion
}
