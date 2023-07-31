package dev.gb.webplayerauthorizationserver.utils;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ObfuscatorUtilTest {
    @Test
    void obfuscateEmailAddress_ShouldReturnEmptyString_WhenEmailAddressDoesNotContainAtSign(){
        String emailAddress = "wrongEmailAddress";

        String obfuscatedEmailAddress = ObfuscatorUtil.obfuscateEmailAddress(emailAddress);

        assertEquals("", obfuscatedEmailAddress);
    }

    @Test
    void obfuscateEmailAddress_ShouldReturnStringStartingWithAtSignAfterObfuscator_WhenItsLengthIsLessThanFive(){
        String emailAddress = "abc@email.com";

        String obfuscatedEmailAddress = ObfuscatorUtil.obfuscateEmailAddress(emailAddress);
        String expectedObfuscatedEmailAddress = ObfuscatorUtil.obfuscator + "@email.com";

        assertEquals(expectedObfuscatedEmailAddress, obfuscatedEmailAddress);
    }

    @Test
    void obfuscateEmailAddress_ShouldReturnStringStartingWithFirstTwoCharactersFromEmail_AndObfuscator_AndLastCharacterFromEmail_AndContinuingFromAtSign_WhenItsLengthIsGreaterThanFour(){
        String emailAddress = "abcde@email.com";

        String obfuscatedEmailAddress = ObfuscatorUtil.obfuscateEmailAddress(emailAddress);
        String expectedObfuscatedEmailAddress = "ab" + ObfuscatorUtil.obfuscator + "e" + "@email.com";

        assertEquals(expectedObfuscatedEmailAddress, obfuscatedEmailAddress);
    }
}