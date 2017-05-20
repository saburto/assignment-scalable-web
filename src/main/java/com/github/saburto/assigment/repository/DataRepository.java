package com.github.saburto.assigment.repository;

import com.github.saburto.assigment.data.Data;

public interface DataRepository {

    void save(Data data, String side);

    Data getById(String id, String side);
}
