package put.poznan.example.employeeStore;

import put.poznan.persistent.FileHeap;
import put.poznan.persistent.Heap;

import java.nio.file.Paths;

public class ReadEmployee {
    public static void main(String[] args) {
        final Heap heap = new FileHeap(Paths.get("employeeHeap.pool"));
        heap.open();
        Employee emp = heap.getObject("emp", Employee.class);

        System.out.println(emp);

        heap.close();
    }
}
