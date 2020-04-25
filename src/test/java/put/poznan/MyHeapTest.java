package put.poznan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {

    private MyHeap heap = new MyHeap();

    @ParameterizedTest
    @CsvSource(value = {
            "1, 8",
            "3, 8",
            "8, 8",
            "12, 16",
            "16, 16",
            " 20, 24",
            "50, 56",
            "254, 256"
    })
    void align(int requested, int expected) {
        int result = heap.align(requested);
        assertEquals(expected, result);

    }
}