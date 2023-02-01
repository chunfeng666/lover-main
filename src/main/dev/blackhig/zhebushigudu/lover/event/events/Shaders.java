//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.event.events;

public class Shaders
{
    public static String packNameNone;
    static IShaderPack shaderPack;
    
    public static String getShaderPackName() {
        return (Shaders.shaderPack == null) ? null : ((Shaders.shaderPack instanceof ShaderPackNone) ? null : Shaders.shaderPack.getName());
    }
    
    static {
        Shaders.shaderPack = null;
    }
}
