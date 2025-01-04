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
import org.phoenix.playerprofile.utils.Logger;

public class Playtime {

    /**
     * Checking Players Playtime System
     * @see #getPlaytime(Long)
     */
    public static String getPlaytime(Long joindate) {
        Long now = System.currentTimeMillis();
        long date = now - joindate;

        long seconds = date / 1000 % 60;
        long minutes = date / (60 * 1000) % 60;
        long hours = date / (60 * 60 * 1000) % 24;
        long days = date / (24 * 60 * 60 * 1000);

        // Check If Language Section is not empty or null
        if (ProfileCore.getConfigData().getInstance().getString("Language") == null) {
            Logger.log(Logger.LogLevel.ERROR, "An error occurred while fetch Language into Language section in config.yml. Please check your Language section in config.yml.");
            return " ";
        }

        return days + ProfileCore.getTranslatorAPI().translate(" Days ") +
                hours + ProfileCore.getTranslatorAPI().translate(" Hours ") +
                minutes + ProfileCore.getTranslatorAPI().translate(" Minutes ") +
                seconds + ProfileCore.getTranslatorAPI().translate(" Seconds");
    }
}