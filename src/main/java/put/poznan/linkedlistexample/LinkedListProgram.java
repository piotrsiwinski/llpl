package put.poznan.linkedlistexample;

import put.poznan.MyHeap;
import put.poznan.MyMemoryBlock;

class LinkedListProgram {

    static private final MyHeap heap = new MyHeap();

    public static void main(String[] args) {
        PersistentLinkedList<Employee> list = new PersistentLinkedList<>(heap);

        Employee e1 = new Employee(1, "John", "Doe", "john.doe@company.com");
        Employee e2 = new Employee(2, "Jane", "Roe", "jane.roe@company.com");
        Employee e3 = new Employee(3, "Jan", "Markovic", "jan.markovic@company.com");
        Employee e4 = new Employee(4, "Marie", "Novakova", "marie.novakova@company.com");

        list.add(e1);
        list.add(e2);
        list.add(e3);
        list.add(e4);

        MyMemoryBlock block1 = heap.allocateMemoryBlock(e1);
        MyMemoryBlock block2 = heap.allocateMemoryBlock(e2);
        MyMemoryBlock block3 = heap.allocateMemoryBlock(e3);
        MyMemoryBlock block4 = heap.allocateMemoryBlock(e4);


    }

}
