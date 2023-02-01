//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.player;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.util.math.*;
import dev.blackhig.zhebushigudu.lover.util.n.*;
import dev.blackhig.zhebushigudu.lover.util.Anti.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.init.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;

public class Anti32k extends Module
{
    private static Anti32k INSTANCE;
    private Setting<Integer> range;
    private Setting<Boolean> packetMine;
    public static BlockPos min;
    int oldslot;
    int shulkInt;
    HashMap<BlockPos, Integer> opendShulk;
    
    public Anti32k() {
        super("Anti32k", "Anti32k", Module.Category.PLAYER, true, false, false);
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)5, (T)3, (T)8));
        this.packetMine = (Setting<Boolean>)this.register(new Setting("PacketMine", (T)false));
        this.oldslot = -1;
        this.opendShulk = new HashMap<BlockPos, Integer>();
    }
    
    public void onDisable() {
        this.oldslot = -1;
        this.shulkInt = 0;
        this.opendShulk.clear();
    }
    
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
        final boolean b = false;
        final BlockPos hopperPos = BlockInteractionHelper.getSphere(EntityUtil.getLocalPlayerPosFloored(), this.range.getValue(), this.range.getValue(), false, true, 0).stream().filter(e -> {
            if (Anti32k.mc.world.getBlockState(e).getBlock() instanceof BlockHopper) {
                if (Anti32k.mc.world.getBlockState(new BlockPos(e.getX(), e.getY() + 1, e.getZ())).getBlock() instanceof BlockShulkerBox) {
                    return false;
                }
            }
            return false;
        }).min(Comparator.comparing(e -> Anti32k.mc.player.getDistanceSq(e))).orElse(null);
        final int slot = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
        if (slot != -1 && hopperPos != null) {
            if (Anti32k.mc.player.getDistance((double)hopperPos.getX(), (double)hopperPos.getY(), (double)hopperPos.getZ()) > this.range.getValue()) {
                return;
            }
            if (Anti32k.mc.player.inventory.currentItem != slot) {
                this.oldslot = Anti32k.mc.player.inventory.currentItem;
                Anti32k.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slot));
            }
            if (this.packetMine.getValue()) {
                Anti32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, hopperPos, EnumFacing.UP));
                Anti32k.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, hopperPos, EnumFacing.UP));
            }
            else {
                Anti32k.mc.playerController.onPlayerDamageBlock(hopperPos, EnumFacing.UP);
                Anti32k.mc.playerController.onPlayerDestroyBlock(hopperPos);
            }
            Anti32k.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
            if (this.oldslot != -1) {
                Anti32k.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(this.oldslot));
            }
        }
    }
    
    static {
        Anti32k.INSTANCE = new Anti32k();
        Anti32k.min = null;
    }
}
