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

package org.phoenix.playerprofile;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.phoenix.playerprofile.api.TranslatorAPI;
import org.phoenix.playerprofile.commands.ingame.CommandHandler;
import org.phoenix.playerprofile.commands.ingame.subcommands.*;
import org.phoenix.playerprofile.commands.ingame.tabcommand.TabComplete;
import org.phoenix.playerprofile.event.ListenerManager;
import org.phoenix.playerprofile.dependencies.PlaceholderAPI.PlaceholderAPIExpansion;
import org.phoenix.playerprofile.dependencies.RegisterDependency;
import org.phoenix.playerprofile.api.LuckPermsAPI;
import org.phoenix.playerprofile.manager.config.ConfigData;
import org.phoenix.playerprofile.manager.config.DiscordConfig;
import org.phoenix.playerprofile.manager.config.EmoteConfig;
import org.phoenix.playerprofile.manager.config.SoundConfig;
import org.phoenix.playerprofile.manager.config.language.ENLangConfig;
import org.phoenix.playerprofile.manager.config.language.IDLangConfig;
import org.phoenix.playerprofile.notifications.VersionChecker;
import org.phoenix.playerprofile.objects.CooldownTimer;
import org.phoenix.playerprofile.utils.ColorCodeTranslate;
import org.phoenix.playerprofile.utils.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * PlayerProfile's main class, can be accessed via {@link #getProfileCore()}
 */
public class ProfileCore extends JavaPlugin {

    private static ProfileCore profileCore;
    private static TranslatorAPI translatorAPI;
    public Map<Player, Player> targetMap = new HashMap<>();
    private int ms;

    // Declare Luckperms Manager variable
    private static LuckPermsAPI luckPermsAPI;

    // Declare Config System variable
    private static ConfigData configData;
    private static DiscordConfig discordConfig;
    private static EmoteConfig emoteConfig;
    private static SoundConfig soundConfig;
    private static ENLangConfig enLangConfig;
    private static IDLangConfig idLangConfig;

    // Registering Plugin Commands
    @SuppressWarnings("ConstantConditions")
    public void registerCommands() {
        CommandHandler commandHandler = new CommandHandler();
        commandHandler.registerCommand(
                new HelpCommand(),
                new InfoCommand(),
                new ProfileCommand(this),
                new ReloadCommand(this),
                new VersionCommand(this));
        getCommand("playerprofile").setExecutor(commandHandler);
        getCommand("playerprofile").setTabCompleter(new TabComplete(commandHandler));
    }

    @Override
    public void onEnable() {
        // Time in millisecond (ms) for plugin enable
        ms = 0;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ms++;
            }
        };
        timer.scheduleAtFixedRate(task, 1, 1);

        // Registering all config system
        configData = new ConfigData(this, "config.yml", true, "ConfigVersion");
        configData.load();
        discordConfig = new DiscordConfig(this, "DiscordConfig.yml", true, "DiscordConfigVersion");
        discordConfig.load();
        emoteConfig = new EmoteConfig(this, "Utils/DiscordEmote.yml", true, "EmoteConfigVersion");
        emoteConfig.load();
        soundConfig = new SoundConfig(this, "SoundConfig.yml", true, "SoundConfigVersion");
        soundConfig.load();
        enLangConfig = new ENLangConfig(this, "Language/en_US.yml", true, "ENLangConfigVersion");
        enLangConfig.load();
        idLangConfig = new IDLangConfig(this, "Language/ind_ID.yml", true, "IDLangConfigVersion");
        idLangConfig.load();

        // Registering PlayerProfile System
        registerCommands();
        RegisterDependency.Register(this);

        // Registering Plugin Instance to Access Main Class
        profileCore = this;
        luckPermsAPI = new LuckPermsAPI(this);
        translatorAPI = new TranslatorAPI();

        CooldownTimer.setupCooldown();
        DiscordCore.getDiscordCore().CreateDiscordBot();
        new ListenerManager(this).RegisterListener();

        // Plugin message while enabled
        Logger.log(Logger.LogLevel.INFO, "Using Language: " + configData.getInstance().getString("Language"));
        Logger.log(Logger.LogLevel.INFO, ColorCodeTranslate.chat("&aEnabling PlayerProfile plugin in " + ms + "ms!"));

        VersionChecker versionChecker = new VersionChecker(this, getDescription().getVersion(), "https://gist.githubusercontent.com/ThunderBolt0624/04f92e37e062416acb9b06ed37c31ca1/raw/PlayerProfilePluginVersion.txt");
        versionChecker.runTaskTimerAsynchronously(this, 0, 20 * 60 * 60);
    }

    @Override
    public void onDisable() {
        // Time in millisecond (ms) for plugin disable
        ms = 0;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ms++;
            }
        };
        timer.scheduleAtFixedRate(task, 1, 1);

        // Unregistering Plugin Instance If Plugin is Disable
        profileCore = null;
        luckPermsAPI = null;
        translatorAPI = null;

        // Unregistering PlayerProfile System
        DiscordCore.getDiscordCore().shutdownJDA();
        new PlaceholderAPIExpansion(this).unregister();

        // Plugin message while disabled
        Logger.log(Logger.LogLevel.INFO, ColorCodeTranslate.chat("&cDisabling PlayerProfile plugin in " + ms + "ms!"));
    }

    /**
     * Creating Public Static ProfileCore
     * Instance to Access Main Class
     *
     * @see #getProfileCore()
     */
    public static ProfileCore getProfileCore() {
        return profileCore;
    }

    /**
     * Creating Public Static TranslatorAPI
     * Instance to Access TranslatorAPI
     *
     * @see #getTranslatorAPI() ()
     */
    public static TranslatorAPI getTranslatorAPI() {
        return translatorAPI;
    }

    /**
     * Creating Public Static Luckperms Manager
     * Instance to Access Luckperms Manager Class
     *
     * @see #getLuckPermsManager()
     */
    public static LuckPermsAPI getLuckPermsManager() {
        return luckPermsAPI;
    }

    /**
     * Creating Public Static Config System
     * Instance to Access Config Class
     *
     * @see #getConfigData()
     * @see #getDiscordConfig()
     * @see #getEmoteConfig()
     * @see #getSoundConfig()
     * @see #getEnLangConfig()
     * @see #getIdLangConfig()
     */
    public static ConfigData getConfigData() {
        return configData;
    }
    public static DiscordConfig getDiscordConfig() {
        return discordConfig;
    }
    public static EmoteConfig getEmoteConfig() {
        return emoteConfig;
    }
    public static SoundConfig getSoundConfig() {
        return soundConfig;
    }
    public static ENLangConfig getEnLangConfig() {
        return enLangConfig;
    }
    public static IDLangConfig getIdLangConfig() {
        return idLangConfig;
    }
}
