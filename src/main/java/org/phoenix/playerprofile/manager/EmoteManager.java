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

package org.phoenix.playerprofile.manager;

import net.dv8tion.jda.api.entities.Emote;
import org.phoenix.playerprofile.DiscordCore;
import org.phoenix.playerprofile.ProfileCore;

import java.util.Optional;

public enum EmoteManager {

    NAME_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.Name")),
    RANK_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.Rank")),
    LAST_SEEN_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.LastSeen")),
    FIRST_JOIN_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.FirstJoin")),
    TOTAL_LEVELS_EXP_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalLevelsExp")),
    TOTAL_PLAYTIME_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalPlaytime")),
    TOTAL_MOBS_KILL_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalMobsKill")),
    CLASS_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.Class")),
    MMO_LEVELS_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.MMOLevels")),
    TOTAL_HEALTH_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalHealth")),
    TOTAL_HEALTH_REGEN_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalHealthRegen")),
    TOTAL_MANA_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalMana")),
    TOTAL_MANA_REGEN_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalManaRegen")),
    TOTAL_STAMINA_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalStamina")),
    TOTAL_STAMINA_REGEN_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalStaminaRegen")),
    TOTAL_STELLIUM_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalStellium")),
    TOTAL_STELLIUM_REGEN_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ProfileCMD.TotalStelliumRegen")),
    RELOAD_TIMEOUT_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ReloadCMD.ReloadTimeout")),
    RELOAD_ASK_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ReloadCMD.ReloadAsk")),
    RELOAD_BTN_NO_CLICK_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ReloadCMD.ReloadBtnNo")),
    RELOAD_PROCESS_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ReloadCMD.ReloadProcess")),
    RELOAD_SUCCESS_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ReloadCMD.ReloadSuccess")),
    RELOAD_UNSUCCESS_EMOTE(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.ReloadCMD.ReloadUnsuccess")),
    VERSION_BUTTON(ProfileCore.getEmoteConfig().getInstance().getLong("EmojiId.VersionCMD.VersionChecker"));

    private final long emojiId;

    EmoteManager(long emojiId) {
        this.emojiId = emojiId;
    }

    public String getEmoji() {
        boolean isEnable = ProfileCore.getEmoteConfig().getInstance().getBoolean("Enable");
        return Optional.ofNullable(DiscordCore.getDiscordCore().getJDA().getEmoteById(emojiId))
                .filter(emote -> isEnable && emote.isAvailable())
                .map(Emote::getAsMention)
                .orElse("**•)**");
    }

    public long getEmojiId() {
        return emojiId;
    }
}
