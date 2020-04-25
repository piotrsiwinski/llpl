package put.poznan.linkedlistexample;

import put.poznan.MyHeap;
import put.poznan.MyMemoryBlock;

import static java.util.Objects.requireNonNull;

class PersistentLinkedList<T> {

    private Node<T> root = null;
    private final MyHeap heap;

    PersistentLinkedList(MyHeap heap) {
        this.heap = requireNonNull(heap);
    }

    public void add(T elem) {
        if (root == null) {
            root = new Node<>(heap, elem);
        } else insertAtEnd(root, elem);

    }

    private void insertAtEnd(Node<T> node, T data) {
        while (node.next != null) {
            node = node.next;
        }
        node.next = new Node<>(heap, data);
    }

    static class Node<T> {
        private T element;
        private Node<T> next;
        private MyMemoryBlock memoryBlock;

        public Node(MyHeap heap, T element) {
            this.element = element;
            this.memoryBlock = heap.allocateMemoryBlock(this);
        }
    }
}
