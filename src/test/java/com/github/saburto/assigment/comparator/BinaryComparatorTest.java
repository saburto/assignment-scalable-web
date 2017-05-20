package com.github.saburto.assigment.comparator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.github.saburto.assigment.data.Data;

public class BinaryComparatorTest {
    
    private BinaryComparator binaryComparator;
    
    @Before
    public void setUp(){
        binaryComparator = new BinaryComparator();
    }

    @Test
    public void compareEqualsByteArraysShouldReturnResultIsEqualTrue() {
        
        Result result = binaryComparator.compare(new Data(new byte[] {1, 2}), new Data(new byte[] {1, 2}));
        
        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isTrue();
    }
    
    @Test
    public void compareDifferentSizeDataShouldReturnResultFalse() {
        Data left = new Data(new byte[] {1, 2});
        Data rigth = new Data(new byte[] {1, 2, 3});
        Result result = binaryComparator.compare(left, rigth);
        
        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isFalse();
    }
    
    @Test
    public void compareSameSizeButDifferentDataShouldReturnResultFalse() {
        Data left = new Data(new byte[] {1, 2});
        Data rigth = new Data(new byte[] {1, 3});
        Result result = binaryComparator.compare(left, rigth);
        
        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isFalse();
    }
}
