package put.poznan.memoryregion;

public interface MemoryRegion {
    long addr() throws UnsupportedOperationException;

    byte getByte(long offset);
    short getShort(long offset);
    int getInt(long offset);
    long getLong(long offset);

    void putByte(long offset, byte value);
    void putShort(long offset, short value);
    void putInt(long offset, int value);
    void putLong(long offset, long value);

    void putDurableByte(long offset, byte value);
    void putDurableShort(long offset, short value);
    void putDurableInt(long offset, int value);
    void putDurableLong(long offset, long value);

    void putRawByte(long offset, byte value);
    void putRawShort(long offset, short value);
    void putRawInt(long offset, int value);
    void putRawLong(long offset, long value);
    void putRawBytes(long offset, byte[] value);

    void flush(long size);
    void flush(long offset, long size);
}
