package io.deeplay.frequency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class FrequencySolutionTest {
    private final FrequencySolution solution = new FrequencySolution();

    @DisplayName("Тест на 1 ответ")
    @Test
    void testUniqueAnswer() {
        var answer = solution.getMostFrequent(new Integer[]{1, 2, 3, 4, 5, 6, 7, 7});
        Assertions.assertThat(answer).hasSameElementsAs(List.of(7));
    }

    @DisplayName("Тест на 2 ответа")
    @Test
    void testTwoAnswers() {
        var answer = solution.getMostFrequent(new Integer[]{1, 2, 3, 4, 4, 5, 6, 7, 7});
        Assertions.assertThat(answer).hasSameElementsAs(List.of(4, 7));
    }

    @DisplayName("Тест на 3 ответа")
    @Test
    void testThreeAnswers() {
        var answer = solution.getMostFrequent(new Integer[]{1, 2, 3, 4, 4, 5, 5, 6, 7, 7});
        Assertions.assertThat(answer).hasSameElementsAs(List.of(4, 5, 7));
    }

    @DisplayName("У всех одна частота")
    @Test
    void testAll() {
        var answer = solution.getMostFrequent(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Assertions.assertThat(answer).hasSameElementsAs(List.of(1, 2, 3, 4, 5, 6, 7));
    }
}
