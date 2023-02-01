//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.combat;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import java.awt.*;
import dev.blackhig.zhebushigudu.lover.util.render.*;
import net.minecraft.item.*;
import dev.blackhig.zhebushigudu.lover.features.command.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import dev.blackhig.zhebushigudu.lover.util.HoleFillPlus.*;
import dev.blackhig.zhebushigudu.lover.*;
import java.util.*;
import dev.blackhig.zhebushigudu.lover.util.SeijaUtil.*;
import net.minecraft.block.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.item.*;
import dev.blackhig.zhebushigudu.lover.util.*;

public class AutoHoleKick extends Module
{
    private final Setting<page> pageSetting;
    private final Setting<Integer> renderTime;
    private final Setting<Boolean> render;
    private final Setting<Integer> colorR;
    private final Setting<Integer> colorG;
    private final Setting<Integer> colorB;
    private final Setting<Integer> colorA;
    private final Setting<Double> renderSpeed;
    private final Setting<Boolean> renderText;
    private final Setting<Float> targetRange;
    private final Setting<Double> maxTargetSpeed;
    private final Setting<surCheckMode> surCheck;
    private final Setting<Boolean> burCheck;
    private final Setting<Bind> forcePlaceBind;
    private final Setting<Integer> minArmorPieces;
    private final Setting<Boolean> onlyPushOnGround;
    private final Setting<Double> targetMinHP;
    private final Setting<Integer> delay;
    private final Setting<Double> circulateDelay;
    private final Setting<FeetPlaceMode> feetPlace;
    private final Setting<Double> placeRange;
    private final Setting<Double> minRange;
    private final Setting<Boolean> noOutOfDistancePlace;
    private final Setting<Boolean> checkPlaceable;
    private final Setting<Boolean> farPlace;
    private final Setting<Boolean> noPlacePisOnBreakPos;
    private final Setting<Boolean> noPlaceRstOnBreakPos;
    private final Setting<Boolean> attackCry;
    private final Setting<Integer> attackRange;
    private final Setting<Integer> noSuicide;
    private final Setting<Boolean> disableOnNoBlock;
    private final Setting<Boolean> onGroundCheck;
    private final Setting<Integer> count;
    private final Setting<Boolean> speedCheck;
    private final Setting<Double> maxSpeed;
    private final Setting<Boolean> noPushSelf;
    private final Setting<Boolean> onUpdateMode;
    private final Setting<Double> cryRange;
    private final Setting<Integer> maxCount;
    private final Setting<Integer> balance;
    private final Setting<Integer> cryWeight;
    private final Setting<Boolean> raytrace;
    private final Setting<Boolean> strictRotate;
    private final Setting<Boolean> newRotate;
    private final Setting<Boolean> packetMine;
    private final Setting<Integer> packSwichCount;
    private final Setting<Double> mineDelay;
    private final Setting<Boolean> alwaysMine;
    private final Setting<Integer> advanceMine;
    private final Setting<Boolean> deBugMode;
    Timer mineTimer;
    Timer timer;
    PushInfo info;
    BlockPos piston;
    BlockPos rst;
    boolean pull;
    EnumFacing facing;
    int stage;
    int ct;
    int ct1;
    EntityPlayer target;
    private BlockPos renderPos;
    Timer dynamicRenderingTimer;
    double renderCount;
    Timer renderTimer;
    Timer circulateTimer;
    boolean canRender;
    int mineCount;
    static int rotate;
    public static boolean hasCry;
    public static boolean oldCry;
    public static int var;
    
    public AutoHoleKick() {
        super("AutoHoleKick", "by KijinSeija", Category.COMBAT, true, false, false);
        this.pageSetting = (Setting<page>)this.register(new Setting("page", (T)page.render));
        this.renderTime = (Setting<Integer>)this.register(new Setting("RenderTime", (T)200, (T)0, (T)1000, v -> this.pageSetting.getValue() == page.render));
        this.render = (Setting<Boolean>)this.register(new Setting("render", (T)true, v -> this.pageSetting.getValue() == page.render));
        this.colorR = (Setting<Integer>)this.register(new Setting("R", (T)232, (T)0, (T)255, v -> this.render.getValue() && this.pageSetting.getValue() == page.render));
        this.colorG = (Setting<Integer>)this.register(new Setting("G", (T)226, (T)0, (T)255, v -> this.render.getValue() && this.pageSetting.getValue() == page.render));
        this.colorB = (Setting<Integer>)this.register(new Setting("B", (T)2, (T)0, (T)255, v -> this.render.getValue() && this.pageSetting.getValue() == page.render));
        this.colorA = (Setting<Integer>)this.register(new Setting("Alpha", (T)100, (T)0, (T)255, v -> this.render.getValue() && this.pageSetting.getValue() == page.render));
        this.renderSpeed = (Setting<Double>)this.register(new Setting("RenderSpeed", (T)5.0, (T)0.0, (T)10.0, v -> this.pageSetting.getValue() == page.render));
        this.renderText = (Setting<Boolean>)this.register(new Setting("RenderText", (T)true, v -> this.pageSetting.getValue() == page.render));
        this.targetRange = (Setting<Float>)this.register(new Setting("TargetRange", (T)4.0f, (T)1.0f, (T)6.0f, v -> this.pageSetting.getValue() == page.target));
        this.maxTargetSpeed = (Setting<Double>)this.register(new Setting("MaxTargetSpeed", (T)4.0, (T)0.0, (T)15.0, v -> this.pageSetting.getValue() == page.target));
        this.surCheck = (Setting<surCheckMode>)this.register(new Setting("OnlyPushSurrounded", (T)surCheckMode.normal, v -> this.pageSetting.getValue() == page.target));
        this.burCheck = (Setting<Boolean>)this.register(new Setting("BurCheck", (T)true, v -> this.pageSetting.getValue() == page.target));
        this.forcePlaceBind = (Setting<Bind>)this.register(new Setting("ForcePlace", (T)new Bind(0), v -> this.pageSetting.getValue() == page.target && this.surCheck.getValue() != surCheckMode.off));
        this.minArmorPieces = (Setting<Integer>)this.register(new Setting("MinArmorPieces", (T)3, (T)0, (T)4, v -> this.pageSetting.getValue() == page.target));
        this.onlyPushOnGround = (Setting<Boolean>)this.register(new Setting("onlyPushOnGround", (T)true, v -> this.pageSetting.getValue() == page.target));
        this.targetMinHP = (Setting<Double>)this.register(new Setting("targetMinHP", (T)11.0, (T)0.0, (T)36.0, v -> this.pageSetting.getValue() == page.target));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)200, (T)0, (T)1000, v -> this.pageSetting.getValue() == page.place));
        this.circulateDelay = (Setting<Double>)this.register(new Setting("circulateDelay", (T)0.0, (T)0.0, (T)200.0, v -> this.pageSetting.getValue() == page.place));
        this.feetPlace = (Setting<FeetPlaceMode>)this.register(new Setting("FeetPlace", (T)FeetPlaceMode.Obsidian, v -> this.pageSetting.getValue() == page.place));
        this.placeRange = (Setting<Double>)this.register(new Setting("PlaceRange", (T)5.0, (T)0.0, (T)6.0, v -> this.pageSetting.getValue() == page.place));
        this.minRange = (Setting<Double>)this.register(new Setting("AntiStickRange", (T)2.6, (T)0.0, (T)4.0, v -> this.pageSetting.getValue() == page.place));
        this.noOutOfDistancePlace = (Setting<Boolean>)this.register(new Setting("NoOutOfDistancePlace", (T)true, v -> this.pageSetting.getValue() == page.place));
        this.checkPlaceable = (Setting<Boolean>)this.register(new Setting("CheckPlaceable", (T)false, v -> this.pageSetting.getValue() == page.place));
        this.farPlace = (Setting<Boolean>)this.register(new Setting("FarPlace", (T)false, v -> this.pageSetting.getValue() == page.place));
        this.noPlacePisOnBreakPos = (Setting<Boolean>)this.register(new Setting("NoPlacePisOnBreakPos", (T)true, v -> this.pageSetting.getValue() == page.place));
        this.noPlaceRstOnBreakPos = (Setting<Boolean>)this.register(new Setting("NoPlaceRstOnBreakPos", (T)true, v -> this.pageSetting.getValue() == page.place));
        this.attackCry = (Setting<Boolean>)this.register(new Setting("AttackCrystal", (T)true, v -> this.pageSetting.getValue() == page.breakCry));
        this.attackRange = (Setting<Integer>)this.register(new Setting("AttackCryRange", (T)5, (T)0, (T)7, v -> this.attackCry.getValue() && this.pageSetting.getValue() == page.breakCry));
        this.noSuicide = (Setting<Integer>)this.register(new Setting("NoSuicideHealth", (T)5, (T)0, (T)20, v -> this.attackCry.getValue() && this.pageSetting.getValue() == page.breakCry));
        this.disableOnNoBlock = (Setting<Boolean>)this.register(new Setting("DisableOnNoBlock", (T)true, v -> this.pageSetting.getValue() == page.selfCheck));
        this.onGroundCheck = (Setting<Boolean>)this.register(new Setting("OnGroundCheck", (T)true, v -> this.pageSetting.getValue() == page.selfCheck));
        this.count = (Setting<Integer>)this.register(new Setting("AntiStickCount", (T)20, (T)0, (T)200, v -> this.pageSetting.getValue() == page.selfCheck));
        this.speedCheck = (Setting<Boolean>)this.register(new Setting("SpeedCheck", (T)true, v -> this.pageSetting.getValue() == page.selfCheck));
        this.maxSpeed = (Setting<Double>)this.register(new Setting("SelfMaxSpeed", (T)4.0, (T)0.0, (T)20.0, v -> this.speedCheck.getValue() && this.pageSetting.getValue() == page.selfCheck));
        this.noPushSelf = (Setting<Boolean>)this.register(new Setting("NoPushSelf", (T)true, v -> this.pageSetting.getValue() == page.selfCheck));
        this.onUpdateMode = (Setting<Boolean>)this.register(new Setting("OnUpdateMode", (T)true, v -> this.pageSetting.getValue() == page.crySpeedCheck));
        this.cryRange = (Setting<Double>)this.register(new Setting("CryRange", (T)5.0, (T)0.0, (T)8.0, v -> this.pageSetting.getValue() == page.crySpeedCheck));
        this.maxCount = (Setting<Integer>)this.register(new Setting("MaxCount", (T)30, (T)2, (T)50, v -> this.pageSetting.getValue() == page.crySpeedCheck && this.cryRange.getValue() > this.cryRange.getMin()));
        this.balance = (Setting<Integer>)this.register(new Setting("Balance", (T)17, (T)2, (T)this.maxCount.getMax(), v -> this.pageSetting.getValue() == page.crySpeedCheck && this.cryRange.getValue() > this.cryRange.getMin()));
        this.cryWeight = (Setting<Integer>)this.register(new Setting("CryWeight", (T)5, (T)1, (T)10, v -> this.pageSetting.getValue() == page.crySpeedCheck && this.cryRange.getValue() > this.cryRange.getMin()));
        this.raytrace = (Setting<Boolean>)this.register(new Setting("rayTrace", (T)false, v -> this.pageSetting.getValue() == page.bypass));
        this.strictRotate = (Setting<Boolean>)this.register(new Setting("UselessMode", (T)false, v -> this.pageSetting.getValue() == page.bypass));
        this.newRotate = (Setting<Boolean>)this.register(new Setting("newRotate", (T)false, v -> this.pageSetting.getValue() == page.bypass && !this.strictRotate.getValue()));
        this.packetMine = (Setting<Boolean>)this.register(new Setting("PacketMine", (T)true, v -> this.pageSetting.getValue() == page.mine));
        this.packSwichCount = (Setting<Integer>)this.register(new Setting("packSwichCount", (T)5, (T)0, (T)20, v -> this.pageSetting.getValue() == page.mine && this.packetMine.getValue()));
        this.mineDelay = (Setting<Double>)this.register(new Setting("mineDelay", (T)20.0, (T)0.0, (T)400.0, v -> this.pageSetting.getValue() == page.mine));
        this.alwaysMine = (Setting<Boolean>)this.register(new Setting("AlwaysMine", (T)true, v -> this.pageSetting.getValue() == page.mine));
        this.advanceMine = (Setting<Integer>)this.register(new Setting("AdvanceMineOnStage", (T)2, (T)0, (T)3, v -> this.pageSetting.getValue() == page.mine && this.alwaysMine.getValue()));
        this.deBugMode = (Setting<Boolean>)this.register(new Setting("Debug", (T)false));
        this.mineTimer = new Timer();
        this.timer = new Timer();
        this.stage = 0;
        this.ct = 0;
        this.ct1 = 0;
        this.dynamicRenderingTimer = new Timer();
        this.renderTimer = new Timer();
        this.circulateTimer = new Timer();
        this.canRender = false;
        this.mineCount = 0;
    }
    
    @Override
    public void onEnable() {
        this.piston = null;
        this.rst = null;
        this.target = null;
        this.renderPos = null;
        this.stage = 0;
        this.ct = 0;
        this.ct1 = 0;
        this.circulateTimer.setMs(10000L);
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (this.piston != null) {
            this.renderPos = this.piston;
        }
        if (this.renderPos == null) {
            return;
        }
        if (this.canRender) {
            if (this.renderCount / 100.0 < 0.5 && this.dynamicRenderingTimer.passedDms(0.5)) {
                this.renderCount += this.renderSpeed.getValue();
                this.dynamicRenderingTimer.reset();
            }
            final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(this.renderPos.getX() + 0.5 - this.renderCount / 100.0, this.renderPos.getY() + 0.5 - this.renderCount / 100.0, this.renderPos.getZ() + 0.5 - this.renderCount / 100.0, this.renderPos.getX() + 0.5 + this.renderCount / 100.0, this.renderPos.getY() + 0.5 + this.renderCount / 100.0, this.renderPos.getZ() + 0.5 + this.renderCount / 100.0);
            if (this.render.getValue()) {
                RenderUtil.drawBBFill(axisAlignedBB, new Color(this.colorR.getValue(), this.colorG.getValue(), this.colorB.getValue()), this.colorA.getValue());
            }
            if (this.renderText.getValue()) {
                RenderUtil.drawText(this.renderPos, "PUSH!");
            }
        }
        else {
            this.renderCount = 0.0;
        }
    }
    
    @Override
    public void onTick() {
        if (!this.onUpdateMode.getValue()) {
            caCheck(this.cryRange.getValue(), 0, this.maxCount.getValue(), this.balance.getValue(), this.cryWeight.getValue(), false);
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.onUpdateMode.getValue()) {
            caCheck(this.cryRange.getValue(), 0, this.maxCount.getValue(), this.balance.getValue(), this.cryWeight.getValue(), false);
        }
        if (!this.circulateTimer.passedDms(this.circulateDelay.getValue())) {
            return;
        }
        if (this.renderTimer.passedDms(this.renderTime.getValue())) {
            this.canRender = false;
        }
        final int oldSlot = AutoHoleKick.mc.player.inventory.currentItem;
        final int obbySlot = InventoryUtil.getItemHotbar(Item.getItemFromBlock(Blocks.OBSIDIAN));
        int pisSlot = InventoryUtil.getItemHotbar(Item.getItemFromBlock((Block)Blocks.PISTON));
        if (pisSlot == -1) {
            pisSlot = InventoryUtil.getItemHotbar(Item.getItemFromBlock((Block)Blocks.STICKY_PISTON));
            if (pisSlot == -1) {
                if (this.disableOnNoBlock.getValue()) {
                    Command.sendMessage("NoPiston");
                    this.disable();
                }
                return;
            }
        }
        final int rstSlot = InventoryUtil.getItemHotbar(Item.getItemFromBlock(Blocks.REDSTONE_BLOCK));
        if (rstSlot == -1) {
            if (this.disableOnNoBlock.getValue()) {
                Command.sendMessage("NoRedStoneBlock");
                this.disable();
            }
            return;
        }
        if (this.stage == 0) {
            if (!this.timer.passedDms(this.delay.getValue())) {
                return;
            }
            final EntityPlayer target = this.getTarget(this.targetRange.getValue());
            this.target = target;
            if (this.getPistonPos(target, this.raytrace.getValue()) == null) {
                return;
            }
            this.info = this.getPistonPos(target, this.raytrace.getValue());
            if (this.info.nullCheck()) {
                return;
            }
            this.pull = this.info.pullMode;
            this.piston = this.info.pistonPos;
            this.facing = this.info.pisFac;
            this.rst = this.info.rstPos;
            this.ct = 0;
            this.ct1 = 0;
            ++this.stage;
        }
        if (this.stage == 1) {
            if (!this.timer.passedDms(this.delay.getValue())) {
                return;
            }
            this.timer.reset();
            if (this.attackCry.getValue()) {
                this.attackCrystal();
            }
            if (this.feetPlace.getValue().equals(FeetPlaceMode.RedStone) && AutoHoleKick.mc.world.getBlockState(this.piston.add(0, -1, 0)).getBlock().equals(Blocks.AIR) && isNoBBoxBlocked(this.piston.add(0, -1, 0))) {
                if (this.noOutOfDistancePlace.getValue() && Math.sqrt(AutoHoleKick.mc.player.getDistanceSq(this.piston.add(0, -1, 0))) > this.placeRange.getValue()) {
                    this.stage = 0;
                    return;
                }
                InventoryUtil.switchToHotbarSlot(rstSlot, false);
                BlockUtil.placeBlock(this.piston.add(0, -1, 0), EnumHand.MAIN_HAND, false, false, false);
                InventoryUtil.switchToHotbarSlot(oldSlot, false);
            }
            if (this.feetPlace.getValue().equals(FeetPlaceMode.Obsidian) && AutoHoleKick.mc.world.getBlockState(this.piston.add(0, -1, 0)).getBlock().equals(Blocks.AIR) && isNoBBoxBlocked(this.piston.add(0, -1, 0))) {
                if (obbySlot == -1) {
                    this.stage = 2;
                    return;
                }
                if (this.noOutOfDistancePlace.getValue() && Math.sqrt(AutoHoleKick.mc.player.getDistanceSq(this.piston.add(0, -1, 0))) > this.placeRange.getValue()) {
                    this.stage = 0;
                    return;
                }
                InventoryUtil.switchToHotbarSlot(obbySlot, false);
                BlockUtil.placeBlock(this.piston.add(0, -1, 0), EnumHand.MAIN_HAND, false, false);
                InventoryUtil.switchToHotbarSlot(oldSlot, false);
            }
            if (this.advanceMine.getValue() == 1 && this.alwaysMine.getValue()) {
                this.minePos(this.rst);
            }
            ++this.stage;
        }
        if (this.stage == 2) {
            if (!this.timer.passedDms(this.delay.getValue())) {
                return;
            }
            this.timer.reset();
            this.canRender = true;
            this.renderTimer.reset();
            if (AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.PISTON) || AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.STICKY_PISTON)) {
                ++this.stage;
                return;
            }
            if (!AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.AIR) && !AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.PISTON) && !AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.STICKY_PISTON)) {
                this.stage = 0;
                return;
            }
            if (this.noOutOfDistancePlace.getValue() && Math.sqrt(AutoHoleKick.mc.player.getDistanceSq(this.piston)) > this.placeRange.getValue()) {
                this.stage = 0;
                return;
            }
            if (!this.strictRotate.getValue() && !this.newRotate.getValue()) {
                if (this.info.pisFac == EnumFacing.EAST) {
                    if (!this.newRotate.getValue()) {
                        RotationUtil.faceYawAndPitch(90.0f, 0.0f);
                    }
                    else {
                        AutoHoleKick.mc.player.rotationYawHead = 90.0f;
                        AutoHoleKick.mc.player.renderYawOffset = 90.0f;
                    }
                }
                else if (this.info.pisFac == EnumFacing.WEST) {
                    if (!this.newRotate.getValue()) {
                        RotationUtil.faceYawAndPitch(-90.0f, 0.0f);
                    }
                    else {
                        AutoHoleKick.mc.player.rotationYawHead = -90.0f;
                        AutoHoleKick.mc.player.renderYawOffset = -90.0f;
                    }
                }
                else if (this.info.pisFac == EnumFacing.NORTH) {
                    if (!this.newRotate.getValue()) {
                        RotationUtil.faceYawAndPitch(0.0f, 0.0f);
                    }
                    else {
                        AutoHoleKick.mc.player.rotationYawHead = 0.0f;
                        AutoHoleKick.mc.player.renderYawOffset = 0.0f;
                    }
                }
                else if (this.info.pisFac == EnumFacing.SOUTH) {
                    if (!this.newRotate.getValue()) {
                        RotationUtil.faceYawAndPitch(180.0f, 0.0f);
                    }
                    else {
                        AutoHoleKick.mc.player.rotationYawHead = 180.0f;
                        AutoHoleKick.mc.player.renderYawOffset = 180.0f;
                    }
                }
            }
            InventoryUtil.switchToHotbarSlot(pisSlot, false);
            SeijaBlockUtil.placeBlock(this.piston, EnumHand.MAIN_HAND, false, false, EnumFacing.DOWN);
            InventoryUtil.switchToHotbarSlot(oldSlot, false);
            if ((AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.PISTON) || AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.STICKY_PISTON) || !this.checkPlaceable.getValue()) && isNoBBoxBlocked(this.piston)) {
                ++this.stage;
                if (this.advanceMine.getValue() == 2 && this.alwaysMine.getValue()) {
                    this.minePos(this.rst);
                }
            }
            else {
                ++this.ct1;
                if (this.ct1 > this.count.getValue()) {
                    this.stage = 0;
                }
            }
        }
        if (this.stage == 3) {
            if (SeijaBlockUtil.haveNeighborBlock(this.piston, Blocks.REDSTONE_BLOCK).size() > 0) {
                this.mineTimer.reset();
                this.stage = 4;
                return;
            }
            if (isNoBBoxBlocked(this.rst) && (!this.checkPlaceable.getValue() || AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.PISTON) || AutoHoleKick.mc.world.getBlockState(this.piston).getBlock().equals(Blocks.STICKY_PISTON))) {
                if (!AutoHoleKick.mc.world.getBlockState(this.rst).getBlock().equals(Blocks.AIR) && !AutoHoleKick.mc.world.getBlockState(this.rst).getBlock().equals(Blocks.REDSTONE_BLOCK)) {
                    this.stage = 0;
                    return;
                }
                InventoryUtil.switchToHotbarSlot(rstSlot, false);
                final Vec3d hitVec = new Vec3d((Vec3i)this.piston).add(0.5, 0.5, 0.5).add(new Vec3d(getRstFac(this.piston, this.rst).getOpposite().getDirectionVec()).scale(0.5));
                SeijaBlockUtil.sneak(this.piston);
                BlockUtil.rightClickBlock(this.piston, hitVec, EnumHand.MAIN_HAND, getRstFac(this.piston, this.rst).getOpposite(), true);
                InventoryUtil.switchToHotbarSlot(oldSlot, false);
                if (AutoHoleKick.mc.world.getBlockState(this.rst).getBlock().equals(Blocks.REDSTONE_BLOCK)) {
                    this.mineTimer.reset();
                    this.stage = 4;
                    if (this.advanceMine.getValue() == 3 && this.alwaysMine.getValue()) {
                        this.minePos(this.rst);
                    }
                }
                ++this.ct;
                if (this.ct > this.count.getValue()) {
                    this.stage = 0;
                }
            }
            else {
                this.stage = 0;
            }
        }
        if (this.stage == 4) {
            if (this.alwaysMine.getValue()) {
                this.minePos(this.rst);
            }
            if (!this.pull) {
                this.stage = 0;
                return;
            }
            if (!this.mineTimer.passedDms(this.mineDelay.getValue())) {
                return;
            }
            this.mineRst(this.target, this.piston);
            this.stage = 0;
        }
        this.circulateTimer.reset();
    }
    
    public void mineRst(final EntityPlayer target, final BlockPos piston) {
        if (AutoHoleKick.mc.world.getBlockState(SeijaBlockUtil.getFlooredPosition((Entity)target).add(0, 2, 0)).getBlock().equals(Blocks.AIR) && !AutoHoleKick.mc.world.getBlockState(piston.add(0, -1, 0)).getBlock().equals(Blocks.AIR) && SeijaBlockUtil.haveNeighborBlock(piston, Blocks.REDSTONE_BLOCK).size() == 1) {
            final BlockPos minePos = SeijaBlockUtil.haveNeighborBlock(piston, Blocks.REDSTONE_BLOCK).get(0);
            if (minePos != null && (InstantMine.breakPos == null || !InstantMine.breakPos.equals((Object)minePos))) {
                if (this.packetMine.getValue()) {
                    if (AutoHoleKick.mc.world.getBlockState(minePos).getBlock().equals(Blocks.REDSTONE_BLOCK)) {
                        ++this.mineCount;
                    }
                    else {
                        this.mineCount = 0;
                    }
                    if (this.mineCount >= this.packSwichCount.getValue()) {
                        final int oldslot = AutoHoleKick.mc.player.inventory.currentItem;
                        SeijaInvUtil.switchToItem(Items.DIAMOND_PICKAXE);
                        pMine(minePos);
                        InventoryUtil.switchToHotbarSlot(oldslot, false);
                        return;
                    }
                    pMine(minePos);
                }
                else {
                    AutoHoleKick.mc.playerController.onPlayerDamageBlock(minePos, BlockUtil.getRayTraceFacing(minePos));
                }
            }
        }
    }
    
    public void minePos(final BlockPos pos) {
        AutoHoleKick.mc.playerController.onPlayerDamageBlock(pos, BlockUtil.getRayTraceFacing(pos));
    }
    
    public static void pMine(final BlockPos minePos) {
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, minePos, BlockUtil.getRayTraceFacing(minePos)));
        InstantMine.mc.player.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, minePos, BlockUtil.getRayTraceFacing(minePos)));
    }
    
    public boolean isSur(final Entity player, final surCheckMode checkMode) {
        final BlockPos playerPos = SeijaBlockUtil.getFlooredPosition(player);
        if (checkMode == surCheckMode.test) {
            return ((!AutoHoleKick.mc.world.getBlockState(playerPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(playerPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR)) && (!AutoHoleKick.mc.world.getBlockState(playerPos.add(-1, 0, 0)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(playerPos.add(-1, 1, 0)).getBlock().equals(Blocks.AIR)) && (!AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR)) && (!AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 0, -1)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 0, -1)).getBlock().equals(Blocks.AIR))) || HoleUtil.isHole(playerPos, false, false).getType() != HoleUtil.HoleType.NONE;
        }
        if (checkMode == surCheckMode.normal && !AutoHoleKick.mc.world.getBlockState(playerPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) && !AutoHoleKick.mc.world.getBlockState(playerPos.add(-1, 0, 0)).getBlock().equals(Blocks.AIR) && !AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) && !AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 0, -1)).getBlock().equals(Blocks.AIR)) {
            return true;
        }
        if (checkMode == surCheckMode.center) {
            final double x = Math.abs(player.posX) - Math.floor(Math.abs(player.posX));
            final double z = Math.abs(player.posZ) - Math.floor(Math.abs(player.posZ));
            if (x <= 0.7 && x >= 0.3 && z <= 0.7 && z >= 0.3) {
                return true;
            }
        }
        if (checkMode == surCheckMode.smart) {
            return (!AutoHoleKick.mc.world.getBlockState(playerPos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(playerPos.add(1, 1, 0)).getBlock().equals(Blocks.AIR)) && (!AutoHoleKick.mc.world.getBlockState(playerPos.add(-1, 0, 0)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(playerPos.add(-1, 1, 0)).getBlock().equals(Blocks.AIR)) && (!AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 0, 1)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 1, 1)).getBlock().equals(Blocks.AIR)) && (!AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 0, -1)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 0, -1)).getBlock().equals(Blocks.AIR));
        }
        return checkMode == surCheckMode.off;
    }
    
    public boolean helpingBlockCheck(final BlockPos pos) {
        return !AutoHoleKick.mc.world.getBlockState(pos.add(1, 0, 0)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(pos.add(-1, 0, 0)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(pos.add(0, -1, 0)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(pos.add(0, 0, -1)).getBlock().equals(Blocks.AIR) || !AutoHoleKick.mc.world.getBlockState(pos.add(0, 0, 1)).getBlock().equals(Blocks.AIR);
    }
    
    private EntityPlayer getTarget(final double range) {
        if (!caCheck(this.cryRange.getValue(), 0, this.maxCount.getValue(), this.balance.getValue(), this.cryWeight.getValue(), true)) {
            if (this.deBugMode.getValue()) {
                Command.sendMessage("crystalIsFast" + AutoHoleKick.var);
            }
            return null;
        }
        if (this.onGroundCheck.getValue() && !AutoHoleKick.mc.player.onGround) {
            if (this.deBugMode.getValue()) {
                Command.sendMessage("You notOnGround");
            }
            return null;
        }
        EntityPlayer target = null;
        double distance = range;
        if (this.speedCheck.getValue() && lover.speedManager.getPlayerSpeed((EntityPlayer)AutoHoleKick.mc.player) > this.maxSpeed.getValue()) {
            if (this.deBugMode.getValue()) {
                Command.sendMessage("YouFast" + lover.speedManager.getPlayerSpeed((EntityPlayer)AutoHoleKick.mc.player));
            }
            return null;
        }
        for (final EntityPlayer player : AutoHoleKick.mc.world.playerEntities) {
            if (this.getPistonPos(player, this.raytrace.getValue()) == null) {
                continue;
            }
            final BlockPos pistonPos = this.getPistonPos(player, this.raytrace.getValue()).pistonPos;
            final BlockPos rstPos = this.getPistonPos(player, this.raytrace.getValue()).rstPos;
            if (EntityUtil.isntValid((Entity)player, range) || lover.friendManager.isFriend(player.getName()) || lover.speedManager.getPlayerSpeed(player) > this.maxTargetSpeed.getValue() || pistonPos == null) {
                continue;
            }
            if (rstPos == null) {
                continue;
            }
            if (TargetUtil.getArmorPieces(player) < this.minArmorPieces.getValue()) {
                if (!this.deBugMode.getValue()) {
                    continue;
                }
                Command.sendMessage("LowArmor" + player.getName() + TargetUtil.getArmorPieces(player));
            }
            else if (this.onlyPushOnGround.getValue() && !player.onGround) {
                if (!this.deBugMode.getValue()) {
                    continue;
                }
                Command.sendMessage("noGround" + player.getName());
            }
            else if (this.targetMinHP.getValue() > player.getHealth()) {
                if (!this.deBugMode.getValue()) {
                    continue;
                }
                Command.sendMessage("LowHp" + player.getName() + player.getHealth());
            }
            else {
                if (this.surCheck.getValue() != surCheckMode.off && !this.forcePlaceBind.getValue().isDown()) {
                    Boolean p = true;
                    if (!this.isSur((Entity)player, this.surCheck.getValue())) {
                        if (this.burCheck.getValue()) {
                            if (AutoHoleKick.mc.world.getBlockState(SeijaBlockUtil.getFlooredPosition((Entity)player)).getBlock().equals(Blocks.AIR)) {
                                if (this.deBugMode.getValue()) {
                                    Command.sendMessage("noBurAndSur" + player.getName());
                                    continue;
                                }
                                continue;
                            }
                            else {
                                p = false;
                            }
                        }
                        if (p) {
                            if (this.deBugMode.getValue()) {
                                Command.sendMessage("NOSur" + player.getName());
                                continue;
                            }
                            continue;
                        }
                    }
                }
                if (this.noPushSelf.getValue() && SeijaBlockUtil.getFlooredPosition((Entity)player).equals((Object)SeijaBlockUtil.getFlooredPosition((Entity)AutoHoleKick.mc.player))) {
                    if (!this.deBugMode.getValue()) {
                        continue;
                    }
                    Command.sendMessage("CanPushSelf" + player.getName());
                }
                else if ((AutoHoleKick.mc.player.posY - player.posY <= -1.0 || AutoHoleKick.mc.player.posY - player.posY >= 2.0) && SeijaDistanceUtil.distanceToXZ(pistonPos.getX() + 0.5, pistonPos.getZ() + 0.5) < this.minRange.getValue()) {
                    if (!this.deBugMode.getValue()) {
                        continue;
                    }
                    Command.sendMessage("Can't Place true Facing" + player.getName());
                }
                else if (target == null) {
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
    
    public void attackCrystal() {
        if (AutoHoleKick.mc.player.getHealth() < this.noSuicide.getValue()) {
            return;
        }
        final ArrayList<Entity> crystalList = new ArrayList<Entity>();
        for (final Entity entity : AutoHoleKick.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal) {
                crystalList.add(entity);
            }
        }
        if (crystalList.size() == 0) {
            return;
        }
        final HashMap<Entity, Double> distantMap = new HashMap<Entity, Double>();
        for (final Entity crystal : crystalList) {
            if (AutoHoleKick.mc.player.getDistance(crystal.posX, crystal.posY, crystal.posZ) < this.attackRange.getValue()) {
                distantMap.put(crystal, AutoHoleKick.mc.player.getDistance(crystal.posX, crystal.posY, crystal.posZ));
            }
        }
        final List<Map.Entry<Entity, Double>> list = new ArrayList<Map.Entry<Entity, Double>>(distantMap.entrySet());
        list.sort((Comparator<? super Map.Entry<Entity, Double>>)Map.Entry.comparingByValue());
        if (list.size() == 0) {
            return;
        }
        if (list.get(0).getValue() < 5.0) {
            EntityUtil.attackEntity(list.get(list.size() - 1).getKey(), true, true);
        }
    }
    
    public BlockPos getRSTPos2(final BlockPos pistonPos, final double range, final boolean rayTrace, final boolean instaMineCheck, final boolean helpBlockCheck) {
        if (pistonPos == null) {
            return null;
        }
        if (SeijaBlockUtil.haveNeighborBlock(pistonPos, Blocks.REDSTONE_BLOCK).size() > 0 && isNoBBoxBlocked(pistonPos)) {
            return SeijaBlockUtil.haveNeighborBlock(pistonPos, Blocks.REDSTONE_BLOCK).get(0);
        }
        final ArrayList<BlockPos> placePosList = new ArrayList<BlockPos>();
        placePosList.add(pistonPos.add(0, 1, 0));
        placePosList.add(pistonPos.add(-1, 0, 0));
        placePosList.add(pistonPos.add(1, 0, 0));
        placePosList.add(pistonPos.add(0, 0, -1));
        placePosList.add(pistonPos.add(0, 0, 1));
        final HashMap<BlockPos, Double> distantMap = new HashMap<BlockPos, Double>();
        for (final BlockPos rSTPos : placePosList) {
            if (AutoHoleKick.mc.world.getBlockState(rSTPos).getBlock().equals(Blocks.AIR) && isNoBBoxBlocked(rSTPos)) {
                if (Math.sqrt(AutoHoleKick.mc.player.getDistanceSq(rSTPos)) > range) {
                    continue;
                }
                if (rayTrace && !CombatUtil.rayTraceRangeCheck(rSTPos, 0.0, 0.0)) {
                    continue;
                }
                if (instaMineCheck && InstantMine.breakPos != null && InstantMine.breakPos.equals((Object)rSTPos)) {
                    continue;
                }
                if (helpBlockCheck && !this.helpingBlockCheck(rSTPos)) {
                    continue;
                }
                distantMap.put(rSTPos, AutoHoleKick.mc.player.getDistanceSq(rSTPos));
            }
        }
        final List<Map.Entry<BlockPos, Double>> list = new ArrayList<Map.Entry<BlockPos, Double>>(distantMap.entrySet());
        list.sort((Comparator<? super Map.Entry<BlockPos, Double>>)Map.Entry.comparingByValue());
        if (list.size() == 0) {
            return null;
        }
        return list.get(0).getKey();
    }
    
    public static boolean headCheck(final BlockPos playerPos) {
        return AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) && AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR);
    }
    
    public static boolean caCheck(final double checkRange, final int min, final int max, final int baseValue, final int weight, final boolean onlyGet) {
        if (onlyGet) {
            return AutoHoleKick.var <= baseValue;
        }
        if (min >= max || baseValue >= max || baseValue <= min) {
            return false;
        }
        final ArrayList<Entity> crystalList = new ArrayList<Entity>();
        for (final Entity entity : AutoHoleKick.mc.world.loadedEntityList) {
            if (entity instanceof EntityEnderCrystal && AutoHoleKick.mc.player.getDistance(entity.posX, entity.posY, entity.posZ) < checkRange) {
                crystalList.add(entity);
            }
        }
        if (crystalList.size() == 0) {
            AutoHoleKick.hasCry = false;
        }
        else {
            AutoHoleKick.hasCry = true;
        }
        if (AutoHoleKick.hasCry != AutoHoleKick.oldCry) {
            AutoHoleKick.oldCry = AutoHoleKick.hasCry;
            AutoHoleKick.var += weight;
        }
        else {
            --AutoHoleKick.var;
        }
        if (AutoHoleKick.var >= max) {
            AutoHoleKick.var = max;
        }
        if (AutoHoleKick.var <= min) {
            AutoHoleKick.var = min;
        }
        return AutoHoleKick.var <= baseValue;
    }
    
    public PushInfo getPistonPos(final EntityPlayer player, final boolean raytrace) {
        if (player == null || player.equals((Object)AutoHoleKick.mc.player)) {
            return null;
        }
        final BlockPos playerPos = SeijaBlockUtil.getFlooredPosition((Entity)player);
        if (AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 1, 0)).getBlock() instanceof BlockPistonExtension) {
            if (!AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR)) {
                return null;
            }
            final EnumFacing headFac = SeijaBlockUtil.getFacing(playerPos.add(0, 1, 0));
            BlockPos pisPos = null;
            switch (headFac) {
                case EAST: {
                    pisPos = playerPos.add(-1, 1, 0);
                    break;
                }
                case WEST: {
                    pisPos = playerPos.add(1, 1, 0);
                    break;
                }
                case NORTH: {
                    pisPos = playerPos.add(0, 1, 1);
                    break;
                }
                case SOUTH: {
                    pisPos = playerPos.add(0, 1, -1);
                    break;
                }
            }
            if (pisPos != null && AutoHoleKick.mc.world.getBlockState(pisPos).getBlock() instanceof BlockPistonBase) {
                final ArrayList l = SeijaBlockUtil.haveNeighborBlock(pisPos, Blocks.REDSTONE_BLOCK);
                if (l.size() == 1) {
                    final BlockPos rstPos = l.get(0);
                    if (raytrace && !CombatUtil.rayTraceRangeCheck(rstPos, 0.0, 0.0)) {
                        return null;
                    }
                    if (Math.sqrt(AutoHoleKick.mc.player.getDistanceSq(rstPos)) > 6.0) {
                        return null;
                    }
                    return new PushInfo(pisPos, rstPos, headFac, true);
                }
            }
            return null;
        }
        else {
            if (!AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 1, 0)).getBlock().equals(Blocks.AIR)) {
                return null;
            }
            final HashMap<PushInfo, Double> distantMap = new HashMap<PushInfo, Double>();
            for (int i = 0; i < 4; ++i) {
                int xOffSet = 0;
                int zOffSet = 0;
                EnumFacing Pisfac = EnumFacing.UP;
                if (i == 0) {
                    xOffSet = 1;
                    zOffSet = 0;
                    Pisfac = EnumFacing.WEST;
                }
                else if (i == 1) {
                    xOffSet = -1;
                    zOffSet = 0;
                    Pisfac = EnumFacing.EAST;
                }
                else if (i == 2) {
                    xOffSet = 0;
                    zOffSet = 1;
                    Pisfac = EnumFacing.NORTH;
                }
                else if (i == 3) {
                    xOffSet = 0;
                    zOffSet = -1;
                    Pisfac = EnumFacing.SOUTH;
                }
                if (this.fakeBBoxCheck(player, new Vec3d((double)(-xOffSet), 1.0, (double)(-zOffSet)), true) && !AutoHoleKick.mc.world.getBlockState(playerPos.add(-xOffSet, 0, -zOffSet)).getBlock().equals(Blocks.AIR)) {
                    final PushInfo pushInfo = new PushInfo(playerPos.add(xOffSet, 1, zOffSet), raytrace, this.noPlaceRstOnBreakPos.getValue(), Pisfac, false);
                    if (pushInfo.check()) {
                        distantMap.put(pushInfo, Math.sqrt(AutoHoleKick.mc.player.getDistanceSq(pushInfo.pistonPos)));
                    }
                }
                else if (!AutoHoleKick.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.AIR) && !AutoHoleKick.mc.world.getBlockState(playerPos).getBlock().equals(Blocks.WEB) && AutoHoleKick.mc.world.getBlockState(playerPos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) && AutoHoleKick.mc.world.getBlockState(playerPos.add(xOffSet, 2, zOffSet)).getBlock().equals(Blocks.AIR)) {
                    final PushInfo pushInfo = new PushInfo(playerPos.add(xOffSet, 1, zOffSet), raytrace, this.noPlaceRstOnBreakPos.getValue(), Pisfac, false);
                    if (pushInfo.check()) {
                        distantMap.put(pushInfo, Math.sqrt(AutoHoleKick.mc.player.getDistanceSq(pushInfo.pistonPos)));
                    }
                }
            }
            final List<Map.Entry<PushInfo, Double>> list = new ArrayList<Map.Entry<PushInfo, Double>>(distantMap.entrySet());
            list.sort((Comparator<? super Map.Entry<PushInfo, Double>>)Map.Entry.comparingByValue());
            int a = 0;
            if (this.farPlace.getValue()) {
                for (a = list.size() - 1; a >= 0; --a) {
                    if ((!this.noPlacePisOnBreakPos.getValue() || InstantMine.breakPos == null || !list.get(a).getKey().pistonPos.equals((Object)InstantMine.breakPos)) && list.get(a).getValue() < this.placeRange.getValue()) {
                        if (!raytrace) {
                            break;
                        }
                        if (CombatUtil.rayTraceRangeCheck(list.get(a).getKey().pistonPos, 0.0, 0.0)) {
                            break;
                        }
                    }
                }
            }
            else {
                for (a = 0; a < list.size(); ++a) {
                    if ((!this.noPlacePisOnBreakPos.getValue() || InstantMine.breakPos == null || !list.get(a).getKey().pistonPos.equals((Object)InstantMine.breakPos)) && list.get(a).getValue() < this.placeRange.getValue()) {
                        if (!raytrace) {
                            break;
                        }
                        if (CombatUtil.rayTraceRangeCheck(list.get(a).getKey().pistonPos, 0.0, 0.0)) {
                            break;
                        }
                    }
                }
            }
            if (a <= -1 || list.size() == 0 || list.size() <= a) {
                return null;
            }
            return list.get(a).getKey();
        }
    }
    
    public static EnumFacing getRstFac(final BlockPos pistonPos, final BlockPos rstPos) {
        for (final EnumFacing facing : EnumFacing.values()) {
            if (rstPos.offset(facing).equals((Object)pistonPos)) {
                return facing;
            }
        }
        return null;
    }
    
    public static boolean isNoBBoxBlocked(final BlockPos pos) {
        final AxisAlignedBB axisAlignedBB = new AxisAlignedBB(pos);
        final List<Entity> l = (List<Entity>)AutoHoleKick.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, axisAlignedBB);
        for (final Entity entity : l) {
            if (!(entity instanceof EntityEnderCrystal) && !(entity instanceof EntityItem) && !(entity instanceof EntityArrow) && !(entity instanceof EntityTippedArrow) && !(entity instanceof EntityArrow)) {
                if (entity instanceof EntityXPOrb) {
                    continue;
                }
                return false;
            }
        }
        return true;
    }
    
    public boolean fakeBBoxCheck(final EntityPlayer player, final Vec3d offset, final boolean headcheck) {
        final Vec3d actualPos = player.getPositionVector().add(offset);
        if (headcheck) {
            final Vec3d playerPos = player.getPositionVector();
            return this.isAir(actualPos.add(0.3, 0.0, 0.3)) && this.isAir(actualPos.add(-0.3, 0.0, 0.3)) && this.isAir(actualPos.add(0.3, 0.0, -0.3)) && this.isAir(actualPos.add(-0.3, 0.0, 0.3)) && this.isAir(actualPos.add(0.3, 1.8, 0.3)) && this.isAir(actualPos.add(-0.3, 1.8, 0.3)) && this.isAir(actualPos.add(0.3, 1.8, -0.3)) && this.isAir(actualPos.add(-0.3, 1.8, 0.3)) && this.isAir(playerPos.add(0.3, 2.8, 0.3)) && this.isAir(playerPos.add(-0.3, 2.8, 0.3)) && this.isAir(playerPos.add(-0.3, 2.8, -0.3)) && this.isAir(playerPos.add(0.3, 2.8, -0.3));
        }
        return this.isAir(actualPos.add(0.3, 0.0, 0.3)) && this.isAir(actualPos.add(-0.3, 0.0, 0.3)) && this.isAir(actualPos.add(0.3, 0.0, -0.3)) && this.isAir(actualPos.add(-0.3, 0.0, 0.3)) && this.isAir(actualPos.add(0.3, 1.8, 0.3)) && this.isAir(actualPos.add(-0.3, 1.8, 0.3)) && this.isAir(actualPos.add(0.3, 1.8, -0.3)) && this.isAir(actualPos.add(-0.3, 1.8, 0.3));
    }
    
    public boolean isAir(final Vec3d vec3d) {
        return AutoHoleKick.mc.world.getBlockState(this.vec3toBlockPos(vec3d, true)).getBlock().equals(Blocks.AIR);
    }
    
    public BlockPos vec3toBlockPos(final Vec3d vec3d, final boolean Yfloor) {
        if (Yfloor) {
            return new BlockPos(Math.floor(vec3d.x), Math.floor(vec3d.y), Math.floor(vec3d.z));
        }
        return new BlockPos(Math.floor(vec3d.x), (double)Math.round(vec3d.y), Math.floor(vec3d.z));
    }
    
    static {
        AutoHoleKick.hasCry = false;
        AutoHoleKick.oldCry = false;
        AutoHoleKick.var = 0;
    }
    
    public enum FeetPlaceMode
    {
        Obsidian, 
        RedStone;
    }
    
    private enum page
    {
        render, 
        target, 
        place, 
        breakCry, 
        selfCheck, 
        crySpeedCheck, 
        mine, 
        bypass;
    }
    
    private enum surCheckMode
    {
        off, 
        normal, 
        center, 
        smart, 
        test;
    }
    
    public class PushInfo
    {
        public BlockPos pistonPos;
        public BlockPos rstPos;
        public EnumFacing pisFac;
        public boolean pullMode;
        
        public boolean nullCheck() {
            return this.pistonPos == null || this.rstPos == null || this.pisFac == null;
        }
        
        public PushInfo(final BlockPos pistonPos, final BlockPos rstPos, final EnumFacing pisFac, final boolean pullMode) {
            this.pistonPos = pistonPos;
            this.rstPos = rstPos;
            this.pisFac = pisFac;
            this.pullMode = pullMode;
        }
        
        public PushInfo(final BlockPos pistonPos, final boolean rayTrace, final boolean instaMineC, final EnumFacing pisFac, final boolean pullMode) {
            this.pistonPos = pistonPos;
            this.rstPos = AutoHoleKick.this.getRSTPos2(pistonPos, AutoHoleKick.this.placeRange.getValue(), rayTrace, instaMineC, false);
            this.pisFac = pisFac;
            this.pullMode = pullMode;
        }
        
        public boolean check() {
            return this.rstPos != null && this.pistonPos != null && ((Util.mc.world.getBlockState(this.pistonPos).getBlock().equals(Blocks.AIR) || ((Util.mc.world.getBlockState(this.pistonPos).getBlock().equals(Blocks.PISTON) || Util.mc.world.getBlockState(this.pistonPos).getBlock().equals(Blocks.STICKY_PISTON)) && SeijaBlockUtil.isFacing(this.pistonPos, this.pisFac)) || (Util.mc.world.getBlockState(this.pistonPos).getBlock().equals(Blocks.STICKY_PISTON) && SeijaBlockUtil.isFacing(this.pistonPos, this.pisFac))) && SeijaBlockUtil.isNoBBoxBlocked(this.pistonPos, true) && SeijaBlockUtil.isNoBBoxBlocked(this.rstPos, true) && (Util.mc.world.getBlockState(this.rstPos).getBlock().equals(Blocks.AIR) || Util.mc.world.getBlockState(this.rstPos).getBlock().equals(Blocks.REDSTONE_BLOCK)));
        }
    }
}
