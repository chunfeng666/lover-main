//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.render;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import java.util.*;
import net.minecraft.entity.player.*;
import java.util.concurrent.atomic.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import net.minecraftforge.fml.common.eventhandler.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.awt.*;
import dev.blackhig.zhebushigudu.lover.util.*;

public class BreakESP extends Module
{
    private final Setting<Double> range;
    private final Setting<Boolean> render;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> alpha;
    private final Setting<Boolean> playername;
    private final Setting<Boolean> render1;
    private final Setting<Integer> red1;
    private final Setting<Integer> green1;
    private final Setting<Integer> blue1;
    private final Setting<Integer> alpha1;
    private final Setting<Boolean> playername1;
    static HashMap<EntityPlayer, InstantMiner> miners;
    
    public BreakESP() {
        super("BreakESP", "Render the miners' breaking position", Module.Category.RENDER, true, false, false);
        this.range = (Setting<Double>)this.register(new Setting("Range", (T)5.5, (T)0.0, (T)10.0));
        this.render = (Setting<Boolean>)this.register(new Setting("FirstRender", (T)true));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)100, (T)20, (T)255));
        this.playername = (Setting<Boolean>)this.register(new Setting("FirstPlayerName", (T)true));
        this.render1 = (Setting<Boolean>)this.register(new Setting("SecondRender", (T)true));
        this.red1 = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255));
        this.green1 = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue1 = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.alpha1 = (Setting<Integer>)this.register(new Setting("Alpha", (T)100, (T)20, (T)255));
        this.playername1 = (Setting<Boolean>)this.register(new Setting("SecondPlayerName", (T)true));
    }
    
    public static boolean isBreaking(final BlockPos pos) {
        final AtomicReference<InstantMiner> lastMiner = new AtomicReference<InstantMiner>(null);
        final AtomicReference<InstantMiner> atomicReference;
        BreakESP.miners.values().forEach(e -> {
            try {
                if (pos.getDistance(e.first.getX(), e.first.getY(), e.first.getZ()) < 1.0 || pos.getDistance(e.second.getX(), e.second.getY(), e.second.getZ()) < 1.0) {
                    atomicReference.set(e);
                }
            }
            catch (Exception ex) {}
            return;
        });
        return lastMiner.get() != null;
    }
    
    @SubscribeEvent
    public void onBreaking(final BlockBreakEvent e) {
        if (e.getBreaker() == BreakESP.mc.player) {
            return;
        }
        if (InstantMine.godBlocks.contains(BreakESP.mc.world.getBlockState(e.getPosition()).getBlock())) {
            return;
        }
        final EntityPlayer breaker = (EntityPlayer)BreakESP.mc.world.getEntityByID(e.getBreakId());
        if (!BreakESP.miners.containsKey(breaker)) {
            BreakESP.miners.put(breaker, new InstantMiner(breaker));
        }
        BreakESP.miners.get(breaker).updateFirst(e.getPosition());
    }
    
    public void onRender3D(final Render3DEvent event) {
        final EntityPlayer[] array;
        final EntityPlayer[] s = array = BreakESP.miners.keySet().toArray(new EntityPlayer[0]);
        for (final EntityPlayer entityPlayer : array) {
            if (entityPlayer != null) {
                if (!entityPlayer.isEntityAlive()) {
                    BreakESP.miners.remove(entityPlayer);
                }
            }
        }
        Vec3d interpolateEntity;
        AxisAlignedBB axisAlignedBB;
        AxisAlignedBB axisAlignedBB2;
        double size;
        Vec3d interpolateEntity2;
        AxisAlignedBB axisAlignedBB3;
        AxisAlignedBB axisAlignedBB4;
        double size2;
        BreakESP.miners.values().forEach(miner -> {
            if (miner.first.getDistance((int)BreakESP.mc.player.posX, (int)BreakESP.mc.player.posY, (int)BreakESP.mc.player.posZ) < this.range.getValue()) {
                interpolateEntity = MathUtil.interpolateEntity((Entity)BreakESP.mc.player, BreakESP.mc.getRenderPartialTicks());
                if (this.render.getValue()) {
                    axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(miner.first);
                    axisAlignedBB2 = axisAlignedBB.grow(0.0020000000949949026).offset(-interpolateEntity.x, -interpolateEntity.y, -interpolateEntity.z);
                    size = miner.firstFade.easeOutQuad();
                    this.draw(axisAlignedBB2, size);
                }
                if (this.playername.getValue()) {
                    RenderUtil.drawText(miner.first, (miner.player == null) ? "Unknown" : miner.player.getName());
                }
            }
            if (!miner.secondFinished && miner.second != null) {
                if (BreakESP.mc.world.isAirBlock(miner.second)) {
                    miner.finishSecond();
                }
                else if (miner.second.getDistance((int)BreakESP.mc.player.posX, (int)BreakESP.mc.player.posY, (int)BreakESP.mc.player.posZ) < this.range.getValue()) {
                    interpolateEntity2 = MathUtil.interpolateEntity((Entity)BreakESP.mc.player, BreakESP.mc.getRenderPartialTicks());
                    if (this.render1.getValue()) {
                        axisAlignedBB3 = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(miner.second);
                        axisAlignedBB4 = axisAlignedBB3.grow(0.0020000000949949026).offset(-interpolateEntity2.x, -interpolateEntity2.y, -interpolateEntity2.z);
                        size2 = miner.secondFade.easeOutQuad();
                        this.draw1(axisAlignedBB4, size2);
                    }
                    if (this.playername1.getValue()) {
                        RenderUtil.drawText(miner.second, (miner.player == null) ? "Unknown" : miner.player.getName());
                    }
                }
            }
        });
    }
    
    public void draw(final AxisAlignedBB axisAlignedBB, final double size) {
        final double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0;
        final double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0;
        final double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0;
        final double full = axisAlignedBB.maxX - centerX;
        final double progressValX = full * size;
        final double progressValY = full * size;
        final double progressValZ = full * size;
        final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
        RenderUtils3D.drawBlockOutline(axisAlignedBB2, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.5f);
        RenderUtil.drawFilledBox(axisAlignedBB2, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()).getRGB());
    }
    
    public void draw1(final AxisAlignedBB axisAlignedBB, final double size) {
        final double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0;
        final double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0;
        final double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0;
        final double full = axisAlignedBB.maxX - centerX;
        final double progressValX = full * size;
        final double progressValY = full * size;
        final double progressValZ = full * size;
        final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
        RenderUtils3D.drawBlockOutline(axisAlignedBB2, new Color(this.red1.getValue(), this.green1.getValue(), this.blue1.getValue(), this.alpha1.getValue()), 1.5f);
        RenderUtil.drawFilledBox(axisAlignedBB2, new Color(this.red1.getValue(), this.green1.getValue(), this.blue1.getValue(), this.alpha1.getValue()).getRGB());
    }
    
    static {
        BreakESP.miners = new HashMap<EntityPlayer, InstantMiner>();
    }
    
    private static class InstantMiner
    {
        public EntityPlayer player;
        public FadeUtils firstFade;
        public FadeUtils secondFade;
        public BlockPos first;
        public BlockPos second;
        public boolean secondFinished;
        
        public InstantMiner(final EntityPlayer player) {
            this.firstFade = new FadeUtils(2000L);
            this.secondFade = new FadeUtils(2000L);
            this.player = player;
            this.secondFinished = true;
        }
        
        public void finishSecond() {
            this.secondFinished = true;
        }
        
        public void updateFirst(final BlockPos pos) {
            if (this.first == pos || this.second == pos) {
                return;
            }
            this.second = this.first;
            this.first = pos;
            this.secondFinished = false;
            this.firstFade.reset();
            this.secondFade.reset();
        }
    }
}
