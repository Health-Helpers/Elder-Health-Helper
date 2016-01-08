package com.hh.ehh.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mpifa on 8/1/16.
 */
public class Validators {
    public static Boolean dniValidator(String dni) {
        String dniPattern = "([0-9]{8})([A-Z])";
        Pattern p = Pattern.compile(dniPattern);
        Matcher matcher = p.matcher(dni);
        return matcher.matches();
    }
}
