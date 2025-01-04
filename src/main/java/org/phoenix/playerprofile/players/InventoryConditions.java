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

package org.phoenix.playerprofile.players;

import org.phoenix.playerprofile.ProfileCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class InventoryConditions {

    private InventoryConditions() { }
    private static final InventoryConditions INVENTORY_CONDITIONS = new InventoryConditions();

    /**
     * Checking Player Armor Name and Durability System
     * Instance to Access InventoryConditions with {@link #getArmorManager()}
     *
     * @see #checkHelmetName(Player)
     * @see  #checkHelmetType(Player)
     * @see  #checkHelmetDurability(Player)
     * @see  #checkHelmetEnchants(Player)
     */
    public String checkHelmetName(Player player) {
        if (player.getInventory().getHelmet() != null) {
            return player.getInventory().getHelmet().getItemMeta() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getHelmet().getItemMeta().hasDisplayName() ? player.getInventory().getHelmet().getItemMeta().getDisplayName() : player.getInventory().getHelmet().getType().name().replace("_", " ").toLowerCase();
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    public Object checkHelmetType(Player player) {
        return player.getInventory().getHelmet() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getHelmet().getType();
    }

    public Object checkHelmetDurability(Player player) {
        return player.getInventory().getHelmet() == null ? "-1" : ItemDurability.getDurability(player.getInventory().getHelmet()) + "/" + player.getInventory().getHelmet().getType().getMaxDurability();
    }

    public String checkHelmetEnchants(Player player) {
        if (player.getInventory().getHelmet() != null) {
            return  player.getInventory().getHelmet().getItemMeta() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getHelmet().getItemMeta().hasEnchants() ? ProfileCore.getTranslatorAPI().translate("Yes") : ProfileCore.getTranslatorAPI().translate("No");
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    /**
     * Checking Player Armor Name and Durability System
     * Instance to Access InventoryConditions with {@link #getArmorManager()}
     *
     * @see #checkChestplateName(Player)
     * @see  #checkChestplateType(Player)
     * @see  #checkChestplateDurability(Player)
     * @see  #checkChestplateEnchants(Player)
     */
    public String checkChestplateName(Player player) {
        if (player.getInventory().getChestplate() != null) {
            return player.getInventory().getChestplate().getItemMeta() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getChestplate().getItemMeta().hasDisplayName() ? player.getInventory().getChestplate().getItemMeta().getDisplayName() : player.getInventory().getChestplate().getType().name().replace("_", " ").toLowerCase();
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    public Object checkChestplateType(Player player) {
        return player.getInventory().getChestplate() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getChestplate().getType();
    }

    public Object checkChestplateDurability(Player player) {
        return player.getInventory().getChestplate() == null ? "-1" : ItemDurability.getDurability(player.getInventory().getChestplate()) + "/" + player.getInventory().getChestplate().getType().getMaxDurability();
    }

    public String checkChestplateEnchants(Player player) {
        if (player.getInventory().getChestplate() != null) {
            return player.getInventory().getChestplate().getItemMeta() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getChestplate().getItemMeta().hasEnchants() ? ProfileCore.getTranslatorAPI().translate("Yes") : ProfileCore.getTranslatorAPI().translate("No");
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    /**
     * Checking Player Armor Name and Durability System
     * Instance to Access InventoryConditions with {@link #getArmorManager()}
     *
     * @see #checkLeggingsName(Player)
     * @see  #checkLeggingsType(Player)
     * @see  #checkLeggingsDurability(Player)
     * @see  #checkLeggingsEnchants(Player)
     */
    public String checkLeggingsName(Player player) {
        if (player.getInventory().getLeggings() != null) {
            return player.getInventory().getLeggings().getItemMeta() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getLeggings().getItemMeta().hasDisplayName() ? player.getInventory().getLeggings().getItemMeta().getDisplayName() : player.getInventory().getLeggings().getType().name().replace("_", " ").toLowerCase();
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    public Object checkLeggingsType(Player player) {
        return player.getInventory().getLeggings() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getLeggings().getType();
    }

    public Object checkLeggingsDurability(Player player) {
        return player.getInventory().getLeggings() == null ? "-1" : ItemDurability.getDurability(player.getInventory().getLeggings()) + "/" + player.getInventory().getLeggings().getType().getMaxDurability();
    }

    public String checkLeggingsEnchants(Player player) {
        if (player.getInventory().getLeggings() != null) {
            return player.getInventory().getLeggings().getItemMeta() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getLeggings().getItemMeta().hasEnchants() ? ProfileCore.getTranslatorAPI().translate("Yes") : ProfileCore.getTranslatorAPI().translate("No");
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    /**
     * Checking Player Armor Name and Durability System
     * Instance to Access InventoryConditions with {@link #getArmorManager()}
     *
     * @see #checkBootsName(Player)
     * @see  #checkBootsType(Player)
     * @see  #checkBootsDurability(Player)
     * @see  #checkBootsEnchants(Player)
     */
    public String checkBootsName(Player player) {
        if (player.getInventory().getBoots() != null) {
            return player.getInventory().getBoots().getItemMeta() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getBoots().getItemMeta().hasDisplayName() ? player.getInventory().getBoots().getItemMeta().getDisplayName() : player.getInventory().getBoots().getType().name().replace("_", " ").toLowerCase();
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    public Object checkBootsType(Player player) {
        return player.getInventory().getBoots() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getBoots().getType();
    }

    public Object checkBootsDurability(Player player) {
        return player.getInventory().getBoots() == null ? "-1" : ItemDurability.getDurability(player.getInventory().getBoots()) + "/" + player.getInventory().getBoots().getType().getMaxDurability();
    }

    public String checkBootsEnchants(Player player) {
        if (player.getInventory().getBoots() != null) {
            return player.getInventory().getBoots().getItemMeta() == null ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getBoots().getItemMeta().hasEnchants() ? ProfileCore.getTranslatorAPI().translate("Yes") : ProfileCore.getTranslatorAPI().translate("No");
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    /**
     * Checking Player Armor Name and Durability System
     * Instance to Access InventoryConditions with {@link #getArmorManager()}
     *
     * @see #checkMainHandName(Player)
     * @see  #checkMainHandType(Player)
     * @see  #checkMainHandDurability(Player)
     * @see  #checkMainHandEnchants(Player)
     */
    public String checkMainHandName(Player player) {
        if (player.getInventory().getItemInMainHand().getItemMeta() != null || !player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            return player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName() ? player.getInventory().getItemInMainHand().getItemMeta().getDisplayName() : player.getInventory().getItemInMainHand().getType().name().replace("_", " ").toLowerCase();
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    public Object checkMainHandType(Player player) {
        return player.getInventory().getItemInMainHand().getType().equals(Material.AIR) ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getItemInMainHand().getType();
    }

    public Object checkMainHandDurability(Player player) {
        return player.getInventory().getItemInMainHand().getType().equals(Material.AIR) ? "-1" : ItemDurability.getDurability(player.getInventory().getItemInMainHand()) + "/" + player.getInventory().getItemInMainHand().getType().getMaxDurability();
    }

    public String checkMainHandEnchants(Player player) {
        if (player.getInventory().getItemInMainHand().getItemMeta() != null || !player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
            return player.getInventory().getItemInMainHand().getItemMeta().hasEnchants() ? ProfileCore.getTranslatorAPI().translate("Yes") : ProfileCore.getTranslatorAPI().translate("No");
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    /**
     * Checking Player Armor Name and Durability System
     * Instance to Access InventoryConditions with {@link #getArmorManager()}
     *
     * @see #checkOffHandName(Player)
     * @see  #checkOffHandType(Player)
     * @see  #checkOffHandDurability(Player)
     * @see  #checkOffHandEnchants(Player)
     */
    public String checkOffHandName(Player player) {
        if (player.getInventory().getItemInOffHand().getItemMeta() != null || !player.getInventory().getItemInOffHand().getType().equals(Material.AIR)) {
            return player.getInventory().getItemInOffHand().getItemMeta().hasDisplayName() ? player.getInventory().getItemInOffHand().getItemMeta().getDisplayName() : player.getInventory().getItemInOffHand().getType().name().replace("_", " ").toLowerCase();
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    public Object checkOffHandType(Player player) {
        return player.getInventory().getItemInOffHand().getType().equals(Material.AIR) ? ProfileCore.getTranslatorAPI().translate("UNKNOWN") : player.getInventory().getItemInOffHand().getType();
    }

    public Object checkOffHandDurability(Player player) {
        return player.getInventory().getItemInOffHand().getType().equals(Material.AIR) ? "-1" : ItemDurability.getDurability(player.getInventory().getItemInOffHand()) + "/" + player.getInventory().getItemInOffHand().getType().getMaxDurability();
    }

    public String checkOffHandEnchants(Player player) {
        if (player.getInventory().getItemInOffHand().getItemMeta() != null || !player.getInventory().getItemInOffHand().getType().equals(Material.AIR)) {
            return player.getInventory().getItemInOffHand().getItemMeta().hasEnchants() ? ProfileCore.getTranslatorAPI().translate("Yes") : ProfileCore.getTranslatorAPI().translate("No");
        } else {
            return ProfileCore.getTranslatorAPI().translate("UNKNOWN");
        }
    }

    public static InventoryConditions getArmorManager() {
        return INVENTORY_CONDITIONS;
    }
}
