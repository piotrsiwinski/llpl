package com.intel.pmem.llpl.put;

import com.intel.pmem.llpl.Heap;
import com.intel.pmem.llpl.MemoryBlock;
import com.intel.pmem.llpl.TestVars;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MemoryBlockTests {

  private final static String HEAP_TEST_PATH = "/mnt/mem/testheap";

  private Heap heap;

  @BeforeEach
  public void setup() {
    TestVars.HEAP_USER_PATH = "/mnt/mem/testheap";
    heap = Heap.createHeap(HEAP_TEST_PATH, TestVars.HEAP_SIZE_100MB);
  }

  @AfterEach
  public void clean_up() {
    TestVars.cleanUp(HEAP_TEST_PATH);
  }

  @Test
  public void can_allocate_memory_block() {
    MemoryBlock memoryBlock = heap.allocateMemoryBlock(TestVars.MEMORY_BLOCK_SIZE_50MB, false);

    assertThat(memoryBlock, is(notNullValue()));

  }

}
