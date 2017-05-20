package com.github.saburto.assignment.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Repository;

import com.github.saburto.assignment.data.Data;
import com.github.saburto.assignment.data.Side;

@Repository
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
            throwExceptionIfFileNoExists(id, side);
            
            byte[] bytes = Files.readAllBytes(getTempFilePath(id, side));
            return new Data(bytes, id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void throwExceptionIfFileNoExists(String id, Side side) {
        if(!fileExists(id, side)){
            throw new FileNoYetExistsException(id, side);
        }
    }

    private void existIdThenThrowException(String id, Side side) {
        if(fileExists(id, side)){
            throw new IdAlreadyExistsException(id, side);
        }
    }

    private boolean fileExists(String id, Side side) {
        return Files.exists(getTempFilePath(id, side));
    }

    private Path getTempFilePath(String id, Side side) {
        return Paths.get(System.getProperty("java.io.tmpdir"), id + side + ".tmp");
    }
}
