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
import org.phoenix.playerprofile.players.InventoryConditions;
import org.phoenix.playerprofile.players.InventoryGUIConditions;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;
import org.phoenix.playerprofile.utils.HeadDatabase.Enum.HeadType;
import org.phoenix.playerprofile.utils.HeadDatabase.HeadManager;
import org.phoenix.playerprofile.utils.Logger;

import java.util.ArrayList;

public class ToolsPage implements Listener {

    private static Inventory ToolsPage;

    @SuppressWarnings("ConstantConditions")
    public static void createProfileGUI(Player player) {
        // Check If Language Section is not empty or null
        if (ProfileCore.getConfigData().getInstance().getString("Language") == null) {
            Logger.log(Logger.LogLevel.ERROR, "An error occurred while fetch Language into Language section in config.yml. Please check your Language section in config.yml.");
            return;
        }

        Player target = ProfileCore.getProfileCore().targetMap.get(player);
        ToolsPage = Bukkit.createInventory(null, 54, ColorCodeTranslate.chat(player.getName().equals(target.getName()) ?
                ProfileCore.getTranslatorAPI().translate("&#32CD32&lYour Profile's") :
                ProfileCore.getTranslatorAPI().translate("&#32CD32&l%ingame_target_name%'s Profile").replace("%ingame_target_name%", target.getName())));

        ItemStack panel1 = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemStack panel2 = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
        ItemStack titleStatus = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYwMWVjNjMzMWEzYmMzMGE4MjA0ZWM1NjM5OGQwOGNhMzg3ODg1NTZiY2E5YjgxZDc3NmY2MjM4ZDU2NzM2NyJ9fX0=").convert());
        ItemStack targetHelmetArmor = new ItemStack(InventoryGUIConditions.getGUIMaterial().checkMaterialHelmet(target));
        ItemStack targetChestplateArmor = new ItemStack(InventoryGUIConditions.getGUIMaterial().checkMaterialChestplate(target));
        ItemStack targetLeggingsArmor = new ItemStack(InventoryGUIConditions.getGUIMaterial().checkMaterialLeggings(target));
        ItemStack targetBootsArmor = new ItemStack(InventoryGUIConditions.getGUIMaterial().checkMaterialBoots(target));
        ItemStack targetMainHandItem = new ItemStack(InventoryGUIConditions.getGUIMaterial().checkMaterialMainHand(target));
        ItemStack targetOffHandItem = new ItemStack(InventoryGUIConditions.getGUIMaterial().checkMaterialOffHand(target));
        ItemStack previousPage = new ItemStack(new HeadManager(HeadType.BASE64, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==").convert());
        ItemStack closePanel = new ItemStack(Material.BARRIER);

        ItemMeta panel1Meta = panel1.getItemMeta();
        ItemMeta panel2Meta = panel2.getItemMeta();
        ItemMeta tStatusMeta = titleStatus.getItemMeta();
        ItemMeta tHelmetArmorMeta = targetHelmetArmor.getItemMeta();
        ItemMeta tChestplateArmorMeta = targetChestplateArmor.getItemMeta();
        ItemMeta tLeggingsArmorMeta = targetLeggingsArmor.getItemMeta();
        ItemMeta tBootsArmorMeta = targetBootsArmor.getItemMeta();
        ItemMeta tMainHandItemMeta = targetMainHandItem.getItemMeta();
        ItemMeta tOffHandItemMeta = targetOffHandItem.getItemMeta();
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        ItemMeta closePanelMeta = closePanel.getItemMeta();

        ArrayList<String> loreTStatus = new ArrayList<>();
        ArrayList<String> loreHelmetArmor = new ArrayList<>();
        ArrayList<String> loreChestplateArmor = new ArrayList<>();
        ArrayList<String> loreLeggingsArmor = new ArrayList<>();
        ArrayList<String> loreBootsArmor = new ArrayList<>();
        ArrayList<String> loreMainHandItem = new ArrayList<>();
        ArrayList<String> loreOffHandItem = new ArrayList<>();
        ArrayList<String> lorePreviousPage = new ArrayList<>();
        ArrayList<String> loreClosePanel = new ArrayList<>();

        panel1.setItemMeta(panel1Meta);
        panel1Meta.setDisplayName(" ");
        panel1.setItemMeta(panel1Meta);

        panel2.setItemMeta(panel2Meta);
        panel2Meta.setDisplayName(" ");
        panel2.setItemMeta(panel2Meta);

        titleStatus.setItemMeta(tStatusMeta);
        tStatusMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&a&lSTATUS INFORMATION:")));
        loreTStatus.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&7Status information about %ingame_target_name%'s profile.").replace("%ingame_target_name%", target.getName())));
        tStatusMeta.setLore(loreTStatus);
        titleStatus.setItemMeta(tStatusMeta);

        targetHelmetArmor.setItemMeta(tHelmetArmorMeta);
        tHelmetArmorMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aHelmet &f▶")));
        loreHelmetArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetName(target)));
        loreHelmetArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetType(target)));
        loreHelmetArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetDurability(target)));
        loreHelmetArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkHelmetEnchants(target)));
        tHelmetArmorMeta.setLore(loreHelmetArmor);
        targetHelmetArmor.setItemMeta(tHelmetArmorMeta);

        targetChestplateArmor.setItemMeta(tChestplateArmorMeta);
        tChestplateArmorMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aChestplate &f▶")));
        loreChestplateArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateName(target)));
        loreChestplateArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateType(target)));
        loreChestplateArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateDurability(target)));
        loreChestplateArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkChestplateEnchants(target)));
        tChestplateArmorMeta.setLore(loreChestplateArmor);
        targetChestplateArmor.setItemMeta(tChestplateArmorMeta);

        targetLeggingsArmor.setItemMeta(tLeggingsArmorMeta);
        tLeggingsArmorMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aLeggings &f▶")));
        loreLeggingsArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsName(target)));
        loreLeggingsArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsType(target)));
        loreLeggingsArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsDurability(target)));
        loreLeggingsArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkLeggingsEnchants(target)));
        tLeggingsArmorMeta.setLore(loreLeggingsArmor);
        targetLeggingsArmor.setItemMeta(tLeggingsArmorMeta);

        targetBootsArmor.setItemMeta(tBootsArmorMeta);
        tBootsArmorMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aBoots &f▶")));
        loreBootsArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkBootsName(target)));
        loreBootsArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkBootsType(target)));
        loreBootsArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkBootsDurability(target)));
        loreBootsArmor.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkBootsEnchants(target)));
        tBootsArmorMeta.setLore(loreBootsArmor);
        targetBootsArmor.setItemMeta(tBootsArmorMeta);

        targetMainHandItem.setItemMeta(tMainHandItemMeta);
        tMainHandItemMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aMain Hand &f▶")));
        loreMainHandItem.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandName(target)));
        loreMainHandItem.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandType(target)));
        loreMainHandItem.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkMainHandDurability(target)));
        loreMainHandItem.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkBootsEnchants(target)));
        tMainHandItemMeta.setLore(loreMainHandItem);
        targetMainHandItem.setItemMeta(tMainHandItemMeta);

        targetOffHandItem.setItemMeta(tOffHandItemMeta);
        tOffHandItemMeta.setDisplayName(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&aOff Hand &f▶")));
        loreOffHandItem.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Name &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandName(target)));
        loreOffHandItem.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Type &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandType(target)));
        loreOffHandItem.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Durability &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandDurability(target)));
        loreOffHandItem.add(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate(" &a•) Enchantments &f▶ &b") + InventoryConditions.getArmorManager().checkOffHandEnchants(target)));
        tOffHandItemMeta.setLore(loreOffHandItem);
        targetOffHandItem.setItemMeta(tOffHandItemMeta);

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

        ToolsPage.setItem(0, panel1);
        ToolsPage.setItem(1, panel1);
        ToolsPage.setItem(2, panel1);
        ToolsPage.setItem(3, panel1);
        ToolsPage.setItem(4, titleStatus);
        ToolsPage.setItem(5, panel1);
        ToolsPage.setItem(6, panel1);
        ToolsPage.setItem(7, panel1);
        ToolsPage.setItem(8, panel1);
        ToolsPage.setItem(9, panel1);
        ToolsPage.setItem(10, panel1);
        ToolsPage.setItem(11, panel2);
        ToolsPage.setItem(12, panel2);
        ToolsPage.setItem(13, panel2);
        ToolsPage.setItem(14, panel2);
        ToolsPage.setItem(15, panel2);
        ToolsPage.setItem(16, panel1);
        ToolsPage.setItem(17, panel1);
        ToolsPage.setItem(18, panel1);
        ToolsPage.setItem(19, panel2);
        ToolsPage.setItem(20, targetHelmetArmor);
        ToolsPage.setItem(21, panel2);
        ToolsPage.setItem(22, targetLeggingsArmor);
        ToolsPage.setItem(23, panel2);
        ToolsPage.setItem(24, targetMainHandItem);
        ToolsPage.setItem(25, panel2);
        ToolsPage.setItem(26, panel1);
        ToolsPage.setItem(27, panel1);
        ToolsPage.setItem(28, panel2);
        ToolsPage.setItem(29, panel2);
        ToolsPage.setItem(30, panel2);
        ToolsPage.setItem(31, panel2);
        ToolsPage.setItem(32, panel2);
        ToolsPage.setItem(33, panel2);
        ToolsPage.setItem(34, panel2);
        ToolsPage.setItem(35, panel1);
        ToolsPage.setItem(36, panel1);
        ToolsPage.setItem(37, panel1);
        ToolsPage.setItem(38, targetChestplateArmor);
        ToolsPage.setItem(39, panel2);
        ToolsPage.setItem(40, targetBootsArmor);
        ToolsPage.setItem(41, panel2);
        ToolsPage.setItem(42, targetOffHandItem);
        ToolsPage.setItem(43, panel1);
        ToolsPage.setItem(44, panel1);
        ToolsPage.setItem(45, panel1);
        ToolsPage.setItem(46, previousPage);
        ToolsPage.setItem(47, panel1);
        ToolsPage.setItem(48, panel1);
        ToolsPage.setItem(49, closePanel);
        ToolsPage.setItem(50, panel1);
        ToolsPage.setItem(51, panel1);
        ToolsPage.setItem(52, panel1);
        ToolsPage.setItem(53, panel1);
        player.openInventory(ToolsPage);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        int clickedSlot = event.getSlot();
        ItemStack clickedItem = event.getCurrentItem();

        if (!event.getInventory().equals(ToolsPage)) return;
        event.setCancelled(true);

        if (clickedItem == null || clickedItem.getType().isAir()) return;

        if (clickedSlot == 46) {
            SoundManager.ON_INVENTORY_PREVIOUS_PAGE.playSound(player);
            MMOStatsPage.createProfileGUI(player);
        }

        if (clickedSlot == 49) {
            SoundManager.ON_INVENTORY_CLOSE.playSound(player);
            ProfileCore.getProfileCore().targetMap.remove(player);
            player.closeInventory();
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getInventory().equals(ToolsPage)) {
            event.setCancelled(true);
        }
    }
}
