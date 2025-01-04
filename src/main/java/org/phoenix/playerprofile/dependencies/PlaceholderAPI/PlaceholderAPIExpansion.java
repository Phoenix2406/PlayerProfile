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

package org.phoenix.playerprofile.dependencies.PlaceholderAPI;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.phoenix.playerprofile.ProfileCore;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIExpansion extends PlaceholderExpansion {

    private final ProfileCore plugin;

    public PlaceholderAPIExpansion(ProfileCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "phoenix";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "playerprofile";
    }

    @Override
    public @NotNull String getVersion() {
        return "2.0.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        if (identifier.equalsIgnoreCase("ingame_name")) {
            return player == null ? "Unknown" : player.getName();
        }

        if (identifier.equalsIgnoreCase("ingame_target_name")) {
            Player target = plugin.targetMap.get(player.getPlayer());
            return target == null ? "Unknown" : target.getName();
        }
        return null;
    }
}
