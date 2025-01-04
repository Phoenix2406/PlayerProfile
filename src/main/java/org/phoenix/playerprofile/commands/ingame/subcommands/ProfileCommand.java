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

package org.phoenix.playerprofile.commands.ingame.subcommands;

import io.lumine.mythic.lib.MythicLib;
import me.clip.placeholderapi.PlaceholderAPI;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.Command;
import org.phoenix.playerprofile.commands.ingame.permission.PermissionsNode;
import org.phoenix.playerprofile.event.gui.GeneralPage;
import org.phoenix.playerprofile.manager.SoundManager;
import org.phoenix.playerprofile.objects.CooldownTimer;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.players.FirstPlayed;
import org.phoenix.playerprofile.players.InventoryConditions;
import org.phoenix.playerprofile.players.LastSeen;
import org.phoenix.playerprofile.players.Playtime;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;

import java.text.DecimalFormat;

public class ProfileCommand {

    private final ProfileCore plugin;
    private final DecimalFormat digit;

    public ProfileCommand(ProfileCore plugin) {
        this.plugin = plugin;
        if (ProfileCore.getConfigData().getInstance().getString("IntegerFormat") != null) {
            digit = MythicLib.plugin.getMMOConfig().newDecimalFormat(ProfileCore.getConfigData().getInstance().getString("IntegerFormat"));
        } else {
            digit = MythicLib.plugin.getMMOConfig().newDecimalFormat("0.#");
        }
    }

    @Command(name = "profile", description = "Access to /playerprofile profile [players] command", permission = PermissionsNode.PLUGIN_PERMISSION_PROFILE)
    public void onProfileCommandUsed(Player player, String[] args) {
        if (CooldownTimer.isOnCooldown(player.getName())) {
            String remainingCooldown = CooldownTimer.getRemainingCooldown(player.getName());
            SoundManager.ON_COOLDOWN_COMMAND.playSound(player);
            player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.CooldownMessage")
                    .replace("%playerprofile_ingame_cooldown%", remainingCooldown)));
            return;
        }
        CooldownTimer.setCooldown(player.getName(), NullableChecker.getInstance().getCooldownIngame());

        if (args.length != 2) {
            if (!ProfileCore.getConfigData().getInstance().getBoolean("GUI")) {
                player.sendMessage(ColorCodeTranslate.chat("&#00D4FF&l&m------------&r&8&l[ &#00D4FFPlayerProfile &8&l]&#00D4FF&l&m------------"));
                player.sendMessage(ColorCodeTranslate.chat(""));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&a&lGENERAL INFORMATION:")));
                player.sendMessage(ColorCodeTranslate.chat(""));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aName &f▶ &b") + player.getName()));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aRank &f▶ &b") + ProfileCore.getLuckPermsManager().getLuckpermsRank(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aLast Seen &f▶ &b") + LastSeen.getLastSeen(player, player.getLastPlayed())));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aFirst Join &f▶ &b") + FirstPlayed.getFirstPlayed(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Levels Exp &f▶ &b") + player.getExpToLevel()));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Playtime &f▶ &b") + Playtime.getPlaytime(player.getFirstPlayed())));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mobs Killed &f▶ &b") + player.getStatistic(Statistic.MOB_KILLS)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aClass &f▶ &b") + PlayerData.get(player).getProfess().getName()));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aMMO Levels &f▶ &b") + PlayerData.get(player).getLevel()));
                player.sendMessage(ColorCodeTranslate.chat(""));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Health &f▶ &b") + digit.format(PlayerData.get(player).getPlayer().getHealth())));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Health Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(player).getPlayer().getHealth() / 100)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mana &f▶ &b") + digit.format(PlayerData.get(player).getMana())));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mana Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(player).getMana() / 100)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stamina &f▶ &b") + digit.format(PlayerData.get(player).getStamina())));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stamina Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(player).getStamina() / 100)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stellium &f▶ &b") + digit.format(PlayerData.get(player).getStellium())));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stellium Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(player).getStellium() / 100)));
                player.sendMessage(ColorCodeTranslate.chat(""));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&a&lSTATUS INFORMATION:")));
                player.sendMessage(ColorCodeTranslate.chat(""));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aHelmet &f▶")));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetName(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetType(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetDurability(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetEnchants(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aChestplate &f▶")));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateName(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateType(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateDurability(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateEnchants(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aLeggings &f▶")));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsName(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsType(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsDurability(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsEnchants(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aBoots &f▶")));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkBootsName(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkBootsType(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkBootsDurability(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkBootsEnchants(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aMain Hand &f▶")));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandName(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandType(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandDurability(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandEnchants(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aOff Hand &f▶")));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandName(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandType(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandDurability(player)));
                player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandEnchants(player)));
                player.sendMessage(ColorCodeTranslate.chat(""));
                player.sendMessage(ColorCodeTranslate.chat("&#00D4FF&l&m-------------------------------------"));
                SoundManager.ON_VIEW_PROFILE_CHAT.playSound(player);
            } else {
                plugin.targetMap.remove(plugin.targetMap.containsKey(player) ?
                        plugin.targetMap.remove(player) :
                        plugin.targetMap.put(player, player));
                SoundManager.ON_INVENTORY_OPEN.playSound(player);
                GeneralPage.createProfileGUI(player);
            }
        } else {
            Player target = Bukkit.getPlayer(args[1]);

            if (target != null) {
                if (!target.getName().equals(player.getName())) {
                    if (!ProfileCore.getConfigData().getInstance().getBoolean("GUI")) {
                        plugin.targetMap.put(target, player);
                        player.sendMessage(ColorCodeTranslate.chat("&#00D4FF&l&m------------&r&8&l[ &#00D4FFPlayerProfile &8&l]&#00D4FF&l&m------------"));
                        player.sendMessage(ColorCodeTranslate.chat(""));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&a&lGENERAL INFORMATION:")));
                        player.sendMessage(ColorCodeTranslate.chat(""));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aName &f▶ &b") + target.getName()));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aRank &f▶ &b") + ProfileCore.getLuckPermsManager().getLuckpermsRank(player)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aLast Seen &f▶ &b") + LastSeen.getLastSeen(target, target.getLastPlayed())));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aFirst Join &f▶ &b") + FirstPlayed.getFirstPlayed(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Levels Exp &f▶ &b") + target.getExpToLevel()));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Playtime &f▶ &b") + Playtime.getPlaytime(target.getFirstPlayed())));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mobs Killed &f▶ &b") + target.getStatistic(Statistic.MOB_KILLS)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aClass &f▶ &b") + PlayerData.get(target).getProfess().getName()));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aMMO Levels &f▶ &b") + PlayerData.get(target).getLevel()));
                        player.sendMessage(ColorCodeTranslate.chat(""));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Health &f▶ &b") + digit.format(PlayerData.get(target).getPlayer().getHealth())));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Health Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(target).getPlayer().getHealth() / 100)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mana &f▶ &b") + digit.format(PlayerData.get(target).getMana())));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mana Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(target).getMana() / 100)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stamina &f▶ &b") + digit.format(PlayerData.get(target).getStamina())));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stamina Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(target).getStamina() / 100)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stellium &f▶ &b") + digit.format(PlayerData.get(target).getStellium())));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stellium Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(target).getStellium() / 100)));
                        player.sendMessage(ColorCodeTranslate.chat(""));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&a&lSTATUS INFORMATION:")));
                        player.sendMessage(ColorCodeTranslate.chat(""));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aHelmet &f▶")));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetName(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetType(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetDurability(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetEnchants(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aChestplate &f▶")));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateName(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateType(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateDurability(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateEnchants(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aLeggings &f▶")));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsName(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsType(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsDurability(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsEnchants(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aBoots &f▶")));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkBootsName(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkBootsType(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkBootsDurability(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkBootsEnchants(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aMain Hand &f▶")));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandName(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandType(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandDurability(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandEnchants(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aOff Hand &f▶")));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandName(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandType(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandDurability(target)));
                        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandEnchants(target)));
                        player.sendMessage(ColorCodeTranslate.chat(""));
                        player.sendMessage(ColorCodeTranslate.chat("&#00D4FF&l&m-------------------------------------"));
                        target.sendMessage(ColorCodeTranslate.chat(PlaceholderAPI.setPlaceholders(target, ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.ViewingProfileMessage"))));
                        plugin.targetMap.remove(target);
                        SoundManager.ON_VIEW_PROFILE_CHAT.playSound(player);
                    } else {
                        plugin.targetMap.remove(plugin.targetMap.containsKey(player) ?
                                plugin.targetMap.remove(player) :
                                plugin.targetMap.put(player, target));
                        SoundManager.ON_INVENTORY_OPEN.playSound(player);
                        GeneralPage.createProfileGUI(player);
                        target.sendMessage(ColorCodeTranslate.chat(PlaceholderAPI.setPlaceholders(target, NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.ViewingProfileMessage"))));
                    }
                } else {
                    player.sendMessage(ColorCodeTranslate.chat(PlaceholderAPI.setPlaceholders(player, NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.NoSelfTargetMessage"))));
                }
            } else {
                player.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.InvalidPlayerMessage").replace("%ingame_target_name%", args[1])));
            }
        }
    }
}
