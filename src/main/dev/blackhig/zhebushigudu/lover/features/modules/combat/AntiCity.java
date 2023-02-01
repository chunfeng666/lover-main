//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import dev.blackhig.zhebushigudu.lover.*;
import net.minecraft.util.math.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class AntiCity extends Module
{
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> toggle;
    private final Setting<Double> range;
    private int obsidian;
    
    public AntiCity() {
        super("AntiCity", "AntiCity", Category.COMBAT, true, false, false);
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.toggle = (Setting<Boolean>)this.register(new Setting("Toggle", (T)false));
        this.range = (Setting<Double>)this.register(new Setting("Range", (T)5.0, (T)1.0, (T)10.0));
        this.obsidian = -1;
    }
    
    public static boolean isHard(final Block block) {
        return block == Blocks.BEDROCK;
    }
    
    @Override
    public void onUpdate() {
        if (AntiCity.mc.player == null || AntiCity.mc.world == null) {
            return;
        }
        if (!lover.moduleManager.isModuleEnabled("Surround")) {
            return;
        }
        this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (this.obsidian == -1) {
            return;
        }
        final BlockPos pos = new BlockPos(AntiCity.mc.player.posX, AntiCity.mc.player.posY, AntiCity.mc.player.posZ);
        if (pos == null) {
            return;
        }
        if (this.getTarget(this.range.getValue()) == null) {
            return;
        }
        if (!isHard(this.getBlock(pos.add(1, 0, 0)).getBlock())) {
            if (this.getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(2, 0, 0));
            }
            if (this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(1, 0, 1));
            }
            if (this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(1, 0, -1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.OBSIDIAN && this.getBlock(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(2, 1, 0));
            }
        }
        if (!isHard(this.getBlock(pos.add(-1, 0, 0)).getBlock())) {
            if (this.getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-2, 0, 0));
            }
            if (this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-1, 0, 1));
            }
            if (this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-1, 0, -1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.OBSIDIAN && this.getBlock(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(-2, 1, 0));
            }
        }
        if (!isHard(this.getBlock(pos.add(0, 0, 1)).getBlock())) {
            if (this.getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(0, 0, 2));
            }
            if (this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(1, 0, 1));
            }
            if (this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-1, 0, 1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.OBSIDIAN && this.getBlock(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(0, 1, 2));
            }
        }
        if (!isHard(this.getBlock(pos.add(0, 0, -1)).getBlock())) {
            if (this.getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(0, 0, -2));
            }
            if (this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(1, 0, -1));
            }
            if (this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN) {
                this.perform(pos.add(-1, 0, -1));
            }
            if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.OBSIDIAN && this.getBlock(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
                this.perform(pos.add(0, 1, -2));
            }
        }
        if (this.toggle.getValue()) {
            this.toggle();
        }
    }
    
    private void switchToSlot(final int slot) {
        AntiCity.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        AntiCity.mc.player.inventory.currentItem = slot;
        AntiCity.mc.playerController.updateController();
    }
    
    private IBlockState getBlock(final BlockPos block) {
        return AntiCity.mc.world.getBlockState(block);
    }
    
    private void perform(final BlockPos pos) {
        final int old = AntiCity.mc.player.inventory.currentItem;
        this.switchToSlot(this.obsidian);
        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        this.switchToSlot(old);
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (final EntityPlayer player : AutoTrap.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range)) {
                if (lover.speedManager.getPlayerSpeed(player) > 10.0) {
                    continue;
                }
                if (target == null) {
                    target = player;
                    distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
                }
                else {
                    if (AutoTrap.mc.player.getDistanceSq((Entity)player) >= distance) {
                        continue;
                    }
                    target = player;
                    distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
                }
            }
        }
        return target;
    }
}
