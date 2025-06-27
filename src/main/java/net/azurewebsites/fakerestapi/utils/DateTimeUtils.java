package net.azurewebsites.fakerestapi.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ofPattern;

@UtilityClass
public class DateTimeUtils {

    public static DateTimeFormatter ISO_WITH_MILLIS = ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

    public static String offsetDateTimeToString(OffsetDateTime date, DateTimeFormatter dtf) {
        return dtf.format(date);
    }

    public static OffsetDateTime getRandomPastDate() {
        var startEpochDay = LocalDate.of(1, 1, 1).toEpochDay();
        var endEpochDay = LocalDate.now(UTC)
                .minusDays(1)
                .toEpochDay();
        var current = ThreadLocalRandom.current();
        var randomDay = current.nextLong(startEpochDay, endEpochDay + 1);
        var randomDate = LocalDate.ofEpochDay(randomDay);

        var hour = current.nextInt(0, 24);
        var minute = current.nextInt(0, 60);
        var second = current.nextInt(0, 60);
        var nano = current.nextInt(0, 1_000_000_000);
        var randomTime = LocalTime.of(hour, minute, second, nano);

        return OffsetDateTime.of(randomDate, randomTime, UTC);
    }
}
