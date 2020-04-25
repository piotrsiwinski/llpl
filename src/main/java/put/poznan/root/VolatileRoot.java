package put.poznan.root;

import put.poznan.memoryregion.MemoryRegion;

import java.util.Map;

public class VolatileRoot implements Root {

    private final MemoryRegion region;


    public VolatileRoot(MemoryRegion region) {
        this.region = region;
    }

    @Override
    public Map<String, Object> getObjectDirectory() {
        return null;
    }
}
