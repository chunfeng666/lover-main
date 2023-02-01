//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.mixin.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ RenderGlobal.class })
public class MixinRenderGlobal
{
    @Inject(method = { "sendBlockBreakProgress" }, at = { @At("HEAD") })
    public void onSendingBlockBreakProgressPre(final int breakerId, final BlockPos pos, final int progress, final CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post((Event)new BlockBreakEvent(breakerId, pos, progress));
    }
}
