package com.modsen.test.data.algorithm;

import java.util.Comparator;
import java.util.List;

public class HeapSortingAlgorithm<E> implements SortingAlgorithm<E> {
    private final Comparator<E> comparator;

    public HeapSortingAlgorithm(Comparator<E> comparator) {
        this.comparator =comparator;
    }

    @Override
    public void sort(List<E> list) {
        int size = list.size();

        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(list, size, i);
        }

        // Heap sort
        for (int i = size - 1; i >= 0; i--) {
            E temp = list.get(0);
            list.set(0, list.get(i));
            list.set(i, temp);

            heapify(list, i, 0);
        }
    }

    private void heapify(List<E> list, int size, int root) {
        int largest = root;
        int leftChild = 2 * root + 1;
        int rightChild = 2 * root + 2;

        if (leftChild < size && comparator.compare(list.get(leftChild), list.get(largest)) > 0) {
            largest = leftChild;
        }

        if (rightChild < size && comparator.compare(list.get(rightChild), list.get(largest)) > 0) {
            largest = rightChild;
        }

        if (largest != root) {
            E temp = list.get(root);
            list.set(root, list.get(largest));
            list.set(largest, temp);

            heapify(list, size, largest);
        }
    }

    @Override
    public String toString() {
        return "Heap sort";
    }
}
