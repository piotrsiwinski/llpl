package com.intel.pmem.llpl.put;

import com.intel.pmem.llpl.Heap;
import com.intel.pmem.llpl.MemoryBlock;
import com.intel.pmem.llpl.TestVars;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MemoryBlockTests {

  private final static String EXISTING_HEAP_TEST_PATH = "/mnt/mem/exisitngtestheap";
  private final static String HEAP_TEST_PATH = "/mnt/mem/testheap";

  private Heap heap;

  public static void cleanUp(String pathToHeap) {
    Path path = Paths.get(pathToHeap);
    File file = path.toFile();
    if (file.exists()) {
      file.delete();
    }
  }

  @BeforeAll
  static void init() {
    cleanUp(EXISTING_HEAP_TEST_PATH);
    Heap myExistingHeap = Heap.createHeap(EXISTING_HEAP_TEST_PATH, TestVars.HEAP_SIZE_100MB);
  }

  @BeforeEach
  void setup() {
    cleanUp(HEAP_TEST_PATH);
    TestVars.HEAP_USER_PATH = "/mnt/mem/testheap";
    heap = Heap.createHeap(HEAP_TEST_PATH, TestVars.HEAP_SIZE_100MB);
  }

  @AfterEach
  void clean_up() {
    TestVars.cleanUp(HEAP_TEST_PATH);
  }

  @Test
  void can_allocate_memory_block() {
    MemoryBlock memoryBlock = heap.allocateMemoryBlock(TestVars.MEMORY_BLOCK_SIZE_50MB, false);

    assertThat(memoryBlock, is(notNullValue()));

  }

  @Test
  void can_write_long_and_read_it() {
    MemoryBlock memoryBlock = heap.allocateMemoryBlock(TestVars.MEMORY_BLOCK_SIZE_50MB, false);

    final long value = 1024;

    memoryBlock.setLong(1, value);
    heap.setRoot(memoryBlock.handle());
    System.out.println("memoryBlock.handle(): " + memoryBlock.handle());
    System.out.println("heap.getRoot(): " + heap.getRoot());

    long aLong = memoryBlock.getLong(1);
    assertThat(aLong, is(value));
  }

  @Test
  void can_write_string_and_read_it() {
    // Arrange
    MemoryBlock testBlock = heap.allocateMemoryBlock(TestVars.MEMORY_BLOCK_SIZE_50MB, false);
    final String data = "MemoryBlockTests";
    byte[] bytes = data.getBytes();
    // set length of the String first
    testBlock.setInt(0, bytes.length);

    for (int i = 0; i < bytes.length; i++) {
      testBlock.setByte(Integer.BYTES + i, bytes[i]);
    }
    // "testBlock" is a root for this heap
    // get address of "testBlock" and set it as root of this heap
    heap.setRoot(testBlock.handle());

    assertThat(heap.getRoot(), is(not(0)));

    // Act
    long heapRoot = heap.getRoot();
    MemoryBlock memoryBlock = heap.memoryBlockFromHandle(heapRoot);
    int stringSize = memoryBlock.getInt(0);

    byte[] savedBytes = new byte[stringSize];
    for (int i = 0; i < stringSize; i++) {
      byte aByte = memoryBlock.getByte(Integer.BYTES + i);
      savedBytes[i] = aByte;
    }

    String readStringFromMemoryBlock = new String(savedBytes);
    assertThat(data, is(readStringFromMemoryBlock));

  }


}
