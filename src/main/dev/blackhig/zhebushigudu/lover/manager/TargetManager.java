//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.manager;

import dev.blackhig.zhebushigudu.lover.util.HoleFillPlus.*;
import net.minecraft.entity.*;

public class TargetManager implements Globals
{
    private EntityLivingBase currentTarget;
    
    public TargetManager() {
        this.currentTarget = null;
    }
    
    public void updateTarget(final EntityLivingBase targetIn) {
        this.currentTarget = targetIn;
    }
    
    public EntityLivingBase getTarget() {
        return this.currentTarget;
    }
}
