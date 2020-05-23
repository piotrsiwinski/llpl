package put.poznan.persistent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class VolatileRoot implements Root {

    private final Heap heap;
    private final MemoryRegion region;
    private final Map<String, Object> objectDirectory;
    private final int size;


    public VolatileRoot(Heap heap, int size) {
        this.heap = requireNonNull(heap);
        this.size = size;
        this.region = new VolatileMemoryRegion(size);
        this.objectDirectory = new HashMap<>();
    }


    @Override
    public void putObject(String name, Object object) {
        this.objectDirectory.put(name, object);
    }

    @Override
    public <T> T getObject(String name, Class<T> aClass) {
        return Optional.ofNullable(objectDirectory.get(name))
                .filter(aClass::isInstance)
                .map(aClass::cast)
                .orElse(null);
    }
}
