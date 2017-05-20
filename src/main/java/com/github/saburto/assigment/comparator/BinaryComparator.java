package com.github.saburto.assigment.comparator;

import com.github.saburto.assigment.data.Data;

public class BinaryComparator {

    public Result compare(Data left, Data rigth) {
        boolean sameSize = left.isSameSize(rigth);

        if (sameSize) {
            for (int i = 0; i < left.size(); i++) {
                if(!left.hasSameByte(i, rigth)){
                    return new Result(false);
                }
            }
        }

        return new Result(sameSize);
    }

}
