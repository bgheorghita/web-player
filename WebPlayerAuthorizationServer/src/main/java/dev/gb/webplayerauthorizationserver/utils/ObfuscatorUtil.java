package dev.gb.webplayerauthorizationserver.utils;

public class ObfuscatorUtil {
    public final static String obfuscator = "*****";

    private ObfuscatorUtil(){}

    public static String obfuscateEmailAddress(String emailAddress) {
        if (!emailAddress.contains("@")) {
            return "";
        }

        if(getEmailAddressLength(emailAddress) < 5){
            return obfuscator + emailAddress.substring(emailAddress.indexOf('@'));
        }

        return emailAddress.substring(0, 2) + obfuscator + emailAddress.substring(emailAddress.indexOf('@') - 1);
    }

    private static int getEmailAddressLength(String emailAddress){
        return emailAddress.substring(0, emailAddress.indexOf('@')).length();
    }
}
