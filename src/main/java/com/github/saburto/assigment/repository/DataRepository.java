package com.github.saburto.assigment.repository;

import com.github.saburto.assigment.data.Data;
import com.github.saburto.assigment.data.Side;

public interface DataRepository {

    void save(Data data, Side sie);

    Data getById(String id, Side side);
}
