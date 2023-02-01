//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.util.math.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import dev.blackhig.zhebushigudu.lover.util.SeijaUtil.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import java.util.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import dev.blackhig.zhebushigudu.lover.*;
import net.minecraft.init.*;

public class FaceMiner extends Module
{
    private final Setting<Double> targetMaxSpeed;
    private final Setting<Double> targetRange;
    private final Setting<Integer> blockerCount;
    private final Setting<Boolean> precedenceHead;
    private final Setting<Boolean> onlyOnPressBind;
    Setting<Bind> mineKey;
    ArrayList<BlockPos> minePosList;
    int blockerCount1;
    
    public FaceMiner() {
        super("FaceMiner", "AntiAntiPiston", Category.COMBAT, true, false, false);
        this.targetMaxSpeed = (Setting<Double>)this.register(new Setting("TargetMaxSpeed", (T)3.6, (T)0.0, (T)15.0));
        this.targetRange = (Setting<Double>)this.register(new Setting("TargetRange", (T)3.6, (T)0.0, (T)7.0));
        this.blockerCount = (Setting<Integer>)this.register(new Setting("blockerCount", (T)20, (T)0, (T)100));
        this.precedenceHead = (Setting<Boolean>)this.register(new Setting("PrecedenceHead", (T)true));
        this.onlyOnPressBind = (Setting<Boolean>)this.register(new Setting("onlyOnPressBind", (T)true));
        this.mineKey = (Setting<Bind>)this.register(new Setting("MineKey", (T)new Bind(0), v -> this.onlyOnPressBind.getValue()));
        this.blockerCount1 = 10;
    }
    
    @Override
    public void onUpdate() {
        if (InstantMine.breakPos == null || this.airCheck(InstantMine.breakPos)) {
            --this.blockerCount1;
        }
        else {
            this.blockerCount1 = this.blockerCount.getValue();
        }
        if (this.onlyOnPressBind.getValue() && !this.mineKey.getValue().isDown()) {
            return;
        }
        final EntityPlayer target = this.getTarget(this.targetRange.getValue());
        if (target == null || this.minePosList.size() == 0) {
            return;
        }
        final HashMap<BlockPos, Double> blockMap = new HashMap<BlockPos, Double>();
        for (final BlockPos minePos : this.minePosList) {
            blockMap.put(minePos, SeijaDistanceUtil.distanceToXZ(minePos.getX(), minePos.getZ()));
        }
        final List<Map.Entry<BlockPos, Double>> list = new ArrayList<Map.Entry<BlockPos, Double>>(blockMap.entrySet());
        list.sort((Comparator<? super Map.Entry<BlockPos, Double>>)Map.Entry.comparingByValue());
        if (this.precedenceHead.getValue() && !this.airCheck(SeijaBlockUtil.getFlooredPosition((Entity)target).add(0, 2, 0))) {
            final HashMap<BlockPos, Double> blockMap2 = new HashMap<BlockPos, Double>();
            blockMap2.put(SeijaBlockUtil.getFlooredPosition((Entity)target).add(0, 2, 0), SeijaDistanceUtil.distanceToXZ(SeijaBlockUtil.getFlooredPosition((Entity)target).add(0, 2, 0)));
            final List<Map.Entry<BlockPos, Double>> list2 = new ArrayList<Map.Entry<BlockPos, Double>>(blockMap2.entrySet());
            list.set(0, list2.get(0));
        }
        if (list.size() >= 2 && InstantMine.breakPos2 == null && this.blockerCount1 < 0) {
            SeijaBlockUtil.mine(list.get(0).getKey(), list.get(1).getKey());
        }
        if (list.size() == 1 && InstantMine.breakPos2 == null && this.blockerCount1 < 0) {
            SeijaBlockUtil.mine(list.get(0).getKey());
        }
    }
    
    private ArrayList<BlockPos> getMineBlock(final BlockPos pos) {
        final ArrayList<BlockPos> posList = new ArrayList<BlockPos>();
        if (!this.airCheck(pos.add(0, 2, 0))) {
            posList.add(pos.add(0, 2, 0));
        }
        if (!this.airCheck(pos.add(1, 2, 0))) {
            posList.add(pos.add(1, 2, 0));
        }
        if (!this.airCheck(pos.add(-1, 2, 0))) {
            posList.add(pos.add(-1, 2, 0));
        }
        if (!this.airCheck(pos.add(0, 2, 1))) {
            posList.add(pos.add(0, 2, 1));
        }
        if (!this.airCheck(pos.add(0, 2, -1))) {
            posList.add(pos.add(0, 2, -1));
        }
        if (!this.airCheck(pos.add(1, 1, 0))) {
            posList.add(pos.add(1, 1, 0));
        }
        if (!this.airCheck(pos.add(-1, 1, 0))) {
            posList.add(pos.add(-1, 1, 0));
        }
        if (!this.airCheck(pos.add(0, 1, 1))) {
            posList.add(pos.add(0, 1, 1));
        }
        if (!this.airCheck(pos.add(0, 1, -1))) {
            posList.add(pos.add(0, 1, -1));
        }
        return posList;
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = range;
        for (final EntityPlayer player : FaceMiner.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range) && !lover.friendManager.isFriend(player.getName()) && FaceMiner.mc.player.posY - player.posY < 5.0 && lover.speedManager.getPlayerSpeed(player) <= this.targetMaxSpeed.getValue()) {
                if (this.getMineBlock(SeijaBlockUtil.getFlooredPosition((Entity)player)).size() == 0) {
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
        if (target != null) {
            this.minePosList = this.getMineBlock(SeijaBlockUtil.getFlooredPosition((Entity)target));
        }
        return target;
    }
    
    public boolean airCheck(final BlockPos pos) {
        return FaceMiner.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || FaceMiner.mc.world.getBlockState(pos).getBlock().equals(Blocks.PISTON) || FaceMiner.mc.world.getBlockState(pos).getBlock().equals(Blocks.STICKY_PISTON) || FaceMiner.mc.world.getBlockState(pos).getBlock().equals(Blocks.REDSTONE_BLOCK);
    }
}
