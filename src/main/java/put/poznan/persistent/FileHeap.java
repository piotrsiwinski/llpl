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
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final long TOTAL_BYTE_BUFFER_SIZE = 128L * 1024L * 1024L; // Heap size: 128 MB
    private static final int metadataAddress = 0;
    private static final int metadataSize = 1 * 1024 * 1024; // 1 MB
    private static final int heapAddress = metadataSize; // 20 MB
    private final Path path;

    private int heapPointer = heapAddress; // hold free position of bytebuffer to allocate new bytes; convert to allocator later
    private MappedByteBuffer byteBuffer;
    private Map<String, ObjectData> objectDirectory;


    public FileHeap(Path path) {
        this.path = path;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectDirectory = new HashMap<>();
    }

    public void putObject(String name, Object object) {
        Transaction.run(this, () -> {
            try {
                // write object first
                byte[] bytes = mapper.writeValueAsBytes(object);
                byteBuffer.position(heapPointer);
                byteBuffer.put(bytes);
                this.objectDirectory.put(name, new ObjectData(heapPointer, bytes.length));
                heapPointer = byteBuffer.position();

                // update object directory
                byte[] objectDir = mapper.writeValueAsBytes(objectDirectory);
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
                        return mapper.readValue(bytes, aClass);
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
            byteBuffer = Optional
                    .ofNullable(channel.map(FileChannel.MapMode.READ_WRITE, 0, TOTAL_BYTE_BUFFER_SIZE))
                    .filter(ByteBuffer::isDirect)
                    .orElseThrow(() -> new RuntimeException("ByteBuffer is not direct"));
            if (!byteBuffer.isLoaded()) {
                byteBuffer.load();
            }
            if (!create) {
                byte[] arr = new byte[metadataSize];
                byteBuffer.get(arr);
                objectDirectory = mapper.readValue(arr, new TypeReference<HashMap<String, ObjectData>>() {});
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("AnyHeap::createHeap cannot create Heap");
        }
    }

    @Override
    public void close() {

    }

    static class ObjectData {
        private int objectAddress;
        private int objectSize;

        private ObjectData() {
        }

        private ObjectData(int objectAddress, int objectSize) {
            this.objectAddress = objectAddress;
            this.objectSize = objectSize;
        }

    }
}
