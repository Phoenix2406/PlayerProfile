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

package org.phoenix.playerprofile.commands.ingame.subcommands;

import org.bukkit.entity.Player;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.Command;
import org.phoenix.playerprofile.commands.ingame.permission.PermissionsNode;
import org.phoenix.playerprofile.dependencies.PlaceholderAPI.PlaceholderAPIExpansion;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;
import org.phoenix.playerprofile.utils.Logger;

import java.io.IOException;

public class ReloadCommand {

    private final ProfileCore plugin;

    public ReloadCommand(ProfileCore plugin) {
        this.plugin = plugin;
    }

    @Command(name = "reload", description = "Access to reload command", permission = PermissionsNode.PLUGIN_PERMISSION_RELOAD)
    public void onReloadCommandUsed(Player player, String[] args) {
        if (ProfileCore.getConfigData().getInstance().getString("Version").equals(plugin.getDescription().getVersion())) {
            player.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.ReloadSuccessMessage")));
            new PlaceholderAPIExpansion(plugin).unregister();
            new PlaceholderAPIExpansion(plugin).register();
            try {
                ProfileCore.getConfigData().getInstance().update();
                ProfileCore.getDiscordConfig().getInstance().update();
                ProfileCore.getSoundConfig().getInstance().update();
                ProfileCore.getEnLangConfig().getInstance().update();
                ProfileCore.getIdLangConfig().getInstance().update();
                ProfileCore.getConfigData().getInstance().reload();
                ProfileCore.getDiscordConfig().getInstance().reload();
                ProfileCore.getSoundConfig().getInstance().reload();
                ProfileCore.getEnLangConfig().getInstance().reload();
                ProfileCore.getIdLangConfig().getInstance().reload();
            } catch (IOException exception) {
                Log4JLogger.printStackTrace(exception);
            }
        } else {
            player.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.ReloadUnsuccessfulMessage")));
            Logger.log(Logger.LogLevel.WARNING, "Your version plugin in config.yml is corrupted! Try for delete config.yml. Don't forget to backup first!");
            throw new UnsupportedClassVersionError("Your plugin version in config.yml isn't match with default plugin version. Maybe your override that?");
        }
    }
}
