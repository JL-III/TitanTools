//package com.nessxxiii.titanenchants.util;
//
//import de.tr7zw.nbtapi.NBTItem;
//import org.bukkit.Bukkit;
//import org.bukkit.inventory.ItemStack;
//
//public class NBTUtils {
//
//    public static boolean setHasChargeBooleanToTrue(ItemStack item) {
//        try {
//            NBTItem nbti = new NBTItem(item);
//            nbti.setBoolean("hasCharge", true);
//            nbti.setBoolean("isImbued", false);
//            nbti.setInteger("itemLevel", 1);
//            nbti.applyNBT(item);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public static boolean setHasChargeBooleanToFalse(ItemStack item) {
//        try {
//            NBTItem nbti = new NBTItem(item);
//            nbti.setBoolean("hasCharge", false);
//            nbti.setInteger("itemLevel", 0);
//            nbti.applyNBT(item);
//            Bukkit.getConsoleSender().sendMessage("Setting has charge to false");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return false;
//        }
//        return true;
//    }
//
//    public static void setIsActiveBooleanToTrue(ItemStack item) {
//        NBTItem nbtItem = new NBTItem(item);
//        nbtItem.setBoolean("isActive", true);
//        nbtItem.applyNBT(item);
//    }
//
//    public static void setIsActiveBooleanToFalse(ItemStack item) {
//
//        NBTItem nbtItem = new NBTItem(item);
//        nbtItem.setBoolean("isActive", false);
//        nbtItem.applyNBT(item);
//
//    }
//
//}
