//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.render;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraftforge.fml.common.eventhandler.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import java.awt.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class SomeRenders extends Module
{
    FadeUtils fadeUtils;
    BlockPos pos;
    Setting<Boolean> hasFade;
    Setting<Integer> red;
    Setting<Integer> green;
    Setting<Integer> blue;
    Setting<Integer> alpha;
    Setting<FadeType> fadeType;
    Setting<FadeUtil> fadeUtil;
    Setting<FadeStyle> fadeStyle;
    Setting<Integer> fadeLength;
    Setting<RenderType> renderType;
    
    public SomeRenders() {
        super("SomeRenderers", "Renders", Module.Category.RENDER, true, false, false);
        this.hasFade = (Setting<Boolean>)this.register(new Setting("Fade", (T)true));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)255, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)100, (T)20, (T)255));
        this.fadeType = (Setting<FadeType>)this.register(new Setting("FadeType", (T)FadeType.FadeIn, v -> this.hasFade.getValue()));
        this.fadeUtil = (Setting<FadeUtil>)this.register(new Setting("FadeUtil", (T)FadeUtil.EasingDefault, v -> this.hasFade.getValue()));
        this.fadeStyle = (Setting<FadeStyle>)this.register(new Setting("FadeStyle", (T)FadeStyle.Heighten, v -> this.hasFade.getValue()));
        this.fadeLength = (Setting<Integer>)this.register(new Setting("FadeLength", (T)2000, (T)1000, (T)10000, v -> this.hasFade.getValue()));
        this.renderType = (Setting<RenderType>)this.register(new Setting("RenderType", (T)RenderType.Outline));
    }
    
    @SubscribeEvent
    public void clickingBlock(final BlockEvent event) {
        this.fadeUtils = new FadeUtils(this.fadeLength.getValue());
        this.pos = event.pos;
    }
    
    public void onRender3D(final Render3DEvent event) {
        if (this.pos == null) {
            return;
        }
        if (!this.hasFade.getValue()) {
            switch (this.renderType.getValue()) {
                case Outline: {
                    RenderUtils3D.drawBlockOutline(this.pos, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.5f, true);
                }
                case Fill: {
                    RenderUtil.drawFilledBoxESPN(this.pos, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()));
                    break;
                }
            }
            return;
        }
        final Vec3d interpolateEntity = MathUtil.interpolateEntity((Entity)SomeRenders.mc.player, SomeRenders.mc.getRenderPartialTicks());
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0).offset(this.pos);
        axisAlignedBB = axisAlignedBB.grow(0.0020000000949949026).offset(-interpolateEntity.x, -interpolateEntity.y, -interpolateEntity.z);
        double size = 0.0;
        switch (this.fadeUtil.getValue()) {
            case EasingDefault: {
                size = ((this.fadeType.getValue() == FadeType.FadeOut) ? this.fadeUtils.easeOutQuad() : this.fadeUtils.easeInQuad());
                break;
            }
            case Eps: {
                size = ((this.fadeType.getValue() == FadeType.FadeOut) ? this.fadeUtils.getEpsEzFadeOut() : this.fadeUtils.getEpsEzFadeIn());
                break;
            }
            case Default: {
                size = ((this.fadeType.getValue() == FadeType.FadeOut) ? this.fadeUtils.getFadeOutDefault() : this.fadeUtils.getFadeInDefault());
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + this.fadeUtil.getValue());
            }
        }
        AxisAlignedBB axisAlignedBB2 = null;
        switch (this.fadeStyle.getValue()) {
            case Heighten: {
                axisAlignedBB2 = new AxisAlignedBB(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ, axisAlignedBB.maxX, axisAlignedBB.minY + size, axisAlignedBB.maxZ);
                break;
            }
            case Growing: {
                final double centerX = axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) / 2.0;
                final double centerY = axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) / 2.0;
                final double centerZ = axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) / 2.0;
                final double full = axisAlignedBB.maxX - centerX;
                final double progressValX = full * size;
                final double progressValY = full * size;
                final double progressValZ = full * size;
                axisAlignedBB2 = new AxisAlignedBB(centerX - progressValX, centerY - progressValY, centerZ - progressValZ, centerX + progressValX, centerY + progressValY, centerZ + progressValZ);
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + this.fadeStyle.getValue());
            }
        }
        switch (this.renderType.getValue()) {
            case Outline: {
                RenderUtils3D.drawBlockOutline(axisAlignedBB2, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.5f);
            }
            case Fill: {
                RenderUtil.drawFilledBox(axisAlignedBB2, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()).getRGB());
            }
            case Solid: {
                RenderUtils3D.drawBlockOutline(axisAlignedBB2, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()), 1.5f);
                RenderUtil.drawFilledBox(axisAlignedBB2, new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()).getRGB());
                break;
            }
        }
    }
    
    enum FadeType
    {
        FadeIn, 
        FadeOut;
    }
    
    enum RenderType
    {
        Outline, 
        Solid, 
        Fill;
    }
    
    enum FadeStyle
    {
        Heighten, 
        Growing;
    }
    
    enum FadeUtil
    {
        Eps, 
        Default, 
        EasingDefault;
    }
}
