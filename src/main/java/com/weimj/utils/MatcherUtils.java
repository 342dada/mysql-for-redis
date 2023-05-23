package com.weimj.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author:Weimj
 * @Date: 2023/5/21  21:29
 */
public class MatcherUtils {
    private static final String PATTERN_STR="\\{([^{}]+)\\}";

    /**
     * 根据模板提取redis key
     * @param template
     * @param values
     * @return
     */
    public static String  extractVariables(String template, Map<String,String> values) {
        Pattern pattern = Pattern.compile(PATTERN_STR);
        Matcher matcher = pattern.matcher(template);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String variable = matcher.group(1);
            if(values.get(variable)!=null){
                String value = String.valueOf(values.get(variable));
                matcher.appendReplacement(buffer, value != null ? value : "");
            }

        }

        return buffer.toString();
    }
}
