//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.util;

import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.opengl.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Title
{
    int ticks;
    int bruh;
    int breakTimer;
    String bruh1;
    boolean qwerty;
    
    public Title() {
        this.ticks = 0;
        this.bruh = 0;
        this.breakTimer = 0;
        this.bruh1 = "KKGod | 0.8.0 [KRY4TAL_ is God]";
        this.qwerty = false;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        ++this.ticks;
        if (this.ticks % 17 == 0) {
            Display.setTitle(this.bruh1.substring(0, this.bruh1.length() - this.bruh));
            if ((this.bruh == this.bruh1.length() && this.breakTimer != 2) || (this.bruh == 0 && this.breakTimer != 4)) {
                ++this.breakTimer;
                return;
            }
            this.breakTimer = 0;
            if (this.bruh == this.bruh1.length()) {
                this.qwerty = true;
            }
            if (this.qwerty) {
                --this.bruh;
            }
            else {
                ++this.bruh;
            }
            if (this.bruh == 0) {
                this.qwerty = false;
            }
        }
    }
}
