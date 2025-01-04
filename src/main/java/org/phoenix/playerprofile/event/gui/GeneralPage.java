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

package org.phoenix.playerprofile.event.gui;

import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.manager.SoundManager;
import org.phoenix.playerprofile.players.FirstPlayed;
import org.phoenix.playerprofile.players.LastSeen;
import org.phoenix.playerprofile.players.Playtime;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;
import org.phoenix.playerprofile.utils.HeadDatabase.Enum.HeadType;
import org.phoenix.playerprofile.utils.HeadDatabase.HeadManager;
import org.phoenix.playerprofile.utils.Logger;

import java.util.ArrayList;

public class GeneralPage implements Listener {

    private static Inventory GeneralPage;

    @SuppressWarnings("ConstantConditions")
    public static void createProfileGUI(Player player) {
        // Check If Language Section is not empty or null
        if (ProfileCore.getConfigData().getInstance().getString("Language") == null) {
            Logger.log(Logger.LogLevel.ERROR, "An error occurred while fetch Language into Language section in config.yml. Please check your Language section in config.yml.");
            return;
        }

        Player target = ProfileCore.getProfileCore().targetMap.get(player);
        GeneralPage = Bukkit.createInventory(null, 54, ColorCodeTranslate.chat(player.getName().equals(target.getName()) ?
                ProfileCore.getTranslatorAPI().translate("&#32CD32&lYour Profile's") :
                ProfileCore.getTranslatorAPI().translate("&#32CD32&l%ingame_target_name%'s Profile").replace("%ingame_target_name%", target.getName())));

        ItemStack panel1 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemStack panel2 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemStack titleGeneral = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMxZTczMDIzMzUyY2JjNzdiODk2ZmU3ZWEyNDJiNDMxNDNlMDEzYmVjNWJmMzE0ZDQxZTVmMjY1NDhmYjJkMiJ9fX0=").convert());
        ItemStack targetName = new ItemStack(new HeadManager(HeadType.PLAYER_HEAD, target.getName()).convert());
        ItemStack targetRank = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVkZmEyODRhYTE1MzI0ZTUxNzg1NjFmODAzZjU5NzYyMjhkOTUxMTU1ODNhYjAzMTI2NmFlMjRlZTFhOTlkMSJ9fX0=").convert());
        ItemStack targetLastSeen = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjBhYzkwNzcxNzM5MzU4MDVhMzc5OTAzYmQ1OGQxMjc0NGViNDQyNjcwYmE5ZTAwNDMzMTI4ZDFjZDUyNjA5ZSJ9fX0=").convert());
        ItemStack targetFirstJoin = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZWQ0ODc1MGJiNWFkYTI5ZGZmNjgyMGRiNjkzM2RjNjJhMGJmNmJkZTcyNzM0MWViN2RkMTg0NTNhMTBkNjQ5MyJ9fX0=").convert());
        ItemStack targetTotalExp = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdkODg1YjMyYjBkZDJkNmI3ZjFiNTgyYTM0MTg2ZjhhNTM3M2M0NjU4OWEyNzM0MjMxMzJiNDQ4YjgwMzQ2MiJ9fX0=").convert());
        ItemStack targetPlaytime = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2UyODljMWMwMDVkNDA0MGEyMjhiODQzNmY1NWQzNjYyZTcxNTcxZmI0OTNjYTllZTU4NjFmMjE5NDBhY2Q0ZiJ9fX0=").convert());
        ItemStack targetTotalMobsKill = new ItemStack(Material.NETHERITE_SWORD);
        ItemStack targetClass = new ItemStack(Material.SHIELD);
        ItemStack targetMMOLevels = new ItemStack(Material.BOW);
        ItemStack nextPage = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19").convert());
        ItemStack closePanel = new ItemStack(Material.BARRIER);

        ItemMeta panel1Meta = panel1.getItemMeta();
        ItemMeta panel2Meta = panel2.getItemMeta();
        ItemMeta tGeneralMeta = titleGeneral.getItemMeta();
        ItemMeta tNameMeta = targetName.getItemMeta();
        ItemMeta tRankMeta = targetRank.getItemMeta();
        ItemMeta tLastSeenMeta = targetLastSeen.getItemMeta();
        ItemMeta tFirstJoinMeta = targetFirstJoin.getItemMeta();
        ItemMeta tTotalExpMeta = targetTotalExp.getItemMeta();
        ItemMeta tPlaytimeMeta = targetPlaytime.getItemMeta();
        ItemMeta tTotalMobsKillMeta = targetTotalMobsKill.getItemMeta();
        ItemMeta tClassMeta = targetClass.getItemMeta();
        ItemMeta tMMOLevelsMeta = targetMMOLevels.getItemMeta();
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        ItemMeta closePanelMeta = closePanel.getItemMeta();

        ArrayList<String> loreTGeneral = new ArrayList<>();
        ArrayList<String> loreNextPage = new ArrayList<>();
        ArrayList<String> loreClosePanel = new ArrayList<>();

        panel1.setItemMeta(panel1Meta);
        panel1Meta.setDisplayName(" ");
        panel1.setItemMeta(panel1Meta);

        panel2.setItemMeta(panel2Meta);
        panel2Meta.setDisplayName(" ");
        panel2.setItemMeta(panel2Meta);

        titleGeneral.setItemMeta(tGeneralMeta);
        tGeneralMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&a&lGENERAL INFORMATION:")));
        loreTGeneral.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&7General information about %ingame_target_name%'s profile.").replace("%ingame_target_name%", target.getName())));
        tGeneralMeta.setLore(loreTGeneral);
        titleGeneral.setItemMeta(tGeneralMeta);

        targetName.setItemMeta(tNameMeta);
        tNameMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aName &f▶ &b")) + target.getName());
        targetName.setItemMeta(tNameMeta);

        targetRank.setItemMeta(tRankMeta);
        tRankMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aRank &f▶ &b") + ProfileCore.getLuckPermsManager().getLuckpermsRank(target)));
        targetRank.setItemMeta(tRankMeta);

        targetLastSeen.setItemMeta(tLastSeenMeta);
        tLastSeenMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aLast Seen &f▶ &b") + LastSeen.getLastSeen(target, target.getLastPlayed())));
        targetLastSeen.setItemMeta(tLastSeenMeta);

        targetFirstJoin.setItemMeta(tFirstJoinMeta);
        tFirstJoinMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aFirst Join &f▶ &b") + FirstPlayed.getFirstPlayed(target)));
        targetFirstJoin.setItemMeta(tFirstJoinMeta);

        targetTotalExp.setItemMeta(tTotalExpMeta);
        tTotalExpMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Levels Exp &f▶ &b") + target.getExpToLevel()));
        targetTotalExp.setItemMeta(tTotalExpMeta);

        targetPlaytime.setItemMeta(tPlaytimeMeta);
        tPlaytimeMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Playtime &f▶ &b") + Playtime.getPlaytime(target.getFirstPlayed())));
        targetPlaytime.setItemMeta(tPlaytimeMeta);

        targetTotalMobsKill.setItemMeta(tTotalMobsKillMeta);
        tTotalMobsKillMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mobs Killed &f▶ &b") + target.getStatistic(Statistic.MOB_KILLS)));
        targetTotalMobsKill.setItemMeta(tTotalMobsKillMeta);

        targetClass.setItemMeta(tClassMeta);
        tClassMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aClass &f▶ &b") + PlayerData.get(target).getProfess().getName()));
        targetClass.setItemMeta(tClassMeta);

        targetMMOLevels.setItemMeta(tMMOLevelsMeta);
        tMMOLevelsMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aMMO Levels &f▶ &b") + PlayerData.get(target).getLevel()));
        targetMMOLevels.setItemMeta(tMMOLevelsMeta);

        nextPage.setItemMeta(nextPageMeta);
        nextPageMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&c&lNext Pages Profile Menu")));
        loreNextPage.add(" ");
        loreNextPage.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&cClick to next pages!")));
        nextPageMeta.setLore(loreNextPage);
        nextPage.setItemMeta(nextPageMeta);

        closePanel.setItemMeta(closePanelMeta);
        closePanelMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&c&lClose Profile Menu")));
        loreClosePanel.add(" ");
        loreClosePanel.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&cClick to close!")));
        closePanelMeta.setLore(loreClosePanel);
        closePanel.setItemMeta(closePanelMeta);

        GeneralPage.setItem(0, panel1);
        GeneralPage.setItem(1, panel1);
        GeneralPage.setItem(2, panel1);
        GeneralPage.setItem(3, panel1);
        GeneralPage.setItem(4, titleGeneral);
        GeneralPage.setItem(5, panel1);
        GeneralPage.setItem(6, panel1);
        GeneralPage.setItem(7, panel1);
        GeneralPage.setItem(8, panel1);
        GeneralPage.setItem(9, panel1);
        GeneralPage.setItem(10, panel1);
        GeneralPage.setItem(11, panel2);
        GeneralPage.setItem(12, panel2);
        GeneralPage.setItem(13, panel2);
        GeneralPage.setItem(14, panel2);
        GeneralPage.setItem(15, panel2);
        GeneralPage.setItem(16, panel1);
        GeneralPage.setItem(17, panel1);
        GeneralPage.setItem(18, panel1);
        GeneralPage.setItem(19, panel2);
        GeneralPage.setItem(20, panel2);
        GeneralPage.setItem(21, targetName);
        GeneralPage.setItem(22, panel2);
        GeneralPage.setItem(23, targetRank);
        GeneralPage.setItem(24, panel2);
        GeneralPage.setItem(25, panel2);
        GeneralPage.setItem(26, panel1);
        GeneralPage.setItem(27, panel1);
        GeneralPage.setItem(28, panel2);
        GeneralPage.setItem(29, targetLastSeen);
        GeneralPage.setItem(30, panel2);
        GeneralPage.setItem(31, targetFirstJoin);
        GeneralPage.setItem(32, panel2);
        GeneralPage.setItem(33, targetTotalExp);
        GeneralPage.setItem(34, panel2);
        GeneralPage.setItem(35, panel1);
        GeneralPage.setItem(36, panel1);
        GeneralPage.setItem(37, targetPlaytime);
        GeneralPage.setItem(38, panel2);
        GeneralPage.setItem(39, targetTotalMobsKill);
        GeneralPage.setItem(40, panel2);
        GeneralPage.setItem(41, targetClass);
        GeneralPage.setItem(42, panel2);
        GeneralPage.setItem(43, targetMMOLevels);
        GeneralPage.setItem(44, panel1);
        GeneralPage.setItem(45, panel1);
        GeneralPage.setItem(46, panel1);
        GeneralPage.setItem(47, panel1);
        GeneralPage.setItem(48, panel1);
        GeneralPage.setItem(49, closePanel);
        GeneralPage.setItem(50, panel1);
        GeneralPage.setItem(51, panel1);
        GeneralPage.setItem(52, nextPage);
        GeneralPage.setItem(53, panel1);
        player.openInventory(GeneralPage);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int clickedSlot = event.getSlot();
        ItemStack clickedItem = event.getCurrentItem();

        if (!event.getInventory().equals(GeneralPage)) return;
        event.setCancelled(true);

        if (clickedItem == null || clickedItem.getType().isAir()) return;

        if (clickedSlot == 49) {
            SoundManager.ON_INVENTORY_CLOSE.playSound(player);
            ProfileCore.getProfileCore().targetMap.remove(player);
            player.closeInventory();
        }

        if (clickedSlot == 52) {
            SoundManager.ON_INVENTORY_NEXT_PAGE.playSound(player);
            MMOStatsPage.createProfileGUI(player);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().equals(GeneralPage)) {
            event.setCancelled(true);
        }
    }
}
