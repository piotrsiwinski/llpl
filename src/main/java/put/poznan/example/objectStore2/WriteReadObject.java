package put.poznan.example.objectStore2;

import put.poznan.persistent.Heap;
import put.poznan.persistent.PersistentPointer;
import put.poznan.persistent.Root;
import put.poznan.persistent.Transaction;
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
        // załóżmy, że teraz to spowoduje, ze w tranzakcji się to zapisze do bazy
        final Heap heap = new VolatileHeap();

        Root root = heap.getRoot();
        Employee emp1 = root.getObject("emp", Employee.class);
        if (emp1 == null) {
            emp1 = new Employee(1L, "John", "Doe", "john.doe@company.com");
            root.putObject("emp", emp1);
        }
        System.out.println(emp1);



        /// !!!!!!!!!!!!!!!!!! Propozycja !!!

        // ZAPIS TRANZAKCYJNY
        Transaction.run(() -> {
            root.putObject("emp", emp1);
        });


//        JUTRO
        // ZAPIS DO PLIKU
        // PROSTA ALOKACJA
        // SZKIELET TRANZAKCJI


    }
}
