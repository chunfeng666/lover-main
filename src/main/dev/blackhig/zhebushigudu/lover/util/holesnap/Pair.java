//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.util.holesnap;

public class Pair<L, R>
{
    L left;
    R right;
    
    public Pair(final L l, final R r) {
        this.left = l;
        this.right = r;
    }
    
    public L getLeft() {
        return this.left;
    }
    
    public R getRight() {
        return this.right;
    }
    
    public Pair<L, R> setLeft(final L left) {
        this.left = left;
        return this;
    }
    
    public Pair<L, R> setRight(final R right) {
        this.right = right;
        return this;
    }
}
