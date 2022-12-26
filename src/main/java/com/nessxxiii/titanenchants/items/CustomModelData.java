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

    //CUSTOM ITEMS
    public static final int CHRISTMAS_PICK = 5000000;
    public static final int GINGERBREAD_MAN = 5000001;

}
