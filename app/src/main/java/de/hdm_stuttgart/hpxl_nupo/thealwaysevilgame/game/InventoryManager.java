package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

import java.util.ArrayList;
import java.util.List;

import de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game.nlp.LancasterStemmer;

/**
 * Created by nerd on 03/05/16.
 */
public class InventoryManager extends ArrayList<InventoryItem> {
//region Constants
public static final String TOKEN_COIN = LancasterStemmer.stem("coin");
    public static final String TOKEN_SWORD = LancasterStemmer.stem("sword");
    public static final String TOKEN_TORCH = LancasterStemmer.stem("torch");
    public static final String TOKEN_COAL = LancasterStemmer.stem("coal");
    public static final String TOKEN_POISON = LancasterStemmer.stem("poison");
    public static final String TOKEN_COKE = LancasterStemmer.stem("coke");
    public static final String TOKEN_BUCKET = LancasterStemmer.stem("bucket");
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

        soundList.add("inventory_items/inventoryStartMessage.ogg");

        for(InventoryItem item : this){
            soundList.add(item.getSound());
        }


        return soundList;

    }

    public List<String> getItemList(){
        List<String> inventoryItems = new ArrayList<>(this.size());

        for(InventoryItem item : this){
            inventoryItems.add(item.name());
        }

        return inventoryItems;
    }

//endregion

//region Inner Classes / Interfaces
//endregion
}
