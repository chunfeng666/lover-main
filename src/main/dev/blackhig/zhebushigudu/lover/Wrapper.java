//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover;

import net.minecraft.client.*;
import dev.blackhig.zhebushigudu.lover.manager.*;
import net.minecraft.client.entity.*;

public class Wrapper
{
    static final Minecraft mc;
    public static FileManager fileManager;
    
    public static Minecraft GetMC() {
        return Wrapper.mc;
    }
    
    public static EntityPlayerSP GetPlayer() {
        return Wrapper.mc.player;
    }
    
    public static FileManager getFileManager() {
        if (Wrapper.fileManager == null) {
            Wrapper.fileManager = new FileManager();
        }
        return Wrapper.fileManager;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
