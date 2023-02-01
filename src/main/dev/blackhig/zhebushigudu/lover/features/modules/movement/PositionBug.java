//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.movement;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.util.SeijaUtil.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;

public class PositionBug extends Module
{
    public final Setting<Integer> offset;
    public final Setting<Boolean> onBurrow;
    public final Setting<Boolean> toggle;
    
    public PositionBug() {
        super("PositionBug", "", Module.Category.MOVEMENT, true, false, false);
        this.offset = (Setting<Integer>)this.register(new Setting("Offset", (T)1, (T)0, (T)10));
        this.onBurrow = (Setting<Boolean>)this.register(new Setting("OnBurrow", (T)false));
        this.toggle = (Setting<Boolean>)this.register(new Setting("AutoToggle", (T)false));
    }
    
    public void onTick() {
        final double x = Math.abs(PositionBug.mc.player.posX) - Math.floor(Math.abs(PositionBug.mc.player.posX));
        final double z = Math.abs(PositionBug.mc.player.posZ) - Math.floor(Math.abs(PositionBug.mc.player.posZ));
        if (x == 0.7 || x == 0.3 || z == 0.7 || z == 0.3 || (!this.onBurrow.getValue() && !PositionBug.mc.world.getBlockState(SeijaBlockUtil.getFlooredPosition((Entity)PositionBug.mc.player)).getBlock().equals(Blocks.AIR))) {
            return;
        }
        final Vec3d playerVec = PositionBug.mc.player.getPositionVector();
        if (!PositionBug.mc.world.getBlockState(SeijaBlockUtil.vec3toBlockPos(playerVec.add(new Vec3d(0.3 + this.offset.getValue() / 100.0, 0.2, 0.0)))).getBlock().equals(Blocks.AIR)) {
            PositionBug.mc.player.setPosition(PositionBug.mc.player.posX + this.offset.getValue() / 100.0, PositionBug.mc.player.posY, PositionBug.mc.player.posZ);
            if (this.toggle.getValue()) {
                this.disable();
            }
            return;
        }
        if (!PositionBug.mc.world.getBlockState(SeijaBlockUtil.vec3toBlockPos(playerVec.add(new Vec3d(-0.3 - this.offset.getValue() / 100.0, 0.2, 0.0)))).getBlock().equals(Blocks.AIR)) {
            PositionBug.mc.player.setPosition(PositionBug.mc.player.posX - this.offset.getValue() / 100.0, PositionBug.mc.player.posY, PositionBug.mc.player.posZ);
            if (this.toggle.getValue()) {
                this.disable();
            }
            return;
        }
        if (!PositionBug.mc.world.getBlockState(SeijaBlockUtil.vec3toBlockPos(playerVec.add(new Vec3d(0.0, 0.2, 0.3 + this.offset.getValue() / 100.0)))).getBlock().equals(Blocks.AIR)) {
            PositionBug.mc.player.setPosition(PositionBug.mc.player.posX, PositionBug.mc.player.posY, PositionBug.mc.player.posZ + this.offset.getValue() / 100.0);
            if (this.toggle.getValue()) {
                this.disable();
            }
            return;
        }
        if (!PositionBug.mc.world.getBlockState(SeijaBlockUtil.vec3toBlockPos(playerVec.add(new Vec3d(0.0, 0.2, -0.3 - this.offset.getValue() / 100.0)))).getBlock().equals(Blocks.AIR)) {
            PositionBug.mc.player.setPosition(PositionBug.mc.player.posX, PositionBug.mc.player.posY, PositionBug.mc.player.posZ - this.offset.getValue() / 100.0);
            if (this.toggle.getValue()) {
                this.disable();
            }
        }
    }
}
