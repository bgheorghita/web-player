package dev.gb.webplayerauthorizationserver.utils;

import java.security.SecureRandom;

public class OtpGeneratorUtil {
    public static final String NUMERIC_CHARACTERS = "0123456789";
    private OtpGeneratorUtil(){}


    public static String getCode(int length){
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(NUMERIC_CHARACTERS.length());
            char randomDigit = NUMERIC_CHARACTERS.charAt(randomIndex);
            sb.append(randomDigit);
        }
        return sb.toString();
    }
}
