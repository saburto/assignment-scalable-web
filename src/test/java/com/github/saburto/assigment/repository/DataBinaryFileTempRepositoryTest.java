package com.github.saburto.assigment.repository;

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

import com.github.saburto.assigment.data.Data;

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
        Path tmpPath = Paths.get(System.getProperty("java.io.tmpdir"), id + "left.tmp");
        File file = tmpPath.toFile();
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void saveFileIntoTmpDir() {
        Path tmpPath = Paths.get(System.getProperty("java.io.tmpdir"), id + "left.tmp");

        dataBinaryRepository.save(new Data(bytes, id), "left");

        assertThat(tmpPath).exists().hasBinaryContent(bytes);

    }

    @Test
    public void saveFileWihtSameIdAndSideThatAlreadyExistsThrowAnException() {
        expectedException.expect(IdAlreadyExistsException.class);
        expectedException.expectMessage(is("Id [" + id + "] already exists for side left"));

        dataBinaryRepository.save(new Data(bytes, id), "left");
        dataBinaryRepository.save(new Data(bytes, id), "left");
    }

    @Test
    public void getDataFromFileOfTmpDir() {
        dataBinaryRepository.save(new Data(bytes, id), "left");

        Data data = dataBinaryRepository.getById(id, "left");

        assertThat(data).isNotNull().extracting(Data::getBytes).containsExactly(bytes);
    }

}
