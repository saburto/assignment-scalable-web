package com.github.saburto.assigment.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DataTest {

    @Test
    public void twoDataWithSameByteArraysReturnSameSizeTrue() {
        Data data1 = new Data(new byte[] {1, 2});
        Data data2 = new Data(new byte[] {1, 2});
        
        assertThat(data1.isSameSize(data2)).isTrue();
    }
    
    @Test
    public void twoDataWithDifferentByteArraysReturnSameSizeFalse() {
        Data data1 = new Data(new byte[] {1, 2});
        Data data2 = new Data(new byte[] {1, 2, 3});
        
        assertThat(data1.isSameSize(data2)).isFalse();
    }
    
    @Test
    public void hasSameByteReturnsTrueWhenTwoBytesAreEqualsOnTheSameIndex() {
        Data data1 = new Data(new byte[] {1, 2});
        Data data2 = new Data(new byte[] {1, 2});
        
        assertThat(data1.hasSameByte(0, data2)).isTrue();
        assertThat(data1.hasSameByte(1, data2)).isTrue();
    }
    
    @Test
    public void hasSameByteReturnsFalseWhenTwoBytesAreNotEqualsOnTheSameIndex() {
        Data data1 = new Data(new byte[] {1, 2});
        Data data2 = new Data(new byte[] {1, 3});
        
        assertThat(data1.hasSameByte(0, data2)).isTrue();
        assertThat(data1.hasSameByte(1, data2)).isFalse();
    }

}
