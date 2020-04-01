package com.intel.pmem.llpl.allocator;

import com.intel.pmem.llpl.HeapException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AllocationClassTest {

  AllocationClass allocationClass = new AllocationClass();

  @Test
  public void shouldFindClassFor8Bytes() {
    // arrange
    final long requestedSize = 8L;

    // act
    Long firstFreeSlot = allocationClass.findFirstFreeSlot(requestedSize);

    // assert
    assertThat(firstFreeSlot)
        .isEqualTo(128L);

  }

  @Test
  public void shouldFindClassForExact128Bytes() {
    // arrange
    final long requestedSize = 128L;

    // act
    Long firstFreeSlot = allocationClass.findFirstFreeSlot(requestedSize);

    // assert
    assertThat(firstFreeSlot)
        .isEqualTo(128L);

  }

  @Test
  public void shouldFindClassForMaxSize() {
    // arrange
    final long requestedSize = 393216L;

    // act
    Long firstFreeSlot = allocationClass.findFirstFreeSlot(requestedSize);

    // assert
    assertThat(firstFreeSlot)
        .isEqualTo(393216L);

  }

  @Test
  public void shouldThrowExceptionForNegativeSize() {
    // arrange
    final long requestedSize = -1;

    // act and assert exception
    Assertions.assertThrows(HeapException.class, () -> {
      allocationClass.findFirstFreeSlot(requestedSize);
    });

  }

  @Test
  public void shouldThrowExceptionForTooBigAllocation() {
    // arrange
    final long requestedSize = 400_000L;

    // act and assert exception
    Assertions.assertThrows(HeapException.class, () -> {
      allocationClass.findFirstFreeSlot(requestedSize);
    });

  }

}