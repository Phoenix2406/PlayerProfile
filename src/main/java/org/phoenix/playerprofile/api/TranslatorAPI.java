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

package org.phoenix.playerprofile.api;

import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import org.bukkit.configuration.file.FileConfiguration;
import org.phoenix.playerprofile.ProfileCore;
import org.phoenix.playerprofile.database.LanguageDB;
import org.phoenix.playerprofile.manager.EmoteManager;
import org.phoenix.playerprofile.utils.ColorChecker;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TranslatorAPI extends LanguageDB {

    private final Map<String, Map<String, String>> translations;
    private final Map<String, FileConfiguration> configTranslations;

    public TranslatorAPI() {
        translations = new HashMap<>();
        configTranslations = new HashMap<>();

        translations.put("en_US", loadENTranslation());
        translations.put("ind_ID", loadIDTranslation());

        configTranslations.put("en_US", loadConfigTranslation("en_US"));
        configTranslations.put("ind_ID", loadConfigTranslation("ind_ID"));
    }

    public String translate(String text) {
        return Optional.of(translations.get(ProfileCore.getConfigData().getInstance().getString("Language")))
                .map(translationMap -> translationMap.get(text))
                .orElseGet(() -> {
                    if (new ColorChecker(ColorChecker.RegexType.COLOR_CODE, text).check() ||
                            new ColorChecker(ColorChecker.RegexType.HEX_CODE, text).check() ||
                            new ColorChecker(ColorChecker.RegexType.GRADIENT_CODE, text).check()) {
                        return ProfileCore.getConfigData().getInstance().getString("Prefix") + "&cTranslation not available for the specified language.";
                    } else {
                        return "An error has occurred while trying to sending message! Translation not available for the specified language.";
                    }
                });
    }

    public String translateConfig(String path) {
        String language = ProfileCore.getConfigData().getInstance().getString("Language");
        return Optional.of(configTranslations.get(language))
                .filter(lang -> lang.contains(language + "." + path) && lang.isString(language + "." + path))
                .map(translation -> translation.getString(language + "." + path))
                .orElseGet(() -> {
                    String[] pathSplit = path.split("\\.");
                    if (pathSplit[0].equals("InGameMessage")) {
                        return ProfileCore.getConfigData().getInstance().getString("Prefix") + "&cTranslation not available for the specified language.";
                    } else if (pathSplit[0].equals("DiscordMessage")) {
                        return "An error has occurred while trying to sending message! Translation not available for the specified language.";
                    } else {
                        throw new IllegalArgumentException("Unknown translation or identification language system!");
                    }
                });
    }

    public String translate(EmoteManager emoteManager, String text) {
        return Optional.of(translations.get(ProfileCore.getConfigData().getInstance().getString("Language")))
                .map(translationMap -> emoteManager.getEmoji() + translationMap.get(text))
                .orElse("An error has occurred while trying to sending message! Translation not available for the specified language.");
    }

    public void translateErrorMessage(SlashCommandInteraction slashCommandInteraction) {
        if (ProfileCore.getConfigData().getInstance().getString("Language").equals("en_US")) {
            slashCommandInteraction.reply("""
                        An error occurred while trying to send embed!

                        If you are **Admin**, you can see console for more information.
                        If you are **Member**, you can ask Admin for this issue.""").setEphemeral(true).queue();
        } else if (ProfileCore.getConfigData().getInstance().getString("Language").equals("ind_ID")) {
            slashCommandInteraction.reply("""
                        Terjadi kesalahan saat mencoba mengirim embed!

                        Jika Anda **Admin**, Anda dapat melihat konsol untuk informasi lebih lanjut.
                        Jika Anda **Anggota**, Anda dapat menanyakan masalah ini kepada Admin.""").setEphemeral(true).queue();
        }
    }
}
