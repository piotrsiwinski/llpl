package put.poznan.persistent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intel.pmem.llpl.PersistentHeap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

class XRoot implements Root, Serializable {
    private final Map<String, Object> objectDirectory;


    public XRoot() {
        this.objectDirectory = new HashMap<>();
    }


    @Override
    public void putObject(String name, Object object) {
        this.objectDirectory.put(name, object);
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
}
