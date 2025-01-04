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

package org.phoenix.playerprofile.objects;

import org.phoenix.playerprofile.ProfileCore;

import java.util.Optional;

public class NullableChecker {

    private NullableChecker() {}
    private static final NullableChecker NULLABLE_CHECKER = new NullableChecker();

    public String getPrefix() {
        return Optional.of(ProfileCore.getConfigData().getInstance().getString("Prefix"))
                .filter(prefix -> !prefix.isEmpty())
                .orElse("&7[&#00D4FFPlayerProfile&7] ");
    }

    public Integer getCooldownIngame() {
        return Optional.of(ProfileCore.getConfigData().getInstance().getInt("CooldownProfile"))
                .filter(cooldown -> cooldown > 0)
                .orElse(10);
    }

    public Integer getCooldownDiscord() {
        return Optional.of(ProfileCore.getDiscordConfig().getInstance().getInt("CooldownCmd"))
                .filter(cooldown -> cooldown > 0)
                .orElse(10);
    }

    public String getPlayerAvatar() {
        return Optional.of(ProfileCore.getDiscordConfig().getInstance().getString("PlayerAvatar"))
                .filter(avatar -> !avatar.isEmpty())
                .orElse("https://cravatar.eu/avatar/%playerprofile_discord_avatar%/64.png");
    }

    public static NullableChecker getInstance() {
        return NULLABLE_CHECKER;
    }
}
