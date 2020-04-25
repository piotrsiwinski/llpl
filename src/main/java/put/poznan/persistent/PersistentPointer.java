package put.poznan.persistent;

import java.util.function.Consumer;
import java.util.function.Function;

public interface PersistentPointer<T> {
    long getAddress();

    T getValue();

    PersistentPointer<T> access(Consumer<? super T> operation);
}
