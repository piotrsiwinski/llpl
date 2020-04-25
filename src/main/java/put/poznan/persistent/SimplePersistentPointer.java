package put.poznan.persistent;

import static java.util.Objects.requireNonNull;

class SimplePersistentPointer<T> implements PersistentPointer<T> {
    private long address;
    private final MemoryRegion memoryRegion;
    private final T object;

    // heap powinien Tworzyc te obiekty i nimi zarzadzac - zmienic konstruktor na private
    SimplePersistentPointer(T object, MemoryRegion memoryRegion) {
        this.object = requireNonNull(object);
        this.memoryRegion = requireNonNull(memoryRegion);
        this.address = 0;
    }

    @Override
    public long getAddress() {
        return address;
    }

    @Override
    public T getValue() {
        return object;
    }
}
