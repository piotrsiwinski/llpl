package put.poznan.persistent;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHeap /*implements Heap*/ {
    private final Path path;

    public FileHeap(String path) {
        this(Paths.get(path));
    }

    public FileHeap(Path path) {
        this.path = path;
    }

}
