package com.intel.pmem.llpl.allocator;

class AllocationClass {

  private byte id;
  private short flags;
  private long size;


  enum AllocationClassType {
    CLASS_UNKNOWN,
    CLASS_HUGE,
    CLASS_RUN,

    MAX_ALLOC_CLASS_TYPES
  }

  ;

  class RuntimeData {
    long size_idx; /* size index of a single run instance */
    long alignment; /* required alignment of objects */
    int nallocs; /* number of allocs per run */
  }

}
