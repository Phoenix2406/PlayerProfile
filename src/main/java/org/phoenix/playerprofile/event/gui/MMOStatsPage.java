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

import io.lumine.mythic.lib.MythicLib;
import net.Indyuce.mmocore.api.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import org.phoenix.playerprofile.utils.ColorCodeTranslate;
import org.phoenix.playerprofile.utils.HeadDatabase.Enum.HeadType;
import org.phoenix.playerprofile.utils.HeadDatabase.HeadManager;
import org.phoenix.playerprofile.utils.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MMOStatsPage implements Listener {

    private static Inventory MMOStatsPage;
    private static DecimalFormat digit;

    public MMOStatsPage() {
        if (ProfileCore.getConfigData().getInstance().getString("IntegerFormat") != null) {
            digit = MythicLib.plugin.getMMOConfig().newDecimalFormat(ProfileCore.getConfigData().getInstance().getString("IntegerFormat"));
        } else {
            digit = MythicLib.plugin.getMMOConfig().newDecimalFormat("0.#");
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static void createProfileGUI(Player player) {
        // Check If Language Section is not empty or null
        if (ProfileCore.getConfigData().getInstance().getString("Language") == null) {
            Logger.log(Logger.LogLevel.ERROR, "An error occurred while fetch Language into Language section in config.yml. Please check your Language section in config.yml.");
            return;
        }

        Player target = ProfileCore.getProfileCore().targetMap.get(player);
        MMOStatsPage = Bukkit.createInventory(null, 54, ColorCodeTranslate.chat(player.getName().equals(target.getName()) ?
                ProfileCore.getTranslatorAPI().translate("&#32CD32&lYour Profile's") :
                ProfileCore.getTranslatorAPI().translate("&#32CD32&l%ingame_target_name%'s Profile").replace("%ingame_target_name%", target.getName())));

        ItemStack panel1 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemStack panel2 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemStack targetTotalHealth = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmI0YTVjMjlkOTAxNzIxODUxZDg4NjhiOTA3NWY0OWM0NzZhODk0MDk4YzdlZjI2NjU4MTNjNTUyYmJjOWFkZCJ9fX0=").convert());
        ItemStack targetTotalHealthRegen = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmI0YTVjMjlkOTAxNzIxODUxZDg4NjhiOTA3NWY0OWM0NzZhODk0MDk4YzdlZjI2NjU4MTNjNTUyYmJjOWFkZCJ9fX0=").convert());
        ItemStack targetTotalMana = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDI0MjRhY2RjNmUwOWEyMTJiNWM3N2MyYzFkOWFiZGNkNDMzZDM4NjVkZWQzMzk1OGE1N2Y0MjA0ZWYzMjc4YSJ9fX0=").convert());
        ItemStack targetTotalManaRegen = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDI0MjRhY2RjNmUwOWEyMTJiNWM3N2MyYzFkOWFiZGNkNDMzZDM4NjVkZWQzMzk1OGE1N2Y0MjA0ZWYzMjc4YSJ9fX0=").convert());
        ItemStack targetTotalStamina = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU1NTM4MTIwOWJmOTg5ZThjYWFjM2FlOGQ2YjdlMTkzMzczZjE3MTgwODhmNWRiZjEyMmY3MWY1ZWFmOTBmMCJ9fX0=").convert());
        ItemStack targetTotalStaminaRegen = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjU1NTM4MTIwOWJmOTg5ZThjYWFjM2FlOGQ2YjdlMTkzMzczZjE3MTgwODhmNWRiZjEyMmY3MWY1ZWFmOTBmMCJ9fX0=").convert());
        ItemStack targetTotalStellium = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQzZTgwZGIxZTFkNmU4N2YyMjNjMDkzYmQ0ZTcwOGYzMmU5YTA1MDQwYmQ2OTg1OWQzN2M1NDU3NTEyODA0In19fQ==").convert());
        ItemStack targetTotalStelliumRegen = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWQzZTgwZGIxZTFkNmU4N2YyMjNjMDkzYmQ0ZTcwOGYzMmU5YTA1MDQwYmQ2OTg1OWQzN2M1NDU3NTEyODA0In19fQ==").convert());
        ItemStack nextPage = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19").convert());
        ItemStack previousPage = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==").convert());
        ItemStack closePanel = new ItemStack(Material.BARRIER);

        ItemMeta panel1Meta = panel1.getItemMeta();
        ItemMeta panel2Meta = panel2.getItemMeta();
        ItemMeta tTotalHealthMeta = targetTotalHealth.getItemMeta();
        ItemMeta tTotalHealthRegenMeta = targetTotalHealthRegen.getItemMeta();
        ItemMeta tTotalManaMeta = targetTotalMana.getItemMeta();
        ItemMeta tTotalManaRegenMeta = targetTotalManaRegen.getItemMeta();
        ItemMeta tTotalStaminaMeta = targetTotalStamina.getItemMeta();
        ItemMeta tTotalStaminaRegenMeta = targetTotalStaminaRegen.getItemMeta();
        ItemMeta tTotalStelliumMeta = targetTotalStellium.getItemMeta();
        ItemMeta tTotalStelliumRegenMeta = targetTotalStelliumRegen.getItemMeta();
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        ItemMeta closePanelMeta = closePanel.getItemMeta();

        ArrayList<String> loreNextPage = new ArrayList<>();
        ArrayList<String> lorePreviousPage = new ArrayList<>();
        ArrayList<String> loreClosePanel = new ArrayList<>();

        panel1.setItemMeta(panel1Meta);
        panel1Meta.setDisplayName(" ");
        panel1.setItemMeta(panel1Meta);

        panel2.setItemMeta(panel2Meta);
        panel2Meta.setDisplayName(" ");
        panel2.setItemMeta(panel2Meta);

        targetTotalHealth.setItemMeta(tTotalHealthMeta);
        tTotalHealthMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Health &f▶ &b") + digit.format(PlayerData.get(target).getPlayer().getHealth())));
        targetTotalHealth.setItemMeta(tTotalHealthMeta);

        targetTotalHealthRegen.setItemMeta(tTotalHealthRegenMeta);
        tTotalHealthRegenMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Health Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(target).getPlayer().getHealth() / 100)));
        targetTotalHealthRegen.setItemMeta(tTotalHealthRegenMeta);

        targetTotalMana.setItemMeta(tTotalManaMeta);
        tTotalManaMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mana &f▶ &b") + digit.format(PlayerData.get(target).getMana())));
        targetTotalMana.setItemMeta(tTotalManaMeta);

        targetTotalManaRegen.setItemMeta(tTotalManaRegenMeta);
        tTotalManaRegenMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Mana Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(target).getMana() / 100)));
        targetTotalManaRegen.setItemMeta(tTotalManaRegenMeta);

        targetTotalStamina.setItemMeta(tTotalStaminaMeta);
        tTotalStaminaMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stamina &f▶ &b") + digit.format(PlayerData.get(target).getStamina())));
        targetTotalStamina.setItemMeta(tTotalStaminaMeta);

        targetTotalStaminaRegen.setItemMeta(tTotalStaminaRegenMeta);
        tTotalStaminaRegenMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stamina Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(target).getStamina() / 100)));
        targetTotalStaminaRegen.setItemMeta(tTotalStaminaRegenMeta);

        targetTotalStellium.setItemMeta(tTotalStelliumMeta);
        tTotalStelliumMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stellium &f▶ &b") + digit.format(PlayerData.get(target).getStellium())));
        targetTotalStellium.setItemMeta(tTotalStelliumMeta);

        targetTotalStelliumRegen.setItemMeta(tTotalStelliumRegenMeta);
        tTotalStelliumRegenMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aTotal Stellium Regeneration(/s) &f▶ &b") + digit.format(PlayerData.get(target).getStellium() / 100)));
        targetTotalStelliumRegen.setItemMeta(tTotalStelliumRegenMeta);

        nextPage.setItemMeta(nextPageMeta);
        nextPageMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&c&lNext Pages Profile Menu")));
        loreNextPage.add(" ");
        loreNextPage.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&cClick to next pages!")));
        nextPageMeta.setLore(loreNextPage);
        nextPage.setItemMeta(nextPageMeta);

        previousPage.setItemMeta(previousPageMeta);
        previousPageMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&c&lPrevious Pages Profile Menu")));
        lorePreviousPage.add(" ");
        lorePreviousPage.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&c&lClick to previous pages!")));
        previousPageMeta.setLore(lorePreviousPage);
        previousPage.setItemMeta(previousPageMeta);

        closePanel.setItemMeta(closePanelMeta);
        closePanelMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&c&lClose Profile Menu")));
        loreClosePanel.add(" ");
        loreClosePanel.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&cClick to close!")));
        closePanelMeta.setLore(loreClosePanel);
        closePanel.setItemMeta(closePanelMeta);

        MMOStatsPage.setItem(0, panel1);
        MMOStatsPage.setItem(1, panel1);
        MMOStatsPage.setItem(2, panel1);
        MMOStatsPage.setItem(3, panel1);
        MMOStatsPage.setItem(4, panel1);
        MMOStatsPage.setItem(5, panel1);
        MMOStatsPage.setItem(6, panel1);
        MMOStatsPage.setItem(7, panel1);
        MMOStatsPage.setItem(8, panel1);
        MMOStatsPage.setItem(9, panel1);
        MMOStatsPage.setItem(10, targetTotalHealth);
        MMOStatsPage.setItem(11, panel2);
        MMOStatsPage.setItem(12, targetTotalHealthRegen);
        MMOStatsPage.setItem(13, panel2);
        MMOStatsPage.setItem(14, targetTotalMana);
        MMOStatsPage.setItem(15, panel2);
        MMOStatsPage.setItem(16, targetTotalManaRegen);
        MMOStatsPage.setItem(17, panel1);
        MMOStatsPage.setItem(18, panel1);
        MMOStatsPage.setItem(19, panel2);
        MMOStatsPage.setItem(20, panel2);
        MMOStatsPage.setItem(21, panel2);
        MMOStatsPage.setItem(22, panel2);
        MMOStatsPage.setItem(23, panel2);
        MMOStatsPage.setItem(24, panel2);
        MMOStatsPage.setItem(25, panel2);
        MMOStatsPage.setItem(26, panel1);
        MMOStatsPage.setItem(27, panel1);
        MMOStatsPage.setItem(28, targetTotalStamina);
        MMOStatsPage.setItem(29, panel2);
        MMOStatsPage.setItem(30, targetTotalStaminaRegen);
        MMOStatsPage.setItem(31, panel2);
        MMOStatsPage.setItem(32, targetTotalStellium);
        MMOStatsPage.setItem(33, panel2);
        MMOStatsPage.setItem(34, targetTotalStelliumRegen);
        MMOStatsPage.setItem(35, panel1);
        MMOStatsPage.setItem(36, panel1);
        MMOStatsPage.setItem(37, panel1);
        MMOStatsPage.setItem(38, panel2);
        MMOStatsPage.setItem(39, panel2);
        MMOStatsPage.setItem(40, panel2);
        MMOStatsPage.setItem(41, panel2);
        MMOStatsPage.setItem(42, panel2);
        MMOStatsPage.setItem(43, panel1);
        MMOStatsPage.setItem(44, panel1);
        MMOStatsPage.setItem(45, panel1);
        MMOStatsPage.setItem(46, previousPage);
        MMOStatsPage.setItem(47, panel1);
        MMOStatsPage.setItem(48, panel1);
        MMOStatsPage.setItem(49, closePanel);
        MMOStatsPage.setItem(50, panel1);
        MMOStatsPage.setItem(51, panel1);
        MMOStatsPage.setItem(52, nextPage);
        MMOStatsPage.setItem(53, panel1);
        player.openInventory(MMOStatsPage);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int clickedSlot = event.getSlot();
        ItemStack clickedItem = event.getCurrentItem();

        if (!event.getInventory().equals(MMOStatsPage)) return;
        event.setCancelled(true);

        if (clickedItem == null || clickedItem.getType().isAir()) return;

        if (clickedSlot == 46) {
            SoundManager.ON_INVENTORY_PREVIOUS_PAGE.playSound(player);
            GeneralPage.createProfileGUI(player);
        }

        if (clickedSlot == 49) {
            SoundManager.ON_INVENTORY_CLOSE.playSound(player);
            ProfileCore.getProfileCore().targetMap.remove(player);
            player.closeInventory();
        }

        if (clickedSlot == 52) {
            SoundManager.ON_INVENTORY_NEXT_PAGE.playSound(player);
            ToolsPage.createProfileGUI(player);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().equals(MMOStatsPage)) {
            event.setCancelled(true);
        }
    }
}
