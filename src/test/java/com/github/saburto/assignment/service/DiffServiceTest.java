package com.github.saburto.assignment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saburto.assignment.comparator.BinaryFileComparator;
import com.github.saburto.assignment.comparator.Result;
import com.github.saburto.assignment.data.Data;
import com.github.saburto.assignment.data.Side;
import com.github.saburto.assignment.repository.DataRepository;

@RunWith(MockitoJUnitRunner.class)
public class DiffServiceTest {

    @Mock
    private DataRepository repository;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private BinaryFileComparator comparator;

    @InjectMocks
    private DiffService diffService;

    @Test
    public void verifyCompareIsCalledWithDataFromRepository() {
        Data left = mock(Data.class);
        when(repository.getById("1", Side.LEFT)).thenReturn(left);

        Data right = mock(Data.class);
        when(repository.getById("1", Side.RIGHT)).thenReturn(right);

        Result result = diffService.compareFromId("1");

        verify(repository).getById("1", Side.LEFT);
        verify(repository).getById("1", Side.RIGHT);

        verify(comparator).compare(left, right);

        assertThat(result).isNotNull();
    }

    @Test
    public void verifySaveToRepository() {


        diffService.save("1", new byte[] {1, 3}, Side.LEFT);;

        ArgumentCaptor<Data> argCaptorData = ArgumentCaptor.forClass(Data.class);
        verify(repository).save(argCaptorData.capture(), Mockito.eq(Side.LEFT));

        Data data = argCaptorData.getValue();

        assertThat(data.getId()).isEqualTo("1");
        assertThat(data.getBytes()).contains(1, 3);
    }
}
