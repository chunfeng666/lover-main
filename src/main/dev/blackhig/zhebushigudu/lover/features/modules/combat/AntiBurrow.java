//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn??ǿ????????\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import dev.blackhig.zhebushigudu.lover.*;
import java.util.*;
import dev.blackhig.zhebushigudu.lover.features.*;
import net.minecraft.client.gui.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;

public class AntiBurrow extends Module
{
    public static BlockPos pos;
    private final Setting<Double> range;
    private final Setting<Boolean> toggle;
    
    public AntiBurrow() {
        super("AntiBurrow", "AntiBurrow", Category.COMBAT, true, false, false);
        this.range = (Setting<Double>)this.register(new Setting("Range", (T)5.0, (T)1.0, (T)8.0));
        this.toggle = (Setting<Boolean>)this.register(new Setting("Toggle", (T)false));
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
    
    @Override
    public void onUpdate() {
        if (Feature.fullNullCheck()) {
            return;
        }
        if (Util.mc.currentScreen instanceof GuiHopper) {
            return;
        }
        final EntityPlayer player = this.getTarget(this.range.getValue());
        if (this.toggle.getValue()) {
            this.toggle();
        }
        if (player == null) {
            return;
        }
        AntiBurrow.pos = new BlockPos(player.posX, player.posY + 0.5, player.posZ);
        if (AntiBurrow.pos == null) {
            return;
        }
        if (InstantMine.breakPos != null) {
            if (InstantMine.breakPos.equals((Object)AntiBurrow.pos)) {
                return;
            }
            if (lover.moduleManager.isModuleEnabled("AutoCity") && AutoCity.target != null && AutoCity.getInstance().priority.getValue()) {
                return;
            }
            if (InstantMine.breakPos.equals((Object)new BlockPos(Util.mc.player.posX, Util.mc.player.posY + 2.0, Util.mc.player.posZ))) {
                return;
            }
            if (InstantMine.breakPos.equals((Object)new BlockPos(Util.mc.player.posX, Util.mc.player.posY - 1.0, Util.mc.player.posZ))) {
                return;
            }
            if (lover.moduleManager.isModuleEnabled("AutoCev")) {
                return;
            }
            if (lover.moduleManager.isModuleEnabled("AutoCev1")) {
                return;
            }
            if (AntiBurrow.mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.WEB) {
                return;
            }
            if (lover.moduleManager.isModuleEnabled("Anti32k") && Util.mc.world.getBlockState(InstantMine.breakPos).getBlock() instanceof BlockHopper) {
                return;
            }
            if (lover.moduleManager.isModuleEnabled("AntiShulkerBox") && Util.mc.world.getBlockState(InstantMine.breakPos).getBlock() instanceof BlockShulkerBox) {
                return;
            }
        }
        if (Util.mc.world.getBlockState(AntiBurrow.pos).getBlock() != Blocks.AIR && !this.isOnLiquid() && !this.isInLiquid() && Util.mc.world.getBlockState(AntiBurrow.pos).getBlock() != Blocks.WATER && Util.mc.world.getBlockState(AntiBurrow.pos).getBlock() != Blocks.LAVA) {
            Util.mc.player.swingArm(EnumHand.MAIN_HAND);
            if (InstantMine.breakPos != null && InstantMine.breakPos.getZ() == AntiBurrow.pos.getZ() && InstantMine.breakPos.getX() == AntiBurrow.pos.getX() && InstantMine.breakPos.getY() == AntiBurrow.pos.getY()) {
                return;
            }
            if (InstantMine.breakPos2 != null && lover.moduleManager.getModuleByClass(InstantMine.class).isEnabled() && InstantMine.doubleBreak.getValue() && !AntiBurrow.pos.equals((Object)InstantMine.breakPos2) && AntiBurrow.pos.equals((Object)InstantMine.breakPos)) {
                InstantMine.ondeve2(AntiBurrow.pos);
            }
            else {
                InstantMine.ondeve(AntiBurrow.pos);
                InstantMine.ondeve(AntiBurrow.pos);
            }
            Util.mc.playerController.onPlayerDamageBlock(AntiBurrow.pos, BlockUtil.getRayTraceFacing(AntiBurrow.pos));
        }
    }
    
    private boolean isOnLiquid() {
        final double y = Util.mc.player.posY - 0.03;
        for (int x = MathHelper.floor(Util.mc.player.posX); x < MathHelper.ceil(Util.mc.player.posX); ++x) {
            for (int z = MathHelper.floor(Util.mc.player.posZ); z < MathHelper.ceil(Util.mc.player.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);
                if (Util.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isInLiquid() {
        final double y = Util.mc.player.posY + 0.01;
        for (int x = MathHelper.floor(Util.mc.player.posX); x < MathHelper.ceil(Util.mc.player.posX); ++x) {
            for (int z = MathHelper.floor(Util.mc.player.posZ); z < MathHelper.ceil(Util.mc.player.posZ); ++z) {
                final BlockPos pos = new BlockPos(x, (int)y, z);
                if (Util.mc.world.getBlockState(pos).getBlock() instanceof BlockLiquid) {
                    return true;
                }
            }
        }
        return false;
    }
}
