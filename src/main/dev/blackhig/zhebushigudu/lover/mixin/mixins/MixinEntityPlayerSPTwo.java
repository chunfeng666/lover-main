//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.mixin.mixins;

import net.minecraft.client.entity.*;
import net.minecraft.client.network.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import dev.blackhig.zhebushigudu.lover.event.events.a.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ EntityPlayerSP.class })
public abstract class MixinEntityPlayerSPTwo extends MixinEntity
{
    @Shadow
    @Final
    public NetHandlerPlayClient connection;
    @Shadow
    public boolean serverSprintState;
    @Shadow
    public boolean serverSneakState;
    @Shadow
    public double lastReportedPosX;
    @Shadow
    public double lastReportedPosY;
    @Shadow
    public double lastReportedPosZ;
    @Shadow
    public float lastReportedYaw;
    @Shadow
    public float lastReportedPitch;
    @Shadow
    public int positionUpdateTicks;
    @Shadow
    public boolean autoJumpEnabled;
    @Shadow
    public boolean prevOnGround;
    @Shadow
    public MovementInput movementInput;
    @Shadow
    protected Minecraft mc;
    
    public MixinEntityPlayerSPTwo() {
        this.autoJumpEnabled = true;
    }
    
    @Shadow
    public boolean isCurrentViewEntity() {
        return false;
    }
    
    @Overwrite
    public void onUpdateWalkingPlayer() {
        final boolean flag = this.isSprinting();
        final UpdateWalkingPlayerEventTwo pre = new UpdateWalkingPlayerEventTwo(0, this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, this.onGround);
        MinecraftForge.EVENT_BUS.post((Event)pre);
        if (pre.isCanceled()) {
            final UpdateWalkingPlayerEventTwo post = new UpdateWalkingPlayerEventTwo(1, pre.getX(), pre.getY(), pre.getZ(), pre.getYaw(), pre.getPitch(), pre.isOnGround());
            MinecraftForge.EVENT_BUS.post((Event)post);
            return;
        }
        if (flag != this.serverSprintState) {
            if (flag) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SPRINTING));
            }
            else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.STOP_SPRINTING));
            }
            this.serverSprintState = flag;
        }
        final boolean flag2 = this.isSneaking();
        if (flag2 != this.serverSneakState) {
            if (flag2) {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.START_SNEAKING));
            }
            else {
                this.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Minecraft.getMinecraft().player, CPacketEntityAction.Action.STOP_SNEAKING));
            }
            this.serverSneakState = flag2;
        }
        if (this.isCurrentViewEntity()) {
            final double d0 = this.posX - this.lastReportedPosX;
            final double d2 = this.getEntityBoundingBox().minY - this.lastReportedPosY;
            final double d3 = this.posZ - this.lastReportedPosZ;
            final double d4 = pre.getYaw() - this.lastReportedYaw;
            final double d5 = pre.getPitch() - this.lastReportedPitch;
            boolean flag3 = d0 * d0 + d2 * d2 + d3 * d3 > 9.0E-4 || this.positionUpdateTicks >= 20;
            final boolean flag4 = d4 != 0.0 || d5 != 0.0;
            if (this.ridingEntity == null) {
                if (flag3 && flag4) {
                    this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(pre.getX(), pre.getY(), pre.getZ(), pre.getYaw(), pre.getPitch(), pre.isOnGround()));
                }
                else if (flag3) {
                    this.connection.sendPacket((Packet)new CPacketPlayer.Position(pre.getX(), pre.getY(), pre.getZ(), pre.isOnGround()));
                }
                else if (flag4) {
                    this.connection.sendPacket((Packet)new CPacketPlayer.Rotation(pre.getYaw(), pre.getPitch(), pre.isOnGround()));
                }
                else {
                    this.connection.sendPacket((Packet)new CPacketPlayer(pre.isOnGround()));
                }
            }
            else {
                this.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(this.motionX, -999.0, this.motionZ, pre.getYaw(), pre.getPitch(), pre.isOnGround()));
                flag3 = false;
            }
            ++this.positionUpdateTicks;
            if (flag3) {
                this.lastReportedPosX = pre.getX();
                this.lastReportedPosY = pre.getY();
                this.lastReportedPosZ = pre.getZ();
                this.positionUpdateTicks = 0;
            }
            if (flag4) {
                this.lastReportedYaw = pre.getYaw();
                this.lastReportedPitch = pre.getPitch();
            }
            this.prevOnGround = this.onGround;
            this.autoJumpEnabled = this.mc.gameSettings.autoJump;
            final UpdateWalkingPlayerEventTwo post2 = new UpdateWalkingPlayerEventTwo(1, pre.getX(), pre.getY(), pre.getZ(), pre.getYaw(), pre.getPitch(), pre.isOnGround());
            MinecraftForge.EVENT_BUS.post((Event)post2);
        }
    }
}
