//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.movement;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import dev.blackhig.zhebushigudu.lover.event.events.a.*;
import dev.blackhig.zhebushigudu.lover.util.*;

public class TimerModule extends Module
{
    Setting<Double> tickNormal;
    Setting<Boolean> packetControl;
    Timer packetListReset;
    private int normalLookPos;
    private int rotationMode;
    private int normalPos;
    private float lastPitch;
    private float lastYaw;
    
    public TimerModule() {
        super("Timer", "Timer", Module.Category.MOVEMENT, true, false, false);
        this.tickNormal = (Setting<Double>)this.register(new Setting("Speed", (T)1.2, (T)1.0, (T)10.0));
        this.packetControl = (Setting<Boolean>)this.register(new Setting("PacketControl", (T)true));
        this.packetListReset = new Timer();
    }
    
    @SubscribeEvent
    public final void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketPlayer.Position && this.rotationMode == 1) {
            final int n = this.normalPos++;
            if (this.normalPos > 20) {
                this.rotationMode = 2;
            }
        }
        else if (event.getPacket() instanceof CPacketPlayer.PositionRotation && this.rotationMode == 2) {
            final int n = this.normalLookPos++;
            if (this.normalLookPos > 20) {
                this.rotationMode = 1;
            }
        }
    }
    
    public void onDisable() {
        if (Module.fullNullCheck()) {
            return;
        }
        Module.mc.timer.tickLength = 50.0f;
        this.packetListReset.reset();
    }
    
    public void onEnable() {
        if (Module.fullNullCheck()) {
            return;
        }
        Module.mc.timer.tickLength = 50.0f;
        this.packetListReset.reset();
        this.lastYaw = Module.mc.player.rotationYaw;
        this.lastPitch = Module.mc.player.rotationPitch;
    }
    
    @SubscribeEvent
    public final void onUpdate(final UpdateWalkingPlayerEventTwo event) {
        if (Module.fullNullCheck()) {
            return;
        }
        if (this.packetListReset.passedMs(1000L)) {
            this.normalPos = 0;
            this.normalLookPos = 0;
            this.rotationMode = 1;
            this.lastYaw = Module.mc.player.rotationYaw;
            this.lastPitch = Module.mc.player.rotationPitch;
            this.packetListReset.reset();
        }
        if (this.packetControl.getValue()) {
            switch (this.rotationMode) {
                case 1: {
                    if (!EntityUtil.isMoving()) {
                        break;
                    }
                    event.setRotation(this.lastYaw, this.lastPitch);
                    break;
                }
                case 2: {
                    event.setRotation(this.lastYaw + RandomUtil.nextFloat(1.0f, 3.0f), this.lastPitch + RandomUtil.nextFloat(1.0f, 3.0f));
                    break;
                }
            }
        }
        TimerModule.mc.timer.tickLength = (float)(50.0 / this.tickNormal.getValue());
    }
}
