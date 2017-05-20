package com.github.saburto.assignment.comparator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.github.saburto.assignment.data.Data;

public class BinaryFileComparatorTest {

    private BinaryFileComparator binaryFileComparator;

    @Before
    public void setUp() {
        binaryFileComparator = new BinaryFileComparator();
    }

    @Test
    public void compareEqualsByteArraysShouldReturnResultIsEqualTrue() {

        Result result =
                binaryFileComparator.compare(new Data(new byte[] {1, 2}, "1"),
                        new Data(new byte[] {1, 2}, "1"));

        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isTrue();
    }

    @Test
    public void compareDifferentSizeDataShouldReturnResultFalse() {
        Data left = new Data(new byte[] {1, 2}, "1");
        Data rigth = new Data(new byte[] {1, 2, 3}, "1");
        Result result = binaryFileComparator.compare(left, rigth);

        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isFalse();
    }

    @Test
    public void compareSameSizeButDifferentDataShouldReturnResultFalse() {
        Data left = new Data(new byte[] {1, 2}, "1");
        Data rigth = new Data(new byte[] {1, 3}, "1");
        Result result = binaryFileComparator.compare(left, rigth);

        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isFalse();
    }

    @Test
    public void resultContainsIndexOfTheByteWithDifference() {
        Data left = new Data(new byte[] {1, 2}, "1");
        Data rigth = new Data(new byte[] {1, 3}, "1");
        Result result = binaryFileComparator.compare(left, rigth);

        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isFalse();
        assertThat(result.getDifferences()).containsKey(1);
    }

    @Test
    public void resultContainsLenghtOfDifference() {
        Data left = new Data(new byte[] {1, 2}, "1");
        Data rigth = new Data(new byte[] {1, 3}, "1");
        Result result = binaryFileComparator.compare(left, rigth);

        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isFalse();
        assertThat(result.getDifferences()).containsValue(1);
    }

    @Test
    public void resultContainsMultipleDifferences() {
        Data left = new Data(new byte[] {1, 2, 4, 6, 7, 8, 9}, "1");
        Data rigth = new Data(new byte[] {1, 3, 5, 6, 7, 9, 8}, "1");
        Result result = binaryFileComparator.compare(left, rigth);

        assertThat(result).isNotNull();
        assertThat(result.isEqual()).isFalse();
        assertThat(result.getDifferences()).containsKeys(1, 5).containsValues(2, 2);
    }
}
