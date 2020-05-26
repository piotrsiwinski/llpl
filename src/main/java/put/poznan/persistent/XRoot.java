package put.poznan.persistent;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class XRoot implements Root, Serializable {
    private final Map<String, Object> objectDirectory;
    private final Heap heap;


    public XRoot(Heap heap) {
        this.heap = requireNonNull(heap);
        this.objectDirectory = new HashMap<>();
    }


    @Override
    public void putObject(String name, Object object) {
        Transaction.run(heap, () -> {
            this.objectDirectory.put(name, object);


        });
    }

    @Override
    public <T> T getObject(String name, Class<T> aClass) {
        Object o = objectDirectory.get(name);
        if (o instanceof HashMap) {
            return FileHeap.objectMapper.convertValue(o, aClass);
        }
        return Optional.ofNullable(objectDirectory.get(name))
                .filter(aClass::isInstance)
                .map(aClass::cast)
                .orElse(null);
    }

    @Override
    public void freeObject(String name) {

    }
}
