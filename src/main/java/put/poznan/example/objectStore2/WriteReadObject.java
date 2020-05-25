package put.poznan.example.objectStore2;

import put.poznan.persistent.FileHeap;
import put.poznan.persistent.Heap;
import put.poznan.persistent.Root;
import put.poznan.persistent.Transaction;

import java.nio.file.Paths;


class WriteReadObject {
    public static void main(String[] args) {
        final Heap heap = new FileHeap(Paths.get("employeeHeap.pool"));
        heap.open();

        Root root = heap.getRoot();
        Employee emp = root.getObject("emp", Employee.class);

        if (emp == null) {
            emp = new Employee(1L, "John", "Doe", "john.doe@company.com");
            final Employee e = emp;
            Transaction.run(heap, () -> {
                root.putObject("emp", e);
            });
            for (int i = 0; i < 10; i++) {
                int id = i;
                Employee myEmp = new Employee(id, "name" + id, "surname" + id, "name.surname" + id + "@company.net");
                Transaction.run(heap, () -> {
                    root.putObject("myEmp" + id, myEmp);
                });
            }
            root.putObject("emp", emp);
        } else {
            System.out.println(emp.toString());
            System.out.println();
            for (int i = 0; i < 10; i++) {
                Employee e = root.getObject("myEmp" + i, Employee.class);
                System.out.println(e);
            }
        }
        heap.close();

    }
}
