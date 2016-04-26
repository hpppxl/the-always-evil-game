package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

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
//endregion

//region Properties & Members
    private Place mCurrentPlace;
//endregion

//region Constructors
//endregion

//region Methods
    public void goNorth(){

    }
    public void goEast(){

    }
    public void goSouth(){

    }
    public void goWest(){

    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
