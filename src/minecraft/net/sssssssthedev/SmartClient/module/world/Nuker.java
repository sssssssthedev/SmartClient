package net.sssssssthedev.SmartClient.module.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import net.sssssssthedev.SmartClient.event.EventTarget;
import net.sssssssthedev.SmartClient.event.impl.UpdateEvent;
import net.sssssssthedev.SmartClient.module.Category;
import net.sssssssthedev.SmartClient.module.Module;
import net.sssssssthedev.SmartClient.module.world.nukerutils.NukerBypassLevel;
import net.sssssssthedev.SmartClient.module.world.nukerutils.NukerMode;
import net.sssssssthedev.SmartClient.utils.BlockUtil;
import net.sssssssthedev.SmartClient.utils.Random;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Nuker extends Module {
    public Nuker() {
        super("Nuker", Keyboard.KEY_0, Category.WORLD);
    }

    int count = 6;
    NukerBypassLevel bypass = NukerBypassLevel.NONE;
    NukerMode mode = NukerMode.ALL;

    int breaksPerCycle() {
        return bypass == NukerBypassLevel.SLOW ? 3 : 1;
    }

    @EventTarget
    public void onUpdate(UpdateEvent e) {
        if(bypass == NukerBypassLevel.NONE || count < 1) {
            BlockPos[] positions = BlockUtil.getBlocksAround(mc.thePlayer, (bypass == NukerBypassLevel.NONE ? 6 : 3), bypass != NukerBypassLevel.NONE);
            if(mode.equals(NukerMode.ALL)) {
                if(bypass == NukerBypassLevel.NONE) {
                    for(BlockPos p : positions)
                        BlockUtil.breakBlock(p);
                }else {
                    if(positions.length <= breaksPerCycle()) {
                        for(BlockPos p : positions)
                            BlockUtil.breakBlock(p);
                    }else {
                        List<Integer> sent = new ArrayList<>();
                        for(int i = 0; i < breaksPerCycle(); i++) {
                            int rr = Random.rand(positions.length-1);
                            while(sent.contains(rr))
                                rr = Random.rand(positions.length-1);
                            if(bypass == NukerBypassLevel.LEGIT) BlockUtil.faceBlock(positions[rr]);
                            BlockUtil.breakBlock(positions[rr]);
                            sent.add(rr);
                        }
                    }
                }
            }else if(mode.equals(NukerMode.CLICK)) {
                BlockPos b = mc.playerController.clickedBlock;
                if(b == null) return;
                if(bypass == NukerBypassLevel.NONE) {
                    for(BlockPos p : positions)
                        if(Block.getIdFromBlock(mc.theWorld.getBlock(p)) == Block.getIdFromBlock(mc.theWorld.getBlock(b)))
                            BlockUtil.breakBlock(p);
                }else {
                    List<BlockPos> poss = new ArrayList<>();
                    for(BlockPos p : positions)
                        if(Block.getIdFromBlock(mc.theWorld.getBlock(p)) == Block.getIdFromBlock(mc.theWorld.getBlock(b)))
                            poss.add(p);

                    if(poss.size() <= breaksPerCycle())
                        for(BlockPos p : poss)
                            BlockUtil.breakBlock(p);
                    else {
                        List<Integer> sent = new ArrayList<>();
                        for(int i = 0; i < breaksPerCycle(); i++) {
                            int rr = Random.rand(poss.size()-1);
                            while(sent.contains(rr))
                                rr = Random.rand(poss.size()-1);
                            if(bypass == NukerBypassLevel.LEGIT) BlockUtil.faceBlock(poss.get(rr));
                            BlockUtil.breakBlock(poss.get(rr));
                            sent.add(rr);
                        }
                    }
                }
            } else System.out.println("\u00a74I guess I f*cked up and forgot to add support for this mode, please report this!");
            count = 6;
        } else count--;
    }


}
