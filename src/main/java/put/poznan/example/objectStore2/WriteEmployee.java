package put.poznan.example.objectStore2;

import put.poznan.persistent.FileHeap;
import put.poznan.persistent.Heap;

import java.nio.file.Paths;


class WriteEmployee {
    public static void main(String[] args) {
        final Heap heap = new FileHeap(Paths.get("employeeHeap.pool"));
        heap.open();
        Employee emp = new Employee(1L, "John", "Doe", "john.doe@company.com");
        heap.putObject("emp", emp);
        heap.close();

    }
}
