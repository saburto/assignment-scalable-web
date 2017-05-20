package com.github.saburto.assignment.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.saburto.assignment.data.Data;
import com.github.saburto.assignment.data.Side;
import com.github.saburto.assignment.repository.DataBinaryFileTempRepository;
import com.github.saburto.assignment.repository.DataRepository;
import com.github.saburto.assignment.repository.FileNoYetExistsException;
import com.github.saburto.assignment.repository.IdAlreadyExistsException;

public class DataBinaryFileTempRepositoryTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private DataRepository dataBinaryRepository;

    private String id;

    private byte[] bytes;

    @Before
    public void setup() {
        dataBinaryRepository = new DataBinaryFileTempRepository();
        id = UUID.randomUUID().toString();
        bytes = new byte[] {1};
    }

    @After
    public void tearDown() {
        Path tmpPath = getTempPath();
        File file = tmpPath.toFile();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void saveFileIntoTmpDir() {
        Path tmpPath = getTempPath();

        dataBinaryRepository.save(new Data(bytes, id), Side.LEFT);

        assertThat(tmpPath).exists().hasBinaryContent(bytes);
    }
    
    @Test
    public void saveTwoFilesSameIdDifferentSideIntoTmpDir() {
        Path tmpPath = getTempPath();

        dataBinaryRepository.save(new Data(bytes, id), Side.LEFT);
        dataBinaryRepository.save(new Data(bytes, id), Side.RIGHT);

        assertThat(tmpPath).exists().hasBinaryContent(bytes);
    }


    @Test
    public void saveFileWihtSameIdAndSideThatAlreadyExistsThrowAnException() {
        expectedException.expect(IdAlreadyExistsException.class);
        expectedException.expectMessage(is("Id [" + id + "] already exists for side LEFT"));

        dataBinaryRepository.save(new Data(bytes, id), Side.LEFT);
        dataBinaryRepository.save(new Data(bytes, id), Side.LEFT);
    }

    @Test
    public void getDataFromFileOfTmpDir() {
        dataBinaryRepository.save(new Data(bytes, id), Side.LEFT);

        Data data = dataBinaryRepository.getById(id, Side.LEFT);

        assertThat(data).isNotNull().extracting(Data::getBytes).containsExactly(bytes);
    }
    
    @Test
    public void throwExceptionWhenFileForIdAndSideNoExists() {
        expectedException.expect(FileNoYetExistsException.class);
        expectedException.expectMessage(is("File for Id [321] not exists yet for side LEFT"));

        dataBinaryRepository.getById("321", Side.LEFT);
    }
    
    private Path getTempPath() {
        return Paths.get(System.getProperty("java.io.tmpdir"), id + Side.LEFT  +".tmp");
    }
}
