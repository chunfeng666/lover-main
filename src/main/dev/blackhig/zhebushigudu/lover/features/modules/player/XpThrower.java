//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.player;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.item.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.entity.*;
import dev.blackhig.zhebushigudu.lover.*;
import java.util.*;
import net.minecraft.inventory.*;

public class XpThrower extends Module
{
    Setting<Boolean> feetThrow;
    Setting<Integer> throwSpeed;
    Setting<Bind> throwKey;
    Setting<Boolean> stopXp;
    Setting<Boolean> noLowDurable;
    Setting<Integer> stopDurable;
    Setting<Boolean> autoXp;
    Setting<Integer> autoXpRange;
    Setting<Boolean> takeoffArmor;
    Setting<Integer> takeoffDurable;
    private final Setting<Integer> takeoffDelay;
    Timer timer;
    private int delay_count;
    
    public XpThrower() {
        super("XpThrower", "AutoXp by KijinSeija", Module.Category.PLAYER, true, false, false);
        this.feetThrow = (Setting<Boolean>)this.register(new Setting("FeetThrow", (T)true));
        this.throwSpeed = (Setting<Integer>)this.register(new Setting("ThorwDelay", (T)20, (T)1, (T)1000));
        this.throwKey = (Setting<Bind>)this.register(new Setting("ThrowKey", (T)new Bind(0)));
        this.stopXp = (Setting<Boolean>)this.register(new Setting("StopXP", (T)true));
        this.noLowDurable = (Setting<Boolean>)this.register(new Setting("NoLowDurable", (T)true, v -> this.stopXp.getValue()));
        this.stopDurable = (Setting<Integer>)this.register(new Setting("StopDurable", (T)100, (T)0, (T)100, v -> this.stopXp.getValue()));
        this.autoXp = (Setting<Boolean>)this.register(new Setting("AutoThrowXp", (T)false));
        this.autoXpRange = (Setting<Integer>)this.register(new Setting("AutoXpRange", (T)8, (T)0, (T)20, v -> this.autoXp.getValue()));
        this.takeoffArmor = (Setting<Boolean>)this.register(new Setting("TakeoffArmor", (T)true, v -> this.stopXp.getValue()));
        this.takeoffDurable = (Setting<Integer>)this.register(new Setting("TakeoffDurable", (T)100, (T)0, (T)100, v -> this.takeoffArmor.getValue()));
        this.takeoffDelay = (Setting<Integer>)this.register(new Setting("Delay", (T)0, (T)0, (T)5, v -> this.takeoffArmor.getValue()));
        this.timer = new Timer();
    }
    
    public void onUpdate() {
        if (!this.throwKey.getValue().isDown() || !this.timer.passedDms(this.throwSpeed.getValue())) {
            if (this.autoXp.getValue() && !this.isRangeNotPlayer(this.autoXpRange.getValue())) {
                return;
            }
            if (!this.autoXp.getValue()) {
                return;
            }
        }
        if (this.stopXp.getValue() && this.stopDurable.getValue() <= this.getArmorDurable(this.noLowDurable.getValue())) {
            return;
        }
        final int XpSlot = InventoryUtil.getItemHotbar(Items.EXPERIENCE_BOTTLE);
        if (XpSlot == -1) {
            return;
        }
        if (this.takeoffArmor.getValue()) {
            this.takeArmorOff();
        }
        final int oldSlot = XpThrower.mc.player.inventory.currentItem;
        if (this.feetThrow.getValue()) {
            final float yaw = XpThrower.mc.player.cameraYaw;
            RotationUtil.faceYawAndPitch(yaw, 90.0f);
        }
        InventoryUtil.switchToHotbarSlot(XpSlot, false);
        XpThrower.mc.playerController.processRightClick((EntityPlayer)XpThrower.mc.player, (World)XpThrower.mc.world, EnumHand.MAIN_HAND);
        InventoryUtil.switchToHotbarSlot(oldSlot, false);
    }
    
    public Double getArmorDurable(final boolean getLowestValue) {
        final ArrayList<Double> DurableList = new ArrayList<Double>();
        for (int i = 5; i <= 8; ++i) {
            final ItemStack armor = (ItemStack)XpThrower.mc.player.inventoryContainer.getInventory().get(i);
            final double max_dam = armor.getMaxDamage();
            final double dam_left = armor.getMaxDamage() - armor.getItemDamage();
            final double percent = dam_left / max_dam * 100.0;
            DurableList.add(percent);
        }
        DurableList.sort(Comparator.naturalOrder());
        if (getLowestValue) {
            return DurableList.get(0);
        }
        return DurableList.get(DurableList.size() - 1);
    }
    
    private Boolean isRangeNotPlayer(final double range) {
        EntityPlayer target = null;
        double distance = range;
        for (final EntityPlayer player : XpThrower.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range) && !lover.friendManager.isFriend(player.getName())) {
                if (XpThrower.mc.player.posY - player.posY >= 5.0) {
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
        if (target == null) {
            return true;
        }
        return false;
    }
    
    private ItemStack getArmor(final int first) {
        return (ItemStack)XpThrower.mc.player.inventoryContainer.getInventory().get(first);
    }
    
    private void takeArmorOff() {
        for (int slot = 5; slot <= 8; ++slot) {
            final ItemStack item = this.getArmor(slot);
            final double max_dam = item.getMaxDamage();
            final double dam_left = item.getMaxDamage() - item.getItemDamage();
            final double percent = dam_left / max_dam * 100.0;
            if (percent >= this.takeoffDurable.getValue() && !item.equals(Items.AIR)) {
                if (InventoryUtil.findItemInventorySlot(Items.AIR, false) == -1) {
                    return;
                }
                if (this.delay_count < this.takeoffDelay.getValue()) {
                    ++this.delay_count;
                    return;
                }
                this.delay_count = 0;
                XpThrower.mc.playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, (EntityPlayer)XpThrower.mc.player);
            }
        }
    }
    
    public void onEnable() {
        this.delay_count = 0;
    }
}
