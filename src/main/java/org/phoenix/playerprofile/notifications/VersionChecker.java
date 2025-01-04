/**
 * LICENSE
 * PlayerProfile for RPG System
 * Copyright (C) 2022 Phoenix
 * ----------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 * END
 */

package org.phoenix.playerprofile.notifications;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;
import org.phoenix.playerprofile.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionChecker extends BukkitRunnable {

    private final ProfileCore plugin;
    private static String currentVersion;
    private final String gistRawUrl;
    public static String latestVersion;

    public VersionChecker(ProfileCore plugin, String currentVersion, String gistRawUrl) {
        this.plugin = plugin;
        VersionChecker.currentVersion = currentVersion;
        this.gistRawUrl = gistRawUrl;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(gistRawUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            latestVersion = reader.readLine();
            reader.close();

            Logger.log(Logger.LogLevel.INFO, "Checking new version for PlayerProfile plugin....");
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!latestVersion.equals(currentVersion)) {
                        Logger.log(Logger.LogLevel.INFO, "A new version for PlayerProfile is available. Ask itzthunder_#0000 in discord to get new version of this plugin.");
                    } else {
                        Logger.log(Logger.LogLevel.INFO, "There are no new version for PlayerProfile plugin.");
                    }
                }
            }.runTaskLater(plugin, 100L);
        } catch (IOException exception) {
            Logger.log(Logger.LogLevel.WARNING, "Error while checking for updates: " + exception.getMessage());
        }
    }

    public static String getCurrentVersion() {
        return currentVersion;
    }

    public static String getLatestVersion() {
        return latestVersion;
    }

    public static class VersionListener implements Listener { }
}

class VersionListener implements Listener {

    @EventHandler
    public void onVersionNotify(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (player.isOp() || player.hasPermission("playerprofile.admin.notify.update")) {
            if (VersionChecker.getLatestVersion().equals(VersionChecker.getCurrentVersion())) {
                player.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translate("&aA new version for PlayerProfile is available. Ask itzthunder_#0000 in discord to get new version of this plugin.")));
            } else {
                player.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translate("&aThere are no new version for PlayerProfile plugin.")));
            }
        }
    }
}