package put.poznan.persistent;

import put.poznan.BitUtil;

public class VolatileMemoryRegion implements MemoryRegion {
    private final byte[] bytes;
    public final long size;

    public VolatileMemoryRegion(long size) {
        this.size = size;
        bytes = new byte[(int)size];
    }

    public long addr() {return -1;}
    public long size() {return size;}

    public byte[] getBytes() {return bytes;}
    public byte getByte(long offset) {return bytes[(int)offset];}
    public short getShort(long offset) {return BitUtil.getShort(bytes, (int)offset);}
    public int getInt(long offset) {return BitUtil.getInt(bytes, (int)offset);}
    public long getLong(long offset) {return BitUtil.getLong(bytes, (int)offset);}

    public void putByte(long offset, byte value) {bytes[(int)offset] = value;}
    public void putShort(long offset, short value) {BitUtil.putShort(bytes, (int)offset, value);}
    public void putInt(long offset, int value) {BitUtil.putInt(bytes, (int)offset, value);}
    public void putLong(long offset, long value) {BitUtil.putLong(bytes, (int)offset, value);}

    public void putDurableByte(long offset, byte value) {putByte(offset, value);}
    public void putDurableShort(long offset, short value) {putShort(offset, value);}
    public void putDurableInt(long offset, int value) {putInt(offset, value);}
    public void putDurableLong(long offset, long value) {putLong(offset, value);}

    public void putRawBytes(long offset, byte[] value) {
        System.arraycopy(value, 0, bytes, (int)offset, value.length);}
    public void putRawByte(long offset, byte value) {putByte(offset, value);}
    public void putRawShort(long offset, short value) {putShort(offset, value);}
    public void putRawInt(long offset, int value) {putInt(offset, value);}
    public void putRawLong(long offset, long value) {putLong(offset, value);}

    public void flush(long size) {}
    public void flush(long offset, long size) {}

    public String toString() {return "VolatileMemoryRegion(size = " + size + ")";}
}
