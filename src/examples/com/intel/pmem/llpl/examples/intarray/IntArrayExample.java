/*
 * Copyright (C) 2019 Intel Corporation
 *
 * SPDX-License-Identifier: BSD-3-Clause
 *
 */

package com.intel.pmem.llpl.examples.intarray;

import com.intel.pmem.llpl.*;

public class IntArrayExample{
    public static void main(String[] args) {
        String heapName = "/mnt/mem/int_array_example6";
        Heap heap = Heap.exists(heapName)
                               ? Heap.openHeap(heapName)
                               : Heap.createHeap(heapName, 500_000_000L);

        long handle = heap.getRoot();
        if (handle == 0) {
            long size = 10;
            System.out.println("Creating New Array of size " + size);
            IntArray ia = new IntArray(heap, size);
            ia.set(5, 10);
            ia.set(7, 20);
            heap.setRoot(ia.handle());
            System.out.println("root: " + heap.getRoot());
        }
        else {
            IntArray ia = IntArray.fromHandle(heap, handle);
            System.out.println("Retrieved IntArray of size " + ia.size());
            for (int i = 0; i < ia.size(); i++) {
                int val = ia.get(i);
                System.out.println("IntArray[" + i + "] = " + val);
            }
        }
    }
}
