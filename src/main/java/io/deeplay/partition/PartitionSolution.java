package io.deeplay.partition;

import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

@Log
public class PartitionSolution {
    List<List<Integer>> solve(List<Integer> numbers, Integer k) {
        log.log(Level.INFO, "Got array {0}, k={1}", new Object[]{numbers, k});
        List<Integer> list = new ArrayList<>(numbers);
        list.sort(Comparator.reverseOrder());
        if (k <= 0 || k > list.size()) {
            log.log(Level.SEVERE, "Impossible to make a partition, invalid k={0}", k);
            return List.of();
        }

        long totalSum = list.stream().reduce(Integer::sum).get();
        if (2 * totalSum % k != 0 || ((2 * totalSum / k) - (k - 1)) % 2 != 0) {
            log.log(Level.SEVERE, "Impossible to make a partition");
            return List.of();
        }

        int l = (int) (((2 * totalSum / k) - (k - 1)) / 2);
        List<List<Integer>> answer = new ArrayList<>(k);
        for (int i = l; i < l + k; i++) {
            var current = findSublist(list, i);
            if (!current.isEmpty()) {
                current.sort(Comparator.reverseOrder());
                list = removeSublist(list, current);
                answer.add(current);
            } else {
                log.log(Level.SEVERE, "Impossible to find sublist for sum:{0}", i);
                return List.of();
            }
        }
        if (answer.size() == k) {
            answer.sort(Comparator.comparingInt(sublist -> sublist.stream().reduce(Integer::sum).get()));
            for (int i = l; i < l + k; i++) {
                int sublistSum = answer.get(i - l).stream().reduce(Integer::sum).get();
                if (sublistSum != i) {
                    log.log(
                            Level.SEVERE,
                            "We saw, we came, we failed. " +
                                    "Wrong sublist sum, expected:{0}, got:{1}",
                            new Object[]{i, sublistSum}
                    );
                    return List.of();
                }
            }
            log.log(Level.INFO, "Answer: {0}", answer);
            return answer;
        }
        log.log(
                Level.SEVERE,
                "Impossible to make a partition, " +
                        "wrong amount of sublists - {0}, {1} expected",
                new Object[]{answer.size(), k}
        );
        return List.of();
    }

    private List<Integer> removeSublist(List<Integer> list, List<Integer> subList) {
        list = new ArrayList<>(list);
        if (list.size() < subList.size()) {
            return list;
        }
        int first = 0;
        for (Integer integer : subList) {
            while (!Objects.equals(list.get(first), integer)) {
                first++;
            }
            list.remove(first);
        }
        return list;
    }

    public static List<Integer> findSublist(List<Integer> multiset, int expectedSum) {
        int n = multiset.size();
        boolean[][] dp = new boolean[n + 1][expectedSum + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= expectedSum; j++) {
                dp[i][j] = dp[i - 1][j];
                if (multiset.get(i - 1) <= j) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j - multiset.get(i - 1)];
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        int i = n;
        int j = expectedSum;
        while (i > 0 && j >= 0) {
            if (!dp[i - 1][j]) {
                result.add(multiset.get(i - 1));
                j -= multiset.get(i - 1);
            }
            i--;
        }
        result.sort(Comparator.reverseOrder());
        return result;
    }


}
