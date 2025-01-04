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

package org.phoenix.playerprofile.utils;

import net.md_5.bungee.api.ChatColor;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorCodeTranslate {

    private static final List<String> SPECIAL_COLORS;

    public static String chat(String message) {
        // This will register for color & hex color chat
        Pattern colors = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = colors.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, ChatColor.of(color.replace("&", "")) + "");
            matcher = colors.matcher(message);
        }

        // This will register for gradient color chat
        Pattern gradient = Pattern.compile("<GRADIENT:([0-9A-Fa-f]{6})>(.*?)</GRADIENT:([0-9A-Fa-f]{6})>");
        String start;
        String end;
        String content;
        for (matcher = gradient.matcher(message); matcher.find(); message = message.replace(matcher.group(), gradient(content, new Color(Integer.parseInt(start, 16)), new Color(Integer.parseInt(end, 16))))) {
            start = matcher.group(1);
            end = matcher.group(3);
            content = matcher.group(2);
        }

        // This will register for unicode chat
        Pattern unicode = Pattern.compile("\\\\u + [a-fA-F0-9]{4}");
        matcher = unicode.matcher(message);
        while (matcher.find()) {
            String code = message.substring(matcher.start(), matcher.end());
            message = message.replace(code, Character.toString((char) Integer.parseInt(code.replace("\\u+", ""), 16)));
            matcher = unicode.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @Nonnull
    private static String gradient(@Nonnull String string, @Nonnull java.awt.Color start, @Nonnull java.awt.Color end) {
        StringBuilder specialColors = new StringBuilder();

        for (String color : SPECIAL_COLORS) {
            if (string.contains(color)) {
                specialColors.append(color);
                string = string.replace(color, "");
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        ChatColor[] colors = createGradient(start, end, string.length());
        String[] characters = string.split("");

        for (int i = 0; i < string.length(); i++) {
            stringBuilder.append(colors[i]).append(specialColors).append(characters[i]);
        }

        return stringBuilder.toString();
    }

    @Nonnull
    private static ChatColor[] createGradient(@Nonnull java.awt.Color start, @Nonnull java.awt.Color end, int step) {
        ChatColor[] colors = new ChatColor[step];
        int stepR = Math.abs(start.getRed() - end.getRed()) / (step - 1);
        int stepG = Math.abs(start.getGreen() - end.getGreen()) / (step - 1);
        int stepB = Math.abs(start.getBlue() - end.getBlue()) / (step - 1);
        int[] direction = new int[]{start.getRed() < end.getRed() ? 1 : -1, start.getGreen() < end.getGreen() ? 1 : -1, start.getBlue() < end.getBlue() ? 1 : -1};

        for(int i = 0; i < step; ++i) {
            java.awt.Color color = new java.awt.Color(start.getRed() + stepR * i * direction[0], start.getGreen() + stepG * i * direction[1], start.getBlue() + stepB * i * direction[2]);
            colors[i] = ChatColor.of(color);
        }
        return colors;
    }

    static {
        SPECIAL_COLORS = Arrays.asList("&l", "&n", "&o", "&k", "&m");
    }
}
