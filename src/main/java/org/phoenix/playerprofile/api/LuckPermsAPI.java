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

package org.phoenix.playerprofile.api;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.phoenix.playerprofile.ProfileCore;

public class LuckPermsAPI {

    private static LuckPerms luckPerms;

    public LuckPermsAPI(ProfileCore plugin) {
        luckPerms = plugin.getServer().getServicesManager().load(LuckPerms.class);
    }

    public String getLuckpermsRank(@NotNull OfflinePlayer offlinePlayer) {
        User user = luckPerms.getUserManager().getUser(offlinePlayer.getUniqueId());

        if (user != null) {
            String rankName = user.getPrimaryGroup();
            String firstName = rankName.substring(0, 1);
            String lastName = rankName.substring(1);
            return firstName.toUpperCase() + lastName;
        }

        return "None";
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
