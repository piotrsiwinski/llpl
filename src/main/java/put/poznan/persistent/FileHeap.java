package put.poznan.persistent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileHeap implements Heap {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private static final long TOTAL_BYTE_BUFFER_SIZE = 128L * 1024L * 1024L; // Heap size: 128 MB
    static final int metadataAddress = 0;
    static final int metadataSize = 1 * 1024 * 1024; // 1 MB
    static final int heapAddress = metadataSize; // 20 MB
    private int heapPointer = heapAddress; // hold free position of bytebuffer to allocate new bytes; convert to allocator later

    private final Path path;


    private MappedByteBuffer byteBuffer;


    // instead of Root to make it more simple
    // map -> <object name, object address> map
    private Map<String, ObjectData> objectDirectory;


    public FileHeap(Path path) {
        this.path = path;
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectDirectory = new HashMap<>();
    }

    public void putObject(String name, Object object) {
        Transaction.run(this, () -> {
            try {
                // write object first
                byte[] bytes = objectMapper.writeValueAsBytes(object);
                byteBuffer.position(heapPointer);
                byteBuffer.put(bytes);
                this.objectDirectory.put(name, new ObjectData(heapPointer, bytes.length));
                heapPointer = byteBuffer.position();

                // update object directory
                byte[] objectDir = objectMapper.writeValueAsBytes(objectDirectory);
                byteBuffer.position(metadataAddress);
                byteBuffer.put(objectDir);
                byteBuffer.force();
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Cannot put object into heap");
            }
        });
    }

    public <T> T getObject(String name, Class<T> aClass) {
        return Optional.ofNullable(objectDirectory.get(name))
                .map(objectData -> {
                    byte[] bytes = new byte[objectData.objectSize];
                    byteBuffer.position(objectData.objectAddress);
                    byteBuffer.get(bytes);
                    try {
                        return objectMapper.readValue(bytes, aClass);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .orElse(null);
    }

    public void freeObject(String name) {

    }

    @Override
    public void open() {
        boolean create = false;
        if (!path.toFile().exists()) {
            create = true;
        }

        try (RandomAccessFile fileInputStream = new RandomAccessFile(path.toFile(), "rw");
             FileChannel channel = fileInputStream.getChannel()) {
            byteBuffer = Optional.ofNullable(channel.map(FileChannel.MapMode.READ_WRITE, 0, TOTAL_BYTE_BUFFER_SIZE))
                    .filter(ByteBuffer::isDirect)
                    .orElseThrow(() -> new RuntimeException("ByteBuffer is not direct"));
            if (!byteBuffer.isLoaded()) {
                byteBuffer.load();
            }
            if (!create) {
                byte[] arr = new byte[metadataSize];
                byteBuffer.get(arr);
                TypeReference<HashMap<String, ObjectData>> typeRef
                        = new TypeReference<HashMap<String, ObjectData>>() {
                };
                objectDirectory = objectMapper.readValue(arr, typeRef);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("AnyHeap::createHeap cannot create Heap");
        }
    }

    @Override
    public void close() {

    }


    @Override
    public Root getRoot() {
        throw new RuntimeException("Root is null. You need to open heap first!");
    }

    static class ObjectData {
        private int objectAddress;
        private int objectSize;

        private ObjectData() {
        }

        ObjectData(int objectAddress, int objectSize) {
            this.objectAddress = objectAddress;
            this.objectSize = objectSize;
        }

    }
}
