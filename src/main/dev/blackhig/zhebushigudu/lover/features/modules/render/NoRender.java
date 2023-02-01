//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.render;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.init.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;

public class NoRender extends Module
{
    private static NoRender INSTANCE;
    public Setting<Boolean> armor;
    public Setting<Boolean> blind;
    public Setting<Boolean> fire;
    public Setting<Boolean> hurtCam;
    public Setting<Boolean> nausea;
    public Setting<Boolean> skyLightUpdate;
    
    public NoRender() {
        super("NoRender", "No Render", Module.Category.RENDER, true, false, false);
        this.armor = (Setting<Boolean>)this.register(new Setting("Armor", (T)true));
        this.blind = (Setting<Boolean>)this.register(new Setting("Blind", (T)true));
        this.fire = (Setting<Boolean>)this.register(new Setting("Frie", (T)true));
        this.hurtCam = (Setting<Boolean>)this.register(new Setting("HurtCam", (T)true));
        this.nausea = (Setting<Boolean>)this.register(new Setting("Nausea", (T)true));
        this.skyLightUpdate = (Setting<Boolean>)this.register(new Setting("SkyLightUpdate", (T)true));
        this.setInstance();
    }
    
    public static NoRender getInstance() {
        if (NoRender.INSTANCE == null) {
            NoRender.INSTANCE = new NoRender();
        }
        return NoRender.INSTANCE;
    }
    
    private void setInstance() {
        NoRender.INSTANCE = this;
    }
    
    public void onUpdate() {
        if (this.blind.getValue() && Util.mc.player.isPotionActive(MobEffects.BLINDNESS)) {
            Util.mc.player.removePotionEffect(MobEffects.BLINDNESS);
        }
        if (this.nausea.getValue() && Util.mc.player.isPotionActive(MobEffects.NAUSEA)) {
            Util.mc.player.removePotionEffect(MobEffects.NAUSEA);
        }
    }
    
    @SubscribeEvent
    public void NoRenderEventListener(final NoRenderEvent event) {
        if (event.getStage() == 0 && this.armor.getValue()) {
            event.setCanceled(true);
        }
        else if (event.getStage() == 1 && this.hurtCam.getValue()) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void blockOverlayEventListener(final RenderBlockOverlayEvent event) {
        if (this.fire.getValue() && event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
            event.setCanceled(true);
        }
    }
    
    static {
        NoRender.INSTANCE = new NoRender();
    }
}
