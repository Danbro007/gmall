package com.danbro.gmall.common.utils.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Danrbo
 * @date 2019/11/14 15:57
 * description
 **/
public class StringTransferUtil {

    private static Pattern humpPattern;

    static {
        humpPattern = Pattern.compile("[A-Z]");
    }

    public static String camelCaseToUnderLine(String camelCase){

        Matcher matcher = humpPattern.matcher(camelCase);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
