//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.misc;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.entity.item.*;
import java.util.stream.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import dev.blackhig.zhebushigudu.lover.features.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.block.state.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.block.material.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import dev.blackhig.zhebushigudu.lover.features.modules.combat.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraftforge.client.event.*;
import net.minecraft.util.math.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import java.util.*;

public class InstantMine extends Module
{
    public static BlockRenderSmooth blockRenderSmooth;
    public static FadeUtils fadeBlockSize;
    public static FadeUtils pos2FadeBlockSize;
    Timer timer;
    Setting<Boolean> haste;
    Setting<Boolean> ghostHand;
    Setting<Boolean> fuck;
    public static Setting<Boolean> doubleBreak;
    Setting<Boolean> crystal;
    Setting<Boolean> attackcrystal;
    Setting<Bind> bind;
    Setting<Float> range;
    Setting<Boolean> instant;
    Setting<Boolean> render;
    Setting<Boolean> boxRender;
    Setting<Integer> boxAlpha;
    Setting<Integer> red;
    Setting<Integer> green;
    Setting<Integer> blue;
    Setting<Integer> alpha;
    Setting<RenderMode> renderMode;
    Setting<Boolean> render2;
    Setting<Boolean> render3;
    Setting<Boolean> boxRender2;
    Setting<Integer> boxAlpha2;
    Setting<Integer> red2;
    Setting<Integer> green2;
    Setting<Integer> blue2;
    Setting<Integer> alpha2;
    Setting<RenderMode> renderMode2;
    public static final List<Block> godBlocks;
    private static boolean cancelStart;
    private static boolean empty;
    private static EnumFacing facing;
    public static BlockPos breakPos;
    public static BlockPos breakPos2;
    private static final Timer breakSuccess;
    public static int tickCount;
    public static int tickCount2;
    public static double time;
    public static double time2;
    private static InstantMine INSTANCE;
    private Block block;
    
    public InstantMine() {
        super("LoverMine", "breaking blocks", Category.MISC, true, false, false);
        this.timer = new Timer();
        this.haste = (Setting<Boolean>)this.register(new Setting("Haste", (T)true));
        this.range = (Setting<Float>)this.register(new Setting("Range", (T)256.0f, (T)1.0f, (T)256.0f));
        this.ghostHand = (Setting<Boolean>)this.register(new Setting("GhostHand", (T)true));
        this.fuck = (Setting<Boolean>)this.register(new Setting("Super Ghost hand", (T)false));
        InstantMine.doubleBreak = (Setting<Boolean>)this.register(new Setting("DoubleBreak", (T)false));
        this.crystal = (Setting<Boolean>)this.register(new Setting("Crystal", (T)false));
        this.attackcrystal = (Setting<Boolean>)this.register(new Setting("Attack Crystal", (T)false, v -> this.crystal.getValue()));
        this.bind = (Setting<Bind>)this.register(new Setting("ObsidianBind", (T)new Bind(-1), v -> this.crystal.getValue()));
        this.instant = (Setting<Boolean>)this.register(new Setting("Instant", (T)true));
        this.render = (Setting<Boolean>)this.register(new Setting("Render", (T)true));
        this.render2 = (Setting<Boolean>)this.register(new Setting("FirstRender", (T)true, v -> this.render.getValue()));
        this.boxRender = (Setting<Boolean>)this.register(new Setting("FirstBox", (T)true, v -> this.render.getValue()));
        this.boxAlpha = (Setting<Integer>)this.register(new Setting("FirstBoxAlpha", (T)85, (T)0, (T)255, v -> this.boxRender.getValue() && this.render.getValue()));
        this.red = (Setting<Integer>)this.register(new Setting("FirstRed", (T)255, (T)0, (T)255, v -> this.render.getValue()));
        this.green = (Setting<Integer>)this.register(new Setting("FirstGreen", (T)255, (T)0, (T)255, v -> this.render.getValue()));
        this.blue = (Setting<Integer>)this.register(new Setting("FirstBlue", (T)255, (T)0, (T)255, v -> this.render.getValue()));
        this.alpha = (Setting<Integer>)this.register(new Setting("FirstAlpha", (T)60, (T)0, (T)255, v -> this.render.getValue()));
        this.renderMode = (Setting<RenderMode>)this.register(new Setting("FirstRenderMode", (T)RenderMode.Outline));
        this.render3 = (Setting<Boolean>)this.register(new Setting("SecondRender", (T)true, v -> this.render.getValue()));
        this.boxRender2 = (Setting<Boolean>)this.register(new Setting("SecondBox", (T)true, v -> this.render.getValue()));
        this.boxAlpha2 = (Setting<Integer>)this.register(new Setting("SecondBoxAlpha", (T)85, (T)0, (T)255, v -> this.render.getValue()));
        this.red2 = (Setting<Integer>)this.register(new Setting("SecondRed", (T)255, (T)0, (T)255, v -> this.render.getValue()));
        this.green2 = (Setting<Integer>)this.register(new Setting("SecondGreen", (T)255, (T)0, (T)255, v -> this.render.getValue()));
        this.blue2 = (Setting<Integer>)this.register(new Setting("SecondBlue", (T)255, (T)0, (T)255, v -> this.render.getValue()));
        this.alpha2 = (Setting<Integer>)this.register(new Setting("SecondAlpha", (T)60, (T)0, (T)255, v -> this.render.getValue()));
    }
    
    public static InstantMine getInstance() {
        if (InstantMine.INSTANCE == null) {
            InstantMine.INSTANCE = new InstantMine();
        }
        return InstantMine.INSTANCE;
    }
    
    private void setInstance() {
        InstantMine.INSTANCE = this;
    }
    
    public static void attackcrystal() {
        for (final Entity crystal : (List)InstantMine.mc.world.loadedEntityList.stream().filter(e -> e instanceof EntityEnderCrystal && !e.isDead).sorted(Comparator.comparing(e -> InstantMine.mc.player.getDistance(e))).collect(Collectors.toList())) {
            if (crystal instanceof EntityEnderCrystal && crystal.getDistanceSq(InstantMine.breakPos) <= 2.0) {
                InstantMine.mc.player.connection.sendPacket((Packet)new CPacketUseEntity(crystal));
                InstantMine.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.OFF_HAND));
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (Feature.fullNullCheck()) {
            rend();
            return;
        }
        if (InstantMine.breakPos != null && InstantMine.mc.player != null && InstantMine.mc.player.getDistanceSq(InstantMine.breakPos) > MathUtil.square(this.range.getValue())) {
            InstantMine.breakPos = null;
            InstantMine.breakPos2 = null;
            InstantMine.cancelStart = false;
            return;
        }
        if (!this.isEnabled()) {
            rend();
            return;
        }
        if (!(event.getPacket() instanceof CPacketPlayerDigging)) {
            return;
        }
        final CPacketPlayerDigging packet = (CPacketPlayerDigging)event.getPacket();
        if (packet.getAction() != CPacketPlayerDigging.Action.START_DESTROY_BLOCK) {
            return;
        }
        event.setCanceled(InstantMine.cancelStart);
    }
    
    public int getBestAvailableToolSlot(final IBlockState blockState) {
        int toolSlot = -1;
        double max = 0.0;
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InstantMine.mc.player.inventory.getStackInSlot(i);
            float speed;
            final int eff;
            if (!stack.isEmpty && (speed = stack.getDestroySpeed(blockState)) > 1.0f && (speed += (float)(((eff = EnchantmentHelper.getEnchantmentLevel(Enchantments.EFFICIENCY, stack)) > 0) ? (Math.pow(eff, 2.0) + 1.0) : 0.0)) > max) {
                max = speed;
                toolSlot = i;
            }
        }
        return toolSlot;
    }
    
    public static void ondeve(final BlockPos pos) {
        if (Feature.fullNullCheck()) {
            return;
        }
        if (!BlockUtil.canBreak(pos)) {
            return;
        }
        if (InstantMine.breakPos != null && InstantMine.breakPos.equals((Object)pos)) {
            return;
        }
        rend();
        InstantMine.blockRenderSmooth.setNewPos(pos);
        InstantMine.fadeBlockSize.reset();
        InstantMine.empty = false;
        InstantMine.cancelStart = false;
        InstantMine.breakPos = pos;
        InstantMine.tickCount = 0;
        InstantMine.breakSuccess.reset();
        InstantMine.facing = EnumFacing.UP;
        if (InstantMine.breakPos == null) {
            return;
        }
        InstantMine.mc.player.swingArm(EnumHand.MAIN_HAND);
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
        InstantMine.cancelStart = true;
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
    }
    
    public static void ondeve2(final BlockPos pos) {
        if (Feature.fullNullCheck()) {
            return;
        }
        if (!BlockUtil.canBreak(pos)) {
            return;
        }
        if (InstantMine.breakPos != null && InstantMine.breakPos.equals((Object)pos)) {
            return;
        }
        rend();
        InstantMine.blockRenderSmooth.setNewPos(pos);
        InstantMine.pos2FadeBlockSize.reset();
        InstantMine.empty = false;
        InstantMine.cancelStart = false;
        InstantMine.breakPos2 = pos;
        InstantMine.tickCount2 = 0;
        InstantMine.breakSuccess.reset();
        InstantMine.facing = EnumFacing.UP;
        if (InstantMine.breakPos == null) {
            return;
        }
        InstantMine.mc.player.swingArm(EnumHand.MAIN_HAND);
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
        InstantMine.cancelStart = true;
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
    }
    
    @SubscribeEvent
    public void onBlockEvent(final BlockEvent event) {
        if (Feature.fullNullCheck()) {
            rend();
            return;
        }
        if (!this.isEnabled()) {
            return;
        }
        if (!BlockUtil.canBreak(event.pos)) {
            return;
        }
        if (InstantMine.breakPos != null && InstantMine.breakPos.equals((Object)event.pos)) {
            return;
        }
        if (InstantMine.mc.world.getBlockState(new BlockPos(InstantMine.blockRenderSmooth.getRenderPos())).getBlock().material != Material.AIR) {
            InstantMine.pos2FadeBlockSize.reset();
        }
        InstantMine.blockRenderSmooth.setNewPos(event.pos);
        InstantMine.fadeBlockSize.reset();
        InstantMine.empty = false;
        InstantMine.cancelStart = false;
        InstantMine.breakPos2 = InstantMine.breakPos;
        InstantMine.breakPos = event.pos;
        InstantMine.tickCount2 = InstantMine.tickCount;
        InstantMine.tickCount = 0;
        InstantMine.breakSuccess.reset();
        InstantMine.facing = event.facing;
        if (InstantMine.breakPos == null) {
            return;
        }
        InstantMine.mc.player.swingArm(EnumHand.MAIN_HAND);
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
        InstantMine.cancelStart = true;
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
        event.setCanceled(true);
    }
    
    public Block whatBlock(final BlockPos pos) {
        return InstantMine.mc.world.getBlockState(pos).getBlock();
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (Feature.fullNullCheck()) {
            rend();
            return;
        }
        if (!InstantMine.cancelStart) {
            return;
        }
        if ((InstantMine.breakPos != null || (this.instant.getValue() && InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.AIR)) && InstantMine.mc.player != null && InstantMine.mc.player.getDistanceSq(InstantMine.breakPos) > MathUtil.square(this.range.getValue())) {
            InstantMine.breakPos = null;
            InstantMine.breakPos2 = null;
            InstantMine.cancelStart = false;
            return;
        }
        if (InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1) {
            return;
        }
        if (!this.fuck.getValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1) {
            return;
        }
        if (InstantMine.doubleBreak.getValue() && InstantMine.breakPos2 != null) {
            final int slotMains = InstantMine.mc.player.inventory.currentItem;
            if (InstantMine.mc.world.getBlockState(InstantMine.breakPos2).getBlock() != Blocks.AIR && InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) != -1) {
                if (InstantMine.mc.world.getBlockState(InstantMine.breakPos2).getBlock() == Blocks.OBSIDIAN && !InstantMine.breakSuccess.passedMs(1234L)) {
                    return;
                }
                InstantMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE)));
                InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos2, InstantMine.facing));
            }
            if (InstantMine.mc.world.getBlockState(InstantMine.breakPos2).getBlock() == Blocks.AIR) {
                InstantMine.breakPos2 = null;
                InstantMine.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(slotMains));
            }
        }
        if (InstantMine.godBlocks.contains(InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock())) {
            return;
        }
        if (!this.ghostHand.getValue() || (!this.fuck.getValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1) || InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1) {
            InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
            if (InstantMine.doubleBreak.getValue()) {
                InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos2, InstantMine.facing));
            }
            return;
        }
        final int slotMain = InstantMine.mc.player.inventory.currentItem;
        if (InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.OBSIDIAN) {
            if (!InstantMine.breakSuccess.passedMs(1234L)) {
                return;
            }
            if (this.fuck.getValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1) {
                for (int i = 9; i < 36; ++i) {
                    if (InstantMine.mc.player.inventory.getStackInSlot(i).getItem() == Items.DIAMOND_PICKAXE) {
                        InstantMine.mc.playerController.windowClick(InstantMine.mc.player.inventoryContainer.windowId, i, InstantMine.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)InstantMine.mc.player);
                        InstantMine.mc.playerController.updateController();
                        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
                        InstantMine.mc.playerController.windowClick(InstantMine.mc.player.inventoryContainer.windowId, i, InstantMine.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)InstantMine.mc.player);
                        InstantMine.mc.playerController.updateController();
                        return;
                    }
                }
                return;
            }
            InstantMine.mc.player.inventory.currentItem = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
            InstantMine.mc.playerController.updateController();
            InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
            InstantMine.mc.player.inventory.currentItem = slotMain;
            InstantMine.mc.playerController.updateController();
        }
        else {
            if (this.fuck.getValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1) {
                for (int i = 9; i < 35; ++i) {
                    if (InstantMine.mc.player.inventory.getStackInSlot(i).getItem() == Items.DIAMOND_PICKAXE) {
                        InstantMine.mc.playerController.windowClick(InstantMine.mc.player.inventoryContainer.windowId, i, InstantMine.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)InstantMine.mc.player);
                        InstantMine.mc.playerController.updateController();
                        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
                        InstantMine.mc.playerController.windowClick(InstantMine.mc.player.inventoryContainer.windowId, i, InstantMine.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)InstantMine.mc.player);
                        InstantMine.mc.playerController.updateController();
                        return;
                    }
                }
                return;
            }
            InstantMine.mc.player.inventory.currentItem = InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE);
            InstantMine.mc.playerController.updateController();
            InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
            InstantMine.mc.player.inventory.currentItem = slotMain;
            InstantMine.mc.playerController.updateController();
        }
    }
    
    public boolean check() {
        return InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY + 2.0, InstantMine.mc.player.posZ)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY + 3.0, InstantMine.mc.player.posZ)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY - 1.0, InstantMine.mc.player.posZ)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX + 1.0, InstantMine.mc.player.posY, InstantMine.mc.player.posZ)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX - 1.0, InstantMine.mc.player.posY, InstantMine.mc.player.posZ)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY, InstantMine.mc.player.posZ + 1.0)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY, InstantMine.mc.player.posZ - 1.0)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX + 1.0, InstantMine.mc.player.posY + 1.0, InstantMine.mc.player.posZ)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX - 1.0, InstantMine.mc.player.posY + 1.0, InstantMine.mc.player.posZ)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY + 1.0, InstantMine.mc.player.posZ + 1.0)) || InstantMine.breakPos.equals((Object)new BlockPos(InstantMine.mc.player.posX, InstantMine.mc.player.posY + 1.0, InstantMine.mc.player.posZ - 1.0));
    }
    
    @Override
    public void onTick() {
        if (fullNullCheck()) {
            return;
        }
        if (InstantMine.mc.player.capabilities.isCreativeMode) {
            return;
        }
        if (!InstantMine.cancelStart) {
            return;
        }
        if (this.crystal.getValue() && this.attackcrystal.getValue() && InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.AIR) {
            attackcrystal();
        }
        if (this.bind.getValue().isDown() && this.crystal.getValue() && InventoryUtil.findHotbarBlock(BlockObsidian.class) != -1 && InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.AIR) {
            final int obbySlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
            final int old = InstantMine.mc.player.inventory.currentItem;
            this.switchToSlot(obbySlot);
            BlockUtil.placeBlock(InstantMine.breakPos, EnumHand.MAIN_HAND, false, true, false);
            this.switchToSlot(old);
        }
        if (InstantMine.breakPos != null && InstantMine.mc.player != null && InstantMine.mc.player.getDistanceSq(InstantMine.breakPos) > MathUtil.square(this.range.getValue())) {
            InstantMine.breakPos = null;
            InstantMine.breakPos2 = null;
            InstantMine.cancelStart = false;
            return;
        }
        if (InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.AIR && !this.instant.getValue()) {
            InstantMine.breakPos = null;
            InstantMine.breakPos2 = null;
            InstantMine.cancelStart = false;
            return;
        }
        if (InstantMine.godBlocks.contains(InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock())) {
            return;
        }
        if (InventoryUtil.getItemHotbar(Items.END_CRYSTAL) != -1 && this.crystal.getValue() && InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock() == Blocks.OBSIDIAN && !this.check() && !InstantMine.breakPos.equals((Object)AntiBurrow.pos)) {
            BlockUtil.placeCrystalOnBlock(InstantMine.breakPos, EnumHand.MAIN_HAND, true, false, true);
        }
        if (this.ghostHand.getValue() || (this.ghostHand.getValue() && this.fuck.getValue())) {
            final float breakTime = InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlockHardness((World)InstantMine.mc.world, InstantMine.breakPos);
            final int slotMain = InstantMine.mc.player.inventory.currentItem;
            if (!InstantMine.breakSuccess.passedMs((int)breakTime)) {
                return;
            }
            if (this.fuck.getValue() && InventoryUtil.getItemHotbar(Items.DIAMOND_PICKAXE) == -1) {
                for (int i = 9; i < 36; ++i) {
                    if (InstantMine.mc.player.inventory.getStackInSlot(i).getItem() == Items.DIAMOND_PICKAXE) {
                        InstantMine.mc.playerController.windowClick(InstantMine.mc.player.inventoryContainer.windowId, i, InstantMine.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)InstantMine.mc.player);
                        InstantMine.mc.playerController.updateController();
                        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
                        InstantMine.mc.playerController.windowClick(InstantMine.mc.player.inventoryContainer.windowId, i, InstantMine.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)InstantMine.mc.player);
                        InstantMine.mc.playerController.updateController();
                        return;
                    }
                }
            }
            try {
                this.block = InstantMine.mc.world.getBlockState(InstantMine.breakPos).getBlock();
            }
            catch (Exception ex) {}
            final int toolSlot = this.getBestAvailableToolSlot(this.block.getBlockState().getBaseState());
            if (InstantMine.mc.player.inventory.currentItem != toolSlot && toolSlot != -1) {
                InstantMine.mc.player.inventory.currentItem = toolSlot;
                InstantMine.mc.playerController.updateController();
                InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
                InstantMine.mc.player.inventory.currentItem = slotMain;
                InstantMine.mc.playerController.updateController();
                return;
            }
        }
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, InstantMine.breakPos, InstantMine.facing));
        ++InstantMine.tickCount;
        ++InstantMine.tickCount2;
    }
    
    private static void rend() {
        InstantMine.time = 0.0;
        InstantMine.time2 = 0.0;
        InstantMine.empty = false;
        InstantMine.cancelStart = false;
        InstantMine.breakPos = null;
    }
    
    @Override
    public void onDisable() {
        if (Feature.fullNullCheck()) {
            return;
        }
        rend();
        InstantMine.fadeBlockSize.reset();
        if (this.haste.getValue()) {
            InstantMine.mc.player.removePotionEffect(MobEffects.HASTE);
        }
    }
    
    @Override
    public void onEnable() {
        if (Feature.fullNullCheck()) {
            rend();
        }
        InstantMine.fadeBlockSize.reset();
    }
    
    private double normalize(final double value, final double max, final double min) {
        return 0.5 * ((value - min) / (max - min)) + 0.5;
    }
    
    @SubscribeEvent
    public void onRenderWorld(final RenderWorldLastEvent event) {
        if (fullNullCheck()) {
            rend();
            return;
        }
        if (!this.isEnabled()) {
            rend();
            return;
        }
        if (!this.render.getValue()) {
            return;
        }
        if (this.render.getValue() && InstantMine.breakPos != null && InstantMine.cancelStart && this.render2.getValue()) {
            final Vec3d interpolateEntity = MathUtil.interpolateEntity((Entity)InstantMine.mc.player, InstantMine.mc.getRenderPartialTicks());
            AxisAlignedBB pos = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(InstantMine.blockRenderSmooth.getRenderPos());
            pos = pos.grow(0.0020000000949949026).offset(-interpolateEntity.x, -interpolateEntity.y, -interpolateEntity.z);
            this.renderESP1(pos, (float)InstantMine.fadeBlockSize.easeOutQuad());
        }
        if (InstantMine.breakPos2 != null && this.render3.getValue()) {
            final Vec3d interpolateEntity = MathUtil.interpolateEntity((Entity)InstantMine.mc.player, InstantMine.mc.getRenderPartialTicks());
            AxisAlignedBB pos = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(InstantMine.breakPos2);
            pos = pos.grow(0.0020000000949949026).offset(-interpolateEntity.x, -interpolateEntity.y, -interpolateEntity.z);
            this.renderESP2(pos, (float)InstantMine.pos2FadeBlockSize.easeOutQuad());
        }
    }
    
    public void renderESP1(final AxisAlignedBB axisAlignedBB, final float size) {
        final double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0;
        final double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0;
        final double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0;
        final double full = axisAlignedBB.maxX - centerX;
        final double progressValX = full * size;
        final double progressValY = full * size;
        final double progressValZ = full * size;
        final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
        if (axisAlignedBB2 != null) {
            RenderUtils3D.drawBoxTest((float)axisAlignedBB2.minX, (float)axisAlignedBB2.minY, (float)axisAlignedBB2.minZ, (float)axisAlignedBB2.maxX - (float)axisAlignedBB2.minX, (float)axisAlignedBB2.maxY - (float)axisAlignedBB2.minY, (float)axisAlignedBB2.maxZ - (float)axisAlignedBB2.minZ, this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue(), 63);
        }
    }
    
    public void renderESP2(final AxisAlignedBB axisAlignedBB, final float size) {
        final double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0;
        final double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0;
        final double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0;
        final double full = axisAlignedBB.maxX - centerX;
        final double progressValX = full * size;
        final double progressValY = full * size;
        final double progressValZ = full * size;
        final AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
        if (axisAlignedBB2 != null) {
            RenderUtils3D.drawBoxTest((float)axisAlignedBB2.minX, (float)axisAlignedBB2.minY, (float)axisAlignedBB2.minZ, (float)axisAlignedBB2.maxX - (float)axisAlignedBB2.minX, (float)axisAlignedBB2.maxY - (float)axisAlignedBB2.minY, (float)axisAlignedBB2.maxZ - (float)axisAlignedBB2.minZ, this.red2.getValue(), this.green2.getValue(), this.blue2.getValue(), this.alpha2.getValue(), 63);
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return "Instant";
    }
    
    private void switchToSlot(final int slot) {
        InstantMine.mc.player.inventory.currentItem = slot;
        InstantMine.mc.playerController.updateController();
    }
    
    static {
        godBlocks = Arrays.asList(Blocks.AIR, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.WATER, Blocks.BEDROCK);
        InstantMine.INSTANCE = new InstantMine();
        InstantMine.blockRenderSmooth = new BlockRenderSmooth(new BlockPos(0, 0, 0), 500L);
        InstantMine.fadeBlockSize = new FadeUtils(2000L);
        InstantMine.pos2FadeBlockSize = new FadeUtils(2000L);
        InstantMine.cancelStart = false;
        InstantMine.empty = false;
        breakSuccess = new Timer();
    }
    
    public enum RenderMode
    {
        Fill, 
        Outline, 
        Both;
    }
}
