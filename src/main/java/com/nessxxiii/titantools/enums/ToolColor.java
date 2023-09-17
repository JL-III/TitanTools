package com.nessxxiii.titantools.enums;

public enum ToolColor {
    RED("§x§7§F§0§0§0§0", "§x§F§F§0§0§0§0"),
    YELLOW("§x§E§E§8§8§0§0", "§x§F§F§E§C§2§7"),
    BLUE("§x§3§1§2§1§B§4", "§x§6§D§5§E§F§F");
    private final String darkColorCode;
    private final String brightColorCode;

    ToolColor(String darkColorCode, String brightColorCode) {
        this.darkColorCode = darkColorCode;
        this.brightColorCode = brightColorCode;
    }

    public String getDarkColorCode() {
        return darkColorCode;
    }
    public String getBrightColorCode() {
        return brightColorCode;
    }
}
