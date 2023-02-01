//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.util.SeijaUtil;

import net.minecraft.client.*;
import net.minecraft.item.*;
import dev.blackhig.zhebushigudu.lover.util.*;

public class SeijaInvUtil
{
    Minecraft mc;
    
    public SeijaInvUtil() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public static int switchToItem(final Item itemIn) {
        final int slot = InventoryUtil.getItemHotbar(itemIn);
        if (slot == -1) {
            return -1;
        }
        InventoryUtil.switchToHotbarSlot(slot, false);
        return slot;
    }
}
