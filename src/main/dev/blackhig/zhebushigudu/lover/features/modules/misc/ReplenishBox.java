//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.misc;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import net.minecraft.block.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import java.util.concurrent.atomic.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import com.mojang.realmsclient.gui.*;
import dev.blackhig.zhebushigudu.lover.features.command.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import dev.blackhig.zhebushigudu.lover.util.Anti.*;
import net.minecraft.client.gui.inventory.*;
import dev.blackhig.zhebushigudu.lover.mixin.mixins.*;
import net.minecraft.inventory.*;
import dev.blackhig.zhebushigudu.lover.*;
import dev.blackhig.zhebushigudu.lover.features.modules.combat.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import java.util.*;

public class ReplenishBox extends Module
{
    public static List<Block> shulkers;
    BlockPos blockpos;
    Setting<Mode> mode;
    Setting<Double> range;
    Setting<Boolean> place;
    Setting<Boolean> open;
    Setting<Boolean> take;
    Setting<Boolean> off;
    Setting<Enums.breakMode> breakMode;
    List<BlockPos> placeables;
    
    public ReplenishBox() {
        super("ReplenishBox", "Repbox", Category.MISC, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.stealer));
        this.range = (Setting<Double>)this.register(new Setting("Range", (T)5.5, (T)0.0, (T)6.0));
        this.place = (Setting<Boolean>)this.register(new Setting("Place", (T)true));
        this.open = (Setting<Boolean>)this.register(new Setting("Open", (T)true, v -> this.place.getValue()));
        this.take = (Setting<Boolean>)this.register(new Setting("Take", (T)false, v -> this.place.getValue() && this.open.getValue()));
        this.off = (Setting<Boolean>)this.register(new Setting("Close", (T)false, v -> this.place.getValue() && this.open.getValue() && this.take.getValue()));
        this.breakMode = (Setting<Enums.breakMode>)this.register(new Setting("BoxBreakMode", (T)Enums.breakMode.LoverMine, v -> this.place.getValue() && this.open.getValue() && this.take.getValue() && this.off.getValue()));
        this.placeables = new ArrayList<BlockPos>();
    }
    
    public int findShulk() {
        final AtomicInteger intt = new AtomicInteger(-1);
        final AtomicInteger atomicInteger;
        ReplenishBox.shulkers.forEach(e -> {
            if (InventoryUtil.findHotbarBlock(e) != -1) {
                atomicInteger.set(InventoryUtil.findHotbarBlock(e));
            }
            return;
        });
        return intt.get();
    }
    
    private List<EntityPlayer> getTargets() {
        final ArrayList<EntityPlayer> targets = new ArrayList<EntityPlayer>();
        final double range = 9.0;
        for (final EntityPlayer player : ReplenishBox.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range)) {
                if (ReplenishBox.mc.player.getDistanceSq((Entity)player) >= range) {
                    continue;
                }
                targets.add(player);
            }
        }
        return targets;
    }
    
    @Override
    public void onEnable() {
        if (!this.place.getValue()) {
            this.disable();
            return;
        }
        this.placeables = BlockUtil.getSphere(ReplenishBox.mc.player.getPosition(), this.range.getValue().floatValue(), 4, false, true, 0);
        if (this.findShulk() == -1) {
            this.disable();
            Command.sendMessage(ChatFormatting.RED + "No Shulk Found");
            return;
        }
        InventoryUtil.switchToHotbarSlot(this.findShulk(), false);
        final List<BlockPos> posList;
        this.getTargets().forEach(e -> {
            posList = BlockUtil.getSphere(EntityUtil.getPlayerPos(e), 5.0f, 5, false, true, 0);
            posList.forEach(c -> this.placeables.remove(c));
            return;
        });
        final ArrayList needRemove = new ArrayList();
        final ArrayList<BlockPos> list;
        this.placeables.forEach(e -> {
            if (BlockUtils.isPlaceable(e, 0.0, true) == null || !ReplenishBox.mc.world.isAirBlock(e.up())) {
                list.add(e);
            }
            return;
        });
        needRemove.forEach(x -> this.placeables.remove(x));
        BlockUtils bu;
        try {
            bu = BlockUtils.isPlaceable(this.placeables.get(0), 0.0, true);
        }
        catch (IndexOutOfBoundsException x2) {
            this.disable();
            Command.sendMessage(ChatFormatting.RED + "No place to place shulk");
            return;
        }
        if (bu == null) {
            this.disable();
            Command.sendMessage(ChatFormatting.RED + "INVALID ERROR1");
            return;
        }
        Objects.requireNonNull(bu).doPlace(true);
        if (!this.open.getValue()) {
            this.disable();
            return;
        }
        this.blockpos = this.placeables.get(0);
        final Vec3d hitVec = new Vec3d((Vec3i)this.blockpos).add(0.5, 0.5, 0.5).add(new Vec3d(EnumFacing.UP.getDirectionVec()).scale(0.5));
        Util.rightClickBlock(this.blockpos, hitVec, EnumHand.MAIN_HAND, EnumFacing.UP, true);
    }
    
    @Override
    public void onTick() {
        if (!(ReplenishBox.mc.currentScreen instanceof GuiShulkerBox)) {
            return;
        }
        if (!this.take.getValue()) {
            this.disable();
            return;
        }
        final GuiShulkerBox l_Chest = (GuiShulkerBox)ReplenishBox.mc.currentScreen;
        final IInventory inventory = ((AccessorGuiShulkerBox)l_Chest).getInventory();
        switch (this.mode.getValue()) {
            case dropper: {
                for (int l_I = 0; l_I < Objects.requireNonNull(inventory).getSizeInventory(); ++l_I) {
                    final ItemStack stack = inventory.getStackInSlot(l_I);
                    if (!stack.isEmpty) {
                        ReplenishBox.mc.playerController.windowClick(l_Chest.inventorySlots.windowId, l_I, -999, ClickType.THROW, (EntityPlayer)ReplenishBox.mc.player);
                    }
                }
            }
            case stealer: {
                for (int l_I = 0; l_I < Objects.requireNonNull(inventory).getSizeInventory(); ++l_I) {
                    final ItemStack stack = inventory.getStackInSlot(l_I);
                    if (!stack.isEmpty) {
                        ReplenishBox.mc.playerController.windowClick(l_Chest.inventorySlots.windowId, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)ReplenishBox.mc.player);
                    }
                }
                break;
            }
        }
        if (!this.off.getValue()) {
            this.disable();
            return;
        }
        switch (this.breakMode.getValue()) {
            case LoverMine: {
                final InstantMine Instance2 = lover.moduleManager.getModuleByClass(InstantMine.class);
                if (Instance2.isOff()) {
                    Instance2.enable();
                    return;
                }
                if (!Instance2.isOn()) {
                    return;
                }
                if (InstantMine.breakPos != null && InstantMine.breakPos.equals((Object)this.blockpos)) {
                    return;
                }
                InstantMine.ondeve(this.blockpos);
                InstantMine.ondeve(this.blockpos);
                AutoCity.mc.playerController.onPlayerDamageBlock(this.blockpos, BlockUtil.getRayTraceFacing(this.blockpos));
                AutoCity.mc.playerController.onPlayerDamageBlock(this.blockpos, BlockUtil.getRayTraceFacing(this.blockpos));
                break;
            }
        }
        this.disable();
    }
    
    static {
        ReplenishBox.shulkers = Arrays.asList(Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);
    }
    
    enum Mode
    {
        stealer, 
        dropper;
    }
}
