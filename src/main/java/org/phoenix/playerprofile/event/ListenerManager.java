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

package org.phoenix.playerprofile.event;

import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.event.gui.GeneralPage;
import org.phoenix.playerprofile.event.gui.MMOStatsPage;
import org.phoenix.playerprofile.event.gui.ToolsPage;
import org.phoenix.playerprofile.manager.EmbedManager;
import org.phoenix.playerprofile.notifications.VersionChecker;

public class ListenerManager {

    private final ProfileCore plugin;
    public ListenerManager(ProfileCore plugin) {
        this.plugin = plugin;
    }

    public void RegisterListener() {
        // Register panel gui system
        plugin.getServer().getPluginManager().registerEvents(new GeneralPage(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MMOStatsPage(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new ToolsPage(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VersionChecker.VersionListener(), plugin);
    }
}
