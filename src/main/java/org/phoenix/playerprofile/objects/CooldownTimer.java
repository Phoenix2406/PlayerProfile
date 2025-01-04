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

import org.bukkit.entity.Player;
import org.phoenix.playerprofile.ProfileCore;

import java.util.HashMap;

public class CooldownTimer {

    private static HashMap<String, Long> cooldowns;

    public CooldownTimer() { }

    public static void setupCooldown() {
        cooldowns = new HashMap<>();
    }

    public static void setCooldown(String player, int seconds) {
        long cooldownTime = System.currentTimeMillis() + convertToLong(seconds);
        cooldowns.put(player, cooldownTime);
    }

    public static void removeCooldown(Player player) {
        cooldowns.remove(player.getName());
    }

    public static String getRemainingCooldown(String player) {
        if (!cooldowns.containsKey(player)) {
            return ProfileCore.getTranslatorAPI().translate("0H 0M 0S");
        }

        long cooldownTime = cooldowns.get(player);
        long currentTime = System.currentTimeMillis();

        if (currentTime < cooldownTime) {
            long remainingTime = cooldownTime - currentTime;

            long seconds = (remainingTime / 1000) % 60;
            long minutes = (remainingTime / (1000 * 60)) % 60;
            long hours = (remainingTime / (1000 * 60 * 60)) % 24;

            if (ProfileCore.getConfigData().getInstance().getString("Language").equals("en_US")) {
                return String.format("%02dH %02dM %02dS", hours, minutes, seconds);
            } else if (ProfileCore.getConfigData().getInstance().getString("Language").equals("ind_ID")) {
                return String.format("%02dJ %02dM %02dD", hours, minutes, seconds);
            }
        } else {
            return ProfileCore.getTranslatorAPI().translate("Cooldown expired");
        }
        return ProfileCore.getTranslatorAPI().translate("Cooldown invalid");
    }

    public static boolean isOnCooldown(String player) {
        if (!cooldowns.containsKey(player)) {
            return false;
        }

        long cooldownTime = cooldowns.get(player);
        long currentTime = System.currentTimeMillis();

        return currentTime < cooldownTime;
    }

    private static long convertToLong(int seconds) {
        return seconds * 1000L;
    }
}
