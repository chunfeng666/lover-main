//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.event.events;

import dev.blackhig.zhebushigudu.lover.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;

@Cancelable
public class MoveEvent2 extends EventStage
{
    public MoverType type;
    public double X;
    public double Y;
    public double Z;
    
    public MoveEvent2(final int stage, final MoverType type, final double x, final double y, final double z) {
        super(stage);
        this.type = type;
        this.X = x;
        this.Y = y;
        this.Z = z;
    }
    
    public MoverType getType() {
        return this.type;
    }
    
    public void setType(final MoverType type) {
        this.type = type;
    }
    
    public double getX() {
        return this.X;
    }
    
    public void setX(final double x) {
        this.X = x;
    }
    
    public double getY() {
        return this.Y;
    }
    
    public void setY(final double y) {
        this.Y = y;
    }
    
    public double getZ() {
        return this.Z;
    }
    
    public void setZ(final double z) {
        this.Z = z;
    }
}
