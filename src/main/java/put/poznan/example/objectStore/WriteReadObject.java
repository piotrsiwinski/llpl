package put.poznan.example.objectStore;

import put.poznan.persistent.Heap;
import put.poznan.persistent.Root;
import put.poznan.persistent.VolatileHeap;

/**
 * Simple implementation of writing one object to Volatile heap.
 * This example shows how to create and mutate persistent object managed by PMDJ
 * and how to read object from root.
 * This object won't be available since heap is volatile.
 * Simple example of PMDJ API
 */

class WriteReadObject {
    public static void main(String[] args) {
        final Heap heap = new VolatileHeap();

//        final Employee emp = new Employee(1L, "John", "Doe", "john.doe@company.com");
//        PersistentPointer<Employee> pointer = heap.makePersistent("emp", emp);
//
//        System.out.println(pointer.getValue());
//
//        pointer
//                .access(e -> e.setSurname("Smith"))
//                .access(e -> e.setEmail("john.smith@company.com"));
//
//        System.out.println(pointer.getValue());


        System.out.println("Reading value...\n");
        Root root = heap.getRoot();
//        PersistentPointer<Employee> emp1 = root.getObject("emp", Employee.class);

//        System.out.println(emp1.getValue());
    }
}
