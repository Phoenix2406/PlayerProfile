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

package org.phoenix.playerprofile.commands.discord;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
import org.phoenix.playerprofile.DiscordCore;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.annotation.JDACommand;
import org.phoenix.playerprofile.annotation.JDAOption;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SlashCommandHandler {
    private final Map<String, Method> mainCommands;
    private final Map<String, String> commandDescriptions;
    private final Map<String, String> commandMemberPerms;
    private final Map<String, String> commandAdminPerms;
    private final Map<String, Boolean> commandOnlyAdmin;
    private final Map<String, List<OptionData>> commandOptions;
    private final List<CommandData> registeredCommands = new ArrayList<>();
    private final Set<String> registeredCommandNames = new HashSet<>();
    private final Map<String, Object> commandInstances;

    public SlashCommandHandler() {
        this.mainCommands = new HashMap<>();
        this.commandDescriptions = new HashMap<>();
        this.commandMemberPerms = new HashMap<>();
        this.commandAdminPerms = new HashMap<>();
        this.commandOnlyAdmin = new HashMap<>();
        this.commandOptions = new HashMap<>();
        this.commandInstances = new HashMap<>();
    }

    public void registerCommands(Object commandObject) {
        Class<?> clazz = commandObject.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(JDACommand.class)) {
                JDACommand annotation = method.getAnnotation(JDACommand.class);
                String commandName = annotation.name();
                mainCommands.put(commandName, method);
                commandDescriptions.put(commandName, annotation.description());
                commandAdminPerms.put(commandName, annotation.adminPerms());
                commandMemberPerms.put(commandName, annotation.memberPerms());
                commandOnlyAdmin.put(commandName, annotation.onlyAdmin());
                commandInstances.put(commandName, commandObject);

                List<OptionData> options = new ArrayList<>();
                for (JDAOption jdaOption : annotation.options()) {
                    options.add(new OptionData(jdaOption.type(), jdaOption.name(), jdaOption.description())
                            .setRequired(jdaOption.isRequired()));
                }
                commandOptions.put(commandName, options);
            }
        }

        for (String commandName : mainCommands.keySet()) {
            if (!registeredCommandNames.contains(commandName)) {
                String description = commandDescriptions.getOrDefault(commandName, "");
                registeredCommandNames.add(commandName);
                List<OptionData> options = commandOptions.get(commandName);
                if (!options.isEmpty()) {
                    registeredCommands.add(Commands.slash(commandName, description).addOptions(commandOptions.get(commandName)));
                    return;
                }
                registeredCommands.add(Commands.slash(commandName, description));
            }
        }
    }

    public void handleCommand(SlashCommandInteractionEvent event) {
        String commandName = event.getName();

        if (mainCommands.containsKey(commandName)) {
            Method method = mainCommands.get(commandName);
            if (method != null) {
                String memberRoleString = commandMemberPerms.get(commandName);
                String adminRoleString = commandAdminPerms.get(commandName);
                boolean onlyAdmin = commandOnlyAdmin.get(commandName);
                Role memberRole = DiscordCore.getDiscordCore().getJDA().getRoleById(memberRoleString.isEmpty() ? "0000000000000000000" : ProfileCore.getDiscordConfig().getInstance().getString(memberRoleString));
                Role adminRole = DiscordCore.getDiscordCore().getJDA().getRoleById(adminRoleString.isEmpty() ? "0000000000000000000" : ProfileCore.getDiscordConfig().getInstance().getString(adminRoleString));

                if (event.getMember() != null) {
                    if (onlyAdmin) {
                        if (event.getMember().hasPermission(Permission.ADMINISTRATOR) ||
                                adminRole == null || event.getMember().getRoles().contains(adminRole)) {
                            try {
                                List<OptionData> options = commandOptions.get(commandName);
                                if (!options.isEmpty()) {
                                    Map<String, Object> parsedOptions = parseOptions(event, options);
                                    Object commandInstance = commandInstances.get(commandName);
                                    method.invoke(commandInstance, event, parsedOptions);
                                } else {
                                    Object commandInstance = commandInstances.get(commandName);
                                    method.invoke(commandInstance, event);
                                }
                            } catch (IllegalAccessException | InvocationTargetException exception) {
                                Log4JLogger.printStackTrace(exception);
                            }
                        } else {
                            event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.PermissionMessage")
                                    .replace("%playerprofile_discord_name%", event.getUser().getAsMention())
                                    .replace("%playerprofile_discord_permission%", "Administrator"))
                                    .setEphemeral(true).queue();
                        }
                    } else {
                        if (event.getMember().hasPermission(Permission.ADMINISTRATOR) ||
                                memberRole == null || event.getMember().getRoles().contains(memberRole)) {
                            try {
                                List<OptionData> options = commandOptions.get(commandName);
                                if (!options.isEmpty()) {
                                    Map<String, Object> parsedOptions = parseOptions(event, options);
                                    Object commandInstance = commandInstances.get(commandName);
                                    method.invoke(commandInstance, event, parsedOptions);
                                } else {
                                    Object commandInstance = commandInstances.get(commandName);
                                    method.invoke(commandInstance, event);
                                }
                            } catch (IllegalAccessException | InvocationTargetException exception) {
                                Log4JLogger.printStackTrace(exception);
                            }
                        } else {
                            event.reply(ProfileCore.getTranslatorAPI().translateConfig("DiscordMessage.PermissionMessage")
                                            .replace("%playerprofile_discord_name%", event.getUser().getAsMention())
                                            .replace("%playerprofile_discord_permission%", memberRole.getAsMention()))
                                    .setEphemeral(true)
                                    .queue();
                        }
                    }
                }
            }
        }
    }

    public String getCommandDescription(String commandName) {
        return commandDescriptions.getOrDefault(commandName, "");
    }

    public List<CommandData> getRegisteredCommands() {
        return registeredCommands;
    }

    public Set<String> getRegisteredCommandNames() {
        return registeredCommandNames;
    }

    @SuppressWarnings("ConstantConditions")
    private Map<String, Object> parseOptions(SlashCommandInteractionEvent event, List<OptionData> options) {
        Map<String, Object> parsedOptions = new HashMap<>();
        for (OptionData optionData : options) {
            String name = optionData.getName();
            OptionType type = optionData.getType();

            switch (type) {
                case STRING -> parsedOptions.put(name, event.getOption(name).getAsString());
                case INTEGER -> parsedOptions.put(name, event.getOption(name).getAsLong());
                case BOOLEAN -> parsedOptions.put(name, event.getOption(name).getAsBoolean());
            }
        }
        return parsedOptions;
    }

    public static class SlashCommandListener extends ListenerAdapter {

        @Override
        public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
            DiscordCore.getDiscordCore().getCommandHandler().handleCommand(event);
        }
    }
}
