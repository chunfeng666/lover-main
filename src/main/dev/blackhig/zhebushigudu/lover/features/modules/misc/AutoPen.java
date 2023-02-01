//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.misc;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.util.*;
import dev.blackhig.zhebushigudu.lover.features.setting.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import dev.blackhig.zhebushigudu.lover.*;
import java.util.*;
import java.io.*;
import java.util.stream.*;

public class AutoPen extends Module
{
    public static ArrayList<String> dictionary;
    Random random;
    Timer coolDown;
    Setting<Integer> delay;
    Setting<Boolean> rndChar;
    Setting<Boolean> whisper;
    Setting<String> name;
    
    public AutoPen() {
        super("AutoFuck", "nmsl", Category.PLAYER, true, false, false);
        this.random = new Random();
        this.coolDown = new Timer();
        this.delay = (Setting<Integer>)this.register(new Setting("Delay", (T)100, (T)0, (T)5000));
        this.rndChar = (Setting<Boolean>)this.register(new Setting("AntiSpam", (T)true));
        this.whisper = (Setting<Boolean>)this.register(new Setting("Whisper", (T)false));
        this.name = (Setting<String>)this.register(new Setting("Name", (T)"sb", v -> this.whisper.getValue()));
    }
    
    @Override
    public void onLogout() {
        this.disable();
    }
    
    @Override
    public void onUpdate() {
        if (!this.coolDown.passedMs(this.delay.getValue())) {
            return;
        }
        this.coolDown.reset();
        final int index = this.random.nextInt(AutoPen.dictionary.size());
        final StringBuilder sb = new StringBuilder();
        if (this.whisper.getValue()) {
            sb.append("/tell ");
            sb.append(this.name.getValue());
            sb.append(" ");
        }
        sb.append(AutoPen.dictionary.get(index));
        if (this.rndChar.getValue()) {
            sb.append("[").append((char)(this.random.nextInt(24) + 65)).append((char)(this.random.nextInt(24) + 97)).append("]");
        }
        AutoPen.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(sb.toString()));
    }
    
    static {
        AutoPen.dictionary = new ArrayList<String>();
        try {
            final BufferedReader buff = new BufferedReader(new InputStreamReader(Objects.requireNonNull(lover.class.getClassLoader().getResourceAsStream("dictionary.txt")), "GBK"));
            AutoPen.dictionary = buff.lines().collect((Collector<? super String, ?, ArrayList<String>>)Collectors.toList());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
