//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import dev.blackhig.zhebushigudu.lover.util.Anti.*;
import net.minecraft.entity.item.*;
import java.util.*;

public class Blocker extends Module
{
    Setting<Boolean> piston;
    Setting<Boolean> cev;
    Setting<Boolean> packetPlace;
    Entity currentEntity;
    Setting<Integer> range;
    private BlockPos b_piston;
    private BlockPos b_cev;
    
    public Blocker() {
        super("Blocker", "Blocked piston and cev", Category.COMBAT, true, false, false);
        this.piston = (Setting<Boolean>)this.register(new Setting("Piston", (T)true));
        this.cev = (Setting<Boolean>)this.register(new Setting("CevBreaker", (T)true));
        this.packetPlace = (Setting<Boolean>)this.register(new Setting("SpeedUP Place", (T)true));
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)6, (T)1, (T)10));
        this.b_piston = null;
        this.b_cev = null;
    }
    
    public BlockPos getPistonPos() {
        return this.b_piston;
    }
    
    private IBlockState getBlock(final BlockPos blockPos) {
        return Blocker.mc.world.getBlockState(blockPos);
    }
    
    private int findMaterials(final Block block) {
        for (int i = 0; i < 9; ++i) {
            if (Blocker.mc.player.inventory.getStackInSlot(i).getItem() instanceof ItemBlock && ((ItemBlock)Blocker.mc.player.inventory.getStackInSlot(i).getItem()).getBlock() == block) {
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public void onTick() {
        if (Blocker.mc.player != null) {
            try {
                final int n2 = this.findMaterials(Blocks.OBSIDIAN);
                if (n2 == -1) {
                    return;
                }
                final BlockPos blockPos = new BlockPos(Blocker.mc.player.posX, Blocker.mc.player.posY, Blocker.mc.player.posZ);
                if (this.piston.getValue()) {
                    final BlockPos[] blockPosArray = { new BlockPos(2, 1, 0), new BlockPos(-2, 1, 0), new BlockPos(0, 1, 2), new BlockPos(0, 1, -2) };
                    for (int n3 = 0; n3 < 4; ++n3) {
                        for (int n4 = 0; n4 < blockPosArray.length; ++n4) {
                            final BlockPos blockPos2 = blockPos.add((Vec3i)blockPosArray[n4].add(0, n3, 0));
                            if (this.getBlock(blockPos2).getBlock() == Blocks.PISTON || this.getBlock(blockPos2).getBlock() == Blocks.STICKY_PISTON) {
                                this.b_piston = blockPos2;
                            }
                        }
                    }
                    if (this.b_piston != null) {
                        if (this.getBlock(this.b_piston).getBlock() == Blocks.AIR) {
                            if (Blocker.mc.player.getDistance((double)this.b_piston.getX(), (double)this.b_piston.getY(), (double)this.b_piston.getZ()) > this.range.getValue()) {
                                return;
                            }
                            final int n3 = Blocker.mc.player.inventory.currentItem;
                            Blocker.mc.player.inventory.currentItem = n2;
                            Blocker.mc.playerController.updateController();
                            Blocker.mc.playerController.updateController();
                            Blocker.mc.playerController.onPlayerDamageBlock(this.b_piston, EnumFacing.DOWN);
                            BlockUtil.placeBlock(this.b_piston, EnumHand.MAIN_HAND, true, this.packetPlace.getValue(), false);
                            Blocker.mc.player.inventory.currentItem = n3;
                            Blocker.mc.playerController.updateController();
                        }
                        if (this.getBlock(this.b_piston).getBlock() == Blocks.OBSIDIAN || Blocker.mc.player.getDistance((double)this.b_piston.getX(), (double)this.b_piston.getY(), (double)this.b_piston.getZ()) > this.range.getValue()) {
                            this.b_piston = null;
                        }
                    }
                }
                if (this.cev.getValue()) {
                    final BlockPos blockPos3 = new BlockPos(Blocker.mc.player.posX, (double)Math.round(Blocker.mc.player.posY), Blocker.mc.player.posZ);
                    final Entity entity2 = (Entity)Blocker.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).filter(entity -> Blocker.mc.player.getDistance(entity.posX, Blocker.mc.player.posY, entity.posZ) < 1.0).min(Comparator.comparing(entity -> Blocker.mc.player.getDistanceSq(entity.posX, entity.posY, entity.posZ))).orElse(null);
                    if (this.getBlock(new BlockPos(blockPos3.getX(), blockPos3.getY() + 2, blockPos3.getZ())).getBlock() == Blocks.OBSIDIAN && entity2 != null) {
                        this.b_cev = new BlockPos(entity2.posX, entity2.posY, entity2.posZ);
                    }
                    if (this.getBlock(new BlockPos(blockPos3.getX() + 1, blockPos3.getY() + 1, blockPos3.getZ())).getBlock() == Blocks.OBSIDIAN && entity2 != null) {
                        this.b_cev = new BlockPos(entity2.posX, entity2.posY, entity2.posZ);
                    }
                    if (this.getBlock(new BlockPos(blockPos3.getX() - 1, blockPos3.getY() + 1, blockPos3.getZ())).getBlock() == Blocks.OBSIDIAN && entity2 != null) {
                        this.b_cev = new BlockPos(entity2.posX, entity2.posY, entity2.posZ);
                    }
                    if (this.getBlock(new BlockPos(blockPos3.getX(), blockPos3.getY() + 1, blockPos3.getZ() + 1)).getBlock() == Blocks.OBSIDIAN && entity2 != null) {
                        this.b_cev = new BlockPos(entity2.posX, entity2.posY, entity2.posZ);
                    }
                    if (this.getBlock(new BlockPos(blockPos3.getX(), blockPos3.getY() + 1, blockPos3.getZ() - 1)).getBlock() == Blocks.OBSIDIAN && entity2 != null) {
                        this.b_cev = new BlockPos(entity2.posX, entity2.posY, entity2.posZ);
                    }
                    if (this.b_cev != null && this.getBlock(this.b_cev).getBlock() == Blocks.AIR) {
                        if (Blocker.mc.player.getDistance((double)this.b_cev.getX(), (double)this.b_cev.getY(), (double)this.b_cev.getZ()) > this.range.getValue()) {
                            return;
                        }
                        if (entity2 == null) {
                            final BlockPos blockPos4 = new BlockPos(Blocker.mc.player.posX, (double)this.b_cev.getY(), Blocker.mc.player.posZ);
                            if (blockPos4.getDistance(this.b_cev.getX(), this.b_cev.getY(), this.b_cev.getZ()) < 1.0) {
                                final int n4 = Blocker.mc.player.inventory.currentItem;
                                Blocker.mc.player.inventory.currentItem = n2;
                                Blocker.mc.playerController.updateController();
                                BlockUtil.placeBlock(this.b_cev.add(0, -1, 0), EnumHand.MAIN_HAND, true, false, false);
                                BlockUtil.placeBlock(this.b_cev, EnumHand.MAIN_HAND, true, false, false);
                                Blocker.mc.player.inventory.currentItem = n4;
                                Blocker.mc.playerController.updateController();
                                this.b_cev = null;
                            }
                        }
                    }
                }
                if (this.cev.getValue()) {
                    final BlockPos blockPos3 = new BlockPos(Blocker.mc.player.posX, Blocker.mc.player.posY, Blocker.mc.player.posZ);
                    final Entity entity2 = (Entity)Blocker.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityEnderCrystal).filter(entity -> Blocker.mc.player.getDistance(entity.posX, Blocker.mc.player.posY, entity.posZ) < 1.0).min(Comparator.comparing(entity -> Blocker.mc.player.getDistanceSq(entity.posX, entity.posY, entity.posZ))).orElse(null);
                    if (this.getBlock(new BlockPos(blockPos3.getX(), blockPos3.getY() + 2, blockPos3.getZ())).getBlock() == Blocks.OBSIDIAN && entity2 != null) {
                        this.b_cev = new BlockPos(entity2.posX, entity2.posY, entity2.posZ);
                    }
                    if (this.b_cev != null && this.getBlock(this.b_cev).getBlock() == Blocks.AIR) {
                        if (Blocker.mc.player.getDistance((double)this.b_cev.getX(), (double)this.b_cev.getY(), (double)this.b_cev.getZ()) > this.range.getValue()) {
                            return;
                        }
                        if (entity2 == null) {
                            final BlockPos blockPos4 = new BlockPos(Blocker.mc.player.posX, (double)this.b_cev.getY(), Blocker.mc.player.posZ);
                            if (blockPos4.getDistance(this.b_cev.getX(), this.b_cev.getY(), this.b_cev.getZ()) < 1.0) {
                                final int n4 = Blocker.mc.player.inventory.currentItem;
                                Blocker.mc.player.inventory.currentItem = n2;
                                Blocker.mc.playerController.updateController();
                                BlockUtil.placeBlock(this.b_cev.add(0, -1, 0), EnumHand.MAIN_HAND, true, false, false);
                                BlockUtil.placeBlock(this.b_cev, EnumHand.MAIN_HAND, true, false, false);
                                Blocker.mc.player.inventory.currentItem = n4;
                                Blocker.mc.playerController.updateController();
                                this.b_cev = null;
                            }
                        }
                    }
                }
            }
            catch (Exception var8) {
                this.b_cev = null;
                this.b_piston = null;
            }
        }
    }
}
