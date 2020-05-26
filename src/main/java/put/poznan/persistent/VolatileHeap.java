package put.poznan.persistent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class VolatileHeap { //implements Heap {
//    private static final ObjectMapper objectMapper = new ObjectMapper();
//    private static final byte MACHINE_WORD_SIZE = 8;
//    private final Map<Integer, LinkedList<MemoryRegion>> heapMap = new HashMap<>();
//    private final Root root;
//
//    public VolatileHeap() {
//        root = new XRoot();
//    }
//
//    @Override
//    public void open() {
//
//    }
//
//    @Override
//    public MemoryRegion allocateRegion(long size) {
//        return null;
//    }
//
//    @Override
//    public MemoryRegion allocateRegion(Object obj) {
//        requireNonNull(obj, "Object cannot be empty");
//        try {
//            byte[] bytes = objectMapper.writeValueAsBytes(obj);
//            int alignedSize = align(bytes.length);
//            MemoryRegion memoryRegion = new VolatileMemoryRegion(alignedSize);
//            for (int i = 0; i < bytes.length; i++) {
//                memoryRegion.putByte(i, bytes[i]);
//            }
//
//            return memoryRegion;
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Cannot allocate object: " + obj.toString() + ", " + e.getMessage());
//        }
//    }
//
//    @Override
//    public MemoryRegion allocateObjectRegion(long size) {
//        return null;
//    }
//
//    @Override
//    public MemoryRegion getMemoryRegion(long address) {
//        return new VolatileMemoryRegion(1024);
//    }
//
//    @Override
//    public void freeRegion(MemoryRegion region) {
//
//    }
//
//    @Override
//    public void close() {
//
//    }
//
//    @Override
//    public Root getRoot() {
//        return this.root;
//    }
//
//
//    int align(int size) {
//        return (size + MACHINE_WORD_SIZE - 1) & -MACHINE_WORD_SIZE;
//    }
}
