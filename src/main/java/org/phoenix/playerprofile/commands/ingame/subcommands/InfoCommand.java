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

package org.phoenix.playerprofile.commands.ingame.subcommands;

import org.bukkit.entity.Player;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.Command;
import org.phoenix.playerprofile.commands.ingame.permission.PermissionsNode;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;

public class InfoCommand {

    @Command(name = "info", description = "Access to info command", permission = PermissionsNode.PLUGIN_PERMISSION_INFO)
    public void onInfoCommandUsed(Player player, String[] args) {
        player.sendMessage(ColorCodeTranslate.chat("&#00D4FF&l&m------------&r&8&l[ &#00D4FFPlayerProfile &8&l]&#00D4FF&l&m------------"));
        player.sendMessage(ColorCodeTranslate.chat(""));
        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&3Name &f▶ &7" + ProfileCore.getProfileCore().getDescription().getName())));
        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&3Description &f▶ &7A Simple Player Profile Plugin for RPG Server")));
        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&3Command In-Game &f▶ &7/playerprofile [command]")));
        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&3Command Discord &f▶ &7Use /help in discord to see all commands!")));
        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&3Aliases &f▶ &7/pp [command]")));
        player.sendMessage(ColorCodeTranslate.chat(""));
        player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&8&oThis Player Profile Plugin Make by Phoenix.")));
        player.sendMessage(ColorCodeTranslate.chat(""));
        player.sendMessage(ColorCodeTranslate.chat("&#00D4FF&l&m-------------------------------------"));
    }
}
