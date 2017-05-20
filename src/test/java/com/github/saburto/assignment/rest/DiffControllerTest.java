package com.github.saburto.assignment.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

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
import com.github.saburto.assignment.service.DiffService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private DiffService diffService;

    @Test
    public void diff() throws Exception {
        
        Mockito.when(diffService.compareFromId("2222")).thenReturn(new Result(true, Collections.emptyMap()));
        
        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/2222").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("{\"equal\":true,\"equalSize\":true,\"diffs\":{}}")));

        verify(diffService).compareFromId("2222");
    }
}
