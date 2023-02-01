//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.features.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.entity.*;
import dev.blackhig.zhebushigudu.lover.*;
import java.util.*;
import net.minecraft.block.state.*;

public class Flatten extends Module
{
    public EntityPlayer target;
    private final Setting<Float> range;
    private final Setting<Boolean> autoDisable;
    private final Setting<Boolean> chestplace;
    private final Setting<Boolean> xzchestplace;
    private final Setting<Boolean> negative;
    
    public Flatten() {
        super("Flatten", "Automatic feetobsidian", Category.COMBAT, true, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)5.0f, (T)1.0f, (T)6.0f));
        this.autoDisable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)true));
        this.chestplace = (Setting<Boolean>)this.register(new Setting("Y Chest Place", (T)true));
        this.xzchestplace = (Setting<Boolean>)this.register(new Setting("X|Z Chest Place", (T)false));
        this.negative = (Setting<Boolean>)this.register(new Setting("-X|-Z Chest Place", (T)false));
    }
    
    @Override
    public void onUpdate() {
        if (Feature.fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue());
        if (this.target == null) {
            return;
        }
        final BlockPos people = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
        final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
        final int chestSlot = InventoryUtil.findHotbarBlock(BlockEnderChest.class);
        if (obbySlot == -1) {
            return;
        }
        final int old = Util.mc.player.inventory.currentItem;
        if (this.getBlock(people.add(0, -1, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, -2, 0)).getBlock() != Blocks.AIR) {
            if (this.chestplace.getValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            }
            else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(0, -1, 0), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        if (this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(1, -1, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR) {
            if (this.negative.getValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            }
            else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(1, -1, 0), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        else if (this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(-1, -1, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR) {
            if (this.xzchestplace.getValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            }
            else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(-1, -1, 0), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        else if (this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, -1, 1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.negative.getValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            }
            else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(0, -1, 1), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        else if (this.getBlock(people.add(0, -1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, -1, -1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.xzchestplace.getValue() && InventoryUtil.findHotbarBlock(BlockEnderChest.class) != -1) {
                this.switchToSlot(chestSlot);
            }
            else {
                this.switchToSlot(obbySlot);
            }
            BlockUtil.placeBlock(people.add(0, -1, -1), EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        if (this.autoDisable.getValue()) {
            this.disable();
        }
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = range;
        for (final EntityPlayer player : Util.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, range)) {
                continue;
            }
            if (lover.friendManager.isFriend(player.getName())) {
                continue;
            }
            if (Util.mc.player.posY - player.posY >= 5.0) {
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
        Util.mc.player.inventory.currentItem = slot;
        Util.mc.playerController.updateController();
    }
    
    private IBlockState getBlock(final BlockPos block) {
        return Util.mc.world.getBlockState(block);
    }
}
