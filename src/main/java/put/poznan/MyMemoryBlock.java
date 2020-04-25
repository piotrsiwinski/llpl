package put.poznan;

import java.util.Arrays;

public class MyMemoryBlock {

    private final int size;
    private boolean used = false;
    private final byte[] data;

    MyMemoryBlock(int size, byte[] data) {
        this.size = size;
        this.used = true;
        this.data = Arrays.copyOf(data, size);
    }

    public byte[] getData() {
        return Arrays.copyOf(this.data, this.data.length);
    }
}
