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

package org.phoenix.playerprofile.commands.ingame;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.Command;
import org.phoenix.playerprofile.commands.ingame.permission.PermissionsNode;
import org.phoenix.playerprofile.objects.NullableChecker;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;
import org.phoenix.playerprofile.utils.Logger;

import java.lang.reflect.Method;
import java.util.*;

public class CommandHandler implements CommandExecutor {
    private final Map<String, Method> commands;
    private final Map<String, String> commandDescriptions;
    private final Map<String, PermissionsNode> commandPermissions;
    private final Map<String, Object> commandInstances;

    public CommandHandler() {
        this.commands = new HashMap<>();
        this.commandDescriptions = new HashMap<>();
        this.commandPermissions = new HashMap<>();
        this.commandInstances = new HashMap<>();
    }

    public void registerCommand(Object... commandInstances) {
        for (Object instance : commandInstances) {
            for (Method method : instance.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(Command.class)) {
                    Command command = method.getAnnotation(Command.class);
                    String commandName = command.name().toLowerCase();
                    commands.put(commandName, method);

                    for (String aliases : command.aliases()) {
                        commands.put(aliases.toLowerCase(), method);
                    }

                    commandDescriptions.put(commandName, command.description());
                    commandPermissions.put(commandName, command.permission());

                    this.commandInstances.put(commandName, instance);
                }
            }
        }
    }

    public Set<String> getCommands() {
        return commands.keySet();
    }

    public String getCommandDescriptions(String commandName) {
        return commandDescriptions.getOrDefault(commandName, "");
    }

    public String getCommandPermissions(String commandName) {
        return commandPermissions.get(commandName).getPermission();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        // Check If Language Section is not empty or null
        if (ProfileCore.getConfigData().getInstance().getString("Language") == null) {
            Logger.log(Logger.LogLevel.ERROR, "An error occurred while fetch Language into Language section in config.yml. Please check your Language section in config.yml.");
            sender.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translate("&cAn internal error occurred while attempting to perform this command.")));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translate("&cYou must be a players to use this command!")));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(ColorCodeTranslate.chat(""));
            player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&#00D4FF&lPlayerProfile Plugin")));
            player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&3To see all command in-game use /playerprofile help.")));
            player.sendMessage(ColorCodeTranslate.chat(ProfileCore.getTranslatorAPI().translate("&3To see all command in-discord use /help.")));
            player.sendMessage(ColorCodeTranslate.chat(""));
            return true;
        }

        String subCommand = args[0].toLowerCase();
        if (commands.containsKey(subCommand)) {
            Method method = commands.get(subCommand);
            Object commandInstance = commandInstances.get(subCommand);

            String requiredPermission = getCommandPermissions(subCommand);
            if (!requiredPermission.isEmpty() && !player.hasPermission(requiredPermission) || !player.isOp()) {
                player.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.PermissionMessage").replace("%playerprofile_ingame_permission%", requiredPermission)));
                return true;
            }

            try {
                method.invoke(commandInstance, player, args);
            } catch (Exception exception) {
                Log4JLogger.printStackTrace(exception);
            }
        } else {
            sender.sendMessage(ColorCodeTranslate.chat(NullableChecker.getInstance().getPrefix() + ProfileCore.getTranslatorAPI().translateConfig("InGameMessage.InvalidArgMessage")));
        }
        return true;
    }
}
