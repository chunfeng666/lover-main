//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn��ǿ��������\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.mojang.realmsclient.gui.*;
import dev.blackhig.zhebushigudu.lover.features.command.*;
import net.minecraft.util.math.*;
import java.util.*;
import dev.blackhig.zhebushigudu.lover.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.*;

public class AutoRedStone extends Module
{
    private final Setting<Integer> delay;
    private final Setting<Integer> blocksPerPlace;
    private final Setting<Boolean> packet;
    private final Setting<Boolean> disable;
    private final Setting<Boolean> togg1e;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> raytrace;
    private final Timer timer;
    public EntityPlayer target;
    private boolean didPlace;
    private boolean isSneaking;
    private int placements;
    private boolean smartRotate;
    private BlockPos startPos;
    
    public AutoRedStone() {
        super("AutoRedStone", "Auto Feet RedStone in player", Category.COMBAT, true, false, false);
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)50, (T)0, (T)250));
        this.blocksPerPlace = (Setting<Integer>)this.register(new Setting("BlocksPerTick", (T)8, (T)1, (T)30));
        this.packet = (Setting<Boolean>)this.register(new Setting("PacketPlace", (T)false));
        this.disable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)false));
        this.togg1e = (Setting<Boolean>)this.register(new Setting("AutoToggle", (T)false));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.raytrace = (Setting<Boolean>)this.register(new Setting("Raytrace", (T)false));
        this.timer = new Timer();
        this.didPlace = false;
        this.placements = 0;
        this.smartRotate = false;
        this.startPos = null;
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            return;
        }
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)AutoRedStone.mc.player);
    }
    
    @Override
    public void onTick() {
        if (InventoryUtil.pickItem(331, false) == -1) {
            Command.sendMessage("<" + this.getDisplayName() + "> " + ChatFormatting.RED + "Web ?");
            this.disable();
            return;
        }
        this.smartRotate = false;
        this.doTrap();
        if (this.togg1e.getValue()) {
            this.toggle();
        }
    }
    
    @Override
    public String getDisplayInfo() {
        if (this.target != null) {
            return this.target.getName();
        }
        return null;
    }
    
    @Override
    public void onDisable() {
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
    }
    
    private void doTrap() {
        if (this.check()) {
            return;
        }
        this.doWebTrap();
        if (this.didPlace) {
            this.timer.reset();
        }
    }
    
    private void doWebTrap() {
        final List<Vec3d> placeTargets = this.getPlacements();
        final int a = AutoRedStone.mc.player.inventory.currentItem;
        AutoRedStone.mc.player.inventory.currentItem = InventoryUtil.pickItem(331, false);
        AutoRedStone.mc.playerController.updateController();
        this.placeList(placeTargets);
        AutoRedStone.mc.player.inventory.currentItem = a;
        AutoRedStone.mc.playerController.updateController();
    }
    
    private List<Vec3d> getPlacements() {
        final ArrayList<Vec3d> list = new ArrayList<Vec3d>();
        final Vec3d baseVec = this.target.getPositionVector();
        list.add(baseVec);
        return list;
    }
    
    private void placeList(final List<Vec3d> list) {
        list.sort((vec3d, vec3d2) -> Double.compare(AutoRedStone.mc.player.getDistanceSq(vec3d2.x, vec3d2.y, vec3d2.z), AutoRedStone.mc.player.getDistanceSq(vec3d.x, vec3d.y, vec3d.z)));
        list.sort(Comparator.comparingDouble(vec3d -> vec3d.y));
        for (final Vec3d vec3d3 : list) {
            final BlockPos position = new BlockPos(vec3d3);
            final int placeability = BlockUtil.isPositionPlaceable(position, this.raytrace.getValue());
            if (placeability != 3 && placeability != 1) {
                continue;
            }
            this.placeBlock(position);
        }
    }
    
    private boolean check() {
        this.didPlace = false;
        this.placements = 0;
        if (this.isOff()) {
            return true;
        }
        if (this.disable.getValue() && !this.startPos.equals((Object)EntityUtil.getRoundedBlockPos((Entity)AutoRedStone.mc.player))) {
            this.disable();
            return true;
        }
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.target = this.getTarget(10.0);
        return this.target == null || !this.timer.passedMs(this.delay.getValue());
    }
    
    private EntityPlayer getTarget(final double range) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (final EntityPlayer player : AutoRedStone.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range) && !player.isInWeb) {
                if (lover.speedManager.getPlayerSpeed(player) > 30.0) {
                    continue;
                }
                if (target == null) {
                    target = player;
                    distance = AutoRedStone.mc.player.getDistanceSq((Entity)player);
                }
                else {
                    if (AutoRedStone.mc.player.getDistanceSq((Entity)player) >= distance) {
                        continue;
                    }
                    target = player;
                    distance = AutoRedStone.mc.player.getDistanceSq((Entity)player);
                }
            }
        }
        return target;
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.placements < this.blocksPerPlace.getValue() && AutoRedStone.mc.player.getDistanceSq(pos) <= MathUtil.square(6.0)) {
            this.isSneaking = (this.smartRotate ? BlockUtil.placeBlockSmartRotate(pos, EnumHand.MAIN_HAND, true, this.packet.getValue(), this.isSneaking) : BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.getValue(), this.isSneaking));
            this.didPlace = true;
            ++this.placements;
        }
    }
}
