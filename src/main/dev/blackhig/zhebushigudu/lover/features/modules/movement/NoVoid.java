//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.movement;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import net.minecraft.util.math.*;

public class NoVoid extends Module
{
    public NoVoid() {
        super("NoVoid", "Glitches you up from void.", Module.Category.MOVEMENT, false, false, false);
    }
    
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (!NoVoid.mc.player.noClip && NoVoid.mc.player.posY <= 0.0) {
            final RayTraceResult trace = NoVoid.mc.world.rayTraceBlocks(NoVoid.mc.player.getPositionVector(), new Vec3d(NoVoid.mc.player.posX, 0.0, NoVoid.mc.player.posZ), false, false, false);
            if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK) {
                return;
            }
            NoVoid.mc.player.setVelocity(0.0, 0.0, 0.0);
            if (NoVoid.mc.player.getRidingEntity() != null) {
                NoVoid.mc.player.getRidingEntity().setVelocity(0.0, 0.0, 0.0);
            }
        }
    }
}
