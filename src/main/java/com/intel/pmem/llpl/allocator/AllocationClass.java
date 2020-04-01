package com.intel.pmem.llpl.allocator;

import com.google.common.collect.ImmutableSet;
import com.intel.pmem.llpl.HeapException;

class AllocationClass {

  private static final int MAX_ALLOCATION_CLASSES = 255;

  ImmutableSet<Long> definedAllocationSizes =
      ImmutableSet.of(128L, 1024L, 2048L, 4096L, 8192L, 16_384L, 32_768L, 131_072L, 393_216L);


  /*
   * findFirstFreeSlot -- searches for the
   *	first available allocation class slot
   *
   * todo: This function must be thread-safe because allocation classes can be created at runtime.
   */
  Long findFirstFreeSlot(long requestedSize) {
    if (requestedSize <= 0) {
      throw new HeapException("Size cannot be less than zero: " + requestedSize);
    }

    for (long size : definedAllocationSizes) {
      if (requestedSize <= size) {
        return size;
      }
    }
    // todo: add new allocation classes at runtime
    throw new HeapException("Cannot find matching allocation class. Requested size too big");
  }
}
