package io.deeplay.partition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тесты 4 задачи")
class PartitionSolutionTest {
    private final PartitionSolution partitionSolution = new PartitionSolution();
    private final static int stressTests = 10000;
    private final static Random generator = new Random();

    @ParameterizedTest
    @MethodSource("provideBasicTestCases")
    @DisplayName("Тест на случаи из задания")
    void testBasicTestCases(List<Integer> numbers, Integer l, Integer k) {
        var answer = partitionSolution.solve(numbers, k);
        assertAnswers(answer, l, k);
    }

    @ParameterizedTest
    @MethodSource("provideStressTestCases")
    @DisplayName("Тяжелый тест на сгенерированных данных")
    void stressTest(List<Integer> numbers, Integer l, Integer k) {
        var answer = partitionSolution.solve(numbers, k);
        assertAnswers(answer, l, k);
    }

    private static Stream<Arguments> provideBasicTestCases() {
        var firstParam = List.of(10, 11, 7, 7, 12);
        var secondParam = List.of(5, 2, 6, 4, 3, 6);
        var thirdParam = List.of(7, 8, 12, 1);

        return Stream.of(
                Arguments.of(firstParam, 23, 2),
                Arguments.of(secondParam, 5, 4),
                Arguments.of(thirdParam, -1, 3)
        );
    }

    private static Stream<Arguments> provideStressTestCases() {
        List<Arguments> arguments = new ArrayList<>();
        for (int i = 0; i < stressTests; i++){
            var multiset = new ArrayList<Integer>();
            int l = generator.nextInt(10, 100);
            int k = generator.nextInt(10, 100);
            for (int j = l; j < l+k; j++){
                var sumSublist = getRandomSumSublist(j);
                multiset.addAll(sumSublist);
            }
            arguments.add(Arguments.of(multiset, l, k));
        }
        return arguments.stream();
    }

    public static List<Integer> getRandomSumSublist(int number) {
        List<Integer> subList = new ArrayList<>();
        int r = number;
        while (r > generator.nextInt(1, number)){
            var element = generator.nextInt(1,r);
            element %= r;
            subList.add(element);
            r -=element;
        }
        subList.add(r);
        return subList;
    }

    private void assertAnswers(List<List<Integer>> answer, Integer l, Integer k) {
        if (l == -1){
            Assertions.assertEquals(0, answer.size());
            return;
        }
        answer = new ArrayList<>(answer);
        answer.sort(Comparator.comparingInt(it -> it.stream().reduce(Integer::sum).get()));
        var sums = answer.stream().map(it -> it.stream().reduce(Integer::sum).get()).toList();
        assertThat(sums).hasSameElementsAs(IntStream.range(l, l+k).boxed().toList());
    }
}