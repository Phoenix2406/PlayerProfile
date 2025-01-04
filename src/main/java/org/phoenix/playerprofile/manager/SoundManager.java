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

package org.phoenix.playerprofile.manager;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.utils.Logger;

public enum SoundManager {

    ON_COOLDOWN_COMMAND(ProfileCore.getSoundConfig().getInstance().getString("ON_COOLDOWN_COMMAND"), "ENTITY_VILLAGER_NO,1,1"),
    ON_VIEW_PROFILE_CHAT(ProfileCore.getSoundConfig().getInstance().getString("ON_VIEW_PROFILE_CHAT"), "ENTITY_PLAYER_LEVELUP,1,1"),
    ON_INVENTORY_OPEN(ProfileCore.getSoundConfig().getInstance().getString("ON_INVENTORY_OPEN"), "BLOCK_CHEST_OPEN,1,1"),
    ON_INVENTORY_CLOSE(ProfileCore.getSoundConfig().getInstance().getString("ON_INVENTORY_CLOSE"), "BLOCK_CHEST_CLOSE,1,1"),
    ON_INVENTORY_NEXT_PAGE(ProfileCore.getSoundConfig().getInstance().getString("ON_INVENTORY_NEXT_PAGE"), "BLOCK_DISPENSER_LAUNCH,1,1"),
    ON_INVENTORY_PREVIOUS_PAGE(ProfileCore.getSoundConfig().getInstance().getString("ON_INVENTORY_PREVIOUS_PAGE"), "BLOCK_DISPENSER_DISPENSE,1,1");

    private final String sounds;
    private final String defSounds;

    SoundManager(String sounds, String defSounds) {
        this.sounds = sounds;
        this.defSounds = defSounds;
    }

    public void playSound(Player player) {
        String[] soundParts = sounds.split(",");
        String[] defSoundParts = defSounds.split(",");
        try {
            player.playSound(player.getLocation(), Sound.valueOf(soundParts[0].trim()), Float.parseFloat(soundParts[1].trim()), Float.parseFloat(soundParts[2].trim()));
        } catch (IllegalArgumentException exception) {
            try {
                player.playSound(player.getLocation(), Sound.valueOf(defSoundParts[0].trim()), Float.parseFloat(defSoundParts[1].trim()), Float.parseFloat(defSoundParts[2].trim()));
            } catch (IllegalArgumentException argumentException) {
                Logger.log(Logger.LogLevel.WARNING, "Couldn't use sound from SoundConfig.yml because of invalid minecraft version sounds!");
            }
        }
    }

    public String getSound() {
        return sounds;
    }
    public String getDefSound() {
        return defSounds;
    }
}
