package com.nessxxiii.titantools.enums;

public enum ToolColor {
    RED("§x§7§F§0§0§0§0", "§x§F§F§0§0§0§0", "#7F0000", "#FF0000"),
    YELLOW("§x§E§E§8§8§0§0", "§x§F§F§E§C§2§7", "#EE8800", "#FFEC27"),
    BLUE("§x§3§1§2§1§B§4", "§x§6§D§5§E§F§F", "#3121B4", "#6D5EFF");
    private final String darkColorCode;
    private final String brightColorCode;
    private final String darkHexCode;
    private final String brightHexCode;

    ToolColor(String darkColorCode, String brightColorCode, String darkHexCode, String brightHexCode) {
        this.darkColorCode = darkColorCode;
        this.brightColorCode = brightColorCode;
        this.darkHexCode = darkHexCode;
        this.brightHexCode = brightHexCode;
    }
    public String getDarkHexCode() { return darkHexCode; }
    public String getBrightHexCode() { return brightHexCode; }
    public String getDarkColorCode() { return darkColorCode; }
    public String getBrightColorCode() { return brightColorCode; }
}
