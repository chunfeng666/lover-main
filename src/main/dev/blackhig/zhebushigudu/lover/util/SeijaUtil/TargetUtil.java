//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.util.SeijaUtil;

import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;

public class TargetUtil
{
    Minecraft mc;
    
    public TargetUtil() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public static byte getArmorPieces(final EntityPlayer target) {
        byte i = 0;
        if (target.inventoryContainer.getSlot(5).getStack().getItem().equals(Items.DIAMOND_HELMET)) {
            ++i;
        }
        if (target.inventoryContainer.getSlot(6).getStack().getItem().equals(Items.DIAMOND_CHESTPLATE)) {
            ++i;
        }
        if (target.inventoryContainer.getSlot(7).getStack().getItem().equals(Items.DIAMOND_LEGGINGS)) {
            ++i;
        }
        if (target.inventoryContainer.getSlot(8).getStack().getItem().equals(Items.DIAMOND_BOOTS)) {
            ++i;
        }
        return i;
    }
}
