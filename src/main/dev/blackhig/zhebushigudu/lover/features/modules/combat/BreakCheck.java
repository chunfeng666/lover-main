//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import net.minecraft.util.math.*;
import net.minecraftforge.client.event.sound.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import net.minecraft.init.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class BreakCheck extends Module
{
    private static BreakCheck INSTANCE;
    public BlockPos BrokenPos;
    
    public BreakCheck() {
        super("BreakCheck", "Check instant mine.", Category.COMBAT, true, false, false);
        this.setInstance();
    }
    
    public static BreakCheck Instance() {
        if (BreakCheck.INSTANCE == null) {
            BreakCheck.INSTANCE = new BreakCheck();
        }
        return BreakCheck.INSTANCE;
    }
    
    private void setInstance() {
        BreakCheck.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void BrokenBlock(final PlaySoundEvent event) {
        if (InstantMine.breakPos != null && InstantMine.breakPos.equals((Object)new BlockPos((double)event.getSound().getXPosF(), (double)event.getSound().getYPosF(), (double)event.getSound().getZPosF()))) {
            return;
        }
        if (!event.getName().endsWith("hit")) {
            return;
        }
        if (event.getName().endsWith("arrow.hit")) {
            return;
        }
        if (event.getName().endsWith("stand.hit")) {
            return;
        }
        if (BreakCheck.mc.world.getBlockState(new BlockPos((double)event.getSound().getXPosF(), (double)event.getSound().getYPosF(), (double)event.getSound().getZPosF())).getBlock() == Blocks.BEDROCK) {
            return;
        }
        this.BrokenPos = new BlockPos((double)event.getSound().getXPosF(), (double)event.getSound().getYPosF(), (double)event.getSound().getZPosF());
    }
    
    @SubscribeEvent
    public void BrokenBlock2(final PlaySoundEvent event) {
        if (InstantMine.breakPos != null && InstantMine.breakPos.equals((Object)new BlockPos((double)event.getSound().getXPosF(), (double)event.getSound().getYPosF(), (double)event.getSound().getZPosF()))) {
            return;
        }
        if (!event.getName().endsWith("break")) {
            return;
        }
        if (event.getName().endsWith("potion.break")) {
            return;
        }
        if (BreakCheck.mc.world.getBlockState(new BlockPos((double)event.getSound().getXPosF(), (double)event.getSound().getYPosF(), (double)event.getSound().getZPosF())).getBlock() == Blocks.BEDROCK) {
            return;
        }
        this.BrokenPos = new BlockPos((double)event.getSound().getXPosF(), (double)event.getSound().getYPosF(), (double)event.getSound().getZPosF());
    }
    
    static {
        BreakCheck.INSTANCE = new BreakCheck();
    }
}
