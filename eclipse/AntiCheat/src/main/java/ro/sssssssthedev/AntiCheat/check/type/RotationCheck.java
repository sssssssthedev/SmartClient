package ro.sssssssthedev.AntiCheat.check.type;

import ro.sssssssthedev.AntiCheat.check.Check;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.RotationUpdate;

public class RotationCheck extends Check<RotationUpdate> {

    public RotationCheck(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(RotationUpdate rotationUpdate) {

    }
}
