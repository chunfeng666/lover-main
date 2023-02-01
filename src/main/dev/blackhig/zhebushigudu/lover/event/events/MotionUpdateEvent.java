//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.event.events;

import dev.blackhig.zhebushigudu.lover.event.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public final class MotionUpdateEvent extends EventStage
{
    private Location location;
    private Vec2f rotation;
    private Vec2f prevRotation;
    private boolean rotating;
    
    public MotionUpdateEvent(final Location location, final Vec2f rotation, final Vec2f prevRotation) {
        this.location = location;
        this.rotation = rotation;
        this.prevRotation = prevRotation;
    }
    
    public final Location getLocation() {
        return this.location;
    }
    
    public final void setLocation(final Location location) {
        this.location = location;
    }
    
    public final Vec2f getRotation() {
        return this.rotation;
    }
    
    public final void setRotation(final Vec2f vec2f) {
        this.rotation = vec2f;
    }
    
    public final Vec2f getPrevRotation() {
        return this.prevRotation;
    }
    
    public final void setPrevRotation(@NotNull final Vec2f vec2f) {
        this.prevRotation = vec2f;
    }
    
    public final boolean getRotating() {
        return this.rotating;
    }
    
    public final void setRotating(final boolean bl) {
        this.rotating = bl;
    }
    
    public class FastTick
    {
    }
}
