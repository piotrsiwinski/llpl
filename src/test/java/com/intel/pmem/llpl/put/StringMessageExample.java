package com.intel.pmem.llpl.put;

import com.intel.pmem.llpl.Heap;
import com.intel.pmem.llpl.MemoryBlock;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StringMessageExample {
  private final static String HEAP_TEST_PATH = "/mnt/mem/testheap";

  public static final long HEAP_SIZE_100MB = 100 * 1024 * 1024;
  public static final long MEMORY_BLOCK_SIZE_50MB = 50 * 1024 * 1024;
  public static final long MEMORY_BLOCK_SIZE_20MB = 20 * 1024 * 1024;


  public static void main(String[] args) {
    Path path = Paths.get(HEAP_TEST_PATH);
    File file = path.toFile();
    if (file.exists()) {
      file.delete();
    }


    Heap heap = Heap.createHeap(HEAP_TEST_PATH, HEAP_SIZE_100MB);

    // just test (make poolHandle public to see this value)
    // System.out.println("Address of the heap: " + heap.poolHandle);
    // System.out.println("Address of the heap (in GB): " + (double) heap.poolHandle / (1024 * 1024 * 1024) + "\n");


    // Arrange
    MemoryBlock testBlock = heap.allocateMemoryBlock(MEMORY_BLOCK_SIZE_50MB, false);
    final String data = "MemoryBlockTests";
    byte[] bytes = data.getBytes();
    // set length of the String first
    final int metadataLength = Integer.BYTES;
    testBlock.setInt(0, bytes.length);

    for (int i = 0; i < bytes.length; i++) {
      testBlock.setByte(metadataLength + i, bytes[i]);
    }
    // "testBlock" is a root for this heap
    // get address of "testBlock" and set it as root of this heap
    heap.setRoot(testBlock.handle());
    System.out.println("Address (handle) of first block: " + testBlock.handle());
    System.out.println("Address (handle) of first block (in MB): " + (double) testBlock.handle() / 1024 / 1024);
    System.out.println();


    // Act
    long heapRoot = heap.getRoot();
    MemoryBlock memoryBlock = heap.memoryBlockFromHandle(heapRoot);
    int stringSize = memoryBlock.getInt(0);

    byte[] savedBytes = new byte[stringSize];
    for (int i = 0; i < stringSize; i++) {
      byte aByte = memoryBlock.getByte(metadataLength + i);
      savedBytes[i] = aByte;
    }

    String s = new String(savedBytes);
    System.out.println(s);
    assert data.equals(s);

    // alokuje kolejny blok 20 MB, żeby zobaczyć address 2-giego bloku
    MemoryBlock secondMemBlock = heap.allocateMemoryBlock(MEMORY_BLOCK_SIZE_20MB, false);

    System.out.println("secondMemBlock::handle():  " + secondMemBlock.handle());
    System.out.println("Address (handle) of second block (in MB): " + (double) secondMemBlock.handle() / 1024 / 1024);
    System.out.println();

    System.out.println("Metadata block in heap handle(): " + heap.getRoot());


  }
}
