package com.github.saburto.assignment.rest;

import java.util.Map;

public class Diff {
    
    private final boolean equal;
    private final boolean equalSize;
    private final Map<Integer, Integer> diffs;
    
    public Diff(boolean equal, Map<Integer, Integer> diffs) {
        this.equal = equal;
        this.equalSize = equal || !diffs.isEmpty();
        this.diffs = diffs;
    }
    
    public boolean isEqual() {
        return equal;
    }

    public boolean isEqualSize() {
        return equalSize;
    }

    public Map<Integer, Integer> getDiffs() {
        return diffs;
    }
}
