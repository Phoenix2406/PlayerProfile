package org.phoenix.playerprofile.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorChecker {

    private final RegexType regexType;
    private final String text;

    public ColorChecker(RegexType regexType, String text) {
        this.regexType = regexType;
        this.text = text;
    }

    public boolean check() {
        Pattern pattern = Pattern.compile(regexType.getRegexKey());
        Matcher matcher = pattern.matcher(text);
        return matcher.find();
    }

    public enum RegexType {
        COLOR_CODE("(&[0-9a-fA-FkKlLmMnNoOrR])"),
        HEX_CODE("#[0-9a-fA-F]{6}"),
        GRADIENT_CODE("<GRADIENT:#[0-9a-fA-F]{6}:#[0-9a-fA-F]{6}>");

        private final String regexKey;

        RegexType(String regexKey) {
            this.regexKey = regexKey;
        }

        public String getRegexKey() {
            return regexKey;
        }
    }
}
