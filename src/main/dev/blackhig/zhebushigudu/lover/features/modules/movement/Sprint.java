//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.movement;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import dev.blackhig.zhebushigudu.lover.features.*;

public class Sprint extends Module
{
    private static Sprint INSTANCE;
    public Setting<Mode> mode;
    
    public Sprint() {
        super("Sprint", "Modifies sprinting", Module.Category.MOVEMENT, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.LEGIT));
        this.setInstance();
    }
    
    private void setInstance() {
        Sprint.INSTANCE = this;
    }
    
    public static Sprint getInstance() {
        if (Sprint.INSTANCE == null) {
            Sprint.INSTANCE = new Sprint();
        }
        return Sprint.INSTANCE;
    }
    
    public void onUpdate() {
        switch (this.mode.getValue()) {
            case RAGE: {
                if ((Util.mc.gameSettings.keyBindForward.isKeyDown() || Util.mc.gameSettings.keyBindBack.isKeyDown() || Util.mc.gameSettings.keyBindLeft.isKeyDown() || Util.mc.gameSettings.keyBindRight.isKeyDown()) && !Util.mc.player.isSneaking() && !Util.mc.player.collidedHorizontally && Util.mc.player.getFoodStats().getFoodLevel() > 6.0f) {
                    Util.mc.player.setSprinting(true);
                }
            }
            case LEGIT: {
                if (Util.mc.gameSettings.keyBindForward.isKeyDown() && !Util.mc.player.isSneaking() && !Util.mc.player.isHandActive() && !Util.mc.player.collidedHorizontally && Util.mc.player.getFoodStats().getFoodLevel() > 6.0f && Util.mc.currentScreen == null) {
                    Util.mc.player.setSprinting(true);
                }
            }
            default: {}
        }
    }
    
    public void onDisable() {
        if (!Feature.nullCheck()) {
            Util.mc.player.setSprinting(false);
        }
    }
    
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }
    
    static {
        Sprint.INSTANCE = new Sprint();
    }
    
    public enum Mode
    {
        LEGIT, 
        RAGE;
    }
}
