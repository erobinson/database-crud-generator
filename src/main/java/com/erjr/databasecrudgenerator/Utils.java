package com.erjr.databasecrudgenerator;

import com.google.common.base.CaseFormat;

public class Utils {

    public static String camelCaseString(String string) {
        if(string == null) {
            return null;
        }
        string = string.toLowerCase().replaceAll("-", "_").replaceAll(" ", "_");
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, string);
    }
}
