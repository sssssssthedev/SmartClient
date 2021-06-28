package ro.sssssssthedev.AntiCheat.alert.type;

import lombok.Getter;

public enum ViolationLevel {
    EXPERIMENTAL(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    @Getter
    private int level;

    ViolationLevel(final int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
