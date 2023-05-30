package com.modsen.test.data.algorithm;

import java.util.Comparator;
import java.util.List;

public class BubbleSortingAlgorithm<E> implements SortingAlgorithm<E> {
    private final Comparator<E> comparator;

    public BubbleSortingAlgorithm(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    @Override
    public void sort(List<E> list) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                E current = list.get(j);
                E next = list.get(j + 1);
                if (comparator.compare(current, next) > 0) {
                    list.set(j, next);
                    list.set(j + 1, current);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Bubble sorting";
    }
}
