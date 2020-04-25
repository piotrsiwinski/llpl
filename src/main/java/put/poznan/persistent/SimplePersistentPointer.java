package put.poznan.persistent;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class SimplePersistentPointer<T> implements PersistentPointer<T> {
    private long address;
    private final MemoryRegion memoryRegion;
    private T object;

    // heap powinien Tworzyc te obiekty i nimi zarzadzac - zmienic konstruktor na private
    SimplePersistentPointer(T object, MemoryRegion memoryRegion) {
        this.object = requireNonNull(object);
        this.memoryRegion = requireNonNull(memoryRegion);
        this.address = 0;
    }


    public PersistentPointer<T> access(Consumer<? super T> operation) {
        // compute change as transaction
        // write change to memory region
        Objects.requireNonNull(operation);
        operation.accept(object);
        return new SimplePersistentPointer<T>(object, this.memoryRegion);
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
