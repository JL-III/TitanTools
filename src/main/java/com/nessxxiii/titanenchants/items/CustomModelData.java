package com.nessxxiii.titanenchants.items;

public class CustomModelData {

    //This file serves as a library to keep track of all the custom model data.
    //When creating the resource pack, refer here as this acts as a final Source of Truth.
    //Private fields are here strictly for reference.
    //Public fields are used natively inside this plugin.

    //TITAN STUFF
    public static final int EXCAVATOR = 1999999;

    public static final int POWER_CRYSTAL_COMMON = 1000000;
    public static final int POWER_CRYSTAL_UNCOMMON = 1000001;
    public static final int POWER_CRYSTAL_SUPER = 1000002;
    public static final int POWER_CRYSTAL_EPIC = 1000003;
    public static final int POWER_CRYSTAL_ULTRA = 1000004;

    //ALL CHARGED TITAN TOOLS WILL SHARE THIS CUSTOM MODEL DATA
    //SHOULD NOT CONFLICT SINCE BASE ITEMS ARE ALWAYS DIFFERENT
    //SHOULD ALLOW FOR SIMPLER CODE - ONLY NEED TO WORRY ABOUT CHARGED OR UNCHARGED
    public static final int CHARGED_TITAN_TOOL = 1000005;
    public static final int IMBUED_TITAN_TOOL = 1000005;

    //UNCHARGED TITAN TOOLS WILL HAVE THIS DATA
    public final static int UNCHARGED_TITAN_TOOL = 1000006;

    //VOTE CRATE KEYS
    //These are handled completely by the crazy crates plugin

    private static final int VOTE_KEY = 3000001;
    private static final int SPAWNER_KEY = 3000002;
    private static final int TITAN_KEY = 3000003;
    private static final int ETHEREAL_KEY = 3000004;
    private static final int CASINO_KEY = 3000005;

    //CRATE GUI ITEMS
    private static final int DENARII = 6000000;

    //HELPER ITEMS
    public static final int SUN_FISH = 3000002;
    public static final int NIGHT_FISH = 3000003;

    //ETHEREAL ITEMS
    public static final int ETHEREAL_FRAGMENT = 4000000;
    //


    //CUSTOM ITEMS
    public static final int CHRISTMAS_PICK = 5000000;
    public static final int GINGERBREAD_MAN = 5000001;
    //BASE ITEM NETHERITE_PICK
    public static final int NINETEEN_FIFTY_PICK = 5000002;
    public static final int CHROME_PICK = 5000003;
    public static final int CODERS_PICK = 5000004;
    public static final int ENDER_PICK = 5000005;
    public static final int GILDED_PICK = 5000006;
    public static final int GRIM_PICK = 5000007;
    public static final int NACHO_CHEESE_PICK = 5000008;
    public static final int PRISMARINE_PICK = 5000009;
    public static final int DARK_PRISMARINE_PICK = 5000010;
    public static final int PINK_PICK = 5000011;
    public static final int ALIEN_PICK = 5000012;
    public static final int ALLUM_PICK = 5000013;
    public static final int BONE_PICK = 5000014;
    public static final int BONEHANDLE_PICK = 5000015;
    public static final int CRAFTERS_PICK = 5000016;
    public static final int DARK_PICK = 5000017;
    public static final int ICE_BONE_PICK = 5000018;
    public static final int ICE_PICK = 5000019;
    public static final int MAGE_PICK = 5000020;
    public static final int NATURE_PICK = 5000021;
    public static final int QUICKSILVER = 5000022;
    public static final int REDSTONE_TORCH_PICK = 5000023;
    public static final int STARRY_PICK = 5000024;
    public static final int BLUE_GLASS_PICK = 5000025;
    public static final int GREEN_GLASS_PICK = 5000026;
    public static final int RED_GLASS_PICK = 5000027;
    public static final int PURPLE_GLASS_PICK = 5000028;
    public static final int JEWEL_RED_PICK = 5000029;
    public static final int JEWEL_BLUE_PICK = 5000030;
    public static final int JEWEL_GREEN_PICK = 5000031;
    public static final int JEWEL_PURPLE_PICK = 5000032;
    public static final int JEWEL_YELLOW_PICK = 5000033;

    //BASE ITEM SWORD
    public static final int DIAMOND_SHORT_SWORD = 5000000;
    public static final int NETHERITE_SHORT_SWORD = 5000000;


    //BASE ITEM PAPER
    public static final int RAFFLE_TICKET = 4000000;


    //FOOD ITEMS
    //base item cookie
    public static final int CHURRO = 7000000;
    public static final int BBQ_DOLL = 4000001;
    public static final int NESS_DOLL = 4000002;
    //base item pumpkinpie
    public static final int CORN = 7000001;
    //base item steak
    public static final int PIZZA = 7000002;
    //base item steak

    public static final int TACO = 7000003;

}
