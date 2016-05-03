package de.hdm_stuttgart.hpxl_nupo.thealwaysevilgame.game;

/**
 * Created by nerd on 26/04/16.
 */
public enum InventoryItem {
//region Constants
    GOLDEN_COIN,
    SWORD,
    UNLIT_TORCH,
    BURNING_TORCH,
    BURNING_COAL,
    RAT_POISON_AND_COKE,
    BUCKET;

//endregion

//region Properties & Members
//endregion

//region Constructors
//endregion

//region Methods
    public String getSound(){
        switch(this){
            case GOLDEN_COIN:
                return "inventory_items/inventory_coin.ogg";
            case SWORD:
                return "inventory_items/inventory_sword.ogg";
            case UNLIT_TORCH:
                return "inventory_items/inventory_unlit_torch.ogg";
            case BURNING_TORCH:
                return "inventory_items/inventory_torch.ogg";
            case BURNING_COAL:
                return "inventory_items/inventory_coal.ogg";
            case RAT_POISON_AND_COKE:
                return "inventory_items/inventory_rat_poison_coke.ogg";
            case BUCKET:
                return "inventory_items/inventory_bucket.ogg";
            default:
                return null;
        }
    }
//endregion

//region Inner Classes / Interfaces
//endregion
}
