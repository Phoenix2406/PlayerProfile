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

import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import net.Indyuce.mmocore.api.player.profess.resource.ResourceRegeneration;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.phoenix.playerprofile.DiscordCore;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.JDACommand;
import org.phoenix.playerprofile.annotation.JDAOption;
import org.phoenix.playerprofile.commands.discord.CommandConstruction;
import org.phoenix.playerprofile.commands.discord.subcommands.libs.TimeZone;
import org.phoenix.playerprofile.manager.EmbedManager;
import org.phoenix.playerprofile.manager.EmoteManager;
import org.phoenix.playerprofile.objects.CooldownTimer;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.players.FirstPlayed;
import org.phoenix.playerprofile.players.InventoryConditions;
import org.phoenix.playerprofile.players.LastSeen;
import org.phoenix.playerprofile.players.Playtime;
import org.phoenix.playerprofile.utils.Logger;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandProfile {

    private final DecimalFormat digit;

    public CommandProfile() {
        if (ProfileCore.getConfigData().getInstance().getString("IntegerFormat") != null) {
            digit = MythicLib.plugin.getMMOConfig().newDecimalFormat(ProfileCore.getConfigData().getInstance().getString("IntegerFormat"));
        } else {
            digit = MythicLib.plugin.getMMOConfig().newDecimalFormat("0.#");
        }
    }

    /*
    Register Slash Command Interaction Event
    for Player Profile command on Discord
     */
    @SuppressWarnings("deprecation")
    @JDACommand(name = "profile", description = "See you or other players profile via discord.", memberPerms = "Permission.Member", options = {
            @JDAOption(name = "name", description = "Write online player ingame.", type = OptionType.STRING, isRequired = true)
    })
    public void onProfileCommandUsed(SlashCommandInteractionEvent event, Map<String, Object> options) {
        String message = (String) options.get("name");
        if (message == null) throw new CommandException("An error occurred while using discord slash command.");

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
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(message);
                if (offlinePlayer.hasPlayedBefore()) {
                    if (offlinePlayer.isOnline()) {
                        Player target = offlinePlayer.getPlayer();
                        if (target != null) {
                            if (ProfileCore.getDiscordConfig().getInstance().getString("Paginating").equals("SINGLE")) {
                                CommandConstruction.ProfileCommand(event, message, digit, target);
                            } else if (ProfileCore.getDiscordConfig().getInstance().getString("Paginating").equals("MULTIPLE")) {
                                List<MessageEmbed> pages = new ArrayList<>();

                                pages.add(ProfilePaginating.GeneralPage(event, message, target));
                                pages.add(ProfilePaginating.MMOStatsPage(event, digit, message, target));
                                pages.add(ProfilePaginating.ToolsPage(event, message, target));

                                event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.SuccessRetrievePlayer").replace("%player_ingame_target%", target.getName())).setEphemeral(true).queue();
                                EmbedManager.createPaginated(event.getTextChannel(), (event.getMember() == null ? 0L : event.getMember().getIdLong()), pages);
                                EmbedManager.updateInteraction((event.getMember() == null ? 0L : event.getMember().getIdLong()));
                            }
                        }
                    } else {
                        if (ProfileCore.getDiscordConfig().getInstance().getString("Paginating").equals("SINGLE")) {
                            CommandConstruction.ProfileCommand(event, message, offlinePlayer);
                        } else if (ProfileCore.getDiscordConfig().getInstance().getString("Paginating").equals("MULTIPLE")) {
                            List<MessageEmbed> pages = new ArrayList<>();

                            pages.add(ProfilePaginating.GeneralPage(event, message, offlinePlayer));

                            event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.SuccessRetrievePlayer").replace("%player_ingame_target%", message)).setEphemeral(true).queue();
                            EmbedManager.createPaginated(event.getTextChannel(), (event.getMember() == null ? 0L : event.getMember().getIdLong()), pages);
                            EmbedManager.updateInteraction((event.getMember() == null ? 0L : event.getMember().getIdLong()));
                        }
                    }
                } else {
                    event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.NoPlayerMessage").replace("%playerprofile_discord_name%", event.getUser().getAsMention())).setEphemeral(true).queue();
                }
            } else {
                if (event.getTextChannel().equals(DiscordCore.getDiscordCore().getTextChannel())) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(message);
                    if (offlinePlayer.hasPlayedBefore()) {
                        if (offlinePlayer.isOnline()) {
                            Player target = offlinePlayer.getPlayer();
                            if (target != null) {
                                if (ProfileCore.getDiscordConfig().getInstance().getString("Paginating").equals("SINGLE")) {
                                    CommandConstruction.ProfileCommand(event, message, digit, target);
                                } else if (ProfileCore.getDiscordConfig().getInstance().getString("Paginating").equals("MULTIPLE")) {
                                    List<MessageEmbed> pages = new ArrayList<>();

                                    pages.add(ProfilePaginating.GeneralPage(event, message, target));
                                    pages.add(ProfilePaginating.MMOStatsPage(event, digit, message, target));
                                    pages.add(ProfilePaginating.ToolsPage(event, message, target));

                                    event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.SuccessRetrievePlayer").replace("%player_ingame_target%", target.getName())).setEphemeral(true).queue();
                                    EmbedManager.createPaginated(event.getTextChannel(), (event.getMember() == null ? 0L : event.getMember().getIdLong()), pages);
                                    EmbedManager.updateInteraction((event.getMember() == null ? 0L : event.getMember().getIdLong()));
                                }
                            }
                        } else {
                            if (ProfileCore.getDiscordConfig().getInstance().getString("Paginating").equals("SINGLE")) {
                                CommandConstruction.ProfileCommand(event, message, offlinePlayer);
                            } else if (ProfileCore.getDiscordConfig().getInstance().getString("Paginating").equals("MULTIPLE")) {
                                List<MessageEmbed> pages = new ArrayList<>();
                                pages.add(ProfilePaginating.GeneralPage(event, message, offlinePlayer));

                                event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.SuccessRetrievePlayer").replace("%player_ingame_target%", message)).setEphemeral(true).queue();
                                EmbedManager.createPaginated(event.getTextChannel(), (event.getMember() == null ? 0L : event.getMember().getIdLong()), pages);
                                EmbedManager.updateInteraction((event.getMember() == null ? 0L : event.getMember().getIdLong()));
                            }
                        }
                    } else {
                        event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.NoPlayerMessage").replace("%playerprofile_discord_name%", event.getUser().getAsMention())).setEphemeral(true).queue();
                    }
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

class ProfilePaginating {

    public static MessageEmbed GeneralPage(SlashCommandInteractionEvent event, String message, Player player) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Profile of %target_message%'s | Profile Requested by ").replace("%target_message%", message) + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**GENERAL INFORMATION:**") + "\n" +
                        "                                                                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.NAME_EMOTE, " **Name:** ") + player.getName() + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.RANK_EMOTE, " **Rank:** ") + ProfileCore.getLuckPermsManager().getLuckpermsRank(player) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.LAST_SEEN_EMOTE, " **Last Seen:** ") + LastSeen.getLastSeen(player, player.getLastPlayed()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.FIRST_JOIN_EMOTE, " **First Join:** ") + FirstPlayed.getFirstPlayed(player, event) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_LEVELS_EXP_EMOTE, " **Total Levels Exp:** ") + player.getExpToLevel() + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_PLAYTIME_EMOTE, " **Total Playtime:** ") + Playtime.getPlaytime(player.getFirstPlayed()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_MOBS_KILL_EMOTE, " **Total Mobs Killed:** ") + player.getStatistic(Statistic.MOB_KILLS) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.CLASS_EMOTE, " **Class:** ") + PlayerData.get(player.getUniqueId()).getProfess().getName() + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.MMO_LEVELS_EMOTE, " **MMO Levels:** ") + PlayerData.get(player.getUniqueId()).getLevel() + "\n" +
                        "                                                                                                                                                                         \n");
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        builder.setThumbnail(NullableChecker.getInstance().getPlayerAvatar().replace("%playerprofile_discord_avatar%", message));
        return builder.build();
    }

    public static MessageEmbed GeneralPage(SlashCommandInteractionEvent event, String message, OfflinePlayer offlinePlayer) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Profile of %target_message%'s | Profile Requested by ").replace("%target_message%", message) + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**GENERAL INFORMATION:**") + "\n" +
                        "                                                                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.NAME_EMOTE, " **Name:** ") + offlinePlayer.getName() + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.RANK_EMOTE, " **Rank:** ") + ProfileCore.getLuckPermsManager().getLuckpermsRank(offlinePlayer) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.LAST_SEEN_EMOTE, " **Last Seen:** ") + LastSeen.getLastSeen(offlinePlayer, offlinePlayer.getLastPlayed()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.FIRST_JOIN_EMOTE, " **First Join:** ") + FirstPlayed.getFirstPlayed(offlinePlayer, event) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_PLAYTIME_EMOTE, " **Total Playtime:** ") + Playtime.getPlaytime(offlinePlayer.getFirstPlayed()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_MOBS_KILL_EMOTE, " **Total Mobs Killed:** ") + offlinePlayer.getStatistic(Statistic.MOB_KILLS) + "\n" +
                        "                                                                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("**WARNING!** Some profile feature may not be available due to limited offline player data.") + "\n" +
                        "                                                                                                                                                                         \n");
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        builder.setThumbnail(NullableChecker.getInstance().getPlayerAvatar().replace("%playerprofile_discord_avatar%", message));
        return builder.build();
    }

    public static MessageEmbed MMOStatsPage(SlashCommandInteractionEvent event, DecimalFormat digit, String message, Player player) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Profile of %target_message%'s | Profile Requested by ").replace("%target_message%", message) + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**MMO STATS INFORMATION:**") + "\n" +
                        "                                                                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_HEALTH_EMOTE, " **Total Health:** ") + digit.format(PlayerData.get(player.getUniqueId()).getPlayer().getHealth()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_HEALTH_REGEN_EMOTE, " **Total Health Regeneration(/s):** ") + digit.format(new ResourceRegeneration(PlayerResource.HEALTH).getRegen(PlayerData.get(player.getUniqueId()))) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_MANA_EMOTE, " **Total Mana:** ") + digit.format(PlayerData.get(player.getUniqueId()).getMana()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_MANA_REGEN_EMOTE, " **Total Mana Regeneration(/s):** ") + digit.format(new ResourceRegeneration(PlayerResource.MANA).getRegen(PlayerData.get(player.getUniqueId()))) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_STAMINA_EMOTE, " **Total Stamina:** ") + digit.format(PlayerData.get(player.getUniqueId()).getStamina()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_STAMINA_REGEN_EMOTE, " **Total Stamina Regeneration(/s):** ") + digit.format(new ResourceRegeneration(PlayerResource.STAMINA).getRegen(PlayerData.get(player.getUniqueId()))) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_STELLIUM_EMOTE, " **Total Stellium:** ") + digit.format(PlayerData.get(player.getUniqueId()).getStellium()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_STELLIUM_REGEN_EMOTE, " **Total Stellium Regeneration(/s):** ") + digit.format(new ResourceRegeneration(PlayerResource.STELLIUM).getRegen(PlayerData.get(player.getUniqueId()))) + "\n" +
                        "                                                                                                                                                                         \n");
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        builder.setThumbnail(NullableChecker.getInstance().getPlayerAvatar().replace("%playerprofile_discord_avatar%", message));
        return builder.build();
    }

    public static MessageEmbed ToolsPage(SlashCommandInteractionEvent event, String message, Player player) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(ProfileCore.getTranslatorAPI().translate("Profile of %target_message%'s | Profile Requested by ").replace("%target_message%", message) + event.getUser().getAsTag());
        builder.setDescription(
                "                                                                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**TOOLS INFORMATION:**") + "\n" +
                        "                                                                                                                                                                         \n");
        builder.addField(
                ProfileCore.getTranslatorAPI().translate("Helmet Armor:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkHelmetName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkHelmetType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkHelmetDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkHelmetEnchants(player),
                true);
        builder.addField(
                ProfileCore.getTranslatorAPI().translate("Chestplate Armor:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkChestplateName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkChestplateType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkChestplateDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkChestplateEnchants(player),
                true);
        builder.addField(
                ProfileCore.getTranslatorAPI().translate("Leggings Armor:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkLeggingsName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkLeggingsType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkLeggingsDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkLeggingsEnchants(player),
                true);
        builder.addField(
                ProfileCore.getTranslatorAPI().translate("Boots Armor:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkBootsName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkBootsType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkBootsDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkBootsEnchants(player),
                true);
        builder.addField(
                ProfileCore.getTranslatorAPI().translate("Main Hand:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkMainHandName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkMainHandType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkMainHandDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkMainHandEnchants(player),
                true);
        builder.addField(
                ProfileCore.getTranslatorAPI().translate("Off Hand:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkOffHandName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkOffHandType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkOffHandDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkOffHandEnchants(player),
                true);
        builder.setColor(new Color(0, 0, 255));
        builder.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        builder.setThumbnail(NullableChecker.getInstance().getPlayerAvatar().replace("%playerprofile_discord_avatar%", message));
        return builder.build();
    }
}
