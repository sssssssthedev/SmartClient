package ro.sssssssthedev.AntiCheat.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ro.sssssssthedev.AntiCheat.update.head.HeadRotation;

@AllArgsConstructor @Getter
public final class RotationUpdate {
    public HeadRotation from, to;

    public HeadRotation getFrom() {
        return from;
    }

    public void setFrom(HeadRotation from) {
        this.from = from;
    }

    public HeadRotation getTo() {
        return to;
    }

    public void setTo(HeadRotation to) {
        this.to = to;
    }
}
