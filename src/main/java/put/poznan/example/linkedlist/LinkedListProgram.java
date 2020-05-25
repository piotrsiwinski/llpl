package put.poznan.example.linkedlist;

import put.poznan.persistent.Heap;
import put.poznan.persistent.VolatileHeap;

import java.util.LinkedList;

/**
 * Simple LinkedList Example. Everything is stored in memory
 */
class LinkedListProgram {
    public static void main(String[] args) {
        Heap heap = new VolatileHeap();

        Employee e1 = new Employee(1, "John", "Doe", "john.doe@company.com");
        Employee e2 = new Employee(2, "Jane", "Roe", "jane.roe@company.com");
        Employee e3 = new Employee(3, "Jan", "Markovic", "jan.markovic@company.com");
        Employee e4 = new Employee(4, "Marie", "Novakova", "marie.novakova@company.com");

        LinkedList<Employee> list = new LinkedList<>();


//        PersistentPointer<LinkedList<Employee>> ptr = heap.makePersistent("employeeList", list);
//
//        System.out.println("Adding elements to linked list");
//        ptr.access(lst -> {
//            lst.add(e1);
//            lst.add(e2);
//            lst.add(e3);
//            lst.add(e4);
//        });
//
//        ptr.access(lst -> {
//            for (Object o : lst) {
//                System.out.println(o);
//            }
//        });

//        Class<? extends LinkedList> aClass = list.getClass();
//
//        PersistentPointer<LinkedList> ptr2 = heap.getRoot().getObject("employeeList", LinkedList.class);
//
//        ptr2.access(lst -> {
//            for (Object o : lst) {
//                System.out.println(o);
//            }
//        });
    }

}
