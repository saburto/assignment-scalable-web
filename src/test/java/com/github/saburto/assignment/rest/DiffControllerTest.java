package com.github.saburto.assignment.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.github.saburto.assignment.comparator.Result;
import com.github.saburto.assignment.data.Side;
import com.github.saburto.assignment.service.DiffService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DiffService diffService;

    @Autowired
    private DiffController diffController;

    @Test
    public void diff() throws Exception {

        Mockito.when(diffService.compareFromId("2222"))
            .thenReturn(new Result(true, Collections.emptyMap()));

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/2222").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(
                    content().string(equalTo("{\"equal\":true,\"equalSize\":true,\"diffs\":[]}")));

        verify(diffService).compareFromId("2222");
    }

    @Test
    public void diffWithDiffMap() throws Exception {

        HashMap<Integer, Integer> diffs = new HashMap<>();
        diffs.put(3, 34);
        diffs.put(55, 67);
        Mockito.when(diffService.compareFromId("2222")).thenReturn(new Result(true, diffs));

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/2222").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(
                    "{\"equal\":false,\"equalSize\":true,\"diffs\":[{\"index\":3,\"length\":34},{\"index\":55,\"length\":67}]}")));

        verify(diffService).compareFromId("2222");
    }

    @Test
    public void postFileLeft() throws Exception {

        mvc.perform(post("/v1/diff/2222/left")
            .content("{\"data\":\"MTIz\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(diffService).save("2222", new byte[] {0x31, 0x32, 0x33}, Side.LEFT);
    }

    @Test
    public void postFileRight() throws Exception {

        mvc.perform(post("/v1/diff/2222/right")
            .content("{\"data\":\"MTIz\"}")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());

        verify(diffService).save("2222", new byte[] {0x31, 0x32, 0x33}, Side.RIGHT);
    }

}
