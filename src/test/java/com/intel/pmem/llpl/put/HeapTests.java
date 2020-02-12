package com.intel.pmem.llpl.put;

import com.intel.pmem.llpl.Heap;
import com.intel.pmem.llpl.TestVars;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HeapTests {

  private final static String HEAP_TEST_PATH = "/mnt/mem/testheap3.txt";

  @Test
  public void shouldCreateNewHeap() {
    Heap heap = Heap.createHeap(HEAP_TEST_PATH, TestVars.HEAP_SIZE);


    long root = heap.getRoot();

    assertThat(heap, is(notNullValue()));
    assertThat(Heap.exists(TestVars.HEAP_USER_PATH), is(true));
  }

}
