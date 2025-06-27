package net.azurewebsites.fakerestapi.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;

import static java.util.stream.Collector.of;
import static net.azurewebsites.fakerestapi.utils.RandomUtils.RANDOM;

@UtilityClass
public class StreamUtils {

    public static <T> Collector<T, List<T>, T> randomItem() {
        return of(
                ArrayList::new,
                List::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                list -> list.isEmpty() ? null : list.get(RANDOM.nextInt(list.size())));
    }

    public static Collector<Integer, ?, Integer> collectNonExistingInt() {
        return Collector.of(
                HashSet::new,
                Set::add,
                (left, right) -> {
                    left.addAll(right);
                    return left;
                },
                existingIds -> {
                    int candidate;
                    do {
                        candidate = RANDOM.nextInt(1, Integer.MAX_VALUE);
                    } while (existingIds.contains(candidate));
                    return candidate;
                }
        );
    }
}
