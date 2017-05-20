package com.github.saburto.assignment.comparator;

import static java.util.Collections.emptyMap;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.saburto.assignment.data.Data;

@Component
public class BinaryFileComparator {

    private static final int RESET_INDEX = 0;

    public Result compare(Data left, Data rigth) {
        boolean sameSize = left.isSameSize(rigth);
        
        Map<Integer, Integer> diffs = sameSize ? getDiffs(left, rigth) : emptyMap() ;
        
        return new Result(sameSize, diffs);
    }

    private Map<Integer, Integer> getDiffs(Data left, Data rigth) {
        int lastKey = RESET_INDEX;
        Map<Integer, Integer> diffs = new HashMap<>();
        
        for (int i = 0; i < left.size(); i++) {
            if (left.hasSameByte(i, rigth)) {
                lastKey = RESET_INDEX;
            } else {
                lastKey = updateLastKey(lastKey, i);
                updateCountLengthDiff(diffs, lastKey);
            }
        }
        
        return diffs;
    }

    private void updateCountLengthDiff(Map<Integer, Integer> diffs, int lastKey) {
        diffs.merge(lastKey, 1, (key, value) -> value + 1);
    }

    private int updateLastKey(int lastKey, int i) {
        return lastKey == RESET_INDEX ? i : lastKey;
    }
}
