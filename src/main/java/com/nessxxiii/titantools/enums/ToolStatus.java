package com.nessxxiii.titantools.enums;

public enum ToolStatus {
    ON("ON"),
    OFF("OFF");
    private final String string;
    ToolStatus(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

}
