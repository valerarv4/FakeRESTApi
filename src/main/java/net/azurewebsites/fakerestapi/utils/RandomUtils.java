package net.azurewebsites.fakerestapi.utils;

import lombok.experimental.UtilityClass;
import net.datafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import static java.lang.Integer.MAX_VALUE;


@UtilityClass
public class RandomUtils {

    public static final Faker FAKER = new Faker();
    public final static Random RANDOM;

    static {
        try {
            RANDOM = new SecureRandom().getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRandomAlphabeticString(int max) {
        return RandomStringUtils
                .insecure()
                .nextAlphabetic(getRandomIntInRange(1, max));
    }

    public static int getRandomPositiveInt() {
        return RANDOM.nextInt(0, MAX_VALUE);
    }

    public static int getRandomIntInRange(int min, int max) {
        return RANDOM.nextInt(max - min + 1) + min;
    }
}
