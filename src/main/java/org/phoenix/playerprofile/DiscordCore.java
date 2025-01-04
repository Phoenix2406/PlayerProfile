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

package org.phoenix.playerprofile;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.phoenix.playerprofile.commands.discord.SlashCommandHandler;
import org.phoenix.playerprofile.commands.discord.subcommands.*;
import org.phoenix.playerprofile.manager.EmbedManager;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;
import org.phoenix.playerprofile.manager.settings.SettingManager;
import org.phoenix.playerprofile.utils.Logger;
import org.phoenix.playerprofile.utils.StreamingLinkMatcher;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * PlayerProfile's main class, can be accessed via {@link #getDiscordCore()}
 */
public class DiscordCore {

    private DiscordCore() {
    }

    private static final DiscordCore discordCore = new DiscordCore();
    private JDA jda;
    SlashCommandHandler commandHandler;

    private final ScheduledExecutorService threadPool = Executors.newSingleThreadScheduledExecutor();


    public void CreateDiscordBot() {
        commandHandler = new SlashCommandHandler();
        if (ProfileCore.getDiscordConfig().getInstance().getBoolean("Enable")) {
            try {
                // Registering Discord Bot System
                JDABuilder jdaBuilder = JDABuilder.createDefault(ProfileCore.getDiscordConfig().getInstance().getString("BotToken"));

                // Registering Discord Bot Listener
                jdaBuilder.addEventListeners(new SlashCommandHandler.SlashCommandListener());
                jdaBuilder.addEventListeners(new CommandReload(ProfileCore.getProfileCore()));
                jdaBuilder.addEventListeners(new EmbedManager());
                jdaBuilder.addEventListeners(new SettingManager());

                // Trying for Activate/Connect Bot to Discord
                jda = jdaBuilder.build();
                jda.awaitReady();

                // Register commands after jda bot successfully activated
                commandHandler.registerCommands(new CommandHelp());
                commandHandler.registerCommands(new CommandInfo());
                commandHandler.registerCommands(new CommandPlayerlist());
                CommandProfile commandProfile = new CommandProfile();
                commandHandler.registerCommands(commandProfile);
                commandHandler.registerCommands(new CommandSetting());
                commandHandler.registerCommands(new CommandReload(ProfileCore.getProfileCore()));
                commandHandler.registerCommands(new CommandVersion(ProfileCore.getProfileCore()));

                // Updates commands after registering commands
                List<CommandData> registeredCommands = commandHandler.getRegisteredCommands();
                jda.updateCommands().addCommands(registeredCommands).queue();

                // Register discord paginating embed system
                EmbedManager embedManager = new EmbedManager();
                SettingManager settingManager = new SettingManager();
                embedManager.startInteractionChecker();
                settingManager.startInteractionChecker();

                // Setting discord bot activity if discord bot utils is enable
                if (ProfileCore.getDiscordConfig().getInstance().getBoolean("Activity.Enable")) {
                    if (jda != null) {
                        Optional<String> discordActivityMode = ProfileCore.getDiscordConfig().getInstance().getString("Activity.Mode").describeConstable();
                        Optional<String> discordActivityMessage = ProfileCore.getDiscordConfig().getInstance().getString("Activity.Message").describeConstable();
                        if (discordActivityMode.isEmpty()) {
                            Logger.log(Logger.LogLevel.WARNING, "The discord activity mode can't be null. Activity for Discord bot can't run properly.");
                            return;
                        }

                        if (discordActivityMessage.isEmpty()) {
                            Logger.log(Logger.LogLevel.WARNING, "The discord activity message can't be null. Activity for Discord bot can't run properly.");
                            return;
                        }

                        if (ProfileCore.getDiscordConfig().getInstance().getString("Activity.Mode").equals("PLAYING")) {
                            jda.getPresence().setActivity(Activity.playing(discordActivityMessage.get()));
                        } else if (ProfileCore.getDiscordConfig().getInstance().getString("Activity.Mode").equals("COMPETING")) {
                            jda.getPresence().setActivity(Activity.competing(discordActivityMessage.get()));
                        } else if (ProfileCore.getDiscordConfig().getInstance().getString("Activity.Mode").equals("LISTENING")) {
                            jda.getPresence().setActivity(Activity.listening(discordActivityMessage.get()));
                        } else if (ProfileCore.getDiscordConfig().getInstance().getString("Activity.Mode").equals("STREAMING")) {
                            if (ProfileCore.getDiscordConfig().getInstance().getString("Activity.Link") != null) {
                                if (StreamingLinkMatcher.match(ProfileCore.getDiscordConfig().getInstance().getString("Activity.Link"))) {
                                    jda.getPresence().setActivity(Activity.streaming(discordActivityMessage.get(), ProfileCore.getDiscordConfig().getInstance().getString("Activity.Link")));
                                } else {
                                    Logger.log(Logger.LogLevel.WARNING, "Invalid URL for Streaming Activity! Please use valid URL for working properly. (Don't forget to give http:// or htpps:// of the first link)");
                                }
                            } else {
                                Logger.log(Logger.LogLevel.WARNING, "Couldn't parse into link, link configuration is null. Check your discord config.");
                            }
                        } else if (ProfileCore.getDiscordConfig().getInstance().getString("Activity.Mode").equals("WATCHING")) {
                            jda.getPresence().setActivity(Activity.watching(discordActivityMessage.get()));
                        }
                    }
                }
            } catch (LoginException | InterruptedException exception) {
                Log4JLogger.printStackTrace(exception);
            }
        }
    }

    public void shutdownJDA() {
        // Shutdown the Discord Bot and Unregistering Discord Bot System
        commandHandler = null;

        if (jda != null) {
            jda.shutdownNow();
            threadPool.shutdownNow();
        }
    }

    public Guild getGuild() {
        boolean isEnable = ProfileCore.getDiscordConfig().getInstance().getBoolean("Enable");
        return Optional.of(ProfileCore.getDiscordConfig().getInstance().getLong("GuildId"))
                .filter(condition -> isEnable && condition.describeConstable().isPresent())
                .map(guildId -> getJDA().getGuildById(guildId))
                .orElseGet(() -> {
                    for (Guild guild : jda.getGuilds()) {
                        return getJDA().getGuildById(guild.getId());
                    }
                    return null;
                });
    }

    public TextChannel getTextChannel() {
        boolean isEnable = ProfileCore.getDiscordConfig().getInstance().getBoolean("Enable");
        return Optional.of(ProfileCore.getDiscordConfig().getInstance().getLong("ChannelId"))
                .filter(condition -> isEnable && condition.describeConstable().isPresent())
                .map(channelId -> getGuild().getTextChannelById(channelId))
                .orElse(getGuild().getCommunityUpdatesChannel());
    }

    public JDA getJDA() {
        return jda;
    }

    public SlashCommandHandler getCommandHandler() {
        return commandHandler;
    }

    public static DiscordCore getDiscordCore() {
        return discordCore;
    }
}
