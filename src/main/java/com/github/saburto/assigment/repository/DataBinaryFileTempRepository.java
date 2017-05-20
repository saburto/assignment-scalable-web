package com.github.saburto.assigment.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.github.saburto.assigment.data.Data;
import com.github.saburto.assigment.data.Side;

public class DataBinaryFileTempRepository implements DataRepository {

    @Override
    public void save(Data data, Side side) {
        try {
            String id = data.getId();

            existIdThenThrowException(id, side);

            Path tempFile = Files.createFile(getTempFilePath(id, side));
            Files.write(tempFile, data.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Data getById(String id, Side side) {
        try {
            byte[] bytes = Files.readAllBytes(getTempFilePath(id, side));
            return new Data(bytes, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void existIdThenThrowException(String id, Side side) {
        if(Files.exists(getTempFilePath(id, side))){
            throw new IdAlreadyExistsException(id, side);
        }
    }

    private Path getTempFilePath(String id, Side side) {
        return Paths.get(System.getProperty("java.io.tmpdir"), id + side + ".tmp");
    }
}
