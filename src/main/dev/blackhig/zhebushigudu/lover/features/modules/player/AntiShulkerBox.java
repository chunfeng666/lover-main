//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.player;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import net.minecraft.util.math.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.*;
import java.util.*;

public class AntiShulkerBox extends Module
{
    private static AntiShulkerBox INSTANCE;
    private final Setting<Double> range;
    private final Setting<Double> saferange;
    
    public AntiShulkerBox() {
        super("AntiShulkerBox", "Automatically dig someone else's box", Module.Category.PLAYER, true, false, false);
        this.range = (Setting<Double>)this.register(new Setting("Range", (T)6.0, (T)1.0, (T)6.0));
        this.saferange = (Setting<Double>)this.register(new Setting("SafeRange", (T)5.5, (T)0.0, (T)6.0));
        this.setInstance();
    }
    
    public static AntiShulkerBox getInstance() {
        if (AntiShulkerBox.INSTANCE == null) {
            AntiShulkerBox.INSTANCE = new AntiShulkerBox();
        }
        return AntiShulkerBox.INSTANCE;
    }
    
    private void setInstance() {
        AntiShulkerBox.INSTANCE = this;
    }
    
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
        final int mainSlot = AntiShulkerBox.mc.player.inventory.currentItem;
        if (InstantMine.getInstance().isOff()) {
            InstantMine.getInstance().enable();
        }
        for (final BlockPos blockPos : this.breakPos((float)this.range.getValue().intValue())) {
            final int slotPick = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
            if (slotPick == -1) {
                return;
            }
            if (AntiShulkerBox.mc.player.getDistanceSq(blockPos) < MathUtil.square(this.saferange.getValue().intValue())) {
                continue;
            }
            if (blockPos == null) {
                continue;
            }
            if (AntiShulkerBox.mc.world.getBlockState(blockPos).getBlock() instanceof BlockShulkerBox) {
                AntiShulkerBox.mc.player.inventory.currentItem = slotPick;
                AntiShulkerBox.mc.player.swingArm(EnumHand.MAIN_HAND);
                AntiShulkerBox.mc.playerController.onPlayerDamageBlock(blockPos, BlockUtil.getRayTraceFacing(blockPos));
            }
            else {
                AntiShulkerBox.mc.player.inventory.currentItem = mainSlot;
            }
        }
    }
    
    private NonNullList<BlockPos> breakPos(final float placeRange) {
        final NonNullList<BlockPos> positions = (NonNullList<BlockPos>)NonNullList.create();
        positions.addAll((Collection)BlockUtil.getSphere(new BlockPos(Math.floor(AntiShulkerBox.mc.player.posX), Math.floor(AntiShulkerBox.mc.player.posY), Math.floor(AntiShulkerBox.mc.player.posZ)), placeRange, 0, false, true, 0));
        return positions;
    }
    
    static {
        AntiShulkerBox.INSTANCE = new AntiShulkerBox();
    }
}
