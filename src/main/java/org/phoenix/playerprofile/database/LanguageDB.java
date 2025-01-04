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

package org.phoenix.playerprofile.database;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.phoenix.playerprofile.ProfileCore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageDB {

    public Map<String, String> loadENTranslation() {
        Map<String, String> ENTranslations = new HashMap<>();
        // Ingame auto translation in English Language
        ENTranslations.put("&cYou must be a players to use this command!", "&cYou must be a players to use this command!");
        ENTranslations.put("&#00D4FF&lPlayerProfile Plugin", "&#00D4FF&lPlayerProfile Plugin");
        ENTranslations.put("&3To see all command in-game use /playerprofile help.", "&3To see all command in-game use /playerprofile help.");
        ENTranslations.put("&3To see all command in-discord use /help.", "&3To see all command in-discord use /help.");
        ENTranslations.put("&3/playerprofile help &f▶ &7To see all command in this plugin.", "&3/playerprofile help &f▶ &7To see all command in this plugin.");
        ENTranslations.put("&3/playerprofile info &f▶ &7To see information about this plugin.", "&3/playerprofile info &f▶ &7To see information about this plugin.");
        ENTranslations.put("&3/playerprofile profile [players] &f▶ &7See your/other players profile.", "&3/playerprofile profile [players] &f▶ &7See your/other players profile.");
        ENTranslations.put("&3/playerprofile reload &f▶ &7To reload this plugin.", "&3/playerprofile reload &f▶ &7To reload this plugin.");
        ENTranslations.put("&3/playerprofile version &f▶ &7See version of this plugin.", "&3/playerprofile version &f▶ &7See version of this plugin.");
        ENTranslations.put("&3Name &f▶ &7" + ProfileCore.getProfileCore().getDescription().getName(), "&3Name &f▶ &7" + ProfileCore.getProfileCore().getDescription().getName());
        ENTranslations.put("&3Description &f▶ &7A Simple Player Profile Plugin for RPG Server", "&3Description &f▶ &7A Simple Player Profile Plugin for RPG Server");
        ENTranslations.put("&3Command In-Game &f▶ &7/playerprofile [command]", "&3Command In-Game &f▶ &7/playerprofile [command]");
        ENTranslations.put("&3Command Discord &f▶ &7Use /help in discord to see all commands!", "&3Command Discord &f▶ &7Use /help in discord to see all commands!");
        ENTranslations.put("&3Aliases &f▶ &7/pp [command]", "&3Aliases &f▶ &7/pp [command]");
        ENTranslations.put("&8&oThis Player Profile Plugin Make by Phoenix.", "&8&oThis Player Profile Plugin Make by Phoenix.");
        ENTranslations.put("&aPlugin version is &b&n", "&aPlugin version is &b&n");

        // Discord auto translation in English Language
        ENTranslations.put("Plugin Help Requested by ", "Plugin Help Requested by ");
        ENTranslations.put("**[DISCORD] PLUGIN HELP :**", "**[DISCORD] PLUGIN HELP :**");
        ENTranslations.put("/help : See all command on this plugin via discord.", "/help : See all command on this plugin via discord.");
        ENTranslations.put("/info : See info about this plugin via discord.", "/info : See info about this plugin via discord.");
        ENTranslations.put("/playerlist : See all online players in game.", "/playerlist : See all online players in game.");
        ENTranslations.put("/profile [player] : See you/other players profile via discord.", "/profile [player] : See you/other players profile via discord.");
        ENTranslations.put("/reload : Reload plugin via discord.", "/reload : Reload plugin via discord.");
        ENTranslations.put("/version : See version of the plugin via discord.", "/version : See version of the plugin via discord.");
        ENTranslations.put("Plugin Info Requested by ", "Plugin Info Requested by ");
        ENTranslations.put("**[DISCORD] PLUGIN INFO :**", "**[DISCORD] PLUGIN INFO :**");
        ENTranslations.put("**Name :** " + ProfileCore.getProfileCore().getDescription().getName(), "**Name :** " + ProfileCore.getProfileCore().getDescription().getName());
        ENTranslations.put("**Description :** A Simple Player Profile Plugin for RPG Server", "**Description :** A Simple Player Profile Plugin for RPG Server");
        ENTranslations.put("**Command In-Game :** /playerprofile [command]", "**Command In-Game :** /playerprofile [command]");
        ENTranslations.put("**Command Discord :** Use /help in discord to see all commands!", "**Command Discord :** Use /help in discord to see all commands!");
        ENTranslations.put("**Aliases :** /pp [command]", "**Aliases :** /pp [command]");
        ENTranslations.put("_**This PlayerProfile Plugin Made By Phoenix.**_", "_**This PlayerProfile Plugin Made By Phoenix.**_");
        ENTranslations.put("Playerlist Requested by ", "Playerlist Requested by ");
        ENTranslations.put("**PLAYERLIST :**", "**PLAYERLIST :**");
        ENTranslations.put("There are no online players!", "There are no online players!");
        ENTranslations.put("Profile of %target_message%'s | Profile Requested by ", "Profile of %target_message%'s | Profile Requested by ");
        ENTranslations.put("Plugin Reload Requested by ", "Plugin Reload Requested by ");
        ENTranslations.put("Timeout!", "Timeout!");
        ENTranslations.put("Cancelled!", "Cancelled!");
        ENTranslations.put("Waiting....", "Waiting....");
        ENTranslations.put("Reload Success!", "Reload Success!");
        ENTranslations.put("Reload Unsuccess!", "Reload Unsuccess!");
        ENTranslations.put("Plugin Version Requested by ", "Plugin Version Requested by ");
        ENTranslations.put("**[DISCORD] PLUGIN VERSION :**", "**[DISCORD] PLUGIN VERSION :**");

        // Profiles auto translation in English Language
        ENTranslations.put("&a&lGENERAL INFORMATION:", "&a&lGENERAL INFORMATION:");
        ENTranslations.put("&7General information about your profile.", "&7General information about your profile.");
        ENTranslations.put("&7General information about %ingame_target_name%'s profile.", "&7General information about %ingame_target_name%'s profile.");
        ENTranslations.put("&a&lSTATUS INFORMATION:", "&a&lSTATUS INFORMATION:");
        ENTranslations.put("&7Status information about your profile.", "&7Status information about your profile.");
        ENTranslations.put("&7Status information about %ingame_target_name%'s profile.", "&7Status information about %ingame_target_name%'s profile.");
        ENTranslations.put("&#32CD32&l%ingame_target_name%'s Profile", "&#32CD32&l%ingame_target_name%'s Profile");
        ENTranslations.put("&aName &f▶ &b", "&aName &f▶ &b");
        ENTranslations.put("&aRank &f▶ &b", "&aRank &f▶ &b");
        ENTranslations.put("&aLast Seen &f▶ &b", "&aLast Seen &f▶ &b");
        ENTranslations.put("&aFirst Join &f▶ &b", "&aFirst Join &f▶ &b");
        ENTranslations.put("&aTotal Levels Exp &f▶ &b", "&aTotal Levels Exp &f▶ &b");
        ENTranslations.put("&aTotal Playtime &f▶ &b", "&aTotal Playtime &f▶ &b");
        ENTranslations.put("&aTotal Mobs Killed &f▶ &b", "&aTotal Mobs Killed &f▶ &b");
        ENTranslations.put("&aClass &f▶ &b", "&aClass &f▶ &b");
        ENTranslations.put("&aMMO Levels &f▶ &b", "&aMMO Levels &f▶ &b");
        ENTranslations.put("&aTotal Health &f▶ &b", "&aTotal Health &f▶ &b");
        ENTranslations.put("&aTotal Health Regeneration(/s) &f▶ &b", "&aTotal Health Regeneration(/s) &f▶ &b");
        ENTranslations.put("&aTotal Mana &f▶ &b", "&aTotal Mana &f▶ &b");
        ENTranslations.put("&aTotal Mana Regeneration(/s) &f▶ &b", "&aTotal Mana Regeneration(/s) &f▶ &b");
        ENTranslations.put("&aTotal Stamina &f▶ &b", "&aTotal Stamina &f▶ &b");
        ENTranslations.put("&aTotal Stamina Regeneration(/s) &f▶ &b", "&aTotal Stamina Regeneration(/s) &f▶ &b");
        ENTranslations.put("&aTotal Stellium &f▶ &b", "&aTotal Stellium &f▶ &b");
        ENTranslations.put("&aTotal Stellium Regeneration(/s) &f▶ &b", "&aTotal Stellium Regeneration(/s) &f▶ &b");
        ENTranslations.put("&aHelmet &f▶", "&aHelmet &f▶");
        ENTranslations.put("&aChestplate &f▶", "&aChestplate &f▶");
        ENTranslations.put("&aLeggings &f▶", "&aLeggings &f▶");
        ENTranslations.put("&aBoots &f▶", "&aBoots &f▶");
        ENTranslations.put("&aMain Hand &f▶", "&aMain Hand &f▶");
        ENTranslations.put("&aOff Hand &f▶", "&aOff Hand &f▶");
        ENTranslations.put(" &a•) Name &f▶ &b", " &a•) Name &f▶ &b");
        ENTranslations.put(" &a•) Type &f▶ &b", " &a•) Type &f▶ &b");
        ENTranslations.put(" &a•) Durability &f▶ &b", " &a•) Durability &f▶ &b");
        ENTranslations.put(" &a•) Enchantments &f▶ &b", " &a•) Enchantments &f▶ &b");

        // Profiles discord auto translation in English Language
        ENTranslations.put("**GENERAL INFORMATION:**", "**GENERAL INFORMATION:**");
        ENTranslations.put("**MMO STATS INFORMATION:**", "**MMO STATS INFORMATION:**");
        ENTranslations.put("**TOOLS INFORMATION:**", "**TOOLS INFORMATION:**");
        ENTranslations.put("**STATUS INFORMATION:**", "**STATUS INFORMATION:**");
        ENTranslations.put("Helmet Armor:", "Helmet Armor:");
        ENTranslations.put("Chestplate Armor:", "Chestplate Armor:");
        ENTranslations.put("Leggings Armor:", "Leggings Armor:");
        ENTranslations.put("Boots Armor:", "Boots Armor:");
        ENTranslations.put("Main Hand:", "Main Hand:");
        ENTranslations.put("Off Hand:", "Off Hand:");
        ENTranslations.put(" **•) Name:** ", " **•) Name:** ");
        ENTranslations.put(" **•) Type:** ", " **•) Type:** ");
        ENTranslations.put(" **•) Durability:** ", " **•) Durability:** ");
        ENTranslations.put(" **•) Enchantments:** ", " **•) Enchantments:** ");

        // Utils auto translation in English Language
        ENTranslations.put("UNKNOWN", "UNKNOWN");
        ENTranslations.put("Yes", "Yes");
        ENTranslations.put("No", "No");
        ENTranslations.put("Now", "Now");
        ENTranslations.put(" Days ", " Days ");
        ENTranslations.put(" Hours ", " Hours ");
        ENTranslations.put(" Minutes ", " Minutes ");
        ENTranslations.put(" Seconds", " Seconds");
        ENTranslations.put("&aA new version for PlayerProfile is available. Ask itzthunder_#0000 in discord to get new version of this plugin.", "&aA new version for PlayerProfile is available. Ask itzthunder_#0000 in discord to get new version of this plugin.");
        ENTranslations.put("&aThere are no new version for PlayerProfile plugin.", "&aThere are no new version for PlayerProfile plugin.");
        ENTranslations.put("&#32CD32&lYour Profile's", "&#32CD32&lYour Profile's");
        ENTranslations.put("&c&lNext Pages Profile Menu", "&c&lNext Pages Profile Menu");
        ENTranslations.put("&cClick to next pages!", "&cClick to next pages!");
        ENTranslations.put("&c&lClose Profile Menu", "&c&lClose Profile Menu");
        ENTranslations.put("&cClick to close!", "&cClick to close!");
        ENTranslations.put("&c&lPrevious Pages Profile Menu", "&c&lPrevious Pages Profile Menu");
        ENTranslations.put("&c&lClick to previous pages!", "&c&lClick to previous pages!");

        // Discord with emote auto translation in English Language
        ENTranslations.put(" You has been timeout to accept reload, write again later", " You has been timeout to accept reload, write again later");
        ENTranslations.put(" Are you sure you want to reload PlayerProfile plugin?", " Are you sure you want to reload PlayerProfile plugin?");
        ENTranslations.put(" You cancelled reload of PlayerProfile Plugin", " You cancelled reload of PlayerProfile Plugin");
        ENTranslations.put(" PlayerProfile Plugin is reloading via discord.... Please wait a moment....", " PlayerProfile Plugin is reloading via discord.... Please wait a moment....");
        ENTranslations.put(" Success! Plugin has been reloaded via discord.", " Success! Plugin has been reloaded via discord.");
        ENTranslations.put(" Error! Plugin can't reload properly via discord. See console for more information.", " Error! Plugin can't reload properly via discord. See console for more information.");
        ENTranslations.put(" Plugin version is ", " Plugin version is ");
        ENTranslations.put(" **Name:** ", " **Name:** ");
        ENTranslations.put(" **Rank:** ", " **Rank:** ");
        ENTranslations.put(" **Last Seen:** ", " **Last Seen:** ");
        ENTranslations.put(" **First Join:** ", " **First Join:** ");
        ENTranslations.put(" **Total Levels Exp:** ", " **Total Levels Exp:** ");
        ENTranslations.put(" **Total Playtime:** ", " **Total Playtime:** ");
        ENTranslations.put(" **Total Mobs Killed:** ", " **Total Mobs Killed:** ");
        ENTranslations.put(" **Class:** ", " **Class:** ");
        ENTranslations.put(" **MMO Levels:** ", " **MMO Levels:** ");
        ENTranslations.put(" **Total Health:** ", " **Total Health:** ");
        ENTranslations.put(" **Total Health Regeneration(/s):** ", " **Total Health Regeneration(/s):** ");
        ENTranslations.put(" **Total Mana:** ", " **Total Mana:** ");
        ENTranslations.put(" **Total Mana Regeneration(/s):** ", " **Total Mana Regeneration(/s):** ");
        ENTranslations.put(" **Total Stamina:** ", " **Total Stamina:** ");
        ENTranslations.put(" **Total Stamina Regeneration(/s):** ", " **Total Stamina Regeneration(/s):** ");
        ENTranslations.put(" **Total Stellium:** ", " **Total Stellium:** ");
        ENTranslations.put(" **Total Stellium Regeneration(/s):** ", " **Total Stellium Regeneration(/s):** ");
        ENTranslations.put("&cAn internal error occurred while attempting to perform this command.", "&cAn internal error occurred while attempting to perform this command.");
        ENTranslations.put("0H 0M 0S", "0H 0M 0S");
        ENTranslations.put("Cooldown expired", "Cooldown expired");
        ENTranslations.put("Cooldown invalid", "Cooldown invalid");
        ENTranslations.put("Plugin Setting Requested by ", "Plugin Setting Requested by ");
        ENTranslations.put("**[DISCORD] CONFIG SETTING EDITOR :", "**[DISCORD] CONFIG SETTING EDITOR :");
        ENTranslations.put("**[DISCORD] DISCORD CONFIG SETTING EDITOR :**", "**[DISCORD] DISCORD CONFIG SETTING EDITOR :**");
        ENTranslations.put("**[DISCORD] SOUND CONFIG SETTING EDITOR :**", "**[DISCORD] SOUND CONFIG SETTING EDITOR :**");
        ENTranslations.put("**[DISCORD] DISCORD EMOTE SETTING EDITOR :**", "**[DISCORD] DISCORD EMOTE SETTING EDITOR :**");
        ENTranslations.put("**[DISCORD] ENGLISH LANGUAGE SETTING EDITOR :**", "**[DISCORD] ENGLISH LANGUAGE SETTING EDITOR :**");
        ENTranslations.put("**[DISCORD] INDONESIAN LANGUAGE SETTING EDITOR :**", "**[DISCORD] INDONESIAN LANGUAGE SETTING EDITOR :**");
        ENTranslations.put("Setting editor for %config_file% via discord, you can select options to", "Setting editor for %config_file% via discord, you can select options to");
        ENTranslations.put("edit path in %config_file% from selection menu below. Don't forget to", "edit path in %config_file% from selection menu below. Don't forget to");
        ENTranslations.put("click reload button to reload all configuration in plugin folder!", "click reload button to reload all configuration in plugin folder!");
        ENTranslations.put("**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.", "**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.");
        ENTranslations.put("**•) %ingame_target_name% :** return target player name.", "**•) %ingame_target_name% :** return target player name.");
        ENTranslations.put("**•) %playerprofile_ingame_cooldown% :** return player cooldown to see profile ingame.", "**•) %playerprofile_ingame_cooldown% :** return player cooldown to see profile ingame.");
        ENTranslations.put("**•) %playerprofile_ingame_permission% :** return permission ingame.", "**•) %playerprofile_ingame_permission% :** return permission ingame.");
        ENTranslations.put("**•) %playerprofile_discord_name% :** return discord user name.", "**•) %playerprofile_discord_name% :** return discord user name.");
        ENTranslations.put("**•) %playerprofile_discord_cooldown% :** return user cooldown to see profile in discord.", "**•) %playerprofile_discord_cooldown% :** return user cooldown to see profile in discord.");
        ENTranslations.put("**•) %playerprofile_discord_permission% :** return permission in discord.", "**•) %playerprofile_discord_permission% :** return permission in discord.");
        ENTranslations.put("Available Placeholder:", "Available Placeholder:");
        ENTranslations.put("Section", "Section");
        ENTranslations.put("Change path in %config_file% section", "Change path in %config_file% section");
        ENTranslations.put("Previous", "Previous");
        ENTranslations.put("Reload", "Reload");
        ENTranslations.put("Next", "Next");
        ENTranslations.put("Success", "Success");
        ENTranslations.put("**WARNING!** Some profile feature may not be available due to limited offline player data.", "**WARNING!** Some profile feature may not be available due to limited offline player data.");
        return ENTranslations;
    }

    public Map<String, String> loadIDTranslation() {
        Map<String, String> IDTranslations = new HashMap<>();
        // Ingame auto translation in Indonesian Language
        IDTranslations.put("&cYou must be a players to use this command!", "&cAnda harus menjadi pemain untuk menggunakan perintah ini!");
        IDTranslations.put("&#00D4FF&lPlayerProfile Plugin", "&#00D4FF&lPlugin PlayerProfile");
        IDTranslations.put("&3To see all command in-game use /playerprofile help.", "&3Untuk melihat semua perintah di game gunakan /playerprofile help.");
        IDTranslations.put("&3To see all command in-discord use /help.", "&3Untuk melihat semua perintah discord gunakan /help.");
        IDTranslations.put("&3/playerprofile help &f▶ &7To see all command in this plugin.", "&3/playerprofile help &f▶ &7Untuk melihat semua perintah di plugin ini.");
        IDTranslations.put("&3/playerprofile info &f▶ &7To see information about this plugin.", "&3/playerprofile info &f▶ &7Untuk melihat informasi tentang plugin ini.");
        IDTranslations.put("&3/playerprofile profile [players] &f▶ &7See your/other players profile.", "&3/playerprofile profile [players] &f▶ &7Lihat profil Anda/pemain lain.");
        IDTranslations.put("&3/playerprofile reload &f▶ &7To reload this plugin.", "&3/playerprofile reload &f▶ &7Untuk memuat ulang plugin ini.");
        IDTranslations.put("&3/playerprofile version &f▶ &7See version of this plugin.", "&3/playerprofile version &f▶ &7Lihat versi plugin ini.");
        IDTranslations.put("&3Name &f▶ &7" + ProfileCore.getProfileCore().getDescription().getName(), "&3Nama &f▶ &7" + ProfileCore.getProfileCore().getDescription().getName());
        IDTranslations.put("&3Description &f▶ &7A Simple Player Profile Plugin for RPG Server", "&3Deskripsi &f▶ &7Plugin PlayerProfile Sederhana untuk Server RPG");
        IDTranslations.put("&3Command In-Game &f▶ &7/playerprofile [command]", "&3Perintah Di Game &f▶ &7/playerprofile [command]");
        IDTranslations.put("&3Command Discord &f▶ &7Use /help in discord to see all commands!", "&3Perintah Discord &f▶ &7Gunakan /help di discord untuk melihat semua perintah!");
        IDTranslations.put("&3Aliases &f▶ &7/pp [command]", "&3Alias &f▶ &7/pp [command]");
        IDTranslations.put("&8&oThis Player Profile Plugin Make by Phoenix.", "&8&oPlugin PlayerProfile Ini Dibuat oleh Phoenix.");
        IDTranslations.put("&aPlugin version is &b&n", "&aVersi plugin adalah &b&n");

        // Discord auto translation in Indonesian Language
        IDTranslations.put("Plugin Help Requested by ", "Bantuan Plugin Diminta oleh ");
        IDTranslations.put("**[DISCORD] PLUGIN HELP :**", "**[DISCORD] BANTUAN PLUGIN :**");
        IDTranslations.put("/help : See all command on this plugin via discord.", "/help : Lihat semua perintah pada plugin ini melalui discord.");
        IDTranslations.put("/info : See info about this plugin via discord.", "/info : Lihat info tentang plugin ini melalui discord.");
        IDTranslations.put("/playerlist : See all online players in game.", "/playerlist : Lihat semua pemain online dalam game.");
        IDTranslations.put("/profile [player] : See you/other players profile via discord.", "/profile [player] : Melihat profil kamu/pemain lain melalui discord.");
        IDTranslations.put("/reload : Reload plugin via discord.", "/reload : Muat ulang plugin melalui discord.");
        IDTranslations.put("/version : See version of the plugin via discord.", "/version : Lihat versi plugin melalui discord.");
        IDTranslations.put("Plugin Info Requested by ", "Info Plugin Diminta oleh ");
        IDTranslations.put("**[DISCORD] PLUGIN INFO :**", "**[DISCORD] INFO PLUGIN :**");
        IDTranslations.put("**Name :** " + ProfileCore.getProfileCore().getDescription().getName(), "**Nama :** " + ProfileCore.getProfileCore().getDescription().getName());
        IDTranslations.put("**Description :** A Simple Player Profile Plugin for RPG Server", "**Deskripsi :** Plugin PlayerProfile Sederhana untuk Server RPG");
        IDTranslations.put("**Command In-Game :** /playerprofile [command]", "**Perintah Di Game :** /playerprofile [command]");
        IDTranslations.put("**Command Discord :** Use /help in discord to see all commands!", "**Perintah Discord :** Gunakan /help di discord untuk melihat semua perintah!");
        IDTranslations.put("**Aliases :** /pp [command]", "**Alias :** /pp [command]");
        IDTranslations.put("_**This PlayerProfile Plugin Made By Phoenix.**_", "_**Plugin PlayerProfile Ini Dibuat oleh Phoenix.**_");
        IDTranslations.put("Playerlist Requested by ", "Daftar Pemain Diminta oleh ");
        IDTranslations.put("**PLAYERLIST :**", "**DAFTAR PEMAIN :**");
        IDTranslations.put("There are no online players!", "Tidak ada pemain online!");
        IDTranslations.put("Profile of %target_message%'s | Profile Requested by ", "Profil dari %target_message%'s | Profil Diminta oleh ");
        IDTranslations.put("Plugin Reload Requested by ", "Muat Ulang Plugin Diminta oleh ");
        IDTranslations.put("Timeout!", "Waktu Habis!");
        IDTranslations.put("Cancelled!", "Dibatalkan!");
        IDTranslations.put("Waiting....", "Menunggu....");
        IDTranslations.put("Reload Success!", "Muat Ulang Berhasil!");
        IDTranslations.put("Reload Unsuccess!", "Muat Ulang Tidak Berhasil!");
        IDTranslations.put("Plugin Version Requested by ", "Versi Plugin Diminta oleh ");
        IDTranslations.put("**[DISCORD] PLUGIN VERSION :**", "**[DISCORD] VERSI PLUGIN :**");

        // Profiles ingame auto translation in Indonesian Language
        IDTranslations.put("&a&lGENERAL INFORMATION:", "&a&lINFORMASI UMUM:");
        IDTranslations.put("&7General information about your profile.", "&7Informasi umum tentang profil Anda.");
        IDTranslations.put("&7General information about %ingame_target_name%'s profile.", "&7Informasi umum tentang profil %ingame_target_name%'s.");
        IDTranslations.put("&a&lSTATUS INFORMATION:", "&a&lINFORMASI STATUS:");
        IDTranslations.put("&7Status information about your profile.", "&7Informasi status tentang profil Anda.");
        IDTranslations.put("&7Status information about %ingame_target_name%'s profile.", "&7Informasi status tentang profil %ingame_target_name%'s.");
        IDTranslations.put("&#32CD32&l%ingame_target_name%'s Profile", "&#32CD32&lProfil %ingame_target_name%'s");
        IDTranslations.put("&aName &f▶ &b", "&aNama &f▶ &b");
        IDTranslations.put("&aRank &f▶ &b", "&aPangkat &f▶ &b");
        IDTranslations.put("&aLast Seen &f▶ &b", "&aTerakhir Terlihat &f▶ &b");
        IDTranslations.put("&aFirst Join &f▶ &b", "&aPertama Gabung &f▶ &b");
        IDTranslations.put("&aTotal Levels Exp &f▶ &b", "&aTotal Tingkat Pengalaman &f▶ &b");
        IDTranslations.put("&aTotal Playtime &f▶ &b", "&aTotal Waktu Bermain &f▶ &b");
        IDTranslations.put("&aTotal Mobs Killed &f▶ &b", "&aTotal Mob Terbunuh &f▶ &b");
        IDTranslations.put("&aClass &f▶ &b", "&aKelas &f▶ &b");
        IDTranslations.put("&aMMO Levels &f▶ &b", "&aLevel MMO &f▶ &b");
        IDTranslations.put("&aTotal Health &f▶ &b", "&aTotal Kesehatan &f▶ &b");
        IDTranslations.put("&aTotal Health Regeneration(/s) &f▶ &b", "&aTotal Regenerasi Kesehatan(/s) &f▶ &b");
        IDTranslations.put("&aTotal Mana &f▶ &b", "&aTotal Mana &f▶ &b");
        IDTranslations.put("&aTotal Mana Regeneration(/s) &f▶ &b", "&aTotal Regenerasi Mana(/s) &f▶ &b");
        IDTranslations.put("&aTotal Stamina &f▶ &b", "&aTotal Daya Tahan &f▶ &b");
        IDTranslations.put("&aTotal Stamina Regeneration(/s) &f▶ &b", "&aTotal Regenerasi Daya Tahan(/s) &f▶ &b");
        IDTranslations.put("&aTotal Stellium &f▶ &b", "&aTotal Stelium &f▶ &b");
        IDTranslations.put("&aTotal Stellium Regeneration(/s) &f▶ &b", "&aTotal Regenerasi Stelium(/s) &f▶ &b");
        IDTranslations.put("&aHelmet &f▶", "&aHelm &f▶");
        IDTranslations.put("&aChestplate &f▶", "&aPelindung Dada &f▶");
        IDTranslations.put("&aLeggings &f▶", "&aPembalut Kaki &f▶");
        IDTranslations.put("&aBoots &f▶", "&aSepatu Bot &f▶");
        IDTranslations.put("&aMain Hand &f▶", "&aTangan Utama &f▶");
        IDTranslations.put("&aOff Hand &f▶", "&aLepas Tangan &f▶");
        IDTranslations.put(" &a•) Name &f▶ &b", " &a•) Nama &f▶ &b");
        IDTranslations.put(" &a•) Type &f▶ &b", " &a•) Tipe &f▶ &b");
        IDTranslations.put(" &a•) Durability &f▶ &b", " &a•) Daya Tahan &f▶ &b");
        IDTranslations.put(" &a•) Enchantments &f▶ &b", " &a•) Pesona &f▶ &b");

        // Profiles discord auto translation in Indonesian Language
        IDTranslations.put("**GENERAL INFORMATION:**", "**INFORMASI UMUM:**");
        IDTranslations.put("**MMO STATS INFORMATION:**", "**INFORMASI STATISTIK MMO:**");
        IDTranslations.put("**TOOLS INFORMATION:**", "**INFORMASI PERALATAN:**");
        IDTranslations.put("**STATUS INFORMATION:**", "**INFORMASI STATUS:**");
        IDTranslations.put("Helmet Armor:", "Pelindung Helm:");
        IDTranslations.put("Chestplate Armor:", "Pelindung Dada:");
        IDTranslations.put("Leggings Armor:", "Pelindung Pembakut Kaki:");
        IDTranslations.put("Boots Armor:", "Pelindung Sepatu Bot:");
        IDTranslations.put("Main Hand:", "Tangan Utama:");
        IDTranslations.put("Off Hand:", "Lepas Tangan:");
        IDTranslations.put(" **•) Name:** ", " **•) Nama:** ");
        IDTranslations.put(" **•) Type:** ", " **•) Tipe:** ");
        IDTranslations.put(" **•) Durability:** ", " **•) Daya Tahan:** ");
        IDTranslations.put(" **•) Enchantments:** ", " **•) Pesona:** ");

        // Utils auto translation in Indonesian Language
        IDTranslations.put("UNKNOWN", "TIDAK DIKENAL");
        IDTranslations.put("Yes", "Ya");
        IDTranslations.put("No", "Tidak");
        IDTranslations.put("Now", "Sekarang");
        IDTranslations.put(" Days ", " Hari ");
        IDTranslations.put(" Hours ", " Jam ");
        IDTranslations.put(" Minutes ", " Menit ");
        IDTranslations.put(" Seconds", " Detik");
        IDTranslations.put("&aA new version for PlayerProfile is available. Ask itzthunder_#0000 in discord to get new version of this plugin.", "&aVersi baru untuk PlayerProfile telah tersedia. Tanyakan ke itzthunder_#0000 di discord untuk mendapatkan versi baru dari plugin ini.");
        IDTranslations.put("&aThere are no new version for PlayerProfile plugin.", "&aTidak ada versi baru untuk plugin PlayerProfile.");
        IDTranslations.put("&#32CD32&lYour Profile's", "&#32CD32&lProfil Kamu");
        IDTranslations.put("&c&lNext Pages Profile Menu", "&c&lMenu Profil Halaman Berikutnya");
        IDTranslations.put("&cClick to next pages!", "&c&lKlik ke halaman berikutnya!");
        IDTranslations.put("&c&lClose Profile Menu", "&c&lTutup Menu Profile");
        IDTranslations.put("&cClick to close!", "&c&lKlik untuk menutup!");
        IDTranslations.put("&c&lPrevious Pages Profile Menu", "&c&lMenu Profil Halaman Sebelumnya");
        IDTranslations.put("&c&lClick to previous pages!", "&c&lKlik ke halaman sebelumnya!");

        // Discord with emote auto translation in Indonesian Language
        IDTranslations.put(" You has been timeout to accept reload, write again later", " Anda telah kehabisan waktu untuk menerima muat ulang, tulis lagi nanti");
        IDTranslations.put(" Are you sure you want to reload PlayerProfile plugin?", " Apakah anda yakin ingin memuat ulang plugin PlayerProfile?");
        IDTranslations.put(" You cancelled reload of PlayerProfile Plugin", " Anda membatalkan memuat ulang Plugin PlayerProfile");
        IDTranslations.put(" PlayerProfile Plugin is reloading via discord.... Please wait a moment....", " Plugin PlayerProfile sedang memuat ulang melalui discord.... Harap tunggu sebentar....");
        IDTranslations.put(" Success! Plugin has been reloaded via discord.", " Sukses! Plugin telah dimuat ulang melalui discord.");
        IDTranslations.put(" Error! Plugin can't reload properly via discord. See console for more information.", " Kesalahan! Plugin tidak dapat memuat ulang dengan benar melalui discord. Lihat konsol untuk informasi lebih lanjut.");
        IDTranslations.put(" Plugin version is ", " Versi plugin adalah ");
        IDTranslations.put(" **Name:** ", " **Nama:** ");
        IDTranslations.put(" **Rank:** ", " **Pangkat:** ");
        IDTranslations.put(" **Last Seen:** ", " **Terakhir Terlihat:** ");
        IDTranslations.put(" **First Join:** ", " **Pertama Gabung:** ");
        IDTranslations.put(" **Total Levels Exp:** ", " **Total Level Exp:** ");
        IDTranslations.put(" **Total Playtime:** ", " **Total Waktu Bermain:** ");
        IDTranslations.put(" **Total Mobs Killed:** ", " **Total Mob Terbunuh:** ");
        IDTranslations.put(" **Class:** ", " **Kelas:** ");
        IDTranslations.put(" **MMO Levels:** ", " **Level MMO:** ");
        IDTranslations.put(" **Total Health:** ", " **Total Kesehatan:** ");
        IDTranslations.put(" **Total Health Regeneration(/s):** ", " **Total Regenerasi Kesehatan(/s):** ");
        IDTranslations.put(" **Total Mana:** ", " **Total Mana:** ");
        IDTranslations.put(" **Total Mana Regeneration(/s):** ", " **Total Regenerasi Mana(/s):** ");
        IDTranslations.put(" **Total Stamina:** ", " **Total Daya Tahan:** ");
        IDTranslations.put(" **Total Stamina Regeneration(/s):** ", " **Total Regenerasi Daya Tahan(/s):** ");
        IDTranslations.put(" **Total Stellium:** ", " **Total Stelium:** ");
        IDTranslations.put(" **Total Stellium Regeneration(/s):** ", " **Total Regenerasi Stelium(/s):** ");
        IDTranslations.put("&cAn internal error occurred while attempting to perform this command.", "&cTerjadi kesalahan internal saat mencoba melakukan perintah ini.");
        IDTranslations.put("0H 0M 0S", "0J 0M 0D");
        IDTranslations.put("Cooldown expired", "Waktu tunggu telah habis");
        IDTranslations.put("Cooldown invalid", "Waktu tunggu tidak valid");
        IDTranslations.put("Plugin Setting Requested by ", "Pengaturan Plugin Diminta oleh");
        IDTranslations.put("**[DISCORD] CONFIG SETTING EDITOR :**", "**[DISCORD] EDITOR PENGATURAN CONFIGURASI");
        IDTranslations.put("**[DISCORD] DISCORD CONFIG SETTING EDITOR :**", "**[DISCORD] EDITOR PENGATURAN DISCORD CONFIG :**");
        IDTranslations.put("**[DISCORD] SOUND CONFIG SETTING EDITOR :**", "**[DISCORD] EDITOR PENGATURAN CONFIG SUARA :**");
        IDTranslations.put("**[DISCORD] DISCORD EMOTE SETTING EDITOR :**", "**[DISCORD] EDITOR PENGATURAN CONFIG DISCORD EMOJI :**");
        IDTranslations.put("**[DISCORD] ENGLISH LANGUAGE SETTING EDITOR :**", "**[DISCORD] EDITOR PENGATURAN CONFIG BAHASA INGGRIS :**");
        IDTranslations.put("**[DISCORD] INDONEESIAN LANGUAGE SETTING EDITOR :**", "**[DISCORD] EDITOR PENGATURAN CONFIG BAHASA INDONESIA :**");
        IDTranslations.put("Setting editor for %config_file% via discord, you can select options to", "Editor pengaturan untuk %config_file% melalui discord, Anda dapat memilih opsi untuk");
        IDTranslations.put("edit path in %config_file% from selection menu below. Don't forget to", "mengedit path pada %config_file% dari menu pilihan dibawah ini. Jangan lupa untuk");
        IDTranslations.put("click reload button to reload all configuration in plugin folder!", "Klik tombol muat ulang untuk memuat ulang semua konfigurasi pada folder plugin!");
        IDTranslations.put("**WARNING!** Some path are not available to edit on this config setting due to risky for config system or server.", "**PERINGATAN!** Beberapa path tidak tersedia untuk diedit pada pengaturan konfigurasi ini karena berisiko untuk sistem konfigurasi atau server.");
        IDTranslations.put("**•) %ingame_target_name% :** return target player name.", "**•) %ingame_target_name% :** Mengembalikan nama target pemain.");
        IDTranslations.put("**•) %playerprofile_ingame_cooldown% :** return player cooldown to see profile ingame.", "**•) %playerprofile_ingame_cooldown% :** Mengembalikan cooldown pemain untuk melihat profil dalam game.");
        IDTranslations.put("**•) %playerprofile_ingame_permission% :** return permission ingame.", "**•) %playerprofile_ingame_permission% :** Mengembalikan izin dalam game.");
        IDTranslations.put("**•) %playerprofile_discord_name% :** return discord user name.", "**•) %playerprofile_discord_name% :** Mengembalikan nama pengguna Discord.");
        IDTranslations.put("**•) %playerprofile_discord_cooldown% :** return user cooldown to see profile in discord.", "**•) %playerprofile_discord_cooldown% :** Mengembalikan cooldown pengguna untuk melihat profil di Discord.");
        IDTranslations.put("**•) %playerprofile_discord_permission% :** return permission in discord.", "**•) %playerprofile_discord_permission% :** Mengembalikan izin dalam Discord.");
        IDTranslations.put("Available Placeholder:", "Placeholder yang Tersedia:");
        IDTranslations.put("Section", "Bagian");
        IDTranslations.put("Change path in %config_file% section", "Ubah path pada bagian %config_file%.");
        IDTranslations.put("Previous", "Sebelumnya");
        IDTranslations.put("Reload", "Muat Ulang");
        IDTranslations.put("Next", "Selanjutnya");
        IDTranslations.put("Success", "Berhasil");
        IDTranslations.put("**WARNING!** Some profile feature may not be available due to limited offline player data.", "**PERINGATAN!** Beberapa fitur profil mungkin tidak tersedia karena terbatasnya data pemain offline.");
        return IDTranslations;
    }

    public FileConfiguration loadConfigTranslation(String language) {
        File configFile = new File(ProfileCore.getProfileCore().getDataFolder(), "Language/" + language + ".yml");
        return YamlConfiguration.loadConfiguration(configFile);
    }
}
