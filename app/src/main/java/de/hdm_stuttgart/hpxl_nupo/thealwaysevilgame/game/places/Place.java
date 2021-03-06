package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.places;

import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.InventoryManager;
import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.Navigator;

/**
 * Created by nerd on 26/04/16.
 */
public abstract class Place implements Navigator{
//region Constants
//endregion

//region Properties & Members
    protected InventoryManager mInventoryManager;
//endregion

//region Constructors
//endregion

//region Methods
    public abstract List<String> parseSpeechInput(List<String> wordlist);

    public abstract String getWelcomeMediaFile();

    public void setInventoryManager(InventoryManager inventoryManager){
        this.mInventoryManager = inventoryManager;
    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
