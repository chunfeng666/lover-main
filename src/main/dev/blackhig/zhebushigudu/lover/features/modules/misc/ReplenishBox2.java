//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.misc;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import net.minecraft.block.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import java.util.concurrent.atomic.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.function.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import dev.blackhig.zhebushigudu.lover.features.command.*;
import com.mojang.realmsclient.gui.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.inventory.*;
import dev.blackhig.zhebushigudu.lover.mixin.mixins.*;
import net.minecraft.inventory.*;
import java.util.*;

public class ReplenishBox2 extends Module
{
    public static List<Block> shulkers;
    BlockPos blockpos;
    Setting<Mode> mode;
    Setting<Double> range;
    Setting<Boolean> place;
    Setting<Boolean> open;
    Setting<Boolean> take;
    Setting<Boolean> off;
    Setting<Boolean> mine;
    Setting<Boolean> smartStolen;
    Setting<Integer> smartCry;
    Setting<Integer> smartExp;
    Setting<Integer> smartTot;
    Setting<Integer> smartGap;
    Setting<Integer> smartEnc;
    List<BlockPos> placeables;
    int[] stealCountList;
    
    public ReplenishBox2() {
        super("ReplenishBox+", "Repbox", Category.MISC, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.stealer));
        this.range = (Setting<Double>)this.register(new Setting("Range", (T)5.5, (T)0.0, (T)6.0));
        this.place = (Setting<Boolean>)this.register(new Setting("Place", (T)true));
        this.open = (Setting<Boolean>)this.register(new Setting("Open", (T)true, v -> this.place.getValue()));
        this.take = (Setting<Boolean>)this.register(new Setting("Take", (T)false, v -> this.place.getValue() && this.open.getValue()));
        this.off = (Setting<Boolean>)this.register(new Setting("Close", (T)false, v -> this.place.getValue() && this.open.getValue() && this.take.getValue()));
        this.mine = (Setting<Boolean>)this.register(new Setting("MineSBox", (T)false, v -> this.place.getValue() && this.open.getValue()));
        this.smartStolen = (Setting<Boolean>)this.register(new Setting("SmartStolen", (T)false));
        this.smartCry = (Setting<Integer>)this.register(new Setting("SmartCry", (T)0, (T)0, (T)27));
        this.smartExp = (Setting<Integer>)this.register(new Setting("SmartExp", (T)0, (T)0, (T)27));
        this.smartTot = (Setting<Integer>)this.register(new Setting("SmartTotem", (T)0, (T)0, (T)27));
        this.smartGap = (Setting<Integer>)this.register(new Setting("SmartGap", (T)0, (T)0, (T)27));
        this.smartEnc = (Setting<Integer>)this.register(new Setting("SmartEndChest", (T)0, (T)0, (T)27));
        this.placeables = new ArrayList<BlockPos>();
        this.stealCountList = new int[] { 0, 0, 0, 0, 0 };
    }
    
    public int findShulk() {
        final AtomicInteger intt = new AtomicInteger(-1);
        final AtomicInteger atomicInteger;
        ReplenishBox2.shulkers.forEach(e -> {
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
        for (final EntityPlayer player : ReplenishBox2.mc.world.playerEntities) {
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
        final int[] invItemCount = { (int)Math.ceil(ReplenishBox2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum() / 64.0), (int)Math.ceil(ReplenishBox2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.EXPERIENCE_BOTTLE).mapToInt(ItemStack::getCount).sum() / 64.0), ReplenishBox2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum(), (int)Math.ceil(ReplenishBox2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.GOLDEN_APPLE).mapToInt(ItemStack::getCount).sum() / 64.0), (int)Math.ceil(ReplenishBox2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Item.getItemFromBlock(Blocks.ENDER_CHEST)).mapToInt(ItemStack::getCount).sum() / 64.0) };
        Command.sendMessage("Totem" + ReplenishBox2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum());
        Command.sendMessage("Endcry" + ReplenishBox2.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.END_CRYSTAL).mapToInt(ItemStack::getCount).sum());
        this.stealCountList[0] = this.smartCry.getValue() - invItemCount[0];
        this.stealCountList[1] = this.smartExp.getValue() - invItemCount[1];
        this.stealCountList[2] = this.smartTot.getValue() - invItemCount[2];
        this.stealCountList[3] = this.smartGap.getValue() - invItemCount[3];
        this.stealCountList[4] = this.smartEnc.getValue() - invItemCount[4];
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
        BlockUtil.rightClickBlock(this.blockpos, hitVec, EnumHand.MAIN_HAND, EnumFacing.UP, true);
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
                        if (!this.smartStolen.getValue()) {
                            ReplenishBox.mc.playerController.windowClick(l_Chest.inventorySlots.windowId, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)ReplenishBox.mc.player);
                        }
                        if (this.canSteal(((AccessorGuiShulkerBox)l_Chest).getInventory().getStackInSlot(l_I).getItem())) {
                            ReplenishBox.mc.playerController.windowClick(l_Chest.inventorySlots.windowId, l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)ReplenishBox.mc.player);
                        }
                    }
                }
                break;
            }
        }
        if (!this.off.getValue()) {
            this.disable();
            return;
        }
        if (this.mine.getValue() && this.blockpos != null) {
            ReplenishBox2.mc.playerController.onPlayerDamageBlock(this.blockpos, BlockUtil.getRayTraceFacing(this.blockpos));
        }
        this.disable();
    }
    
    private boolean canSteal(final Item i) {
        if (i.equals(Items.END_CRYSTAL) && this.stealCountList[0] > 0) {
            final int[] stealCountList = this.stealCountList;
            final int n = 0;
            --stealCountList[n];
            return true;
        }
        if (i.equals(Items.EXPERIENCE_BOTTLE) && this.stealCountList[1] > 0) {
            final int[] stealCountList2 = this.stealCountList;
            final int n2 = 1;
            --stealCountList2[n2];
            return true;
        }
        if (i.equals(Items.TOTEM_OF_UNDYING) && this.stealCountList[2] > 0) {
            final int[] stealCountList3 = this.stealCountList;
            final int n3 = 2;
            --stealCountList3[n3];
            return true;
        }
        if (i.equals(Items.GOLDEN_APPLE) && this.stealCountList[3] > 0) {
            final int[] stealCountList4 = this.stealCountList;
            final int n4 = 3;
            --stealCountList4[n4];
            return true;
        }
        if (i.equals(Item.getItemFromBlock(Blocks.ENDER_CHEST)) && this.stealCountList[4] > 0) {
            final int[] stealCountList5 = this.stealCountList;
            final int n5 = 4;
            --stealCountList5[n5];
            return true;
        }
        return false;
    }
    
    static {
        ReplenishBox2.shulkers = Arrays.asList(Blocks.BLACK_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);
    }
    
    enum Mode
    {
        stealer, 
        dropper;
    }
}
