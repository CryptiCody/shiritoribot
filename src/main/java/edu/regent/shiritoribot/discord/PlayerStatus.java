package edu.regent.shiritoribot.discord;

public enum PlayerStatus {
    NO_VISIBLE_STATUS(""),
    ALIVE("\u2705"),
    DEAD("\u274C"),
    ACTIVE("\u2B05");

    private String suffix;

    PlayerStatus(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
