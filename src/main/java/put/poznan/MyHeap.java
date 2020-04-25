package put.poznan;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

import static java.util.Optional.*;

// musza byc alloc classy, zeby moc okreslic address zmiennej
// zeby dalo sie pobrac po addressie??

public class MyHeap {

    private static final byte MACHINE_WORD_SIZE = 8;
    private static final int MAX_ALLOC_CLASSES = 255;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<Integer, LinkedList<MyMemoryBlock>> heapMap = new HashMap<>();
    private final Set<MyMemoryBlock> blockSet = new TreeSet<>();

    public long getRoot() {
        return -1;
    }


    private MyMemoryBlock allocateMemoryBlock(int size, byte[] object) {
        validateSize(size);
        System.out.println("Object size " + object.length + ", block size: " + size);
        MyMemoryBlock block = new MyMemoryBlock(size, object);
        LinkedList<MyMemoryBlock> blocks = ofNullable(heapMap.get(size)).orElse(new LinkedList<>());
        blocks.add(block);
        heapMap.put(size, blocks);

        return block;
    }

    public MyMemoryBlock allocateMemoryBlock(Object obj) {
        try {
            byte[] object = objectMapper.writeValueAsBytes(obj);
            return allocateMemoryBlock(align(object.length), object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot allocate object: " + obj.toString() + ", " + e.getMessage());
        }
    }

    public <T> T objectFromMemoryBlock(MyMemoryBlock block, Class<T> clazz) {
        byte[] data = block.getData();
        try {
            return objectMapper.readValue(data, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot read object from memoryBlock" + e.getMessage());
        }
    }

    // do we need this?
    public MyMemoryBlock memoryBlockFromAddress(long address) {
        return null;
    }

    int align(int size) {
        return (size + MACHINE_WORD_SIZE - 1) & -MACHINE_WORD_SIZE;
    }

    private void validateSize(int size) {
        if (size <= 0) {
            throw new RuntimeException("Size is wrong: " + size);
        }
    }
}
