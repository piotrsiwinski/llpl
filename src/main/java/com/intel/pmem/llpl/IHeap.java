package com.intel.pmem.llpl;

public interface IHeap {
  long nativeCreateHeap(String path, long size, long[] allocationClasses, String layout);

  long nativeOpenHeap(String path, long[] allocationClasses, String layout);
}
