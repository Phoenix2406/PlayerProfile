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

package org.phoenix.playerprofile.commands.ingame.permission;

import org.bukkit.entity.Player;

public enum PermissionsNode {

    PLUGIN_PERMISSION_HELP("playerprofile.user.help"),
    PLUGIN_PERMISSION_INFO("playerprofile.admin.info"),
    PLUGIN_PERMISSION_PROFILE("playerprofile.user.profile"),
    PLUGIN_PERMISSION_RELOAD("playerprofile.admin.reload"),
    PLUGIN_PERMISSION_VERSION("playerprofile.admin.version");

    private final String permission;

    PermissionsNode(String perms) {
        this.permission = perms;
    }

    public boolean checkPermission(Player player) {
        return player.hasPermission(permission);
    }

    public String getPermission() {
        return permission;
    }
}
