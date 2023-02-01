//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.util.Anti.*;
import net.minecraft.entity.*;
import dev.blackhig.zhebushigudu.lover.*;
import dev.blackhig.zhebushigudu.lover.util.e.*;
import com.mojang.realmsclient.gui.*;
import dev.blackhig.zhebushigudu.lover.features.command.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.block.state.*;

public class SmartAntiCity extends Module
{
    private final Setting<Boolean> rotate;
    public Setting<Boolean> packet;
    private int obsidian;
    private int enderchest;
    private final Timer timer;
    private final Timer retryTimer;
    private float yaw;
    private float pitch;
    private boolean rotating;
    private boolean isSneaking;
    private final Setting<Boolean> center;
    private final Setting<BlockMode> mode;
    private final Setting<Integer> delay;
    private final Setting<Boolean> crystal;
    private final Map<BlockPos, Integer> retries;
    private BlockPos startPos;
    
    public SmartAntiCity() {
        super("SmartAntiCity", "SmartAntiCity", Category.COMBAT, true, false, false);
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)true));
        this.obsidian = -1;
        this.enderchest = -1;
        this.timer = new Timer();
        this.retryTimer = new Timer();
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.rotating = false;
        this.center = (Setting<Boolean>)this.register(new Setting("TPCenter", (T)true));
        this.mode = (Setting<BlockMode>)this.register(new Setting("BlockMode", (T)BlockMode.Smart));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)0, (T)0, (T)300));
        this.crystal = (Setting<Boolean>)this.register(new Setting("BreakCrystal", (T)true));
        this.retries = new HashMap<BlockPos, Integer>();
    }
    
    @Override
    public void onEnable() {
        this.startPos = EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player);
        if (this.center.getValue()) {
            lover.positionManager.setPositionPacket(this.startPos.getX() + 0.5, this.startPos.getY(), this.startPos.getZ() + 0.5, true, true, true);
        }
    }
    
    @Override
    public void onTick() {
        if (!this.startPos.equals((Object)EntityUtil.getRoundedBlockPos((Entity)Surround.mc.player))) {
            this.toggle();
        }
    }
    
    @Override
    public void onUpdate() {
        final Vec3d a = Blocker.mc.player.getPositionVector();
        if (!Util.mc.world.isBlockLoaded(Util.mc.player.getPosition())) {
            return;
        }
        BlockPos pos = new BlockPos(SmartAntiCity.mc.player.posX, SmartAntiCity.mc.player.posY, SmartAntiCity.mc.player.posZ);
        if (pos == null) {
            return;
        }
        if (this.mode.getValue() == BlockMode.Obsidian && Util.findHotbarBlock(BlockObsidian.class) == -1) {
            Command.sendMessage(ChatFormatting.RED + "Obsidian ?");
            this.disable();
            return;
        }
        if (this.mode.getValue() == BlockMode.Chest && Util.findHotbarBlock(BlockEnderChest.class) == -1) {
            Command.sendMessage(ChatFormatting.RED + "Ender Chest ?");
            this.disable();
            return;
        }
        if (this.mode.getValue() == BlockMode.Smart && Util.findHotbarBlock(BlockEnderChest.class) == -1 && Util.findHotbarBlock(BlockObsidian.class) == -1) {
            Command.sendMessage(ChatFormatting.RED + "Obsidian/Ender Chest ?");
            this.disable();
            return;
        }
        pos = new BlockPos(SmartAntiCity.mc.player.posX, SmartAntiCity.mc.player.posY, SmartAntiCity.mc.player.posZ);
        if (pos == null) {
            return;
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 1)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 1)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(0, 1, 1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -1)) != null) {
            this.place(a, EntityUtil.getVarOffsets(0, 1, -1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 1, 0)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 1, 0)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(1, 1, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 1, 0)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 1, 0)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(-1, 1, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(0, 0, 1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -1)) != null) {
            this.place(a, EntityUtil.getVarOffsets(0, 0, -1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 2, 1)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 2, 1)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(0, 2, 1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 2, -1)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 2, -1)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(0, 2, -1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 2, 0)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 2, 0)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(1, 2, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 2, 0)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 2, 0)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(-1, 2, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 4, 0)) != null) {
            this.place(a, EntityUtil.getVarOffsets(0, 4, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 5, 0)) != null) {
            this.place(a, EntityUtil.getVarOffsets(0, 5, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 3, 0)) != null) {
            this.place(a, EntityUtil.getVarOffsets(0, 3, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 3, 1)) != null) {
            this.place(a, EntityUtil.getVarOffsets(0, 3, 1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 3, -1)) != null) {
            this.place(a, EntityUtil.getVarOffsets(0, 3, -1));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 3, 0)) != null) {
            this.place(a, EntityUtil.getVarOffsets(1, 3, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 3, 0)) != null) {
            this.place(a, EntityUtil.getVarOffsets(-1, 3, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(1, 0, 0));
        }
        if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)) != null) {
            if (this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)), true);
            }
            this.place(a, EntityUtil.getVarOffsets(-1, 0, 0));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(2, 1, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(2, 1, 0)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(2, 1, 0)), true);
            }
            this.perform(pos.add(2, 1, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-2, 1, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-2, 1, 0)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-2, 1, 0)), true);
            }
            this.perform(pos.add(-2, 1, 0));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, 2)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 2)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, 2)), true);
            }
            this.perform(pos.add(0, 1, 2));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, -2)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -2)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 1, -2)), true);
            }
            this.perform(pos.add(0, 1, -2));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, -1, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 1)), true);
            }
            this.perform(pos.add(0, -1, 1));
            this.perform(pos.add(0, 0, 1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, -1, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -1)), true);
            }
            this.perform(pos.add(0, -1, -1));
            this.perform(pos.add(0, 0, -1));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, -1, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 0)), true);
            }
            this.perform(pos.add(1, -1, 0));
            this.perform(pos.add(1, 0, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, -1, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 0)), true);
            }
            this.perform(pos.add(-1, -1, 0));
            this.perform(pos.add(-1, 0, 0));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, 2)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, 2)), true);
            }
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(0, 0, 2));
            this.perform(pos.add(0, -1, 2));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 0, -2)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(0, 0, -2)), true);
            }
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(0, 0, -2));
            this.perform(pos.add(0, -1, -2));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(2, 0, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(2, 0, 0)), true);
            }
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(2, 0, 0));
            this.perform(pos.add(2, -1, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-2, 0, 0)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-2, 0, 0)), true);
            }
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-2, 0, 0));
            this.perform(pos.add(-2, -1, 0));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(0, 1, 1));
            this.perform(pos.add(0, 1, 2));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(0, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(0, 1, -1));
            this.perform(pos.add(0, 1, -2));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, 0)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 1, 0));
            this.perform(pos.add(2, 1, 0));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, 0)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 1, 0));
            this.perform(pos.add(-2, 1, 0));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)), true);
            }
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 0, 1));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, 1)), true);
            }
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(1, 0, 1));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)), true);
            }
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 0, -1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, -1)), true);
            }
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(-1, 0, -1));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)), true);
            }
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 0, 1));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(-1, 0, 1)), true);
            }
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(-1, 0, 1));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)), true);
            }
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 0, -1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR) {
            if (this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)) != null && this.crystal.getValue()) {
                EntityUtil.attackEntity(this.checkCrystal(a, EntityUtil.getVarOffsets(1, 0, -1)), true);
            }
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(1, 0, -1));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 0, 1));
            this.perform(pos.add(1, 1, 1));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(1, 0, 1));
            this.perform(pos.add(1, 1, 1));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 0, -1));
            this.perform(pos.add(-1, 1, -1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(-1, 0, -1));
            this.perform(pos.add(-1, 1, -1));
        }
        if (this.getBlock(pos.add(-1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(-1, 0, 0));
            this.perform(pos.add(-1, 0, 1));
            this.perform(pos.add(-1, 1, 1));
        }
        if (this.getBlock(pos.add(0, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 0, 1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(-1, 1, 1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, 1));
            this.perform(pos.add(-1, 0, 1));
            this.perform(pos.add(-1, 1, 1));
        }
        if (this.getBlock(pos.add(1, 0, 0)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(1, 0, 0));
            this.perform(pos.add(1, 0, -1));
            this.perform(pos.add(1, 1, -1));
        }
        if (this.getBlock(pos.add(0, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 0, -1)).getBlock() == Blocks.AIR && this.getBlock(pos.add(1, 1, -1)).getBlock() == Blocks.AIR) {
            this.perform(pos.add(0, 0, -1));
            this.perform(pos.add(1, 0, -1));
            this.perform(pos.add(1, 1, -1));
        }
    }
    
    private void switchToSlot(final int slot) {
        SmartAntiCity.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
        SmartAntiCity.mc.player.inventory.currentItem = slot;
        SmartAntiCity.mc.playerController.updateController();
    }
    
    private Boolean place(final Vec3d pos, final Vec3d[] list) {
        final Vec3d[] var3 = list;
        for (int var4 = list.length, var5 = 0; var5 < var4; ++var5) {
            final Vec3d vec3d = var3[var5];
            final BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            final int a = Blocker.mc.player.inventory.currentItem;
            if (this.retryTimer.passedMs(2500L)) {
                this.retries.clear();
                this.retryTimer.reset();
            }
            if (this.mode.getValue() == BlockMode.Obsidian) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Util.findHotbarBlock(BlockObsidian.class)));
                Util.mc.player.inventory.currentItem = Util.findHotbarBlock(BlockObsidian.class);
            }
            if (this.mode.getValue() == BlockMode.Chest) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Util.findHotbarBlock(BlockEnderChest.class)));
                Util.mc.player.inventory.currentItem = Util.findHotbarBlock(BlockEnderChest.class);
            }
            if (this.mode.getValue() == BlockMode.Smart) {
                if (Util.findHotbarBlock(BlockObsidian.class) != -1) {
                    Util.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Util.findHotbarBlock(BlockObsidian.class)));
                    Util.mc.player.inventory.currentItem = Util.findHotbarBlock(BlockObsidian.class);
                }
                else if (Util.findHotbarBlock(BlockEnderChest.class) != -1) {
                    Util.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Util.findHotbarBlock(BlockEnderChest.class)));
                    Util.mc.player.inventory.currentItem = Util.findHotbarBlock(BlockEnderChest.class);
                }
            }
            Blocker.mc.playerController.updateController();
            this.isSneaking = BlockUtil.placeBlock(position, EnumHand.MAIN_HAND, false, this.packet.getValue(), true);
            Blocker.mc.player.inventory.currentItem = a;
            Blocker.mc.playerController.updateController();
        }
        return !this.timer.passedMs(this.delay.getValue());
    }
    
    Entity checkCrystal(final Vec3d pos, final Vec3d[] list) {
        Entity crystal = null;
        final Vec3d[] var4 = list;
        for (int var5 = list.length, var6 = 0; var6 < var5; ++var6) {
            final Vec3d vec3d = var4[var6];
            final BlockPos position = new BlockPos(pos).add(vec3d.x, vec3d.y, vec3d.z);
            for (final Entity entity : Blocker.mc.world.getEntitiesWithinAABB((Class)Entity.class, new AxisAlignedBB(position))) {
                if (entity instanceof EntityEnderCrystal) {
                    if (crystal != null) {
                        continue;
                    }
                    crystal = entity;
                }
            }
        }
        return crystal;
    }
    
    private IBlockState getBlock(final BlockPos block) {
        return SmartAntiCity.mc.world.getBlockState(block);
    }
    
    private void perform(final BlockPos pos) {
        final int old = SmartAntiCity.mc.player.inventory.currentItem;
        if (this.mode.getValue() == BlockMode.Obsidian) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Util.findHotbarBlock(BlockObsidian.class)));
            Util.mc.player.inventory.currentItem = Util.findHotbarBlock(BlockObsidian.class);
        }
        if (this.mode.getValue() == BlockMode.Chest) {
            Util.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Util.findHotbarBlock(BlockEnderChest.class)));
            Util.mc.player.inventory.currentItem = Util.findHotbarBlock(BlockEnderChest.class);
        }
        if (this.mode.getValue() == BlockMode.Smart) {
            if (Util.findHotbarBlock(BlockObsidian.class) != -1) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Util.findHotbarBlock(BlockObsidian.class)));
                Util.mc.player.inventory.currentItem = Util.findHotbarBlock(BlockObsidian.class);
            }
            else if (Util.findHotbarBlock(BlockEnderChest.class) != -1) {
                Util.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(Util.findHotbarBlock(BlockEnderChest.class)));
                Util.mc.player.inventory.currentItem = Util.findHotbarBlock(BlockEnderChest.class);
            }
        }
        BlockUtil.placeBlock(pos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        this.switchToSlot(old);
    }
    
    enum BlockMode
    {
        Obsidian, 
        Chest, 
        Smart;
    }
}
