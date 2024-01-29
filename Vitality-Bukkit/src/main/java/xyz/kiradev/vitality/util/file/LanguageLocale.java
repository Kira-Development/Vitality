package xyz.kiradev.vitality.util.file;

/*
 *
 * Vitality is a property of Kira-Development-Team
 * 16/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.kiradev.vitality.Vitality;
import xyz.kiradev.vitality.api.model.profile.Profile;
import xyz.kiradev.vitality.util.text.C;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum LanguageLocale {
    // Themes
    MAIN_COLOR("themes.main-color", "YELLOW"),
    SECONDARY_COLOR("themes.secondary-color", "WHITE"),
    ERROR_COLOR("themes.error-color", "RED"),
    INVALID_COLOR("themes.invalid-color-error", "<ec>Invalid color!"),

    // Command Help
    COMMAND_HELP("commands.help-message", "<mc>&l<command name> Help"),
    COMMAND_ENTRY("commands.help-entry", "&7 * <mc>/<command> <sc><args>"),

    // Profile messages
    PROFILE_HELP("profile-success.help-message", "&7* <mc><command> <sc><args>"),
    PROFILE_ADDED_PERMISSION("profile-success.added-permission", "<mc>You have successfully added a <mc>permission <mc>to <sc><player>"),
    PROFILE_REMOVED_PERMISSION("profile-success.removed-permission", "<mc>You have successfully removed a <sc>permission <mc>from <sc><player>"),
    PROFILE_LIST_PERMISSION("profile-success.list-permissions", "<mc>Permissions: <sc><V>"),

    // Profile errors
    PROFILE_NOT_FOUND("profile-errors.not-found", "<ec>This profile was not found."),

    // Rank errors
    RANK_ALREADY_EXISTS("rank-errors.already-exists", "<ec>This rank already exists!"),
    RANK_DOESNT_EXISTS("rank-errors.rank-doesnt-exists", "<ec>This rank is invalid!"),
    RANK_COLOR_DOESNT_EXISTS("rank-errors.rank-color-doesnt-exists", "<ec>You cannot set a rank color with an invalid color."),
    RANK_ALREADY_HAS_PERMISSION("rank-errors.rank-already-has-permission", "<ec>This rank already contains this permission!"),
    RANK_DOESNT_HAVE_PERMISSION("rank-errors.rank-doesnt-have-permission", "<ec>This rank does not contain this permission!"),
    RANK_ALREADY_HAS_INHERITANCE("rank-errors.rank-already-has-inheritance", "<ec>This rank already contains this rank as an inheritance."),
    RANK_DOESNT_HAVE_INHERITANCE("rank-errors.rank-doesnt-have-inheritance", "<ec>This rank does not contain this rank as an inheritance."),

    // Rank messages
    CREATED_RANK("rank-success.created-rank", "<mc>You have successfully created a new rank <sc><name>"),
    DELETED_RANK("rank-success.deleted-rank", "<mc>You have successfully deleted <sc><name> rank"),
    SET_PREFIX("rank-success.set-prefix", "<mc>You have successfully set <sc><rank>'s <sc>prefix <mc>to <sc><prefix>"),
    SET_SUFFIX("rank-success.set-suffix", "<mc>You have successfully set <sc><rank>'s <sc>suffix <mc>to <sc><suffix>"),
    SET_COLOR("rank-success.set-color", "<mc>You have successfully set <sc><rank>'s <sc>color <mc>to <color>"),
    SET_ITALIC("rank-success.set-italic", "<mc>You have successfully set <sc><rank>'s <sc>italic <mc>to <sc><value>"),
    SET_BOLD("rank-success.set-bold", "<mc>You have successfully set <sc><rank>'s <sc>bold <mc>to <sc><value>"),
    SET_STAFF("rank-success.set-staff", "<mc>You have successfully set <sc><rank>'s <sc>staff <mc>to <sc><value>"),
    SET_WEIGHT("rank-success.set-weight", "<mc>You have successfully set <rank>'s <sc>weight <mc>to <sc><value>"),
    ADDED_RANK_PERMISSION("rank-success.added-rank-permission", "<mc>You have successfully added <sc><value> permission to <rank>"),
    REMOVED_RANK_PERMISSION("rank-success.removed-rank-permission", "<mc>You have successfully removed <sc><value> permission <mc>to <rank>"),
    LIST_RANK_PERMISSIONS("rank-success.list-rank-permissions", "<rank> <mc>Permissions \n<sc><value>"),
    LIST_RANK_INHERITANCE("rank-success.list-rank-inheritances", "<rank> <mc>Inheritances \n<sc><value>"),
    RANK_INFO("rank-success.info", "<rank> <mc>Info \n<sc><value>"),

    // Grant errors
    CANCELLED_GRANT("grant-errors.cancelled-grant", "<ec>Cancelled grant."),
    INVALID_DURATION("grant-errors.invalid-duration", "<ec>Invalid Duration! &7(Example: permanent, 1y, 1mo, 1w, 1d, 1h, 1m, 1s)"),

    // Grant messages
    PICK_DURATION("grant-success.pick-duration", "<mc>Pick the duration. &7(Example: permanent, 1y, 1mo, 1w, 1d, 1h, 1m, 1s)"),
    PICK_REASON("grant-success.pick-reason", "<mc>Provide the reason you want."),
    PICKED_DURATION("grant-success.picked-duration", "<mc>You have successfully picked the duration <sc><duration><mc>."),
    PICKED_REASON("grant-success.picked-reason", "<mc>You have successfully picked the reason <sc><reason><mc>."),

    // Other
    RANKS_LIST_FORMAT("other.rank-list-format", "<value>"),
    PLAYERS_LIST_FORMAT("other.player-list-format", "&7(<online>/<max>): <value>"),
            ;

    private static YamlConfiguration yamlConfiguration;
    private String path;
    @Setter
    private Object content;

    LanguageLocale(String path, Object content) {
        this.path = path;
        this.content = content;
    }

    public String getString() {
        return C.color((String) content);
    }

    public int getInt() {
        return (int) content;
    }

    public List<String> getStringList() {
        List<String> old = (List<String>) content;
        List<String> contents = new ArrayList<>();
        for(String str : old) {
            contents.add(C.color(str));
        }
        return contents;
    }

    public void sendMessage(CommandSender sender) {
        String message = (String) content;
        if(sender instanceof Player) {
            sendMessage((Player) sender);
        } else C.sendMessage(sender, message
                .replace("<mc>", "" + ChatColor.valueOf(Vitality.getInstance().getLanguageFile().getString("themes.main-color")))
                .replace("<sc>", "" + ChatColor.valueOf(Vitality.getInstance().getLanguageFile().getString("themes.secondary-color")))
                .replace("<tc>", "" + ChatColor.valueOf(Vitality.getInstance().getLanguageFile().getString("themes.third-color")))
                .replace("<ec>", "" + ChatColor.valueOf(Vitality.getInstance().getLanguageFile().getString("themes.error-color"))));
    }

    public void sendMessage(Player player) {
        String message = (String) content;
        Profile profile = Vitality.getInstance().getApi().getApi().getProfileManager().getProfileByID(player.getUniqueId());
        ChatColor mainColor = ChatColor.valueOf(LanguageLocale.MAIN_COLOR.getString());
        ChatColor secondColor = ChatColor.valueOf(LanguageLocale.SECONDARY_COLOR.getString());
        ChatColor errorColor = ChatColor.valueOf(LanguageLocale.ERROR_COLOR.getString());
        C.sendMessage(player, message.replace("<mc>", "" + mainColor)
                .replace("<sc>", "" + secondColor)
                .replace("<ec>", "" + errorColor));
    }

    @SneakyThrows
    public static void init() {
        for (LanguageLocale locale : values()) {
            yamlConfiguration = (YamlConfiguration) Vitality.getInstance().getLanguageFile().getConfiguration();
            if (!yamlConfiguration.contains(locale.getPath())) {
                yamlConfiguration.set(locale.getPath(), locale.getContent());
            }
            locale.setContent(yamlConfiguration.get(locale.getPath()));
            Vitality.getInstance().getLanguageFile().save();
        }
    }
}