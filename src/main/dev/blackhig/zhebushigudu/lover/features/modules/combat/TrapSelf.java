//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.entity.*;
import dev.blackhig.zhebushigudu.lover.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.block.state.*;

public class TrapSelf extends Module
{
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> hole;
    private final Setting<Boolean> center;
    private final Setting<Boolean> toggle;
    private int obsidian;
    
    public TrapSelf() {
        super("TrapSelf", "One Self Trap", Category.COMBAT, true, false, false);
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.hole = (Setting<Boolean>)this.register(new Setting("Hole or Burrrow", (T)false));
        this.center = (Setting<Boolean>)this.register(new Setting("TPCenter", (T)false));
        this.toggle = (Setting<Boolean>)this.register(new Setting("Toggle", (T)false));
        this.obsidian = -1;
    }
    
    @Override
    public void onEnable() {
        final BlockPos startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
        if (this.center.getValue()) {
            lover.positionManager.setPositionPacket(startPos.getX() + 0.5, startPos.getY(), startPos.getZ() + 0.5, true, true, true);
        }
    }
    
    @Override
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
        this.obsidian = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
        if (this.obsidian == -1) {
            return;
        }
        final BlockPos pos = new BlockPos(TrapSelf.mc.player.posX, TrapSelf.mc.player.posY, TrapSelf.mc.player.posZ);
        if ((!EntityUtil.isInHole((Entity)TrapSelf.mc.player) || !this.isBurrowed((EntityPlayer)TrapSelf.mc.player)) && this.hole.getValue()) {
            return;
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR) {
            this.place(pos.add(1, 0, 0));
        }
        if (this.getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 0)).getBlock() != Blocks.AIR) {
            this.place(pos.add(1, 1, 0));
        }
        if (this.getBlock(pos.add(1, 2, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, 0)).getBlock() != Blocks.AIR) {
            this.place(pos.add(1, 2, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR) {
            this.place(pos.add(-1, 0, 0));
        }
        if (this.getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 0)).getBlock() != Blocks.AIR) {
            this.place(pos.add(-1, 1, 0));
        }
        if (this.getBlock(pos.add(-1, 2, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, 0)).getBlock() != Blocks.AIR) {
            this.place(pos.add(-1, 2, 0));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR) {
            this.place(pos.add(0, 0, 1));
        }
        if (this.getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 1)).getBlock() != Blocks.AIR) {
            this.place(pos.add(0, 1, 1));
        }
        if (this.getBlock(pos.add(0, 2, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, 1)).getBlock() != Blocks.AIR) {
            this.place(pos.add(0, 2, 1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR) {
            this.place(pos.add(0, 0, -1));
        }
        if (this.getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -1)).getBlock() != Blocks.AIR) {
            this.place(pos.add(0, 1, -1));
        }
        if (this.getBlock(pos.add(0, 2, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, -1)).getBlock() != Blocks.AIR) {
            this.place(pos.add(0, 2, -1));
        }
        if (this.getBlock(pos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
            this.place(pos.add(0, 2, 0));
        }
        if (this.toggle.getValue() || lover.speedManager.getPlayerSpeed((EntityPlayer)TrapSelf.mc.player) > 10.0) {
            this.toggle();
        }
    }
    
    private void switchToSlot(final int slot) {
        TrapSelf.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        TrapSelf.mc.player.inventory.currentItem = slot;
        TrapSelf.mc.playerController.updateController();
    }
    
    private void place(final BlockPos pos) {
        final int old = TrapSelf.mc.player.inventory.currentItem;
        this.switchToSlot(this.obsidian);
        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        this.switchToSlot(old);
    }
    
    private IBlockState getBlock(final BlockPos block) {
        return TrapSelf.mc.world.getBlockState(block);
    }
    
    private boolean isBurrowed(final EntityPlayer entityPlayer) {
        final BlockPos blockPos = new BlockPos(Math.floor(entityPlayer.posX), Math.floor(entityPlayer.posY + 0.2), Math.floor(entityPlayer.posZ));
        return TrapSelf.mc.world.getBlockState(blockPos).getBlock() == Blocks.ENDER_CHEST || TrapSelf.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN || TrapSelf.mc.world.getBlockState(blockPos).getBlock() == Blocks.CHEST;
    }
}
