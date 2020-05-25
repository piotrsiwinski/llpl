package put.poznan.persistent;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Optional;

public class FileHeap implements Heap {
    private final Path path;
    private final long size = 128L * 1024L * 1024L; // Heap size: 128 MB
    private MappedByteBuffer byteBuffer;
    private Root root;
    public static final ObjectMapper objectMapper = new ObjectMapper();
    private Metadata metadata;


    public FileHeap(Path path) {
        this.root = new XRoot();
        this.path = path;
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Override
    public void open() {

        boolean create = false;
        if (!path.toFile().exists()) {
            create = true;
        }

        try (RandomAccessFile fileInputStream = new RandomAccessFile(path.toFile(), "rw");
             FileChannel channel = fileInputStream.getChannel()) {

            byteBuffer = Optional.ofNullable(channel.map(FileChannel.MapMode.READ_WRITE, 0, size))
                    .filter(ByteBuffer::isDirect)
                    .orElseThrow(() -> new RuntimeException("ByteBuffer is not direct"));

            if (!byteBuffer.isLoaded()) {
                byteBuffer.load();
            }

            if (create) { // create new root
                metadata = new Metadata();
                byte[] metadata = objectMapper.writeValueAsBytes(this.metadata);
                Transaction.run(this, () -> {
                    byteBuffer.put(metadata);
                    byteBuffer.position(Metadata.metadataSize);
                    byteBuffer.force();
                });
            } else {
                byte[] arr = new byte[Metadata.metadataSize];
                byteBuffer.get(arr);
                metadata = objectMapper.readValue(arr, Metadata.class);
                byte[] rootAsBytes = new byte[metadata.rootSize];
                byteBuffer.get(rootAsBytes);
                root = objectMapper.readValue(rootAsBytes, XRoot.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("AnyHeap::createHeap cannot create Heap");
        }
    }

    @Override
    public void close() {
        try {
            byte[] rootAsBytes = objectMapper.writeValueAsBytes(root);
            Transaction.run(this, () -> {
                byteBuffer.position(Metadata.metadataSize);
                byteBuffer.put(rootAsBytes);
                byteBuffer.force();
            });
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("AnyHeap::createHeap cannot close Heap");
        }
    }

    @Override
    public MemoryRegion allocateRegion(long size) {
        return null;
    }

    @Override
    public MemoryRegion allocateRegion(Object obj) {
        return null;
    }

    @Override
    public MemoryRegion allocateObjectRegion(long size) {
        return null;
    }

    @Override
    public MemoryRegion getMemoryRegion(long address) {
        return null;
    }

    @Override
    public void freeRegion(MemoryRegion region) {

    }

    @Override
    public Root getRoot() {
        if (root == null) {
            throw new RuntimeException("Root is null. You need to open heap first!");
        }
        return this.root;
    }

    static class Metadata {
        static int metadataSize = 256; // 256 bytes
        private int rootSize = 20 * 1024 * 1024; // 20 MB
        private int rootOffset = metadataSize;

        public Metadata() {

        }

        public int getRootSize() {
            return rootSize;
        }

        public void setRootSize(int rootSize) {
            this.rootSize = rootSize;
        }

        public int getRootOffset() {
            return rootOffset;
        }

        public void setRootOffset(int rootOffset) {
            this.rootOffset = rootOffset;
        }

        public int getMetadataSize() {
            return metadataSize;
        }

        public void setMetadataSize(int metadataSize) {
            this.metadataSize = metadataSize;
        }
    }
}
