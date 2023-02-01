//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.render;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import net.minecraft.init.*;
import java.awt.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import dev.blackhig.zhebushigudu.lover.util.render.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.util.*;

public class AntiCtiyRenderPlus extends Module
{
    private final Setting<Integer> range;
    public EntityPlayer target;
    
    public AntiCtiyRenderPlus() {
        super("Anti Y CityRender", "AntiCityRender", Module.Category.RENDER, true, false, false);
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)5, (T)1, (T)10));
    }
    
    public void onRender3D(final Render3DEvent event) {
        if (fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue());
        this.surroundRender();
    }
    
    private void surroundRender() {
        if (this.target != null) {
            final Vec3d a = this.target.getPositionVector();
            if (AntiCtiyRenderPlus.mc.world.getBlockState(new BlockPos(a)).getBlock() == Blocks.OBSIDIAN || AntiCtiyRenderPlus.mc.world.getBlockState(new BlockPos(a)).getBlock() == Blocks.ENDER_CHEST) {
                RenderUtil.drawBoxESP(new BlockPos(a), new Color(255, 255, 0), false, new Color(255, 255, 0), 1.0f, false, true, 42, true);
            }
            if (EntityUtil.getSurroundWeakness(a, -1, 1)) {
                this.surroundRender(a, -2.0, 1.0, 0.0, true);
            }
            if (EntityUtil.getSurroundWeakness(a, -1, 2)) {
                this.surroundRender(a, 2.0, 1.0, 0.0, true);
            }
            if (EntityUtil.getSurroundWeakness(a, -1, 3)) {
                this.surroundRender(a, 0.0, 1.0, -2.0, true);
            }
            if (EntityUtil.getSurroundWeakness(a, -1, 4)) {
                this.surroundRender(a, 0.0, 1.0, 2.0, true);
            }
            if (EntityUtil.getSurroundWeakness(a, -1, 5)) {
                this.surroundRender(a, -2.0, 1.0, 0.0, false);
            }
            if (EntityUtil.getSurroundWeakness(a, -1, 6)) {
                this.surroundRender(a, 2.0, 1.0, 0.0, false);
            }
            if (EntityUtil.getSurroundWeakness(a, -1, 7)) {
                this.surroundRender(a, 0.0, 1.0, -2.0, false);
            }
            if (EntityUtil.getSurroundWeakness(a, -1, 8)) {
                this.surroundRender(a, 0.0, 1.0, 2.0, false);
            }
        }
    }
    
    private void surroundRender(final Vec3d pos, final double x, final double y, final double z, final boolean red) {
        final BlockPos position = new BlockPos(pos).add(x, y, z);
        if (AntiCtiyRenderPlus.mc.world.getBlockState(position).getBlock() == Blocks.AIR || AntiCtiyRenderPlus.mc.world.getBlockState(position).getBlock() == Blocks.FIRE || AntiCtiyRenderPlus.mc.world.getBlockState(position).getBlock() != Blocks.OBSIDIAN) {
            return;
        }
        if (red) {
            RenderUtil.drawBoxESP(position, new Color(98, 0, 255), false, new Color(98, 0, 255), 1.0f, false, true, 42, true);
        }
        else {
            RenderUtil.drawBoxESP(position, new Color(0, 0, 255), false, new Color(0, 0, 255), 1.0f, false, true, 42, true);
        }
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = range;
        for (final EntityPlayer player : AntiCtiyRenderPlus.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, range)) {
                continue;
            }
            if (!EntityUtil.isInHole((Entity)player)) {
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
}
