//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.event;

import net.minecraft.client.*;

public interface MixinInterface
{
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final boolean nullCheck = MixinInterface.mc.player == null || MixinInterface.mc.world == null;
}
