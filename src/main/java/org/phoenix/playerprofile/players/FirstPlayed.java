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

package org.phoenix.playerprofile.players;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.utils.Logger;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class FirstPlayed {

    public static String getFirstPlayed(Player player) {
        Date dateFirstPlayed = new Date(player.getFirstPlayed());
        SimpleDateFormat dateFormatFirstPlayed = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        if (ProfileCore.getConfigData().getInstance().getString("Timezone") != null) {
            if (ProfileCore.getConfigData().getInstance().getString("Timezone").equals("default")) {
                dateFormatFirstPlayed.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            } else {
                dateFormatFirstPlayed.setTimeZone(TimeZone.getTimeZone(ProfileCore.getConfigData().getInstance().getString("Timezone")));
            }
        } else {
            throw new DateTimeException("An error occurred while parsing Timezone Date Format! Your Timezone ID is null in Discord Config.");
        }
        return dateFormatFirstPlayed.format(dateFirstPlayed);
    }

    public static String getFirstPlayed(OfflinePlayer offlinePlayer, SlashCommandInteraction slashCommandInteraction) {
        Date dateFirstPlayed = new Date(offlinePlayer.getFirstPlayed());
        SimpleDateFormat dateFormatFirstPlayed = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

        // Check If Language Section is not empty or null
        if (ProfileCore.getConfigData().getInstance().getString("Language") == null) {
            Logger.log(Logger.LogLevel.ERROR, "An error occurred while fetch Language into Language section in config.yml. Please check your Language section in config.yml.");
            return " ";
        }

        if (ProfileCore.getDiscordConfig().getInstance().getString("Timezone") != null) {
            if (ProfileCore.getDiscordConfig().getInstance().getString("Timezone").equals("default")) {
                dateFormatFirstPlayed.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            } else {
                dateFormatFirstPlayed.setTimeZone(TimeZone.getTimeZone(ProfileCore.getDiscordConfig().getInstance().getString("Timezone")));
            }
        } else {
            ProfileCore.getTranslatorAPI().translateErrorMessage(slashCommandInteraction);
            throw new DateTimeException("An error occurred while parsing Timezone Date Format! Your Timezone ID is null in Discord Config.");
        }
        return dateFormatFirstPlayed.format(dateFirstPlayed);
    }
}