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
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.Modal;
import net.dv8tion.jda.api.interactions.components.selections.SelectMenu;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import org.phoenix.playerprofile.DiscordCore;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.JDACommand;
import org.phoenix.playerprofile.commands.discord.subcommands.libs.TimeZone;
import org.phoenix.playerprofile.objects.CooldownTimer;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.manager.settings.SettingManager;
import org.phoenix.playerprofile.utils.Logger;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandSetting {

    /*
    Register Slash Command Interaction Event
    for Player Profile help command on Discord
    */
    @JDACommand(name = "setting", description = "Setting plugin configuration via discord.", adminPerms = "Permission.Admin", onlyAdmin = true)
    public void onSettingCommandUsed(SlashCommandInteractionEvent event) {
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
                Map<List<MessageEmbed>, List<SelectMenu>> pages = new HashMap<>();
                Map<SelectMenu, List<Modal>> modalPages = new HashMap<>();

                pages.put(List.of(SettingPaginating.ConfigPage(event),
                                SettingPaginating.DiscordConfigPage(event),
                                SettingPaginating.SoundConfigPage(event),
                                SettingPaginating.DiscordEmotePage(event),
                                SettingPaginating.ENLangPage(event),
                                SettingPaginating.IDLangPage(event)),
                        List.of(SettingSelectMenu.ConfigSelectMenu(),
                                SettingSelectMenu.DiscordConfigSelectMenu(),
                                SettingSelectMenu.SoundConfigSelectMenu(),
                                SettingSelectMenu.DiscordEmoteSelectMenu(),
                                SettingSelectMenu.ENLangSelectMenu(),
                                SettingSelectMenu.IDLangSelectMenu()));
                modalPages.put(SettingSelectMenu.ConfigSelectMenu(), SettingModals.ConfigModals());
                modalPages.put(SettingSelectMenu.DiscordConfigSelectMenu(), SettingModals.DiscordConfigModals());
                modalPages.put(SettingSelectMenu.SoundConfigSelectMenu(), SettingModals.SoundConfigModals());
                modalPages.put(SettingSelectMenu.DiscordEmoteSelectMenu(), SettingModals.DiscordEmoteModals());
                modalPages.put(SettingSelectMenu.ENLangSelectMenu(), SettingModals.ENLangModals());
                modalPages.put(SettingSelectMenu.IDLangSelectMenu(), SettingModals.IDLangModals());

                SettingManager.createPaginated(event.getTextChannel(), (event.getMember() == null ? 0L : event.getMember().getIdLong()), pages, modalPages);
                SettingManager.updateInteraction((event.getMember() == null ? 0L : event.getMember().getIdLong()));
            } else {
                if (event.getTextChannel().equals(DiscordCore.getDiscordCore().getTextChannel())) {
                    Map<List<MessageEmbed>, List<SelectMenu>> pages = new HashMap<>();
                    Map<SelectMenu, List<Modal>> modalPages = new HashMap<>();

                    pages.put(List.of(SettingPaginating.ConfigPage(event),
                                    SettingPaginating.DiscordConfigPage(event),
                                    SettingPaginating.SoundConfigPage(event),
                                    SettingPaginating.DiscordEmotePage(event),
                                    SettingPaginating.ENLangPage(event),
                                    SettingPaginating.IDLangPage(event)),
                            List.of(SettingSelectMenu.ConfigSelectMenu(),
                                    SettingSelectMenu.DiscordConfigSelectMenu(),
                                    SettingSelectMenu.SoundConfigSelectMenu(),
                                    SettingSelectMenu.DiscordEmoteSelectMenu(),
                                    SettingSelectMenu.ENLangSelectMenu(),
                                    SettingSelectMenu.IDLangSelectMenu()));
                    modalPages.put(SettingSelectMenu.ConfigSelectMenu(), SettingModals.ConfigModals());
                    modalPages.put(SettingSelectMenu.DiscordConfigSelectMenu(), SettingModals.DiscordConfigModals());
                    modalPages.put(SettingSelectMenu.SoundConfigSelectMenu(), SettingModals.SoundConfigModals());
                    modalPages.put(SettingSelectMenu.DiscordEmoteSelectMenu(), SettingModals.DiscordEmoteModals());
                    modalPages.put(SettingSelectMenu.ENLangSelectMenu(), SettingModals.ENLangModals());
                    modalPages.put(SettingSelectMenu.IDLangSelectMenu(), SettingModals.IDLangModals());

                    SettingManager.createPaginated(event.getTextChannel(), (event.getMember() == null ? 0L : event.getMember().getIdLong()), pages, modalPages);
                    SettingManager.updateInteraction((event.getMember() == null ? 0L : event.getMember().getIdLong()));
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

class SettingPaginating {

    public static MessageEmbed ConfigPage(SlashCommandInteractionEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Setting Requested by ") + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                                                                             \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] CONFIG SETTING EDITOR :**") + "\n" +
                        "                                                                                                                                                                                     \n" +
                        ProfileCore.getTranslatorAPI().translate("Setting editor for %config_file% via discord, you can select options to").replace("%config_file%", "config.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("edit path in %config_file% from selection menu below. Don't forget to").replace("%config_file%", "config.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("click reload button to reload all configuration in plugin folder!") + "\n" +
                        "                                                                                                                                                                                    \n" +
                        ProfileCore.getTranslatorAPI().translate("**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.") + "\n" +
                        "                                                                                                                                                                                       ");
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        return builder.build();
    }

    public static MessageEmbed DiscordConfigPage(SlashCommandInteractionEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Setting Requested by ") + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] DISCORD CONFIG SETTING EDITOR :**") + "\n" +
                        "                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("Setting editor for %config_file% via discord, you can select options to").replace("%config_file%", "DiscordConfig.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("edit path in %config_file% from selection menu below. Don't forget to").replace("%config_file%", "DiscordConfig.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("click reload button to reload all configuration in plugin folder!") + "\n" +
                        "                                                                                                                                                                                    \n" +
                        ProfileCore.getTranslatorAPI().translate("**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.") + "\n" +
                        "                                                                                                                            ");
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        return builder.build();
    }

    public static MessageEmbed SoundConfigPage(SlashCommandInteractionEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Setting Requested by ") + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] SOUND CONFIG SETTING EDITOR :**") + "\n" +
                        "                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("Setting editor for %config_file% via discord, you can select options to").replace("%config_file%", "SoundConfig.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("edit path in %config_file% from selection menu below. Don't forget to").replace("%config_file%", "SoundConfig.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("click reload button to reload all configuration in plugin folder!") + "\n" +
                        "                                                                                                                                                                                    \n" +
                        ProfileCore.getTranslatorAPI().translate("**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.") + "\n" +
                        "                                                                                                                            ");
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        return builder.build();
    }

    public static MessageEmbed DiscordEmotePage(SlashCommandInteractionEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Setting Requested by ") + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] DISCORD EMOTE SETTING EDITOR :**") + "\n" +
                        "                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("Setting editor for %config_file% via discord, you can select options to").replace("%config_file%", "DiscordEmote.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("edit path in %config_file% from selection menu below. Don't forget to").replace("%config_file%", "DiscordEmote.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("click reload button to reload all configuration in plugin folder!") + "\n" +
                        "                                                                                                                                                                                    \n" +
                        ProfileCore.getTranslatorAPI().translate("**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.") + "\n" +
                        "                                                                                                                            ");
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        return builder.build();
    }

    public static MessageEmbed ENLangPage(SlashCommandInteractionEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Setting Requested by ") + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] ENGLISH LANGUAGE SETTING EDITOR :**") + "\n" +
                        "                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("Setting editor for %config_file% via discord, you can select options to").replace("%config_file%", "en_US.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("edit path in %config_file% from selection menu below. Don't forget to").replace("%config_file%", "en_US.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("click reload button to reload all configuration in plugin folder!") + "\n" +
                        "                                                                                                                                                                                    \n" +
                        ProfileCore.getTranslatorAPI().translate("**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.") + "\n" +
                        "                                                                                                                            ");
        builder.addField(
                ProfileCore.getTranslatorAPI().translate("Available Placeholder:"),
                ProfileCore.getTranslatorAPI().translate("**•) %ingame_target_name% :** return target player name.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_ingame_cooldown% :** return player cooldown to see profile ingame.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_ingame_permission% :** return permission ingame.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_discord_name% :** return discord user name.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_discord_cooldown% :** return user cooldown to see profile in discord.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_discord_permission% :** return permission in discord."),
                true);
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        return builder.build();
    }

    public static MessageEmbed IDLangPage(SlashCommandInteractionEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Setting Requested by ") + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] INDONESIAN LANGUAGE SETTING EDITOR :**") + "\n" +
                        "                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("Setting editor for %config_file% via discord, you can select options to").replace("%config_file%", "ind_ID.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("edit path in %config_file% from selection menu below. Don't forget to").replace("%config_file%", "ind_ID.yml") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("click reload button to reload all configuration in plugin folder!") + "\n" +
                        "                                                                                                                                                                                    \n" +
                        ProfileCore.getTranslatorAPI().translate("**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.") + "\n" +
                        "                                                                                                                            ");
        builder.addField(
                ProfileCore.getTranslatorAPI().translate("Available Placeholder:"),
                ProfileCore.getTranslatorAPI().translate("**•) %ingame_target_name% :** return target player name.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_ingame_cooldown% :** return player cooldown to see profile ingame.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_ingame_permission% :** return permission ingame.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_discord_name% :** return discord user name.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_discord_cooldown% :** return user cooldown to see profile in discord.") + "\n"
                        + ProfileCore.getTranslatorAPI().translate("**•) %playerprofile_discord_permission% :** return permission in discord."),
                true);
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        return builder.build();
    }
}

class SettingSelectMenu {

    public static SelectMenu ConfigSelectMenu() {
        SelectMenu.Builder selectMenu = SelectMenu.create("config.yml");
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 1", "section_1", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 1"));
        return selectMenu.build();
    }

    public static SelectMenu DiscordConfigSelectMenu() {
        SelectMenu.Builder selectMenu = SelectMenu.create("DiscordConfig.yml");
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 1", "section_1", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 1"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 2", "section_2", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 2"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 3", "section_3", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 3"));
        return selectMenu.build();
    }

    public static SelectMenu SoundConfigSelectMenu() {
        SelectMenu.Builder selectMenu = SelectMenu.create("SoundConfig.yml");
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 1", "section_1", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 1"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 2", "section_2", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 2"));
        return selectMenu.build();
    }

    public static SelectMenu DiscordEmoteSelectMenu() {
        SelectMenu.Builder selectMenu = SelectMenu.create("DiscordEmote.yml");
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 1", "section_1", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 1"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 2", "section_2", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 2"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 3", "section_3", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 3"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 4", "section_4", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 4"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 5", "section_5", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 5"));
        return selectMenu.build();
    }

    public static SelectMenu ENLangSelectMenu() {
        SelectMenu.Builder selectMenu = SelectMenu.create("en_US.yml");
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 1", "section_1", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 1"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 2", "section_2", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 2"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 3", "section_3", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 3"));
        return selectMenu.build();
    }

    public static SelectMenu IDLangSelectMenu() {
        SelectMenu.Builder selectMenu = SelectMenu.create("en_US.yml");
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 1", "section_1", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 1"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 2", "section_2", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 2"));
        selectMenu.addOption(ProfileCore.getTranslatorAPI().translate("Section") + " 3", "section_3", ProfileCore.getTranslatorAPI().translate("Change path in %config_file% section").replace("%config_file%", selectMenu.getId() + " 3"));
        return selectMenu.build();
    }
}

class SettingModals {

    public static List<Modal> ConfigModals() {
        // <-- Section 1 of Config Editor Setting -->
        TextInput langPath = TextInput.create("Language", "Language Path", TextInputStyle.SHORT)
                .setPlaceholder("Available: en_US and ind_ID")
                .setRequiredRange(5, 6)
                .build();

        TextInput prefixPath = TextInput.create("Prefix", "Prefix Path", TextInputStyle.SHORT)
                .setPlaceholder("Support: Color Code, Color Hex, and Unicode Chat")
                .build();

        TextInput GUIPath = TextInput.create("GUI", "GUI Path", TextInputStyle.SHORT)
                .setPlaceholder("Available: true or false")
                .setRequiredRange(4, 5)
                .build();

        TextInput cooldownProfilePath = TextInput.create("CooldownProfile", "Cooldown Profile Path", TextInputStyle.SHORT)
                .setPlaceholder("Recommended up to 10 seconds")
                .setRequiredRange(1, 5)
                .build();

        TextInput timezonePath = TextInput.create("Timezone", "Timezone Path", TextInputStyle.SHORT)
                .setPlaceholder("List Timezone: https://en.wikipedia.org/wiki/List_of_tz_database_time_zones")
                .build();

        Modal modal = Modal.create("config.yml", "Config Setting")
                .addActionRows(
                        ActionRow.of(langPath),
                        ActionRow.of(prefixPath),
                        ActionRow.of(GUIPath),
                        ActionRow.of(cooldownProfilePath),
                        ActionRow.of(timezonePath))
                .build();

        return List.of(modal);
    }

    public static List<Modal> DiscordConfigModals() {
        // <-- Section 1 of Discord Config Editor Setting -->
        TextInput dcEnablePath = TextInput.create("Enable", "Is Discord Enable?", TextInputStyle.SHORT)
                .setPlaceholder("Available: true or false")
                .setRequiredRange(4, 5)
                .build();

        TextInput botTokenPath = TextInput.create("BotToken", "Bot Token Path", TextInputStyle.SHORT)
                .setPlaceholder("Tutorial for creating bot: https://discordpy.readthedocs.io/en/stable/discord.html")
                .setRequiredRange(72, 75)
                .build();

        TextInput guildIdPath = TextInput.create("GuildId", "GuildId Path", TextInputStyle.SHORT)
                .setPlaceholder("Ex: 0000000000000000000")
                .setRequiredRange(19, 20)
                .build();

        TextInput channelIdPath = TextInput.create("ChannelId", "ChannelId Path", TextInputStyle.SHORT)
                .setPlaceholder("Ex: 0000000000000000000")
                .setRequiredRange(19, 20)
                .build();

        Modal modalSection1 = Modal.create("DiscordConfig.yml", "Discord Config Setting")
                .addActionRows(
                        ActionRow.of(dcEnablePath),
                        ActionRow.of(botTokenPath),
                        ActionRow.of(guildIdPath),
                        ActionRow.of(channelIdPath))
                .build();

        // <-- Section 2 of Discord Config Editor Setting -->
        TextInput permsAdminPath = TextInput.create("Permission.Admin", "Permission Admin Path", TextInputStyle.SHORT)
                .setPlaceholder("Ex: 0000000000000000000")
                .setRequiredRange(19, 20)
                .build();

        TextInput permsMemberPath = TextInput.create("Permission.Member", "Permission Member Path", TextInputStyle.SHORT)
                .setPlaceholder("Ex: 0000000000000000000")
                .setRequiredRange(19, 20)
                .build();

        TextInput cooldownCmdPath = TextInput.create("CooldownCmd", "Cooldown Command Path", TextInputStyle.SHORT)
                .setPlaceholder("Recommended up to 10 seconds")
                .setRequiredRange(1, 5)
                .build();

        TextInput paginatingPath = TextInput.create("Paginating", "Paginating Path", TextInputStyle.SHORT)
                .setPlaceholder("Available Type: SINGLE and MULTIPLE")
                .setRequiredRange(6, 8)
                .build();

        Modal modalSection2 = Modal.create("DiscordConfig.yml", "Discord Config Setting")
                .addActionRows(
                        ActionRow.of(permsAdminPath),
                        ActionRow.of(permsMemberPath),
                        ActionRow.of(cooldownCmdPath),
                        ActionRow.of(paginatingPath))
                .build();

        // <-- Section 3 of Discord Config Editor Setting -->
        TextInput activityEnablePath = TextInput.create("Activity.Enable", "Is Activity Enable?", TextInputStyle.SHORT)
                .setPlaceholder("Available: true or false")
                .setRequiredRange(4, 5)
                .build();

        TextInput activityModePath = TextInput.create("Activity.Mode", "Activity Mode Path", TextInputStyle.SHORT)
                .setPlaceholder("Available: PLAYING, COMPETING, LISTENING, STREAMING, WATCHING")
                .setRequiredRange(19, 20)
                .build();

        TextInput activityMessagePath = TextInput.create("Activity.Message", "Activity Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want in activity")
                .build();

        TextInput activityLinkPath = TextInput.create("Activity.Link", "Activity Link Path", TextInputStyle.SHORT)
                .setPlaceholder("Only work if Activity Mode is STREAMING")
                .build();

        TextInput timezonePath = TextInput.create("Timezone", "Timezone Path", TextInputStyle.SHORT)
                .setPlaceholder("List Timezone: https://en.wikipedia.org/wiki/List_of_tz_database_time_zones")
                .build();

        Modal modalSection3 = Modal.create("DiscordConfig.yml", "Discord Config Setting")
                .addActionRows(
                        ActionRow.of(activityEnablePath),
                        ActionRow.of(activityModePath),
                        ActionRow.of(activityMessagePath),
                        ActionRow.of(activityLinkPath),
                        ActionRow.of(timezonePath))
                .build();

        return List.of(modalSection1, modalSection2, modalSection3);
    }

    public static List<Modal> SoundConfigModals() {
        // <-- Section 1 of Sound Config Editor Setting -->
        TextInput cdCommandPath = TextInput.create("ON_COOLDOWN_COMMAND", "On Cooldown Command Path", TextInputStyle.SHORT)
                .setPlaceholder("Sound List: https://www.digminecraft.com/lists/sound_list_pc.php")
                .build();

        TextInput viewProfilePath = TextInput.create("ON_VIEW_PROFILE_CHAT", "On View Profile Chat Path", TextInputStyle.SHORT)
                .setPlaceholder("Sound List: https://www.digminecraft.com/lists/sound_list_pc.php")
                .build();

        TextInput invOpenPath = TextInput.create("ON_INVENTORY_OPEN", "On Inventory Open Path", TextInputStyle.SHORT)
                .setPlaceholder("Sound List: https://www.digminecraft.com/lists/sound_list_pc.php")
                .build();

        Modal modalSection1 = Modal.create("SoundConfig.yml", "Sound Config Setting")
                .addActionRows(
                        ActionRow.of(cdCommandPath),
                        ActionRow.of(viewProfilePath),
                        ActionRow.of(invOpenPath))
                .build();

        // <-- Section 2 of Sound Config Editor Setting -->
        TextInput invClosePath = TextInput.create("ON_INVENTORY_CLOSE", "On Inventory Close Path", TextInputStyle.SHORT)
                .setPlaceholder("Sound List: https://www.digminecraft.com/lists/sound_list_pc.php")
                .build();

        TextInput invNextPagePath = TextInput.create("ON_INVENTORY_NEXT_PAGE", "On Inventory Next Page Path", TextInputStyle.SHORT)
                .setPlaceholder("Sound List: https://www.digminecraft.com/lists/sound_list_pc.php")
                .build();

        TextInput invPrevPagePath = TextInput.create("ON_INVENTORY_PREVIOUS_PAGE", "On Inventory Previous Page Path", TextInputStyle.SHORT)
                .setPlaceholder("Sound List: https://www.digminecraft.com/lists/sound_list_pc.php")
                .build();

        Modal modalSection2 = Modal.create("SoundConfig.yml", "Sound Config Setting")
                .addActionRows(
                        ActionRow.of(invClosePath),
                        ActionRow.of(invNextPagePath),
                        ActionRow.of(invPrevPagePath))
                .build();

        return List.of(modalSection1, modalSection2);
    }

    public static List<Modal> DiscordEmoteModals() {
        // <-- Section 1 of Discord Emote Editor Setting -->
        TextInput emoteEnablePath = TextInput.create("Enable", "Is Emote Feature Enable?", TextInputStyle.SHORT)
                .setPlaceholder("Available: true or false")
                .setRequiredRange(4, 5)
                .build();

        TextInput profileNamePath = TextInput.create("EmojiId.ProfileCMD.Name", "Profile Name Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id name of player>")
                .build();

        TextInput profileRankPath = TextInput.create("EmojiId.ProfileCMD.Rank", "Profile Rank Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id rank of player>")
                .build();

        TextInput profileLastSeenPath = TextInput.create("EmojiId.ProfileCMD.LastSeen", "Profile Last Seen Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id last seen of player>")
                .build();

        TextInput profileFirstJoinPath = TextInput.create("EmojiId.ProfileCMD.FirstJoin", "Profile First Join Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id first join of player>")
                .build();

        Modal modalSection1 = Modal.create("DiscordEmote.yml", "Discord Emote Setting")
                .addActionRows(
                        ActionRow.of(emoteEnablePath),
                        ActionRow.of(profileNamePath),
                        ActionRow.of(profileRankPath),
                        ActionRow.of(profileLastSeenPath),
                        ActionRow.of(profileFirstJoinPath))
                .build();

        // <-- Section 2 of Discord Emote Editor Setting -->
        TextInput profileLevelsExpPath = TextInput.create("EmojiId.ProfileCMD.TotalLevelsExp", "Profile Total Levels Exp Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total exp of player>")
                .build();

        TextInput profileTotalPlaytimePath = TextInput.create("EmojiId.ProfileCMD.TotalPlaytime", "Profile Total Playtime Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total playtime of player>")
                .build();

        TextInput profileTotalMobsKillPath = TextInput.create("EmojiId.ProfileCMD.TotalMobsKill", "Profile Total Mobs Kill Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id mobs kill of player>")
                .build();

        TextInput profileClassPath = TextInput.create("EmojiId.ProfileCMD.Class", "Profile Class Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id class of player>")
                .build();

        TextInput profileMMOLevelsPath = TextInput.create("EmojiId.ProfileCMD.MMOLevels", "Profile MMO Levels Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id MMO level of player>")
                .build();

        Modal modalSection2 = Modal.create("DiscordEmote.yml", "Discord Emote Setting")
                .addActionRows(
                        ActionRow.of(profileLevelsExpPath),
                        ActionRow.of(profileTotalPlaytimePath),
                        ActionRow.of(profileTotalMobsKillPath),
                        ActionRow.of(profileClassPath),
                        ActionRow.of(profileMMOLevelsPath))
                .build();

        // <-- Section 3 of Discord Emote Editor Setting -->
        TextInput profileTotalHealthPath = TextInput.create("EmojiId.ProfileCMD.TotalHealth", "Profile Total Health Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total health of player>")
                .build();

        TextInput profileTotalHealthRegenPath = TextInput.create("EmojiId.ProfileCMD.TotalHealthRegen", "Profile Total Health Regen Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total health regen of player>")
                .build();

        TextInput profileTotalManaPath = TextInput.create("EmojiId.ProfileCMD.TotalMana", "Profile Total Mana Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total mana of player>")
                .build();

        TextInput profileTotalManaRegenPath = TextInput.create("EmojiId.ProfileCMD.TotalManaRegen", "Profile Total Mana Regen Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total mana regen of player>")
                .build();

        TextInput profileTotalStaminaPath = TextInput.create("EmojiId.ProfileCMD.TotalStamina", "Profile Total Stamina Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total stamina of player>")
                .build();

        Modal modalSection3 = Modal.create("DiscordEmote.yml", "Discord Emote Setting")
                .addActionRows(
                        ActionRow.of(profileTotalHealthPath),
                        ActionRow.of(profileTotalHealthRegenPath),
                        ActionRow.of(profileTotalManaPath),
                        ActionRow.of(profileTotalManaRegenPath),
                        ActionRow.of(profileTotalStaminaPath))
                .build();

        // <-- Section 4 of Discord Emote Editor Setting -->
        TextInput profileTotalStaminaRegenPath = TextInput.create("EmojiId.ProfileCMD.TotalStaminaRegen", "Profile Total Stamina Regen Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total stamina regen of player>")
                .build();

        TextInput profileTotalStelliumPath = TextInput.create("EmojiId.ProfileCMD.TotalStellium", "Profile Total Stellium Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total stellium of player>")
                .build();

        TextInput profileTotalStelliumRegenPath = TextInput.create("EmojiId.ProfileCMD.TotalStelliumRegen", "Profile Total Stellium Regen Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id total stellium regen of player>")
                .build();

        TextInput reloadTimeoutPath = TextInput.create("EmojiId.ReloadCMD.ReloadTimeout", "Reload Timeout Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id of reload timeout>")
                .build();

        TextInput reloadAskPath = TextInput.create("EmojiId.ReloadCMD.ReloadAsk", "Reload Ask Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id of reload ask>")
                .build();

        Modal modalSection4 = Modal.create("DiscordEmote.yml", "Discord Emote Setting")
                .addActionRows(
                        ActionRow.of(profileTotalStaminaRegenPath),
                        ActionRow.of(profileTotalStelliumPath),
                        ActionRow.of(profileTotalStelliumRegenPath),
                        ActionRow.of(reloadTimeoutPath),
                        ActionRow.of(reloadAskPath))
                .build();

        // <-- Section 5 of Discord Emote Editor Setting -->
        TextInput reloadBtnNoPath = TextInput.create("EmojiId.ReloadCMD.ReloadBtnNo", "Reload Button No Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id of reload button NO>")
                .build();

        TextInput reloadProcessPath = TextInput.create("EmojiId.ReloadCMD.ReloadProcess", "Reload Process Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id of reload process>")
                .build();

        TextInput reloadSuccessPath = TextInput.create("EmojiId.ReloadCMD.TotalStelliumRegen", "Profile Total Stellium Regen Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id of reload success>")
                .build();

        TextInput reloadUnsuccessPath = TextInput.create("EmojiId.ReloadCMD.ReloadUnsuccess", "Reload Unsuccess Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id of reload unsuccess>")
                .build();

        TextInput versionCheckerPath = TextInput.create("EmojiId.VersionCMD.VersionChecker", "Version Checker Path", TextInputStyle.SHORT)
                .setPlaceholder("<Emoji Id of reload unsuccess>")
                .build();

        Modal modalSection5 = Modal.create("DiscordEmote.yml", "Discord Emote Setting")
                .addActionRows(
                        ActionRow.of(reloadBtnNoPath),
                        ActionRow.of(reloadProcessPath),
                        ActionRow.of(reloadSuccessPath),
                        ActionRow.of(reloadUnsuccessPath),
                        ActionRow.of(versionCheckerPath))
                .build();

        return List.of(modalSection1, modalSection2, modalSection3, modalSection4, modalSection5);
    }

    public static List<Modal> ENLangModals() {
        // <-- Section 1 of English Language Editor Setting -->
        TextInput viewProfileMsgPath = TextInput.create("en_US.InGameMessage.ViewingProfileMessage", "Viewing Profile Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for viewing profile")
                .build();

        TextInput invalidPlayerMsgPath = TextInput.create("en_US.InGameMessage.InvalidPlayerMessage", "Invalid Player Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for invalid player")
                .build();

        TextInput cooldownMsgPath = TextInput.create("en_US.InGameMessage.CooldownMessage", "Cooldown Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for cooldown")
                .build();

        TextInput reloadSuccessMsgPath = TextInput.create("en_US.InGameMessage.ReloadSuccessMessage", "Reload Success Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for reload success")
                .build();

        TextInput reloadUnsuccessfulMsgPath = TextInput.create("en_US.InGameMessage.ReloadUnsuccessfulMessage", "Reload Unsuccessful Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for reload unsuccess")
                .build();

        Modal modalSection1 = Modal.create("en_US.yml", "English Language Setting")
                .addActionRows(
                        ActionRow.of(viewProfileMsgPath),
                        ActionRow.of(invalidPlayerMsgPath),
                        ActionRow.of(cooldownMsgPath),
                        ActionRow.of(reloadSuccessMsgPath),
                        ActionRow.of(reloadUnsuccessfulMsgPath))
                .build();

        // <-- Section 2 of English Language Editor Setting -->
        TextInput invalidArgMsgPath = TextInput.create("en_US.InGameMessage.InvalidArgMessage", "Invalid Argument Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for invalid argument")
                .build();

        TextInput noSelfTargetMsgPath = TextInput.create("en_US.InGameMessage.NoSelfTargetMessage", "No Self Target Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for no self target")
                .build();

        TextInput permissionMsgPath = TextInput.create("en_US.InGameMessage.PermissionMessage", "Permission Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for permission")
                .build();

        TextInput successRetrievePlayerPath = TextInput.create("en_US.DiscordMessage.SuccessRetrievePlayer", "Discord Success Retrieve Player Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord success retrieve player")
                .build();

        TextInput noPlayerMsgPath = TextInput.create("en_US.DiscordMessage.NoPlayerMessage", "Discord No Player Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord no player")
                .build();

        Modal modalSection2 = Modal.create("en_US.yml", "English Language Setting")
                .addActionRows(
                        ActionRow.of(invalidArgMsgPath),
                        ActionRow.of(noSelfTargetMsgPath),
                        ActionRow.of(permissionMsgPath),
                        ActionRow.of(successRetrievePlayerPath),
                        ActionRow.of(noPlayerMsgPath))
                .build();

        // <-- Section 3 of English Language Editor Setting -->
        TextInput dcInvalidArgMsgPath = TextInput.create("en_US.DiscordMessage.InvalidArgMessage", "Discord Invalid Argument Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord invalid argument")
                .build();

        TextInput dcCooldownMsgPath = TextInput.create("en_US.DiscordMessage.CooldownMessage", "Discord Cooldown Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord cooldown")
                .build();

        TextInput specificChannelMsgPath = TextInput.create("en_US.DiscordMessage.SpecificChannelMessage", "Discord Specific Channel Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord specific channel")
                .build();

        TextInput dcPermissionMsgPath = TextInput.create("en_US.DiscordMessage.PermissionMessage", "Discord Permission Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord permission")
                .build();

        Modal modalSection3 = Modal.create("en_US.yml", "English Language Setting")
                .addActionRows(
                        ActionRow.of(dcInvalidArgMsgPath),
                        ActionRow.of(dcCooldownMsgPath),
                        ActionRow.of(specificChannelMsgPath),
                        ActionRow.of(dcPermissionMsgPath))
                .build();

        return List.of(modalSection1, modalSection2, modalSection3);
    }

    public static List<Modal> IDLangModals() {
        // <-- Section 1 of Indonesian Language Editor Setting -->
        TextInput viewProfileMsgPath = TextInput.create("ind_ID.InGameMessage.ViewingProfileMessage", "Viewing Profile Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for viewing profile")
                .build();

        TextInput invalidPlayerMsgPath = TextInput.create("ind_ID.InGameMessage.InvalidPlayerMessage", "Invalid Player Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for invalid player")
                .build();

        TextInput cooldownMsgPath = TextInput.create("ind_ID.InGameMessage.CooldownMessage", "Cooldown Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for cooldown")
                .build();

        TextInput reloadSuccessMsgPath = TextInput.create("ind_ID.InGameMessage.ReloadSuccessMessage", "Reload Success Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for reload success")
                .build();

        TextInput reloadUnsuccessfulMsgPath = TextInput.create("ind_ID.InGameMessage.ReloadUnsuccessfulMessage", "Reload Unsuccessful Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for reload unsuccess")
                .build();

        Modal modalSection1 = Modal.create("ind_ID.yml", "Indonesian Language Setting")
                .addActionRows(
                        ActionRow.of(viewProfileMsgPath),
                        ActionRow.of(invalidPlayerMsgPath),
                        ActionRow.of(cooldownMsgPath),
                        ActionRow.of(reloadSuccessMsgPath),
                        ActionRow.of(reloadUnsuccessfulMsgPath))
                .build();

        // <-- Section 2 of Indonesian Language Editor Setting -->
        TextInput invalidArgMsgPath = TextInput.create("ind_ID.InGameMessage.InvalidArgMessage", "Invalid Argument Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for invalid argument")
                .build();

        TextInput noSelfTargetMsgPath = TextInput.create("ind_ID.InGameMessage.NoSelfTargetMessage", "No Self Target Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for no self target")
                .build();

        TextInput permissionMsgPath = TextInput.create("ind_ID.InGameMessage.PermissionMessage", "Permission Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for permission")
                .build();

        TextInput successRetrievePlayerPath = TextInput.create("ind_ID.DiscordMessage.SuccessRetrievePlayer", "Discord Success Retrieve Player Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord success retrieve player")
                .build();

        TextInput noPlayerMsgPath = TextInput.create("ind_ID.DiscordMessage.NoPlayerMessage", "Discord No Player Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord no player")
                .build();

        Modal modalSection2 = Modal.create("ind_ID.yml", "Indonesian Language Setting")
                .addActionRows(
                        ActionRow.of(invalidArgMsgPath),
                        ActionRow.of(noSelfTargetMsgPath),
                        ActionRow.of(permissionMsgPath),
                        ActionRow.of(successRetrievePlayerPath),
                        ActionRow.of(noPlayerMsgPath))
                .build();

        // <-- Section 3 of Indonesian Language Editor Setting -->
        TextInput dcInvalidArgMsgPath = TextInput.create("ind_ID.DiscordMessage.InvalidArgMessage", "Discord Invalid Argument Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord invalid argument")
                .build();

        TextInput dcCooldownMsgPath = TextInput.create("ind_ID.DiscordMessage.CooldownMessage", "Discord Cooldown Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord cooldown")
                .build();

        TextInput specificChannelMsgPath = TextInput.create("ind_ID.DiscordMessage.SpecificChannelMessage", "Discord Specific Channel Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord specific channel")
                .build();

        TextInput dcPermissionMsgPath = TextInput.create("ind_ID.DiscordMessage.PermissionMessage", "Discord Permission Message Path", TextInputStyle.PARAGRAPH)
                .setPlaceholder("Set messages you want for discord permission")
                .build();

        Modal modalSection3 = Modal.create("ind_ID.yml", "Indonesian Language Setting")
                .addActionRows(
                        ActionRow.of(dcInvalidArgMsgPath),
                        ActionRow.of(dcCooldownMsgPath),
                        ActionRow.of(specificChannelMsgPath),
                        ActionRow.of(dcPermissionMsgPath))
                .build();

        return List.of(modalSection1, modalSection2, modalSection3);
    }
}