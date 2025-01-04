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

package org.phoenix.playerprofile.dependencies;

import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.dependencies.PlaceholderAPI.PlaceholderAPIHook;

public class RegisterDependency {

    // Registering dependency
    public static void Register(ProfileCore plugin) {
        new LuckPermsHook();
        new PlaceholderAPIHook(plugin);
        new MythicLibHook();
        new MMOCoreHook();
    }
}
