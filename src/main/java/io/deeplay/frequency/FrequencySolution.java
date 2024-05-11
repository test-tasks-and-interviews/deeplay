package io.deeplay.frequency;

import lombok.extern.java.Log;

import java.util.*;
import java.util.logging.Level;

@Log
public class FrequencySolution {
    private final Map<Integer, Integer> frequencyMap = new HashMap<>();
    public List<Integer> getMostFrequent(Integer[] nums) {
       log.log(Level.INFO, "Received array:{0}", nums);
       for (Integer num : nums){
           frequencyMap.merge(num, 1, Integer::sum);
       }
       List<Integer> answer = new ArrayList<>();
       var maxFrequency = frequencyMap.values().stream().max(Comparator.naturalOrder()).get();
       frequencyMap.entrySet().stream().filter(it -> Objects.equals(it.getValue(), maxFrequency))
               .forEach(num -> answer.add(num.getKey()));
        log.log(Level.INFO ,"Most frequent numbers: {0}", answer);
        return answer;
    }
}
