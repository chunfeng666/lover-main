//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.render;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import net.minecraft.init.*;
import java.awt.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import dev.blackhig.zhebushigudu.lover.features.modules.combat.*;
import dev.blackhig.zhebushigudu.lover.util.Anti.*;
import net.minecraft.entity.*;
import dev.blackhig.zhebushigudu.lover.*;
import java.util.*;

public class CityESP extends Module
{
    public EntityPlayer target;
    private final Setting<Float> range;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> alpha;
    private final Setting<Integer> red1;
    private final Setting<Integer> green1;
    private final Setting<Integer> blue1;
    private final Setting<Integer> alpha1;
    
    public CityESP() {
        super("CityESP", "Show enemy's hole flaws.", Module.Category.RENDER, true, false, false);
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)7.0f, (T)1.0f, (T)12.0f));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)118, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)118, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)42, (T)20, (T)255));
        this.red1 = (Setting<Integer>)this.register(new Setting("BurRed", (T)255, (T)0, (T)255));
        this.green1 = (Setting<Integer>)this.register(new Setting("BurGreen", (T)255, (T)0, (T)255));
        this.blue1 = (Setting<Integer>)this.register(new Setting("BurBlue", (T)0, (T)0, (T)255));
        this.alpha1 = (Setting<Integer>)this.register(new Setting("BurAlpha", (T)42, (T)20, (T)255));
    }
    
    public void onRender3D(final Render3DEvent event) {
        if (fullNullCheck()) {
            return;
        }
        this.target = this.getTarget(this.range.getValue());
        this.surroundRender1();
    }
    
    private void surroundRender1() {
        if (this.target == null) {
            return;
        }
        final Vec3d a = this.target.getPositionVector();
        final BlockPos people = new BlockPos(this.target.posX, this.target.posY, this.target.posZ);
        if (this.getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(-2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, -1.0, 0.0, 0.0, true);
            }
            else if (this.getBlock(people.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(-2, 1, 0)).getBlock() != Blocks.BEDROCK) {
                this.surroundRender1(a, -1.0, 0.0, 0.0, false);
            }
        }
        if (this.getBlock(people.add(1, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(1, 0, 0)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(2, 1, 0)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 1.0, 0.0, 0.0, true);
            }
            else if (this.getBlock(people.add(2, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(2, 1, 0)).getBlock() != Blocks.BEDROCK) {
                this.surroundRender1(a, 1.0, 0.0, 0.0, false);
            }
        }
        if (this.getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(-2, 1, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(-2, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, -2.0, 0.0, 0.0, true);
            }
            else {
                this.surroundRender1(a, -2.0, 0.0, 0.0, false);
            }
        }
        if (this.getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(-2, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(-2, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(-2, 1, 0)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(-2, 0, 0)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, -2.0, 1.0, 0.0, true);
            }
            else {
                this.surroundRender1(a, -2.0, 1.0, 0.0, false);
            }
        }
        if (this.getBlock(people.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(2, 1, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(2, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(2, 0, 0)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(2, 1, 0)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 2.0, 0.0, 0.0, true);
            }
            else {
                this.surroundRender1(a, 2.0, 0.0, 0.0, false);
            }
        }
        if (this.getBlock(people.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(2, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(2, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(2, 1, 0)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(2, 0, 0)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 2.0, 1.0, 0.0, true);
            }
            else {
                this.surroundRender1(a, 2.0, 1.0, 0.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, 1)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 0, 1)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(0, 0, 2)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 1, 2)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 0.0, 0.0, 1.0, true);
            }
            else if (this.getBlock(people.add(0, 0, 2)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 1, 2)).getBlock() != Blocks.BEDROCK) {
                this.surroundRender1(a, 0.0, 0.0, 1.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, -1)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 0, -1)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(0, 0, -2)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 1, -2)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 0.0, 0.0, -1.0, true);
            }
            else if (this.getBlock(people.add(0, 0, -2)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 1, -2)).getBlock() != Blocks.BEDROCK) {
                this.surroundRender1(a, 0.0, 0.0, -1.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 1, 2)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 0, 2)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 0, 2)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 1, 2)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 0.0, 0.0, 2.0, true);
            }
            else {
                this.surroundRender1(a, 0.0, 0.0, 2.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 0, 2)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 1, 2)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 1, 2)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 0, 2)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 0.0, 1.0, 2.0, true);
            }
            else {
                this.surroundRender1(a, 0.0, 1.0, 2.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 1, -2)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 0, -2)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 0, -2)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 1, -2)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 0.0, 0.0, -2.0, true);
            }
            else {
                this.surroundRender1(a, 0.0, 0.0, -2.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 0, -2)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 1, -2)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 1, -2)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 0, -2)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 0.0, 1.0, -2.0, true);
            }
            else {
                this.surroundRender1(a, 0.0, 1.0, -2.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 0, 1)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 1, 1)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 1, 1)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 0, 1)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 0.0, 1.0, 1.0, true);
            }
            else {
                this.surroundRender1(a, 0.0, 1.0, 1.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 0, -1)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(0, 1, -1)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 1, -1)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(people.add(0, 0, -1)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 0.0, 1.0, -1.0, true);
            }
            else {
                this.surroundRender1(a, 0.0, 1.0, -1.0, false);
            }
        }
        if (this.getBlock(people.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(1, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(1, 1, 0)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(1, 0, 0)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, 1.0, 1.0, 0.0, true);
            }
            else {
                this.surroundRender1(a, 1.0, 1.0, 0.0, false);
            }
        }
        if (this.getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(-1, 0, 0)).getBlock() != Blocks.BEDROCK && this.getBlock(people.add(-1, 1, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(-1, 1, 0)).getBlock() != Blocks.BEDROCK) {
            if (this.getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(people.add(-1, 0, 0)).getBlock() == Blocks.AIR) {
                this.surroundRender1(a, -1.0, 1.0, 0.0, true);
            }
            else {
                this.surroundRender1(a, -1.0, 1.0, 0.0, false);
            }
        }
        if (this.getBlock(people.add(0, 0, 0)).getBlock() != Blocks.AIR && this.getBlock(people.add(0, 0, 0)).getBlock() != Blocks.BEDROCK) {
            RenderUtil.drawBoxESP(new BlockPos(a), new Color(this.red1.getValue(), this.green1.getValue(), this.blue1.getValue()), false, new Color(this.red1.getValue(), this.green1.getValue(), this.blue1.getValue()), 1.0f, false, true, this.alpha1.getValue(), true);
        }
    }
    
    private void surroundRender1(final Vec3d pos, final double x, final double y, final double z, final boolean red) {
        final BlockPos position = new BlockPos(pos).add(x, y, z);
        if (CityESP.mc.world.getBlockState(position).getBlock() == Blocks.AIR) {
            return;
        }
        if (CityESP.mc.world.getBlockState(position).getBlock() == Blocks.FIRE) {
            return;
        }
        if (red) {
            RenderUtil.drawBoxESP(position, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue()), false, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue()), 1.0f, false, true, this.alpha.getValue(), true);
            return;
        }
        RenderUtil.drawBoxESP(position, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue()), false, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue()), 1.0f, false, true, this.alpha.getValue(), true);
    }
    
    private IBlockState getBlock(final BlockPos block) {
        return AntiCity.mc.world.getBlockState(block);
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = range;
        for (final EntityPlayer player : CityESP.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range) && !lover.friendManager.isFriend(player.getName())) {
                if (CityESP.mc.player.posY - player.posY >= 5.0) {
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
        }
        return target;
    }
}
