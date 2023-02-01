//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.event.events;

import dev.blackhig.zhebushigudu.lover.event.*;

public class EventRender3D extends EventStage
{
    private float ticks;
    private boolean isUsingShaders;
    
    public EventRender3D() {
        this.isUsingShaders = (Shaders.getShaderPackName() != null);
    }
    
    public EventRender3D(final float ticks) {
        this.ticks = ticks;
        this.isUsingShaders = (Shaders.getShaderPackName() != null);
    }
    
    public float getPartialTicks() {
        return this.ticks;
    }
    
    public boolean isUsingShaders() {
        return this.isUsingShaders;
    }
}
