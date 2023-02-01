//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.render;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;

public class FullBright extends Module
{
    float gammabackup;
    Setting<mode> Mode;
    
    public FullBright() {
        super("FullBright", "Gamma to full", Module.Category.RENDER, true, false, false);
        this.Mode = (Setting<mode>)this.register(new Setting("Bright Mode", (T)mode.Gamma));
    }
    
    public void onTick() {
        if (this.Mode.getValue().equals(mode.Gamma)) {
            FullBright.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
            this.gammabackup = FullBright.mc.gameSettings.gammaSetting;
            FullBright.mc.gameSettings.gammaSetting = 255.0f;
        }
        if (this.Mode.getValue().equals(mode.Potion)) {
            FullBright.mc.gameSettings.gammaSetting = this.gammabackup;
            FullBright.mc.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 32548, 127));
        }
    }
    
    public void onDisable() {
        if (this.Mode.getValue().equals(mode.Gamma)) {
            FullBright.mc.gameSettings.gammaSetting = this.gammabackup;
        }
        if (this.Mode.getValue().equals(mode.Potion)) {
            FullBright.mc.player.removePotionEffect(MobEffects.NIGHT_VISION);
        }
    }
    
    enum mode
    {
        Potion, 
        Gamma;
    }
}
