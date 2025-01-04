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

package org.phoenix.playerprofile.commands.discord;

import net.Indyuce.mmocore.api.player.PlayerData;
import net.Indyuce.mmocore.api.player.profess.resource.PlayerResource;
import net.Indyuce.mmocore.api.player.profess.resource.ResourceRegeneration;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.commands.discord.subcommands.libs.TimeZone;
import org.phoenix.playerprofile.manager.EmoteManager;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.players.FirstPlayed;
import org.phoenix.playerprofile.players.InventoryConditions;
import org.phoenix.playerprofile.players.LastSeen;
import org.phoenix.playerprofile.players.Playtime;

import java.awt.*;
import java.text.DecimalFormat;

public class CommandConstruction {

    public static void HelpCommand(SlashCommandInteractionEvent event) {
        EmbedBuilder commandHelp = new EmbedBuilder();
        commandHelp.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Help Requested by ") + event.getUser().getAsTag());
        commandHelp.setDescription(
                "                                                                                                                          \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] PLUGIN HELP :**") + "\n" +
                        "                                                                                                                  \n" +
                        ProfileCore.getTranslatorAPI().translate("/help : See all command on this plugin via discord.") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("/info : See info about this plugin via discord.") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("/playerlist : See all online players in game.") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("/profile [player] : See you/other players profile via discord.") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("/reload : Reload plugin via discord.") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("/version : See version of the plugin via discord.") + "\n" +
                        "                                                                                                                     ");
        commandHelp.setColor(new Color(0, 0, 255));
        commandHelp.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        commandHelp.setThumbnail("https://png.pngtree.com/png-vector/20220903/ourmid/pngtree-shining-bright-light-bulb-png-image_6136095.png");
        event.replyEmbeds(commandHelp.build()).setEphemeral(false).queue();
    }

    public static void InfoCommand(SlashCommandInteractionEvent event) {
        EmbedBuilder commandInfo = new EmbedBuilder();
        commandInfo.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Info Requested by ") + event.getUser().getAsTag());
        commandInfo.setDescription(
                "                                                                                                                                 \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] PLUGIN INFO :**") + "\n" +
                        "                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("**Name :** " + ProfileCore.getProfileCore().getDescription().getName()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate("**Description :** A Simple Player Profile Plugin for RPG Server") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("**Command In-Game :** /playerprofile [command]") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("**Command Discord :** Use /help in discord to see all commands!") + "\n" +
                        ProfileCore.getTranslatorAPI().translate("**Aliases :** /pp [command]") + "\n" +
                        "                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("_**This PlayerProfile Plugin Made By Phoenix.**_") + "\n" +
                        "                                                                                                                            ");
        commandInfo.setColor(new Color(0, 0, 255));
        commandInfo.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        commandInfo.setThumbnail("https://www.nicepng.com/png/full/221-2213196_red-question-mark-png.png");
        event.replyEmbeds(commandInfo.build()).setEphemeral(false).queue();
    }

    public static void PlayerlistCommand(SlashCommandInteractionEvent event) {
        if (!Bukkit.getServer().getOnlinePlayers().isEmpty()) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                EmbedBuilder onlinePlayerList = new EmbedBuilder();
                onlinePlayerList.setAuthor(ProfileCore.getTranslatorAPI().translate("Playerlist Requested by ") + event.getUser().getAsTag());
                onlinePlayerList.setDescription(
                        """
                                \s
                                """ + ProfileCore.getTranslatorAPI().translate("**PLAYERLIST :**") + """
                                ```
                                """ + (player != null ? player.getName() : null) + """
                                ```                                                                      \s""");
                onlinePlayerList.setColor(new Color(0, 0, 255));
                onlinePlayerList.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
                onlinePlayerList.setThumbnail("https://static.wikia.nocookie.net/minecraft_gamepedia/images/8/8f/Steve_%28classic%29_JE6.png");
                event.replyEmbeds(onlinePlayerList.build()).setEphemeral(false).queue();
            }
        } else {
            EmbedBuilder offlinePlayerList = new EmbedBuilder();
            offlinePlayerList.setAuthor(ProfileCore.getTranslatorAPI().translate("Playerlist Requested by ") + event.getUser().getAsTag());
            offlinePlayerList.setDescription(
                    """
                            \s
                            """ + ProfileCore.getTranslatorAPI().translate("**PLAYERLIST :**") + """
                            ```
                            """ + ProfileCore.getTranslatorAPI().translate("There are no online players!") + """
                            ```                                                                                 \s""");
            offlinePlayerList.setColor(new Color(0, 0, 255));
            offlinePlayerList.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
            offlinePlayerList.setThumbnail("https://static.wikia.nocookie.net/minecraft_gamepedia/images/8/8f/Steve_%28classic%29_JE6.png");
            event.replyEmbeds(offlinePlayerList.build()).setEphemeral(false).queue();
        }
    }

    public static void ProfileCommand(SlashCommandInteractionEvent event, String message, DecimalFormat digit, Player player) {
        EmbedBuilder commandProfile = new EmbedBuilder();
        commandProfile.setAuthor(ProfileCore.getTranslatorAPI().translate("Profile of %target_message%'s | Profile Requested by ").replace("%target_message%", message) + event.getUser().getAsTag());
        commandProfile.setDescription(
                "                                                                                                                                                                                                                    \n" +
                        ProfileCore.getTranslatorAPI().translate("**GENERAL INFORMATION:**") + "\n" +
                        "                                                                                                                                                                                                            \n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.NAME_EMOTE, " **Name:** ") + player.getName() + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.RANK_EMOTE, " **Rank:** ") + ProfileCore.getLuckPermsManager().getLuckpermsRank(player) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.LAST_SEEN_EMOTE, " **Last Seen:** ") + LastSeen.getLastSeen(player, player.getLastPlayed()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.FIRST_JOIN_EMOTE, " **First Join:** ") + FirstPlayed.getFirstPlayed(player, event) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_LEVELS_EXP_EMOTE, " **Total Levels Exp:** ") + player.getExpToLevel() + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_PLAYTIME_EMOTE, " **Total Playtime:** ") + Playtime.getPlaytime(player.getFirstPlayed()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_MOBS_KILL_EMOTE, " **Total Mobs Killed:** ") + player.getStatistic(Statistic.MOB_KILLS) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.CLASS_EMOTE, " **Class:** ") + PlayerData.get(player.getUniqueId()).getProfess().getName() + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.MMO_LEVELS_EMOTE, " **MMO Levels:** ") + PlayerData.get(player.getUniqueId()).getLevel() + "\n" +
                        "                                                                                                                                                                                                            \n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_HEALTH_EMOTE, " **Total Health:** ") + digit.format(PlayerData.get(player.getUniqueId()).getPlayer().getHealth()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_HEALTH_REGEN_EMOTE, " **Total Health Regeneration(/s):** ") + digit.format(new ResourceRegeneration(PlayerResource.HEALTH).getRegen(PlayerData.get(player.getUniqueId()))) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_MANA_EMOTE, " **Total Mana:** ") + digit.format(PlayerData.get(player.getUniqueId()).getMana()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_MANA_REGEN_EMOTE, " **Total Mana Regeneration(/s):** ") + digit.format(new ResourceRegeneration(PlayerResource.MANA).getRegen(PlayerData.get(player.getUniqueId()))) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_STAMINA_EMOTE, " **Total Stamina:** ") + digit.format(PlayerData.get(player.getUniqueId()).getStamina()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_STAMINA_REGEN_EMOTE, " **Total Stamina Regeneration(/s):** ") + digit.format(new ResourceRegeneration(PlayerResource.STAMINA).getRegen(PlayerData.get(player.getUniqueId()))) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_STELLIUM_EMOTE, " **Total Stellium:** ") + digit.format(PlayerData.get(player.getUniqueId()).getStellium()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_STELLIUM_REGEN_EMOTE, " **Total Stellium Regeneration(/s):** ") + digit.format(new ResourceRegeneration(PlayerResource.STELLIUM).getRegen(PlayerData.get(player.getUniqueId()))) + "\n" +
                        "                                                                                                                                                                                                            \n" +
                        ProfileCore.getTranslatorAPI().translate("**STATUS INFORMATION:**") + "\n" +
                        "                                                                                                                                                                                                             \n");
        commandProfile.addField(
                ProfileCore.getTranslatorAPI().translate("Helmet Armor:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkHelmetName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkHelmetType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkHelmetDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkHelmetEnchants(player),
                true);
        commandProfile.addField(
                ProfileCore.getTranslatorAPI().translate("Chestplate Armor:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkChestplateName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkChestplateType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkChestplateDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkChestplateEnchants(player),
                true);
        commandProfile.addField(
                ProfileCore.getTranslatorAPI().translate("Leggings Armor:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkLeggingsName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkLeggingsType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkLeggingsDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkLeggingsEnchants(player),
                true);
        commandProfile.addField(
                ProfileCore.getTranslatorAPI().translate("Boots Armor:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkBootsName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkBootsType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkBootsDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkBootsEnchants(player),
                true);
        commandProfile.addField(
                ProfileCore.getTranslatorAPI().translate("Main Hand:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkMainHandName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkMainHandType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkMainHandDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkMainHandEnchants(player),
                true);
        commandProfile.addField(
                ProfileCore.getTranslatorAPI().translate("Off Hand:"),
                ProfileCore.getTranslatorAPI().translate(" **•) Name:** ") + InventoryConditions.getArmorManager().checkOffHandName(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Type:** ") + InventoryConditions.getArmorManager().checkOffHandType(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Durability:** ") + InventoryConditions.getArmorManager().checkOffHandDurability(player) + "\n"
                        + ProfileCore.getTranslatorAPI().translate(" **•) Enchantments:** ") + InventoryConditions.getArmorManager().checkOffHandEnchants(player),
                true);
        commandProfile.setColor(new Color(0, 0, 255));
        commandProfile.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        commandProfile.setThumbnail(NullableChecker.getInstance().getPlayerAvatar().replace("%playerprofile_discord_avatar%", message));
        event.replyEmbeds(commandProfile.build()).setEphemeral(false).queue();
    }

    public static void ProfileCommand(SlashCommandInteractionEvent event, String message, OfflinePlayer offlinePlayer) {
        EmbedBuilder commandProfile = new EmbedBuilder();
        commandProfile.setAuthor(ProfileCore.getTranslatorAPI().translate("Profile of %target_message%'s | Profile Requested by ").replace("%target_message%", message) + event.getUser().getAsTag());
        commandProfile.setDescription(
                "                                                                                                                                                                                                                    \n" +
                        ProfileCore.getTranslatorAPI().translate("**GENERAL INFORMATION:**") + "\n" +
                        "                                                                                                                                                                                                            \n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.NAME_EMOTE, " **Name:** ") + offlinePlayer.getName() + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.RANK_EMOTE, " **Rank:** ") + ProfileCore.getLuckPermsManager().getLuckpermsRank(offlinePlayer) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.LAST_SEEN_EMOTE, " **Last Seen:** ") + LastSeen.getLastSeen(offlinePlayer, offlinePlayer.getLastPlayed()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.FIRST_JOIN_EMOTE, " **First Join:** ") + FirstPlayed.getFirstPlayed(offlinePlayer, event) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_PLAYTIME_EMOTE, " **Total Playtime:** ") + Playtime.getPlaytime(offlinePlayer.getFirstPlayed()) + "\n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.TOTAL_MOBS_KILL_EMOTE, " **Total Mobs Killed:** ") + offlinePlayer.getStatistic(Statistic.MOB_KILLS) + "\n" +
                        "                                                                                                                                                                         \n" +
                        ProfileCore.getTranslatorAPI().translate("**WARNING!** Some profile feature may not be available due to limited offline player data.") + "\n" +
                        "                                                                                                                                                                                                             \n");
        commandProfile.setColor(new Color(0, 0, 255));
        commandProfile.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        commandProfile.setThumbnail(NullableChecker.getInstance().getPlayerAvatar().replace("%playerprofile_discord_avatar%", message));
        event.replyEmbeds(commandProfile.build()).setEphemeral(false).queue();
    }

    public static void ReloadCommand(ProfileCore plugin, SlashCommandInteractionEvent event) {
        EmbedBuilder commandReload = new EmbedBuilder();
        commandReload.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Reload Requested by ") + event.getUser().getAsTag());
        commandReload.setDescription(
                """
                        \s
                        """ + ProfileCore.getTranslatorAPI().translate(EmoteManager.RELOAD_ASK_EMOTE, " Are you sure you want to reload PlayerProfile plugin?") + """
                        \s""");
        commandReload.setColor(new Color(0, 0, 255));
        commandReload.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        commandReload.setThumbnail("https://pngimg.com/d/gear_PNG53.png");
        event.replyEmbeds(commandReload.build()).addActionRow(net.dv8tion.jda.api.interactions.components.buttons.Button.success("yes", "Yes"), net.dv8tion.jda.api.interactions.components.buttons.Button.danger("no", "No")).queue(editMessage -> new BukkitRunnable() {
            @Override
            public void run() {
                commandReload.setDescription(
                        """
                                \s
                                """ + ProfileCore.getTranslatorAPI().translate(EmoteManager.RELOAD_TIMEOUT_EMOTE, " You has been timeout to accept reload, write again later") + """
                                \s""");
                editMessage.editOriginalEmbeds(commandReload.build()).setActionRow(Button.danger("timeout_reload", ProfileCore.getTranslatorAPI().translate("Timeout!")).asDisabled()).queue();
            }
        }.runTaskLater(plugin, 1000));
    }

    public static void VersionCommand(ProfileCore plugin, SlashCommandInteractionEvent event) {
        EmbedBuilder commandVersion = new EmbedBuilder();
        commandVersion.setAuthor(ProfileCore.getTranslatorAPI().translate("Plugin Version Requested by ") + event.getUser().getAsTag());
        commandVersion.setDescription(
                "                                                                                                                                                   \n" +
                        ProfileCore.getTranslatorAPI().translate("**[DISCORD] PLUGIN VERSION :**") + "\n" +
                        "                                                                                                                                           \n" +
                        ProfileCore.getTranslatorAPI().translate(EmoteManager.VERSION_BUTTON, " Plugin version is ") + plugin.getDescription().getVersion() + "\n" +
                        "                                                                                                                                            \n");
        commandVersion.setColor(new Color(0, 0, 255));
        commandVersion.setFooter("© PlayerProfile  •  " + TimeZone.getTimeZone(), null);
        commandVersion.setThumbnail("https://cdn-icons-png.flaticon.com/512/1601/1601884.png");
        event.replyEmbeds(commandVersion.build()).setEphemeral(false).queue();
    }
}