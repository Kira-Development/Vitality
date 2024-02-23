package xyz.kiradev.vitality.model.profile.commands;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 16/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.kiradev.clash.command.annotation.Command;
import xyz.kiradev.clash.command.annotation.Parameter;
import xyz.kiradev.clash.command.annotation.SubCommand;
import xyz.kiradev.clash.utils.C;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.model.profile.permission.PermissionManager;
import xyz.kiradev.vitality.util.file.LanguageLocale;

public class ProfileCommand {

    @Command(label = "user", permission = "core.admin", aliases = {"profile", "player"})
    public void command(CommandSender sender) {
        ChatColor mainColor = ChatColor.valueOf(LanguageLocale.MAIN_COLOR.getString());
        ChatColor secondColor = ChatColor.valueOf(LanguageLocale.SECONDARY_COLOR.getString());
        ChatColor errorColor = ChatColor.valueOf(LanguageLocale.ERROR_COLOR.getString());
        C.sendMessage(sender, LanguageLocale.PROFILE_HELP.getString().replaceAll("<command>", "/user").replaceAll("<args>", "addpermission <profile> <permission>").replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor));
        C.sendMessage(sender, LanguageLocale.PROFILE_HELP.getString().replaceAll("<command>", "/user").replaceAll("<args>", "removepermission <profile> <permission>").replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor));
        C.sendMessage(sender, LanguageLocale.PROFILE_HELP.getString().replaceAll("<command>", "/user").replaceAll("<args>", "permissions <profile>").replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor));
        C.sendMessage(sender, LanguageLocale.PROFILE_HELP.getString().replaceAll("<command>", "/user").replaceAll("<args>", "wipe <profile>").replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor));
    }

    @SubCommand(label = "addpermission", aliases = "addperm", parent = "user", permission = "core.admin")
    public void addPermission(CommandSender sender, @Parameter(name = "player") Profile profile, @Parameter(name = "permission") String permission) {
        ChatColor mainColor = ChatColor.valueOf(LanguageLocale.MAIN_COLOR.getString());
        ChatColor secondColor = ChatColor.valueOf(LanguageLocale.SECONDARY_COLOR.getString());
        ChatColor errorColor = ChatColor.valueOf(LanguageLocale.ERROR_COLOR.getString());
        if(profile == null) return;
        profile.addPermission(permission);
        Vitality.getInstance().getApi().getApi().getProfileManager().save(profile);
        Player player = Bukkit.getPlayer(profile.getUuid());
        if(player != null && player.isOnline()) PermissionManager.refreshAll(player);
        C.sendMessage(sender, LanguageLocale.PROFILE_ADDED_PERMISSION.getString().replaceAll("<player>", profile.getName()).replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor));
    }

    @SubCommand(label = "removepermission", aliases = "removeperm", parent = "user", permission = "core.admin")
    public void removePermission(CommandSender sender, @Parameter(name = "player") Profile profile, @Parameter(name = "permission") String permission) {
        ChatColor mainColor = ChatColor.valueOf(LanguageLocale.MAIN_COLOR.getString());
        ChatColor secondColor = ChatColor.valueOf(LanguageLocale.SECONDARY_COLOR.getString());
        ChatColor errorColor = ChatColor.valueOf(LanguageLocale.ERROR_COLOR.getString());
        if(profile == null) return;
        profile.removePermission(permission);
        Vitality.getInstance().getApi().getApi().getProfileManager().save(profile);
        Player player = Bukkit.getPlayer(profile.getUuid());
        if(player != null && player.isOnline()) PermissionManager.refreshAll(player);
        C.sendMessage(sender, LanguageLocale.PROFILE_REMOVED_PERMISSION.getString().replaceAll("<player>", profile.getName()).replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor));
    }

    @SubCommand(label = "permissions", parent = "user", permission = "core.admin")
    public void permissions(CommandSender sender, @Parameter(name = "player") Profile profile) {
        ChatColor mainColor = ChatColor.valueOf(LanguageLocale.MAIN_COLOR.getString());
        ChatColor secondColor = ChatColor.valueOf(LanguageLocale.SECONDARY_COLOR.getString());
        ChatColor errorColor = ChatColor.valueOf(LanguageLocale.ERROR_COLOR.getString());
        if(profile == null) return;
        C.sendMessage(sender, LanguageLocale.PROFILE_LIST_PERMISSION.getString().replaceAll("<V>", "" + profile.getPermissions()).replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor));
    }

    @SubCommand(label = "wipe", parent = "user", permission = "core.admin")
    public void wipe(CommandSender sender, @Parameter(name = "player") Profile profile) {
        if(profile == null) return;
        C.sendMessage(sender, "<ec>This feature is currently under maintenance!");
    }
}