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

import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.Command;
import org.phoenix.playerprofile.commands.ingame.permission.PermissionsNode;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;
import org.bukkit.entity.Player;

public class VersionCommand {

    private final ProfileCore plugin;

    public VersionCommand(ProfileCore plugin) {
        this.plugin = plugin;
    }

    @Command(name = "version", description = "Access to version command", permission = PermissionsNode.PLUGIN_PERMISSION_VERSION)
    public void onVersionCommandUsed(Player player, String[] args) {
        player.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translate("&aPlugin version is &b&n") + plugin.getDescription().getVersion()));
    }
}
