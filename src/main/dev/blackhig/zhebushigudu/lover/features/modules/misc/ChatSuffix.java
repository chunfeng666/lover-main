//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.features.modules.misc;

import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.event.events.*;
import net.minecraft.network.play.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ChatSuffix extends Module
{
    public ChatSuffix() {
        super("ChatSuffix", "suffix", Category.MISC, true, false, false);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage) {
            final CPacketChatMessage packet = (CPacketChatMessage)event.getPacket();
            final String message = packet.getMessage();
            if (!message.startsWith("/")) {
                String loverChat = message + " > Lover";
                if (loverChat.length() >= 256) {
                    loverChat = loverChat.substring(0, 256);
                }
                packet.message = loverChat;
            }
        }
    }
}
