//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.*;
import net.minecraft.util.math.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class AutoCity extends Module
{
    public static EntityPlayer target;
    private static AutoCity INSTANCE;
    public final Setting<Boolean> priority;
    private final Setting<Float> range;
    private final Setting<Boolean> toggle;
    
    public AutoCity() {
        super("AutoCity", "AutoCity", Category.COMBAT, true, false, false);
        this.priority = (Setting<Boolean>)this.register(new Setting("City Priority ", (T)true));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)5.0f, (T)1.0f, (T)8.0f));
        this.toggle = (Setting<Boolean>)this.register(new Setting("AutoToggle", (T)false));
        this.setInstance();
    }
    
    public static AutoCity getInstance() {
        if (AutoCity.INSTANCE == null) {
            AutoCity.INSTANCE = new AutoCity();
        }
        return AutoCity.INSTANCE;
    }
    
    private void setInstance() {
        AutoCity.INSTANCE = this;
    }
    
    @Override
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
        if (lover.moduleManager.isModuleEnabled("AutoCev")) {
            return;
        }
        if (lover.moduleManager.isModuleEnabled("AutoCev1")) {
            return;
        }
        if (InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1) {
            return;
        }
        AutoCity.target = this.getTarget(this.range.getValue());
        this.surroundMine();
    }
    
    @Override
    public String getDisplayInfo() {
        if (AutoCity.target != null) {
            return AutoCity.target.getName();
        }
        return null;
    }
    
    private void surroundMine() {
        if (AutoCity.target != null) {
            final Vec3d a = AutoCity.target.getPositionVector();
            if (EntityUtil.getSurroundWeakness(a, 1, -1)) {
                this.surroundMine(a, -1.0, 0.0, 0.0);
            }
            else if (EntityUtil.getSurroundWeakness(a, 2, -1)) {
                this.surroundMine(a, 1.0, 0.0, 0.0);
            }
            else if (EntityUtil.getSurroundWeakness(a, 3, -1)) {
                this.surroundMine(a, 0.0, 0.0, -1.0);
            }
            else if (EntityUtil.getSurroundWeakness(a, 4, -1)) {
                this.surroundMine(a, 0.0, 0.0, 1.0);
            }
            else if (EntityUtil.getSurroundWeakness(a, 5, -1)) {
                this.surroundMine(a, -1.0, 0.0, 0.0);
            }
            else if (EntityUtil.getSurroundWeakness(a, 6, -1)) {
                this.surroundMine(a, 1.0, 0.0, 0.0);
            }
            else if (EntityUtil.getSurroundWeakness(a, 7, -1)) {
                this.surroundMine(a, 0.0, 0.0, -1.0);
            }
            else if (EntityUtil.getSurroundWeakness(a, 8, -1)) {
                this.surroundMine(a, 0.0, 0.0, 1.0);
            }
            else {
                AutoCity.target = null;
            }
        }
        if (this.toggle.getValue()) {
            this.toggle();
        }
    }
    
    private void surroundMine(final Vec3d pos, final double x, final double y, final double z) {
        final BlockPos position = new BlockPos(pos).add(x, y, z);
        lover.moduleManager.enableModule("InstantMine");
        if (InstantMine.breakPos != null) {
            if (InstantMine.breakPos.equals((Object)position)) {
                return;
            }
            if (InstantMine.breakPos.equals((Object)new BlockPos(AutoCity.target.posX, AutoCity.target.posY, AutoCity.target.posZ)) && AutoCity.mc.world.getBlockState(new BlockPos(AutoCity.target.posX, AutoCity.target.posY, AutoCity.target.posZ)).getBlock() != Blocks.AIR && !this.priority.getValue()) {
                return;
            }
            if (InstantMine.breakPos.equals((Object)new BlockPos(AutoCity.mc.player.posX, AutoCity.mc.player.posY + 2.0, AutoCity.mc.player.posZ))) {
                return;
            }
            if (InstantMine.breakPos.equals((Object)new BlockPos(AutoCity.mc.player.posX, AutoCity.mc.player.posY - 1.0, AutoCity.mc.player.posZ))) {
                return;
            }
            if (AutoCity.mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.WEB) {
                return;
            }
            if (lover.moduleManager.isModuleEnabled("Anti32k") && AutoCity.mc.world.getBlockState(InstantMine.breakPos).getBlock() instanceof BlockHopper) {
                return;
            }
            if (lover.moduleManager.isModuleEnabled("AntiShulkerBox") && AutoCity.mc.world.getBlockState(InstantMine.breakPos).getBlock() instanceof BlockShulkerBox) {
                return;
            }
        }
        InstantMine.ondeve(position);
        AutoCity.mc.playerController.onPlayerDamageBlock(position, BlockUtil.getRayTraceFacing(position));
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = range;
        for (final EntityPlayer player : AutoCity.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, range)) {
                continue;
            }
            if (this.check(player) || lover.friendManager.isFriend(player.getName())) {
                continue;
            }
            if (AutoCity.mc.player.posY - player.posY >= 5.0) {
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
    
    public boolean check(final EntityPlayer player) {
        return AutoCity.mc.world.getBlockState(new BlockPos(player.posX + 1.0, player.posY, player.posZ)).getBlock() == Blocks.AIR || AutoCity.mc.world.getBlockState(new BlockPos(player.posX - 1.0, player.posY, player.posZ)).getBlock() == Blocks.AIR || AutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ + 1.0)).getBlock() == Blocks.AIR || AutoCity.mc.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ - 1.0)).getBlock() == Blocks.AIR;
    }
    
    static {
        AutoCity.INSTANCE = new AutoCity();
    }
}
