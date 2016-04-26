package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

/**
 * Created by nerd on 26/04/16.
 */
public interface Navigator {

    PlaceManager.PlaceIdentifier goNorth();
    PlaceManager.PlaceIdentifier goEast();
    PlaceManager.PlaceIdentifier goSouth();
    PlaceManager.PlaceIdentifier goWest();

}
