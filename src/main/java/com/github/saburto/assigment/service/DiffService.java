package com.github.saburto.assigment.service;

import com.github.saburto.assigment.comparator.BinaryFileComparator;
import com.github.saburto.assigment.comparator.Result;
import com.github.saburto.assigment.data.Data;
import com.github.saburto.assigment.repository.DataRepository;

public class DiffService {

    private final DataRepository dataRepository;
    private final BinaryFileComparator binaryFileComparator;

    public DiffService(DataRepository dataRepository, BinaryFileComparator binaryFileComparator) {
        this.dataRepository = dataRepository;
        this.binaryFileComparator = binaryFileComparator;
    }

    public Result compareFromId(String id) {
        
        Data left = dataRepository.getById(id, "left");
        Data right = dataRepository.getById(id, "right");
        
        return binaryFileComparator.compare(left, right);
    }
}
