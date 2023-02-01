//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.player;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import net.minecraft.util.math.*;
import dev.blackhig.zhebushigudu.lover.features.modules.combat.*;
import net.minecraft.block.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.*;

public class AntiHoleKick extends Module
{
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> head;
    private final Setting<Boolean> mine;
    BlockPos pos;
    
    public AntiHoleKick() {
        super("AntiHoleKick", "Anti piston push.", Module.Category.PLAYER, true, false, false);
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.head = (Setting<Boolean>)this.register(new Setting("TrapHead", (T)false));
        this.mine = (Setting<Boolean>)this.register(new Setting("Mine", (T)true));
    }
    
    public void onUpdate() {
        this.pos = new BlockPos(AntiHoleKick.mc.player.posX, AntiHoleKick.mc.player.posY, AntiHoleKick.mc.player.posZ);
        if (this.getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.PISTON || this.getBlock(this.pos.add(0, 2, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(0, 1, 2)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK) {
            if (this.getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.AIR) {
                this.perform(this.pos.add(0, 1, -1));
            }
            if (this.getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.PISTON && this.mine.getValue()) {
                AntiHoleKick.mc.playerController.onPlayerDamageBlock(this.pos.add(0, 1, 1), BlockUtil.getRayTraceFacing(this.pos.add(0, 1, 1)));
            }
            if (this.getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && this.head.getValue()) {
                this.perform(this.pos.add(0, 2, -1));
                this.perform(this.pos.add(0, 2, 0));
            }
        }
        if (this.getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.PISTON || this.getBlock(this.pos.add(0, 2, -1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(0, 1, -2)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
            if (this.getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.AIR) {
                this.perform(this.pos.add(0, 1, 1));
            }
            if (this.getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.PISTON && this.mine.getValue()) {
                AntiHoleKick.mc.playerController.onPlayerDamageBlock(this.pos.add(0, 1, -1), BlockUtil.getRayTraceFacing(this.pos.add(0, 1, -1)));
            }
            if (this.getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && this.head.getValue()) {
                this.perform(this.pos.add(0, 2, 1));
                this.perform(this.pos.add(0, 2, 0));
            }
        }
        if (this.getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.PISTON || this.getBlock(this.pos.add(1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
            if (this.getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(this.pos.add(-1, 1, 0));
            }
            if (this.getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.PISTON && this.mine.getValue()) {
                AntiHoleKick.mc.playerController.onPlayerDamageBlock(this.pos.add(1, 1, 0), BlockUtil.getRayTraceFacing(this.pos.add(1, 1, 0)));
            }
            if (this.getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && this.head.getValue()) {
                this.perform(this.pos.add(-1, 2, 0));
                this.perform(this.pos.add(0, 2, 0));
            }
        }
        if (this.getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.PISTON || this.getBlock(this.pos.add(-1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
            if (this.getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(this.pos.add(1, 1, 0));
            }
            if (this.getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.PISTON && this.mine.getValue()) {
                AntiHoleKick.mc.playerController.onPlayerDamageBlock(this.pos.add(-1, 1, 0), BlockUtil.getRayTraceFacing(this.pos.add(-1, 1, 0)));
            }
            if (this.getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && this.head.getValue()) {
                this.perform(this.pos.add(1, 2, 0));
                this.perform(this.pos.add(0, 2, 0));
            }
        }
        if (this.getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.STICKY_PISTON || this.getBlock(this.pos.add(0, 2, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(0, 1, 2)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK) {
            if (this.getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.AIR) {
                this.perform(this.pos.add(0, 1, -1));
            }
            if (this.getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.STICKY_PISTON && this.mine.getValue()) {
                AntiHoleKick.mc.playerController.onPlayerDamageBlock(this.pos.add(0, 1, 1), BlockUtil.getRayTraceFacing(this.pos.add(0, 1, 1)));
            }
            if (this.getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && this.head.getValue()) {
                this.perform(this.pos.add(0, 2, -1));
                this.perform(this.pos.add(0, 2, 0));
            }
        }
        if (this.getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.STICKY_PISTON || this.getBlock(this.pos.add(0, 2, -1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(0, 1, -2)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
            if (this.getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.AIR) {
                this.perform(this.pos.add(0, 1, 1));
            }
            if (this.getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.STICKY_PISTON && this.mine.getValue()) {
                AntiHoleKick.mc.playerController.onPlayerDamageBlock(this.pos.add(0, 1, -1), BlockUtil.getRayTraceFacing(this.pos.add(0, 1, -1)));
            }
            if (this.getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && this.head.getValue()) {
                this.perform(this.pos.add(0, 2, 1));
                this.perform(this.pos.add(0, 2, 0));
            }
        }
        if (this.getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.STICKY_PISTON || this.getBlock(this.pos.add(1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
            if (this.getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(this.pos.add(-1, 1, 0));
            }
            if (this.getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.STICKY_PISTON && this.mine.getValue()) {
                AntiHoleKick.mc.playerController.onPlayerDamageBlock(this.pos.add(1, 1, 0), BlockUtil.getRayTraceFacing(this.pos.add(1, 1, 0)));
            }
            if (this.getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && this.head.getValue()) {
                this.perform(this.pos.add(-1, 2, 0));
                this.perform(this.pos.add(0, 2, 0));
            }
        }
        if (this.getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.STICKY_PISTON || this.getBlock(this.pos.add(-1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
            if (this.getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.AIR) {
                this.perform(this.pos.add(1, 1, 0));
            }
            if (this.getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.STICKY_PISTON && this.mine.getValue()) {
                AntiHoleKick.mc.playerController.onPlayerDamageBlock(this.pos.add(-1, 1, 0), BlockUtil.getRayTraceFacing(this.pos.add(-1, 1, 0)));
            }
            if (this.getBlock(this.pos.add(0, 2, 0)).getBlock() == Blocks.AIR && this.head.getValue()) {
                this.perform(this.pos.add(1, 2, 0));
                this.perform(this.pos.add(0, 2, 0));
            }
        }
    }
    
    private IBlockState getBlock(final BlockPos block) {
        return AntiHoleKick.mc.world.getBlockState(block);
    }
    
    public String getDisplayInfo() {
        if (AntiHoleKick.mc.player != null) {
            if (this.getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.PISTON || this.getBlock(this.pos.add(-1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return "Working";
            }
            if (this.getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.PISTON || this.getBlock(this.pos.add(1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return "Working";
            }
            if (this.getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.PISTON || this.getBlock(this.pos.add(0, 2, -1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(0, 1, -2)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return "Working";
            }
            if (this.getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.PISTON || this.getBlock(this.pos.add(0, 2, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(0, 1, 2)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return "Working";
            }
            if (this.getBlock(this.pos.add(-1, 1, 0)).getBlock() == Blocks.STICKY_PISTON || this.getBlock(this.pos.add(-1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return "Working";
            }
            if (this.getBlock(this.pos.add(1, 1, 0)).getBlock() == Blocks.STICKY_PISTON || this.getBlock(this.pos.add(1, 2, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(2, 1, 0)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return "Working";
            }
            if (this.getBlock(this.pos.add(0, 1, -1)).getBlock() == Blocks.STICKY_PISTON || this.getBlock(this.pos.add(0, 2, -1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(0, 1, -2)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, -1)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return "Working";
            }
            if (this.getBlock(this.pos.add(0, 1, 1)).getBlock() == Blocks.STICKY_PISTON || this.getBlock(this.pos.add(0, 2, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(0, 1, 2)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK || this.getBlock(this.pos.add(-1, 1, 1)).getBlock() == Blocks.REDSTONE_BLOCK) {
                return "Working";
            }
        }
        return null;
    }
    
    private void perform(final BlockPos pos2) {
        final int old = AntiHoleKick.mc.player.inventory.currentItem;
        if (AntiHoleKick.mc.world.getBlockState(pos2).getBlock() == Blocks.AIR) {
            if (InstantMine.breakPos != null && new BlockPos((Vec3i)InstantMine.breakPos).equals((Object)new BlockPos((Vec3i)pos2))) {
                return;
            }
            if (BreakCheck.Instance().BrokenPos != null && new BlockPos((Vec3i)BreakCheck.Instance().BrokenPos).equals((Object)new BlockPos((Vec3i)pos2))) {
                return;
            }
            if (InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1) {
                AntiHoleKick.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(BlockObsidian.class);
                AntiHoleKick.mc.playerController.updateController();
                BlockUtil.placeBlock(pos2, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
                AntiHoleKick.mc.player.inventory.currentItem = old;
                AntiHoleKick.mc.playerController.updateController();
            }
        }
    }
}
