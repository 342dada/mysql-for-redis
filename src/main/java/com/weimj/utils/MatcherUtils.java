package com.weimj.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

/**
 * @Author:Weimj
 * @Date: 2023/5/21  21:29
 */
public class MatcherUtils {
    private static final String PATTERN_STR="\\{(.*?)\\}";

    public static String extractVariables(String template, Map<String, String> variables) {
        if(StringUtils.isBlank(template)){
            return StringUtils.EMPTY;
        }
        Pattern pattern = compile(PATTERN_STR);
        Matcher matcher = pattern.matcher(template);
        while (matcher.find()) {
            String replacement = String.valueOf(variables.get(matcher.group(1)));
            if (replacement == null) {
                replacement = "";
            }
            template = template.replace(matcher.group(), replacement);
        }
        return template;
    }

}
