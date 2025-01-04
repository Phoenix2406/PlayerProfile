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

package org.phoenix.playerprofile.commands.discord.subcommands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.phoenix.playerprofile.DiscordCore;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.JDACommand;
import org.phoenix.playerprofile.commands.discord.CommandConstruction;
import org.phoenix.playerprofile.objects.CooldownTimer;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.utils.Logger;

public class CommandHelp {

    /*
    Register Slash Command Interaction Event
    for Player Profile help command on Discord
    */
    @JDACommand(name = "help", description = "See all command on this plugin via discord.", memberPerms = "Permission.Member")
    public void onHelpCommandUsed(SlashCommandInteractionEvent event) {
        if (ProfileCore.getConfigData().getInstance().getString("Language") == null) {
            Logger.log(Logger.LogLevel.ERROR, "An error occurred while fetch Language into Language section in config.yml. Please check your Language section in config.yml.");
            ProfileCore.getTranslatorAPI().translateErrorMessage(event);
            return;
        }

        if (CooldownTimer.isOnCooldown(event.getUser().getId())) {
            String remainingCooldown = CooldownTimer.getRemainingCooldown(event.getUser().getId());
            event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.CooldownMessage")
                    .replace("%playerprofile_discord_name%", event.getUser().getAsMention())
                    .replace("%playerprofile_discord_cooldown%", remainingCooldown))
                    .setEphemeral(true)
                    .queue();
            return;
        }
        CooldownTimer.setCooldown(event.getUser().getId(), NullableChecker.getInstance().getCooldownDiscord());

        if (DiscordCore.getDiscordCore().getGuild().getVoiceChannelById(ProfileCore.getDiscordConfig().getInstance().getString("ChannelId")) == null) {
            if (DiscordCore.getDiscordCore().getGuild().getTextChannelById(ProfileCore.getDiscordConfig().getInstance().getString("ChannelId")) == null) {
                CommandConstruction.HelpCommand(event);
            } else {
                if (event.getTextChannel().equals(DiscordCore.getDiscordCore().getTextChannel())) {
                    CommandConstruction.HelpCommand(event);
                } else {
                    event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.SpecificChannelMessage")
                                    .replace("%playerprofile_discord_name%", event.getUser().getAsMention())
                                    .replace("%playerprofile_discord_channel%", DiscordCore.getDiscordCore().getTextChannel().getAsMention()))
                            .setEphemeral(true)
                            .queue();
                }
            }
        } else if (DiscordCore.getDiscordCore().getGuild().getVoiceChannelById(ProfileCore.getDiscordConfig().getInstance().getString("ChannelId")) != null) {
            Logger.log(Logger.LogLevel.ERROR, "Can't use Voice Channel Id in DiscordConfig.yml, use instead of Text Channel Id.");
            ProfileCore.getTranslatorAPI().translateErrorMessage(event);
        }
    }
}
