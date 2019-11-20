package com.example.dmucatch_store.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public static boolean isNull(String str) {
        return (str == null || (str.trim().length()) == 0 || str.equals(""));
    } // null 이면 return true

    public static boolean isNoFoundUser(String str) {
        return (str.equalsIgnoreCase("No Such User Found") || str.equalsIgnoreCase("No Such User Foun"));
    } // 유저가 없는 경우

    public static boolean isNoFoundMenu(String str) {
        return (str.equalsIgnoreCase("No Such Menu Found") || str.equalsIgnoreCase("No Such Menu Foun"));
    } // 메뉴가 없는 경우

    public static boolean isFailed(String str) {
        return str.equalsIgnoreCase("fail");
    }

    public static boolean isEmail(String str) {

        String ptn = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        // email 정규식

        return Pattern.matches(ptn, str);
    }

    public static boolean isValidPwd(String str) {

        String ptn = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,}$";
        // 대소문자 구분 숫자 특수문자  조합 9 ~ 12 자리

        return Pattern.matches(ptn, str);
    }

    public static boolean isPhNumber(String str){
        String ptn = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
        // 01(0,1,6-9) - 3자리 || 4자리 - 4자리

        return Pattern.matches(ptn, str);
    }
}
