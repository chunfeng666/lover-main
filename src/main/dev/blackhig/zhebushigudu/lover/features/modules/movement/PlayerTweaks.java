//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.movement;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.server.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;

public class PlayerTweaks extends Module
{
    public static PlayerTweaks INSTANCE;
    public Setting<Boolean> noSlow;
    public Setting<Boolean> antiKnockBack;
    public Setting<Boolean> noEntityPush;
    public Setting<Boolean> noBlockPush;
    public Setting<Boolean> noWaterPush;
    public Setting<Boolean> Sprint;
    public Setting<Boolean> guiMove;
    
    public PlayerTweaks() {
        super("PlayerTweaks", "tweaks", Module.Category.MOVEMENT, true, false, false);
        this.noSlow = (Setting<Boolean>)this.register(new Setting("No Slow", (T)true));
        this.antiKnockBack = (Setting<Boolean>)this.register(new Setting("Velocity", (T)true));
        this.noEntityPush = (Setting<Boolean>)this.register(new Setting("No PlayerPush", (T)true));
        this.noBlockPush = (Setting<Boolean>)this.register(new Setting("No BlockPush", (T)true));
        this.noWaterPush = (Setting<Boolean>)this.register(new Setting("No LiquidPush", (T)true));
        this.Sprint = (Setting<Boolean>)this.register(new Setting("Sprint", (T)true));
        this.guiMove = (Setting<Boolean>)this.register(new Setting("Gui Move", (T)true));
        this.setInstance();
    }
    
    public static PlayerTweaks getInstance() {
        if (PlayerTweaks.INSTANCE == null) {
            PlayerTweaks.INSTANCE = new PlayerTweaks();
        }
        return PlayerTweaks.INSTANCE;
    }
    
    private void setInstance() {
        PlayerTweaks.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void Slow(final InputUpdateEvent event) {
        if (this.noSlow.getValue() && PlayerTweaks.mc.player.isHandActive() && !PlayerTweaks.mc.player.isRiding()) {
            final MovementInput movementInput = event.getMovementInput();
            movementInput.moveStrafe *= 5.0f;
            final MovementInput movementInput2 = event.getMovementInput();
            movementInput2.moveForward *= 5.0f;
        }
    }
    
    @SubscribeEvent
    public void onPacketReceived(final PacketEvent.Receive event) {
        if (fullNullCheck()) {
            return;
        }
        if (this.antiKnockBack.getValue()) {
            if (event.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)event.getPacket()).getEntityID() == PlayerTweaks.mc.player.getEntityId()) {
                event.setCanceled(true);
            }
            if (event.getPacket() instanceof SPacketExplosion) {
                event.setCanceled(true);
            }
        }
    }
    
    public void onTick() {
        if (this.Sprint.getValue() && (PlayerTweaks.mc.player.moveForward != 0.0f || PlayerTweaks.mc.player.moveStrafing != 0.0f) && !PlayerTweaks.mc.player.isSprinting()) {
            PlayerTweaks.mc.player.setSprinting(true);
        }
    }
    
    @SubscribeEvent
    public void onPush(final PushEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (event.getStage() == 0 && this.noEntityPush.getValue() && event.entity.equals((Object)PlayerTweaks.mc.player)) {
            event.x = -event.x * 0.0;
            event.y = -event.y * 0.0;
            event.z = -event.z * 0.0;
        }
        else if (event.getStage() == 1 && this.noBlockPush.getValue()) {
            event.setCanceled(true);
        }
        else if (event.getStage() == 2 && this.noWaterPush.getValue() && PlayerTweaks.mc.player != null && PlayerTweaks.mc.player.equals((Object)event.entity)) {
            event.setCanceled(true);
        }
    }
    
    static {
        PlayerTweaks.INSTANCE = new PlayerTweaks();
    }
}
