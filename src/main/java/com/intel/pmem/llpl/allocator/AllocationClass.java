package com.intel.pmem.llpl.allocator;

import com.google.common.collect.ImmutableSet;

public class AllocationClass {

  ImmutableSet<Long> build =
      ImmutableSet.of(128L, 1024L, 2048L, 4096L, 8192L, 16384L, 32768L, 131072L, 393216L);
}
