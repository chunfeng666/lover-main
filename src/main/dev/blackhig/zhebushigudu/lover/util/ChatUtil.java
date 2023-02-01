//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "E:\cn×îÇ¿·´±àÒëÆ÷\1.12 stable mappings"!

//Decompiled by Procyon!

package dev.blackhig.zhebushigudu.lover.util;

import net.minecraft.client.*;
import net.minecraft.util.text.*;
import dev.blackhig.zhebushigudu.lover.features.modules.*;
import dev.blackhig.zhebushigudu.lover.*;
import com.mojang.realmsclient.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class ChatUtil
{
    public static char SECTIONSIGN;
    private static final int ChatLineId = 777;
    
    public static void printChatMessage(final String message) {
        printRawChatMessage(ChatUtil.SECTIONSIGN + "7[" + ChatUtil.SECTIONSIGN + "b" + "Lover" + ChatUtil.SECTIONSIGN + "7] " + ChatUtil.SECTIONSIGN + "r" + message);
    }
    
    private static void ChatMessage(final String message) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(message));
    }
    
    public static void printRawChatMessage(final String message) {
        if (Minecraft.getMinecraft().player == null) {
            return;
        }
        ChatMessage(message);
    }
    
    public static void printErrorChatMessage(final String message) {
        printRawChatMessage(ChatUtil.SECTIONSIGN + "7[" + ChatUtil.SECTIONSIGN + "4" + ChatUtil.SECTIONSIGN + "lERROR" + ChatUtil.SECTIONSIGN + "7] " + ChatUtil.SECTIONSIGN + "r" + message);
    }
    
    static {
        ChatUtil.SECTIONSIGN = '¡ì';
    }
    
    public static class PrivateMessageSender
    {
        public static void sendPrivateMessage(final String message, final boolean delete) {
            if (Module.fullNullCheck()) {
                return;
            }
            if (delete) {
                Util.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)new TextComponentString(message), 777);
            }
            else {
                Util.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(message));
            }
        }
        
        public static void sendModuleNotifyMessageNoPersist(final String moduleName, final String message) {
            sendPrivateMessage(lover.commandManager.getClientMessage() + ChatFormatting.WHITE + " [" + ChatFormatting.BLUE + moduleName + ChatFormatting.WHITE + "] " + ChatFormatting.GRAY + message, true);
        }
        
        public static void sendModuleNotifyMessagePersist(final String moduleName, final String message) {
            sendPrivateMessage(lover.commandManager.getClientMessage() + ChatFormatting.WHITE + " [" + ChatFormatting.BLUE + moduleName + ChatFormatting.WHITE + "] " + ChatFormatting.GRAY + message, false);
        }
    }
    
    public static class PublicMessageSender
    {
        public static void sendMessageToSever(final String message) {
            if (Module.fullNullCheck()) {
                return;
            }
            Util.mc.player.connection.sendPacket((Packet)new CPacketChatMessage(message));
        }
    }
}
