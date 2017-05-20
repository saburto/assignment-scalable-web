package com.github.saburto.assignment.repository;

import com.github.saburto.assignment.data.Data;
import com.github.saburto.assignment.data.Side;

public interface DataRepository {

    void save(Data data, Side sie);

    Data getById(String id, Side side);
}
