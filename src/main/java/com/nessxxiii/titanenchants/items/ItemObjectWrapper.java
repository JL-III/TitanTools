package com.nessxxiii.titanenchants.items;

import org.bukkit.inventory.ItemStack;

public class ItemObjectWrapper {

//    The point of this class is to prove a way to check all Titan Tool information at once.
//    currently items are checked several times for the same information.
//    This class will hopefully remove that need to check the lore over and over again during processes

    private Boolean isBoolean;
    private Boolean isImbued;
    private Boolean isCharged;
    private Boolean isTitanTool;
    private Integer powerLevel;

    public ItemObjectWrapper(ItemStack item) {
        this.isTitanTool = ItemInfo.isTitanTool(item);
        this.isImbued = ItemInfo.isImbued(item);
        this.isCharged = ItemInfo.isCharged(item);
        this.powerLevel = ItemInfo.getItemLevel(item);
    }

}
