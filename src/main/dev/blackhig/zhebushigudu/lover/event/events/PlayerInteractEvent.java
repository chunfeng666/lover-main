//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.event.events;

import dev.blackhig.zhebushigudu.lover.event.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;

public class PlayerInteractEvent extends EventStage
{
    private final MovementInput MovementInput;
    
    public PlayerInteractEvent(final EntityPlayer Player, final MovementInput movementInput) {
        this.MovementInput = movementInput;
    }
    
    public MovementInput getMovementInput() {
        return this.MovementInput;
    }
}
