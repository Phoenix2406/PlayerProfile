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

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Optional;

public class InventoryGUIConditions {

    private InventoryGUIConditions() { }
    private static final InventoryGUIConditions INVENTORY_GUI_CONDITIONS = new InventoryGUIConditions();

    /**
     * Checking Inventory GUI Materials System
     * Instance to Access InventoryConditions with {@link #getGUIMaterial()}
     *
     * @see #checkMaterialHelmet(Player)
     * @see #checkMaterialChestplate(Player)
     * @see #checkMaterialLeggings(Player)
     * @see #checkMaterialBoots(Player)
     * @see #checkMaterialMainHand(Player)
     * @see #checkMaterialOffHand(Player)
     */
    public Material checkMaterialHelmet(Player player) {
        return Optional.of(player.getInventory())
                .filter(condition -> condition.getHelmet() != null)
                .map(helmet -> helmet.getHelmet().getType())
                .orElse(Material.WHITE_STAINED_GLASS_PANE);
    }

    public Material checkMaterialChestplate(Player player) {
        return Optional.of(player.getInventory())
                .filter(condition -> condition.getChestplate() != null)
                .map(chestplate -> chestplate.getChestplate().getType())
                .orElse(Material.WHITE_STAINED_GLASS_PANE);
    }
    
    public Material checkMaterialLeggings(Player player) {
        return Optional.of(player.getInventory())
                .filter(condition -> condition.getLeggings() != null)
                .map(leggings -> leggings.getLeggings().getType())
                .orElse(Material.WHITE_STAINED_GLASS_PANE);
    }
    
    public Material checkMaterialBoots(Player player) {
        return Optional.of(player.getInventory())
                .filter(condition -> condition.getBoots() != null)
                .map(boots -> boots.getBoots().getType())
                .orElse(Material.WHITE_STAINED_GLASS_PANE);
    }

    public Material checkMaterialMainHand(Player player) {
        return Optional.of(player.getInventory())
                .filter(condition -> !condition.getItemInMainHand().getType().equals(Material.AIR))
                .map(mainhand -> mainhand.getItemInMainHand().getType())
                .orElse(Material.WHITE_STAINED_GLASS_PANE);
    }

    public Material checkMaterialOffHand(Player player) {
        return Optional.of(player.getInventory())
                .filter(condition -> !condition.getItemInOffHand().getType().equals(Material.AIR))
                .map(offhand -> offhand.getItemInOffHand().getType())
                .orElse(Material.WHITE_STAINED_GLASS_PANE);
    }

    public static InventoryGUIConditions getGUIMaterial() {
        return INVENTORY_GUI_CONDITIONS;
    }
}