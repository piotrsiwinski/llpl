package put.poznan.example.stringstore;

import put.poznan.persistent.*;

class Writer {

    public static void main(String[] args) {
        Heap heap = new VolatileHeap(); // should be created from file
        Root root = heap.getRoot();


        final String helloString = "Hello persistent programming";
        PersistentPointer<String> pointer = heap.makePersistent("helloString", helloString);

        String helloString1 = root.getObject("helloString", String.class);

        System.out.println(helloString);


        // this should be now persistent....
    }
}
