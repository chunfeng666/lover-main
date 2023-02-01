//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.event.events;

import dev.blackhig.zhebushigudu.lover.event.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.util.*;

public class BlockBreakEvent extends EventStage
{
    private final int breakId;
    private final BlockPos position;
    private final int progress;
    
    public BlockBreakEvent(final int breakId, final BlockPos position, final int progress) {
        this.breakId = breakId;
        this.position = position;
        this.progress = progress;
    }
    
    public BlockPos getPosition() {
        return this.position;
    }
    
    public int getBreakId() {
        return this.breakId;
    }
    
    public int getProgress() {
        return this.progress;
    }
    
    public EntityPlayer getBreaker() {
        return (EntityPlayer)Util.mc.world.getEntityByID(this.getBreakId());
    }
}
