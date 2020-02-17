package com.intel.pmem.llpl.put;

import com.intel.pmem.llpl.Heap;
import com.intel.pmem.llpl.TestVars;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class HeapTests {

  @BeforeEach
  public void setup() {
    TestVars.HEAP_USER_PATH = "/mnt/mem/testheap";
  }

  @AfterEach
  public void clean_up() {
    TestVars.cleanUp(TestVars.HEAP_USER_PATH);
  }

  @Test
  public void can_create_new_heap() throws IOException {
    Heap heap = Heap.createHeap(TestVars.HEAP_USER_PATH, TestVars.HEAP_SIZE_100MB);

    assertThat(heap, is(notNullValue()));
    assertThat(Heap.exists(TestVars.HEAP_USER_PATH), is(true));
    assertThat(Files.exists(Paths.get(TestVars.HEAP_USER_PATH)), is(true));
    assertThat(Files.size(Paths.get(TestVars.HEAP_USER_PATH)), is(TestVars.HEAP_SIZE_100MB));
    assertThat(Files.exists(Paths.get(TestVars.HEAP_USER_PATH)), is(true));
  }

}
