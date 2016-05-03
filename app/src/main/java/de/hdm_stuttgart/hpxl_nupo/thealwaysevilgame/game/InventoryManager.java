package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nerd on 03/05/16.
 */
public class InventoryManager extends ArrayList<InventoryItem> {
//region Constants
//endregion

//region Properties & Members
//endregion

//region Constructors
    public InventoryManager(){
        // initially add sword
        this.add(InventoryItem.SWORD);
    }
//endregion

//region Methods
    public List<String> getInventorySoundList(){
        List <String> soundList = new ArrayList<>(this.size() + 1);

        soundList.add("globalSounds/inventoryStartMessage.ogg");

        for(InventoryItem item : this){
            soundList.add(item.getSound());
        }


        return soundList;

    }

//endregion

//region Inner Classes / Interfaces
//endregion
}
