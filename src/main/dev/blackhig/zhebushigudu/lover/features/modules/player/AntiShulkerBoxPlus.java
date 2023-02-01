//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.player;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import dev.blackhig.zhebushigudu.lover.util.e.*;
import dev.blackhig.zhebushigudu.lover.mixin.mixins.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;
import dev.blackhig.zhebushigudu.lover.features.modules.misc.*;
import dev.blackhig.zhebushigudu.lover.features.modules.combat.*;
import net.minecraft.item.*;

public class AntiShulkerBoxPlus extends Module
{
    Setting<Integer> range;
    Setting<takeType> type;
    Setting<Integer> delay;
    Setting<Enums.breakMode> breakMode;
    Timer takeTimer;
    int l_I;
    boolean first;
    BlockPos pos;
    
    public AntiShulkerBoxPlus() {
        super("AntiShulk+", "ex,steal enemy's box item", Module.Category.PLAYER, true, false, false);
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)5, (T)0, (T)6));
        this.type = (Setting<takeType>)this.register(new Setting("TakeType", (T)takeType.dropper));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)0, (T)0, (T)1000));
        this.breakMode = (Setting<Enums.breakMode>)this.register(new Setting("BoxBreakMode", (T)Enums.breakMode.LoverMine));
        this.takeTimer = new Timer();
        this.l_I = 0;
        this.first = false;
        this.pos = null;
    }
    
    public void onTick() {
        if (lover.moduleManager.getModuleByClass(ReplenishBox.class).isEnabled()) {
            return;
        }
        if (this.pos == null) {
            this.pos = BlockUtil.getSphere(AntiShulkerBoxPlus.mc.player.getPosition(), this.range.getValue(), 5, false, true, 0).stream().filter(poss -> ReplenishBox.shulkers.contains(AntiShulkerBoxPlus.mc.world.getBlockState(poss).getBlock())).findFirst().orElse(null);
            this.first = true;
            this.takeTimer.reset();
            this.l_I = 0;
        }
        else if (!(AntiShulkerBoxPlus.mc.currentScreen instanceof GuiShulkerBox) && this.first) {
            this.first = false;
            final Vec3d hitVec = new Vec3d((Vec3i)this.pos).add(0.5, 0.5, 0.5).add(new Vec3d(EnumFacing.UP.getDirectionVec()).scale(0.5));
            Util.rightClickBlock(this.pos, hitVec, EnumHand.MAIN_HAND, EnumFacing.UP, true);
        }
        else {
            if (!(AntiShulkerBoxPlus.mc.currentScreen instanceof GuiShulkerBox) && !this.first) {
                this.pos = null;
                return;
            }
            if (AntiShulkerBoxPlus.mc.currentScreen instanceof GuiShulkerBox) {
                if (this.delay.getValue() != 0 && !this.takeTimer.passedMs(this.delay.getValue())) {
                    return;
                }
                this.takeTimer.reset();
                final GuiShulkerBox l_Chest = (GuiShulkerBox)AntiShulkerBoxPlus.mc.currentScreen;
                final IInventory inventory = ((AccessorGuiShulkerBox)l_Chest).getInventory();
                if (this.l_I < Objects.requireNonNull(inventory).getSizeInventory()) {
                    final ItemStack stack = inventory.getStackInSlot(this.l_I);
                    if (stack.isEmpty) {
                        ++this.l_I;
                    }
                    switch (this.type.getValue()) {
                        case dropper: {
                            AntiShulkerBoxPlus.mc.playerController.windowClick(l_Chest.inventorySlots.windowId, this.l_I, -999, ClickType.THROW, (EntityPlayer)AntiShulkerBoxPlus.mc.player);
                            break;
                        }
                        case stealer: {
                            AntiShulkerBoxPlus.mc.playerController.windowClick(l_Chest.inventorySlots.windowId, this.l_I, 0, ClickType.QUICK_MOVE, (EntityPlayer)AntiShulkerBoxPlus.mc.player);
                            break;
                        }
                    }
                    ++this.l_I;
                }
                else {
                    l_Chest.onGuiClosed();
                    AntiShulkerBoxPlus.mc.currentScreen.onGuiClosed();
                    AntiShulkerBoxPlus.mc.player.closeScreen();
                    switch (this.breakMode.getValue()) {
                        case LoverMine: {
                            final InstantMine Instance = lover.moduleManager.getModuleByClass(InstantMine.class);
                            if (Instance.isOff()) {
                                Instance.enable();
                                return;
                            }
                            if (!Instance.isOn()) {
                                return;
                            }
                            if (InstantMine.breakPos != null && InstantMine.breakPos.equals((Object)this.pos)) {
                                return;
                            }
                            InstantMine.ondeve(this.pos);
                            InstantMine.ondeve(this.pos);
                            AutoCity.mc.playerController.onPlayerDamageBlock(this.pos, BlockUtil.getRayTraceFacing(this.pos));
                            AutoCity.mc.playerController.onPlayerDamageBlock(this.pos, BlockUtil.getRayTraceFacing(this.pos));
                            AutoCityPlus.mc.playerController.onPlayerDamageBlock(this.pos, BlockUtil.getRayTraceFacing(this.pos));
                            AutoCityPlus.mc.playerController.onPlayerDamageBlock(this.pos, BlockUtil.getRayTraceFacing(this.pos));
                            break;
                        }
                    }
                    this.first = false;
                    this.pos = null;
                    this.l_I = 0;
                }
            }
        }
    }
    
    enum takeType
    {
        dropper, 
        stealer;
    }
}
