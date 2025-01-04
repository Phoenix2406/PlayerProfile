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

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.phoenix.playerprofile.DiscordCore;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.JDACommand;
import org.phoenix.playerprofile.commands.discord.CommandConstruction;
import org.phoenix.playerprofile.commands.discord.subcommands.libs.TimeZone;
import org.phoenix.playerprofile.dependencies.PlaceholderAPI.PlaceholderAPIExpansion;
import org.phoenix.playerprofile.manager.EmoteManager;
import org.phoenix.playerprofile.objects.CooldownTimer;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;
import org.phoenix.playerprofile.utils.Logger;

import java.awt.*;
import java.io.IOException;

public class CommandReload extends ListenerAdapter {

    private final ProfileCore plugin;

    public CommandReload(ProfileCore plugin) {
        this.plugin = plugin;
    }

    /*
    Register Slash Command Interaction Event
    for Player Profile reload command on Discord
    */
    @JDACommand(name = "reload", description = "Reload plugin via discord.", adminPerms = "Permission.Admin", onlyAdmin = true)
    public void onReloadCommandUsed(SlashCommandInteractionEvent event) {
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
                CommandConstruction.ReloadCommand(plugin, event);
            } else {
                if (event.getTextChannel().equals(DiscordCore.getDiscordCore().getTextChannel())) {
                    CommandConstruction.ReloadCommand(plugin, event);
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

    /*
    Register Button Interaction Event
    for Player Profile reload command on Discord
     */
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getComponentId().equals("yes")) {
            Bukkit.getScheduler().cancelTasks(ProfileCore.getProfileCore());
            EmbedBuilder commandReload = new EmbedBuilder();
            commandReload.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Reload Requested by ") + event.getUser().getAsTag());
            commandReload.setDescription(
                    """
                            \s
                            """ + ProfileCore.getTranslatorAPI().translate(EmoteManager.RELOAD_PROCESS_EMOTE, " PlayerProfile Plugin is reloading via discord.... Please wait a moment....") + """
                            \s""");
            commandReload.setColor(new Color(0, 0, 255));
            commandReload.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
            commandReload.setThumbnail("https://pngimg.com/d/gear_PNG53.png");
            event.editMessageEmbeds(commandReload.build()).setActionRow(Button.primary("waiting_reload", ProfileCore.getTranslatorAPI().translate("Waiting....")).asDisabled()).queue(editMessage -> new BukkitRunnable() {
                @Override
                public void run() {
                    if (ProfileCore.getConfigData().getInstance().getString("Version").equals(plugin.getDescription().getVersion())) {
                                commandReload.setDescription(
                                """
                                        \s
                                        """ + ProfileCore.getTranslatorAPI().translate(EmoteManager.RELOAD_SUCCESS_EMOTE, " Success! Plugin has been reloaded via discord.") + """
                                        \s""");
                        commandReload.setColor(new Color(26, 255, 0));
                        commandReload.clearFields();
                        editMessage.editOriginalEmbeds(commandReload.build()).setActionRow(Button.success("reload_success", ProfileCore.getTranslatorAPI().translate("Reload Success!")).asDisabled()).queue();
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
                        commandReload.setDescription(
                                """
                                        \s
                                        """ + ProfileCore.getTranslatorAPI().translate(EmoteManager.RELOAD_UNSUCCESS_EMOTE, " Error! Plugin can't reload properly via discord. See console for more information.") + """
                                        \s""");
                        commandReload.setColor(new Color(255, 0, 0));
                        editMessage.editOriginalEmbeds(commandReload.build()).setActionRow(Button.danger("reload_unsuccess", ProfileCore.getTranslatorAPI().translate("Reload Unsuccess!")).asDisabled()).queue();
                        Logger.log(Logger.LogLevel.WARNING, "Your version plugin in config.yml is corrupted! Try for delete config.yml. Don't forget to backup first!");
                        throw new UnsupportedClassVersionError("Your plugin version in config.yml isn't match with default plugin version. Maybe your override that?");
                    }
                }
            }.runTaskLater(plugin, 100));
        } else if (event.getComponentId().equals("no")) {
            EmbedBuilder commandReload = new EmbedBuilder();
            commandReload.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Reload Requested by ") + event.getUser().getAsTag());
            commandReload.setDescription(
                    """
                            \s
                            """ + ProfileCore.getTranslatorAPI().translate(EmoteManager.RELOAD_BTN_NO_CLICK_EMOTE, " You cancelled reload of PlayerProfile Plugin") + """
                            \s""");
            commandReload.setColor(new Color(255, 0, 0));
            commandReload.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
            commandReload.setThumbnail("https://pngimg.com/d/gear_PNG53.png");
            event.editMessageEmbeds(commandReload.build()).setActionRow(Button.danger("reload_cancelled", ProfileCore.getTranslatorAPI().translate("Cancelled!")).asDisabled()).queue();
        }
    }
}
