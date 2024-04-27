package com.tibame201020.backend.util;

import java.util.regex.Pattern;

/**
 * regex wrapper
 */
public class PatternValidUtils {

    private static final String LEGAL_WORD_PATTERN = "^[0-9a-zA-Z]+$";
    private static final String EMAIL_PATTERN = "^(.+)@(\\S+)$";

    public static boolean isEmailLegal(String email) {
        return Pattern.compile(EMAIL_PATTERN)
                .matcher(email)
                .matches();
    }

    public static boolean isInputLegal(String input) {
        return Pattern.compile(LEGAL_WORD_PATTERN)
                .matcher(input)
                .matches();
    }
}
