package com.ippse.web.utils;

import java.util.UUID;

public class KeyUtil {
    public static String UUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
