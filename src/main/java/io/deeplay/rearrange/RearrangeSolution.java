package io.deeplay.rearrange;

import lombok.extern.java.Log;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.logging.Level;

@Log
public class RearrangeSolution {
    private final Random generator = new Random();

    public Integer[] solve(Integer length) {
        Integer[] nums = new Integer[length];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = generator.nextInt();
        }
        log.log(
                Level.INFO,
                "Generated array with length {0}: {1}",
                new Object[]{length, Arrays.toString(nums)}
        );
        swapOddFirst(nums);
        sortOdd(nums);
        swapZerosAndEven(nums);
        sortEven(nums);
        log.log(Level.INFO, "Rearranged array: {0}", Arrays.toString(nums));
        return nums;
    }

    private static void swapZerosAndEven(Integer[] nums) {
        int even;
        int zero = 0;
        while (zero < nums.length && nums[zero] % 2 != 0) {
            zero++;
        }
        even = nums.length - 1;
        while (zero < even) {
            while (zero < even && nums[zero] == 0) {
                zero++;
            }
            while (even > zero && nums[even] != 0) {
                even--;
            }
            swap(nums, even, zero);
            zero++;
            even--;
        }
    }

    private static void sortOdd(Integer[] nums) {
        int zero = 0;
        while (zero < nums.length && nums[zero] % 2 != 0) {
            zero++;
        }
        Arrays.sort(nums, 0, zero, Comparator.naturalOrder());
    }

    private static void sortEven(Integer[] nums) {
        int even;
        even = nums.length - 1;
        while (even > -1 && nums[even] != 0 && nums[even] % 2 == 0) {
            even--;
        }
        Arrays.sort(nums, Math.max(even + 1, 0), nums.length, Comparator.reverseOrder());
    }

    private static void swapOddFirst(Integer[] nums) {
        Integer even = 0;
        Integer odd = nums.length - 1;
        while (even < odd) {
            while (even < odd && nums[even] % 2 != 0) {
                even++;
            }
            while (odd > even && nums[odd] % 2 == 0) {
                odd--;
            }
            swap(nums, even, odd);
            even++;
            odd--;
        }
    }

    private static void swap(Integer[] nums, Integer i, Integer j) {
        Integer temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
