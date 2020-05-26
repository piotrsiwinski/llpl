package put.poznan.example.employeeStore;

import put.poznan.persistent.FileHeap;
import put.poznan.persistent.Heap;

import java.nio.file.Paths;

class DeleteEmployee {
    public static void main(String[] args) {
        Heap heap = new FileHeap(Paths.get("employeeHeap.pool"));
        Employee emp = heap.getObject("emp", Employee.class);
        heap.freeObject("emp");



        Employee shouldBeDeleted = heap.getObject("emp", Employee.class);
        assert shouldBeDeleted == null;
    }
}
