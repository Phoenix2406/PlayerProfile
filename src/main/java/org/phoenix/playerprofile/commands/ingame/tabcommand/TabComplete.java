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

package org.phoenix.playerprofile.commands.ingame.tabcommand;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.phoenix.playerprofile.commands.ingame.CommandHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TabComplete implements TabCompleter {
    private final CommandHandler commandHandler;

    public TabComplete(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (sender instanceof Player player) {
            if (args.length == 1) {
                String commandName = args[0].toLowerCase();
                String requiredPermission = commandHandler.getCommandPermissions(commandName);

                if (requiredPermission.isEmpty() || player.hasPermission(requiredPermission) || player.isOp()) {
                    Set<String> mainCommands = commandHandler.getCommands();
                    StringUtil.copyPartialMatches(args[0], mainCommands, completions);
                }
            } else if (args.length == 2) {
                String subCommandName = args[1].toLowerCase();
                String requiredPermission = commandHandler.getCommandPermissions(subCommandName);

                if (requiredPermission.isEmpty() || player.hasPermission(requiredPermission) || player.isOp()) {
                    Set<String> subCommands = new HashSet<>();
                    if (subCommandName.equalsIgnoreCase("profile")) {
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            subCommands.add(players.getName());
                        }
                    }
                    StringUtil.copyPartialMatches(args[1], subCommands, completions);
                }
            }
        }
        return completions;
    }
}
