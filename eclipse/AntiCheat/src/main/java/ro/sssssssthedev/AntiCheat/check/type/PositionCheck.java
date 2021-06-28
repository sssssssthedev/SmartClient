package ro.sssssssthedev.AntiCheat.check.type;

import ro.sssssssthedev.AntiCheat.check.Check;
import ro.sssssssthedev.AntiCheat.data.PlayerData;
import ro.sssssssthedev.AntiCheat.update.PositionUpdate;

public class PositionCheck extends Check<PositionUpdate> {

    public PositionCheck(final PlayerData playerData) {
        super(playerData);
    }

    @Override
    public void process(PositionUpdate positionUpdate) {

    }
}
