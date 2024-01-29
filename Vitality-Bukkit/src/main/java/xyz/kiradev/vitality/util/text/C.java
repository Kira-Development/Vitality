package xyz.kiradev.vitality.util.text;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 1/27/2024
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.VitalityAPI;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.util.file.LanguageLocale;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class C {
    public static String LONG_LINE = "&8&m----------------------------------------";
    public static String NORMAL_LINE = "&8&m-----------------------------";
    public static String SHORT_LINE = "&8&m---------------";

    public static String color(String text) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

        for(Matcher matcher = pattern.matcher(text); matcher.find(); matcher = pattern.matcher(text)) {
            String hexCode = text.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();

            for (char c : ch) {
                builder.append("&").append(c);
            }

            text = text.replace(hexCode, builder.toString());
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> color(List<String> list) {
        return list.stream().map(xyz.kiradev.clash.utils.C::color).collect(Collectors.toList());
    }

    public static String[] color(String[] array) {
        for(int i = 0; i < array.length; ++i) {
            array[i] = color(array[i]);
        }

        return array;
    }

    public static void sendMessage(CommandSender sender, String message) {
        if(sender instanceof Player) {
            sendMessage((Player) sender, message);
        } else sender.sendMessage(color(message
                .replace("<mc>", "" + ChatColor.valueOf(Vitality.getInstance().getLanguageFile().getString("themes.main-color")))
                .replace("<sc>", "" + ChatColor.valueOf(Vitality.getInstance().getLanguageFile().getString("themes.secondary-color")))
                .replace("<ec>", "" + ChatColor.valueOf(Vitality.getInstance().getLanguageFile().getString("themes.error-color")))));
    }

    public static void sendMessage(Player player, String message) {
        Profile profile = Vitality.getInstance().getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        ChatColor mainColor = ChatColor.valueOf(LanguageLocale.MAIN_COLOR.getString());
        ChatColor secondColor = ChatColor.valueOf(LanguageLocale.SECONDARY_COLOR.getString());
        ChatColor errorColor = ChatColor.valueOf(LanguageLocale.ERROR_COLOR.getString());
        player.sendMessage(color(message.replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor)));
    }

    public static void broadcastMessage(String message) {
        Bukkit.getOnlinePlayers().forEach((player) -> {
            sendMessage(player, message);
        });
    }

    public static void broadcastMessage(String message, String permission) {
        Bukkit.getOnlinePlayers().stream().filter((player) -> player.hasPermission(permission.toLowerCase())).collect(Collectors.toList()).forEach((player) -> {
            sendMessage(player, message);
        });
    }
}