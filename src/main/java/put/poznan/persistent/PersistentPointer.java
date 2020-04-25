package put.poznan.persistent;

// marker interface for PersistentPointers
public interface PersistentPointer<T> {
    long getAddress();
    T getValue();
}
