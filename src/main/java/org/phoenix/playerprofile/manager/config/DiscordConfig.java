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

package org.phoenix.playerprofile.manager.config;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;

import java.io.File;
import java.io.IOException;

public class DiscordConfig {

    private final ProfileCore plugin;
    private static YamlDocument discordConfig;
    private final String configName;
    private final boolean setAutoUpdate;
    private final String versioningPath;

    public DiscordConfig(ProfileCore plugin, String configName, boolean setAutoUpdate, String versioningPath) {
        this.plugin = plugin;
        this.configName = configName;
        this.setAutoUpdate = setAutoUpdate;
        this.versioningPath = versioningPath;
    }

    @SuppressWarnings("ConstantConditions")
    public void load() {
        try {
            discordConfig = YamlDocument.create(new File(plugin.getDataFolder(), configName), plugin.getResource(configName), GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(setAutoUpdate).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning(versioningPath)).build());
        } catch (IOException exception) {
            Log4JLogger.printStackTrace(exception);
        }
    }

    public YamlDocument getInstance() {
        return discordConfig;
    }
}
