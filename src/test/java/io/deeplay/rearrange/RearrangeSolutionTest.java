package io.deeplay.rearrange;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@DisplayName("Тесты первой задачи")
class RearrangeSolutionTest {
    private final RearrangeSolution solution = new RearrangeSolution();
    private static final Integer generateAmount = 1000;

    @ParameterizedTest
    @MethodSource("lengthStream")
    @DisplayName("Тесты на сгенерированных данных")
    void testGeneratedValues(Integer length) {
        var answer = solution.solve(length);
        Assertions.assertTrue(isRearrangedCorrectly(answer));
    }

    private boolean isRearrangedCorrectly(Integer[] answer) {
        int i = 0;
        int prev = Integer.MIN_VALUE;
        while (i < answer.length && answer[i] % 2 != 0){
            if (answer[i] < prev){
                return false;
            }
            prev = answer[i];
            i++;
        }
        if (i == answer.length)
            return true;

        while (i < answer.length && answer[i] == 0){
            i++;
        }
        if (i == answer.length)
            return true;

        prev = Integer.MAX_VALUE;
        while (i < answer.length && answer[i] %2 == 0 && answer[i] != 0){
            if (answer[i] > prev){
                return false;
            }
            prev = answer[i];
            i++;
        }
        return i == answer.length;
    }

    private static Stream<Arguments> lengthStream(){
        return IntStream.range(1,generateAmount+1).boxed().map(Arguments::of);
    }
}