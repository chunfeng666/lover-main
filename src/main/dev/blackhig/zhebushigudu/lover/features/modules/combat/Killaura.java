//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.*;
import net.minecraft.entity.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import java.util.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import java.awt.*;
import dev.blackhig.zhebushigudu.lover.features.modules.render.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.*;

public class Killaura extends Module
{
    public static Entity target;
    private final Timer timer;
    private final Setting<page> pageSetting;
    public Setting<Boolean> players;
    public Setting<Boolean> mobs;
    public Setting<Boolean> animals;
    public Setting<Boolean> vehicles;
    public Setting<Boolean> projectiles;
    public Setting<Float> range;
    public Setting<Boolean> rotate;
    public Setting<Boolean> onlySharp;
    public Setting<Float> raytrace;
    public Setting<Boolean> packet;
    public Setting<Boolean> delay;
    public Setting<Boolean> tps;
    public Setting<Boolean> esp;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> red2;
    public Setting<Integer> green2;
    public Setting<Integer> blue2;
    public Setting<Integer> points;
    public Setting<Boolean> firstP;
    
    public Killaura() {
        super("Killaura", "Kills aura.", Category.COMBAT, true, false, false);
        this.timer = new Timer();
        this.pageSetting = (Setting<page>)this.register(new Setting("page", (T)page.target));
        this.players = (Setting<Boolean>)this.register(new Setting("Players", (T)true, v -> this.pageSetting.getValue() == page.target));
        this.mobs = (Setting<Boolean>)this.register(new Setting("Mobs", (T)false, v -> this.pageSetting.getValue() == page.target));
        this.animals = (Setting<Boolean>)this.register(new Setting("Animals", (T)false, v -> this.pageSetting.getValue() == page.target));
        this.vehicles = (Setting<Boolean>)this.register(new Setting("Entities", (T)false, v -> this.pageSetting.getValue() == page.target));
        this.projectiles = (Setting<Boolean>)this.register(new Setting("Projectiles", (T)false, v -> this.pageSetting.getValue() == page.target));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)6.0f, (T)0.1f, (T)7.0f, v -> this.pageSetting.getValue() == page.target));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true, v -> this.pageSetting.getValue() == page.target));
        this.onlySharp = (Setting<Boolean>)this.register(new Setting("SwordOnly", (T)true, v -> this.pageSetting.getValue() == page.target));
        this.raytrace = (Setting<Float>)this.register(new Setting("Raytrace", (T)6.0f, (T)0.1f, (T)7.0f, v -> this.pageSetting.getValue() == page.target, "Wall Range."));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)false, v -> this.pageSetting.getValue() == page.target));
        this.delay = (Setting<Boolean>)this.register(new Setting("HitDelay", (T)true, v -> this.pageSetting.getValue() == page.delay));
        this.tps = (Setting<Boolean>)this.register(new Setting("TpsSync", (T)true, v -> this.pageSetting.getValue() == page.delay));
        this.esp = (Setting<Boolean>)this.register(new Setting("ESP", (T)true, v -> this.pageSetting.getValue() == page.render));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)255, v -> this.pageSetting.getValue() == page.render));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255, v -> this.pageSetting.getValue() == page.render));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)0, (T)0, (T)255, v -> this.pageSetting.getValue() == page.render));
        this.red2 = (Setting<Integer>)this.register(new Setting("Red2", (T)0, (T)0, (T)255, v -> this.pageSetting.getValue() == page.render));
        this.green2 = (Setting<Integer>)this.register(new Setting("Green2", (T)255, (T)0, (T)255, v -> this.pageSetting.getValue() == page.render));
        this.blue2 = (Setting<Integer>)this.register(new Setting("Blue2", (T)0, (T)0, (T)255, v -> this.pageSetting.getValue() == page.render));
        this.points = (Setting<Integer>)this.register(new Setting("Points", (T)12, (T)4, (T)64, v -> this.pageSetting.getValue() == page.render));
        this.firstP = (Setting<Boolean>)this.register(new Setting("FirstPerson", (T)true, v -> this.pageSetting.getValue() == page.render));
    }
    
    @Override
    public void onTick() {
        if (!this.rotate.getValue()) {
            this.doKillaura();
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0 && this.rotate.getValue()) {
            this.doKillaura();
        }
    }
    
    private void doKillaura() {
        if (this.onlySharp.getValue() && !EntityUtil.holdingWeapon((EntityPlayer)Util.mc.player)) {
            Killaura.target = null;
            return;
        }
        final int wait = this.delay.getValue() ? ((int)(DamageUtil.getCooldownByWeapon((EntityPlayer)Util.mc.player) * (this.tps.getValue() ? lover.serverManager.getTpsFactor() : 1.0f))) : 0;
        if (!this.timer.passedMs(wait)) {
            return;
        }
        Killaura.target = this.getTarget();
        if (Killaura.target == null) {
            return;
        }
        if (this.rotate.getValue()) {
            lover.rotationManager.lookAtEntity(Killaura.target);
        }
        EntityUtil.attackEntity(Killaura.target, this.packet.getValue(), true);
        this.timer.reset();
    }
    
    private Entity getTarget() {
        Entity target = null;
        double distance = this.range.getValue();
        double maxHealth = 36.0;
        for (final Entity entity : Util.mc.world.playerEntities) {
            if ((this.players.getValue() && entity instanceof EntityPlayer) || (this.animals.getValue() && EntityUtil.isPassive(entity)) || (this.mobs.getValue() && EntityUtil.isMobAggressive(entity)) || (this.vehicles.getValue() && EntityUtil.isVehicle(entity)) || (this.projectiles.getValue() && EntityUtil.isProjectile(entity))) {
                if (entity instanceof EntityLivingBase && EntityUtil.isntValid(entity, distance)) {
                    continue;
                }
                if (!Util.mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && Util.mc.player.getDistanceSq(entity) > MathUtil.square(this.raytrace.getValue())) {
                    continue;
                }
                if (target == null) {
                    target = entity;
                    distance = Util.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
                else {
                    if (entity instanceof EntityPlayer && DamageUtil.isArmorLow((EntityPlayer)entity, 18)) {
                        target = entity;
                        break;
                    }
                    if (Util.mc.player.getDistanceSq(entity) < distance) {
                        target = entity;
                        distance = Util.mc.player.getDistanceSq(entity);
                        maxHealth = EntityUtil.getHealth(entity);
                    }
                    if (EntityUtil.getHealth(entity) >= maxHealth) {
                        continue;
                    }
                    target = entity;
                    distance = Util.mc.player.getDistanceSq(entity);
                    maxHealth = EntityUtil.getHealth(entity);
                }
            }
        }
        return target;
    }
    
    @Override
    public void onRender3D(final Render3DEvent render3DEvent) {
        float f = 0.0f;
        if (Killaura.target != null && this.esp.getValue() && (Killaura.mc.gameSettings.thirdPersonView != 0 || this.firstP.getValue())) {
            for (int i = 0; i < 400; ++i) {
                f = (float)getGradientOffset(new Color(this.red2.getValue(), this.green2.getValue(), this.blue2.getValue(), 255), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), 255), Math.abs(System.currentTimeMillis() / 7L - i / 2) / 120.0).getRGB();
                drawHat(Killaura.target, 0.009 + i * 0.0014, render3DEvent.getPartialTicks(), this.points.getValue(), 2.0f, 2.2f - i * 7.85E-4f - (ChinaHat.mc.player.isSneaking() ? 0.07f : 0.03f), (int)f);
            }
        }
    }
    
    public static void drawHat(final Entity entity, final double d, final float f, final int n, final float f2, final float f3, final int n2) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glDepthMask(false);
        GL11.glLineWidth(f2);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2929);
        GL11.glBegin(3);
        final float f4 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * Minecraft.getMinecraft().timer.renderPartialTicks;
        final float f5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * Minecraft.getMinecraft().timer.renderPartialTicks;
        final double d2 = interpolate(entity.prevPosX, entity.posX, f) - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        final double d3 = interpolate(entity.prevPosY + f3, entity.posY + f3, f) - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        final double d4 = interpolate(entity.prevPosZ, entity.posZ, f) - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        GL11.glColor4f(new Color(n2).getRed() / 255.0f, new Color(n2).getGreen() / 255.0f, new Color(n2).getBlue() / 255.0f, 0.15f);
        for (int i = 0; i <= n; ++i) {
            GL11.glVertex3d(d2 + d * Math.cos(i * 3.141592653589793 * 2.0 / n), d3, d4 + d * Math.sin(i * 3.141592653589793 * 2.0 / n));
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static Color getGradientOffset(final Color color, final Color color2, double d) {
        if (d > 1.0) {
            final double d2 = d % 1.0;
            final int n = (int)d;
            d = ((n % 2 == 0) ? d2 : (1.0 - d2));
        }
        final double d2 = 1.0 - d;
        final int n = (int)(color.getRed() * d2 + color2.getRed() * d);
        final int n2 = (int)(color.getGreen() * d2 + color2.getGreen() * d);
        final int n3 = (int)(color.getBlue() * d2 + color2.getBlue() * d);
        return new Color(n, n2, n3);
    }
    
    public static double interpolate(final double d, final double d2, final double d3) {
        return d + (d2 - d) * d3;
    }
    
    @Override
    public String getDisplayInfo() {
        if (Killaura.target instanceof EntityPlayer) {
            return Killaura.target.getName();
        }
        return null;
    }
    
    private enum page
    {
        target, 
        delay, 
        render;
    }
}
