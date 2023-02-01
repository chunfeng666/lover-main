//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import java.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import dev.blackhig.zhebushigudu.lover.features.modules.render.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import dev.blackhig.zhebushigudu.lover.*;
import net.minecraft.block.state.*;

public class TrapHead extends Module
{
    private final Setting<Float> range;
    public EntityPlayer target;
    
    public TrapHead() {
        super("TrapHead", "Trap Head", Category.COMBAT, true, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)5.0f, (T)1.0f, (T)6.0f));
    }
    
    public static void breakCrystal() {
        for (final Entity crystal : (List)TrapHead.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal && !e.isDead).sorted(Comparator.comparing(e -> TrapHead.mc.player.getDistance(e))).collect(Collectors.toList())) {
            if (crystal instanceof EntityEnderCrystal && TrapHead.mc.player.getDistance(crystal) <= 4.0f) {
                TrapHead.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                TrapHead.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
            }
        }
    }
    
    @Override
    public void onEnable() {
        breakCrystal();
    }
    
    @Override
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue());
        if (this.target == null) {
            return;
        }
        final BlockPos people = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
        final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        if (obbySlot == -1) {
            return;
        }
        final int old = TrapHead.mc.player.inventory.currentItem;
        if (this.getBlock(people.add(0, 2, 0)).getBlock() == Blocks.AIR) {
            if (this.getBlock(people.add(1, 2, 0)).getBlock() != Blocks.AIR || this.getBlock(people.add(0, 2, 1)).getBlock() != Blocks.AIR || this.getBlock(people.add(-1, 2, 0)).getBlock() != Blocks.AIR || this.getBlock(people.add(0, 2, -1)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(0, 2, 0))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(0, 2, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(1, 1, 0)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(1, 2, 0))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(1, 2, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(-1, 1, 0)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(-1, 2, 0))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(-1, 2, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(0, 1, 1)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(0, 2, 1))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(0, 2, 1), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(0, 1, -1)).getBlock() != Blocks.AIR && !BreakESP.isBreaking(people.add(0, 2, -1))) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(0, 2, -1), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            }
        }
        if (this.getBlock(people.add(0, 3, 0)).getBlock() == Blocks.AIR) {
            if (this.getBlock(people.add(1, 3, 0)).getBlock() != Blocks.AIR || this.getBlock(people.add(0, 3, 1)).getBlock() != Blocks.AIR || this.getBlock(people.add(-1, 3, 0)).getBlock() != Blocks.AIR || this.getBlock(people.add(0, 3, -1)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(0, 3, 0))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(0, 3, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(1, 2, 0)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(1, 3, 0))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(1, 3, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(-1, 2, 0)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(-1, 3, 0))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(-1, 3, 0), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(0, 2, 1)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(0, 3, 1))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(0, 3, 1), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(0, 2, -1)).getBlock() != Blocks.AIR) {
                if (!BreakESP.isBreaking(people.add(0, 3, -1))) {
                    this.switchToSlot(obbySlot);
                    BlockUtil.placeBlock(people.add(0, 3, -1), EnumHand.MAIN_HAND, false, true, false);
                    this.switchToSlot(old);
                }
            }
            else if (this.getBlock(people.add(1, 0, 0)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(1, 1, 0), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            }
            else if (this.getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(-1, 1, 0), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            }
            else if (this.getBlock(people.add(0, 0, 1)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(0, 1, 1), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            }
            else if (this.getBlock(people.add(0, 0, -1)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(0, 1, -1), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            }
            else if (this.getBlock(people.add(0, 0, 0)).getBlock() != Blocks.AIR) {
                this.switchToSlot(obbySlot);
                BlockUtil.placeBlock(people.add(0, 0, -1), EnumHand.MAIN_HAND, false, true, false);
                BlockUtil.placeBlock(people.add(0, 0, 1), EnumHand.MAIN_HAND, false, true, false);
                this.switchToSlot(old);
            }
        }
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = range;
        for (final EntityPlayer player : TrapHead.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, range)) {
                continue;
            }
            if (lover.friendManager.isFriend(player.getName())) {
                continue;
            }
            if (TrapHead.mc.player.posY - player.posY >= 5.0) {
                continue;
            }
            if (target == null) {
                target = player;
                distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
            }
            else {
                if (EntityUtil.mc.player.getDistanceSq((Entity)player) >= distance) {
                    continue;
                }
                target = player;
                distance = EntityUtil.mc.player.getDistanceSq((Entity)player);
            }
        }
        return target;
    }
    
    private void switchToSlot(final int slot) {
        TrapHead.mc.player.inventory.currentItem = slot;
        TrapHead.mc.playerController.updateController();
    }
    
    private IBlockState getBlock(final BlockPos block) {
        return TrapHead.mc.world.getBlockState(block);
    }
}
