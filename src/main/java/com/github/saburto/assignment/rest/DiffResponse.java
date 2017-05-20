package com.github.saburto.assignment.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiffResponse {

    private final boolean equal;
    private final boolean equalSize;
    private final List<Detail> diffs;

    public DiffResponse(boolean equal, Map<Integer, Integer> diffs) {
        this.equal = equal;
        this.equalSize = equal || !diffs.isEmpty();
        this.diffs = diffs.entrySet()
            .stream()
            .map(entry -> new Detail(entry.getKey(), entry.getValue()))
            .sorted()
            .collect(Collectors.toList());
    }

    public boolean isEqual() {
        return equal;
    }

    public boolean isEqualSize() {
        return equalSize;
    }

    public List<Detail> getDiffs() {
        return diffs;
    }

    public static class Detail implements Comparable<Detail> {
        private final Integer index;
        private final Integer length;

        public Detail(Integer index, Integer length) {
            this.index = index;
            this.length = length;
        }

        public Integer getIndex() {
            return index;
        }

        public Integer getLength() {
            return length;
        }

        @Override
        public int compareTo(Detail other) {
            return Integer.compare(index, other.index);
        }
    }
}
