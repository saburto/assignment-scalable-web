package com.github.saburto.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.saburto.assignment.comparator.BinaryFileComparator;
import com.github.saburto.assignment.comparator.Result;
import com.github.saburto.assignment.data.Data;
import com.github.saburto.assignment.data.Side;
import com.github.saburto.assignment.repository.DataRepository;

@Service
public class DiffService {

    private final DataRepository dataRepository;
    private final BinaryFileComparator binaryFileComparator;

    @Autowired
    public DiffService(DataRepository dataRepository, BinaryFileComparator binaryFileComparator) {
        this.dataRepository = dataRepository;
        this.binaryFileComparator = binaryFileComparator;
    }

    public Result compareFromId(String id) {

        Data left = dataRepository.getById(id, Side.LEFT);
        Data right = dataRepository.getById(id, Side.RIGHT);

        return binaryFileComparator.compare(left, right);
    }

    public void save(String id, byte[] bytes, Side side) {
        dataRepository.save(new Data(bytes, id), side);
    }
}
