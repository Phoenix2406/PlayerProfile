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

package org.phoenix.playerprofile.utils.HeadDatabase;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.phoenix.playerprofile.objects.exception.Log4JLogger;
import org.phoenix.playerprofile.utils.HeadDatabase.Enum.HeadType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public record HeadManager(HeadType type, String value) {

    public ItemStack convert() {
        if (type.equals(HeadType.PLAYER_HEAD)) {
            return getSkullByTexture(getPlayerHeadTexture(value));
        } else {
            return getSkullByTexture(value);
        }
    }

    public HeadType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    private ItemStack getSkullByTexture(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if (url.isEmpty() || url.equals("none")) return head;

        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            assert skullMeta != null;
            Method method = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
            method.setAccessible(true);
            method.invoke(skullMeta, profile);
        } catch (Exception exception) {
            Log4JLogger.printStackTrace(exception);
        }

        head.setItemMeta(skullMeta);
        return head;
    }

    private String getPlayerHeadTexture(String username) {
        if (getPlayerId(username).equals("none")) return "none";
        String url = "https://sessionserver.mojang.com/session/minecraft/profile/" + getPlayerId(username);

        try {
            JsonParser jsonParser = new JsonParser();
            String userData = readUrl(url);
            Object parseData = jsonParser.parse(userData);

            JsonObject jsonData = (JsonObject) parseData;
            JsonArray streamArray = (JsonArray) jsonData.get("properties");
            JsonObject jsonMap = (JsonObject) streamArray.get(0);

            return jsonMap.get("value").toString();
        } catch (Exception exception) {
            Log4JLogger.printStackTrace(exception);
        }
        return "none";
    }

    private String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;

        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder buffer = new StringBuilder();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1) buffer.append(chars, 0, read);
            return buffer.toString();
        } finally {
            if (reader != null) reader.close();
        }
    }

    private String getPlayerId(String playerName) {
        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + playerName;
            InputStream inputStream = new URL(url).openStream();
            Scanner scanner = new Scanner(inputStream);
            String data = scanner.nextLine();
            JsonObject object = (JsonObject) new JsonParser().parse(data);
            if (object.getAsString().contains("id")) {
                return object.get("id").toString();
            }
        } catch (Exception ignored) {
            return "none";
        }
        return "none";
    }
}
