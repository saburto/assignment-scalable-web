package com.github.saburto.assigment.comparator;

import static java.util.Collections.unmodifiableMap;

import java.util.Map;

public class Result {
    private final boolean isEqual;
    private final Map<Integer, Integer> diffs;

    public Result(boolean isEqual, Map<Integer, Integer> diffs) {
        this.diffs = unmodifiableMap(diffs);
        this.isEqual = isEqual && diffs.isEmpty();
    }

    public boolean isEqual() {
        return isEqual;
    }

    public Map<Integer, Integer> getDifferences() {
        return diffs;
    }
}
