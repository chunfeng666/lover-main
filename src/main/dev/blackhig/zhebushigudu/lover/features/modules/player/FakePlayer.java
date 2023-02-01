//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.player;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import net.minecraft.client.entity.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import java.util.*;
import com.mojang.authlib.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FakePlayer extends Module
{
    private EntityOtherPlayerMP clonedPlayer;
    private final Setting<Integer> setHealth;
    private final Setting<String> playername;
    private final Setting<Boolean> iscopyplayerstack;
    private final Setting<Boolean> runner;
    private ItemStack[] playerarmor;
    
    public FakePlayer() {
        super("FakePlayer", "Spawns a FakePlayer for testing", Module.Category.PLAYER, true, false, false);
        this.setHealth = (Setting<Integer>)this.register(new Setting("SetHealth", (T)20, (T)1, (T)20));
        this.playername = (Setting<String>)this.register(new Setting("", (T)"LoverHack"));
        this.iscopyplayerstack = (Setting<Boolean>)this.register(new Setting("CopyInventory", (T)true));
        this.runner = (Setting<Boolean>)this.register(new Setting("Runner", (T)false));
        this.playerarmor = null;
        this.playerarmor = new ItemStack[] { new ItemStack((Item)Items.DIAMOND_BOOTS), new ItemStack((Item)Items.DIAMOND_LEGGINGS), new ItemStack((Item)Items.DIAMOND_CHESTPLATE), new ItemStack((Item)Items.DIAMOND_HELMET) };
    }
    
    public void onEnable() {
        if (FakePlayer.mc.player == null || FakePlayer.mc.player.isDead) {
            this.disable();
            return;
        }
        final InventoryPlayer a = FakePlayer.mc.player.inventory;
        (this.clonedPlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.fromString("a3ca166d-c5f1-3d5a-baac-b18a5b38d4cd"), (String)this.playername.getValue()))).copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
        this.clonedPlayer.rotationYawHead = FakePlayer.mc.player.rotationYawHead;
        this.clonedPlayer.rotationYaw = FakePlayer.mc.player.rotationYaw;
        this.clonedPlayer.rotationPitch = FakePlayer.mc.player.rotationPitch;
        this.clonedPlayer.setGameType(GameType.SURVIVAL);
        this.clonedPlayer.setHealth((float)this.setHealth.getValue());
        FakePlayer.mc.world.addEntityToWorld(-114514, (Entity)this.clonedPlayer);
        if (this.iscopyplayerstack.getValue()) {
            this.clonedPlayer.inventory.copyInventory(a);
        }
        this.clonedPlayer.onLivingUpdate();
    }
    
    @SubscribeEvent
    public void input(final InputUpdateEvent e) {
        System.out.println("e.getMovementInput() = " + e.getMovementInput());
    }
    
    public void onDisable() {
        if (FakePlayer.mc.world != null) {
            FakePlayer.mc.world.removeEntityFromWorld(-114514);
        }
    }
    
    private double roundValueToCenter(final double inputVal) {
        double roundVal = (double)Math.round(inputVal);
        if (roundVal > inputVal) {
            roundVal -= 0.5;
        }
        else if (roundVal <= inputVal) {
            roundVal += 0.5;
        }
        return roundVal;
    }
}
