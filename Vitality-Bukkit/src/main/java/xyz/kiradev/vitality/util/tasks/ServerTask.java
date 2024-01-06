package xyz.kiradev.vitality.util.tasks;

/*
 *
 * Iron is a property of Kira-Development-Team
 * 28/11/2023
 * Coded by the founders of Kira-Development-Team
 * EmpireMTR & Vifez
 *
 */

import org.bukkit.scheduler.BukkitRunnable;
import xyz.kiradev.vitality.Vitality;

public class ServerTask extends BukkitRunnable {

    private final Vitality plugin;

    public ServerTask(Vitality plugin) {
        this.plugin = plugin;
        runTaskTimerAsynchronously(plugin, 20L, 4 * 20L);
    }

    @Override
    public void run() {
        this.plugin.getCurrentServer().setLastKeepAlive(System.currentTimeMillis());
        this.plugin.getCurrentServer().keepAlive();
    }
}