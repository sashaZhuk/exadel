package com.modsen.test.data.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSortingAlgorithm<E> implements SortingAlgorithm<E> {
    private final Comparator<E> comparator;

    public MergeSortingAlgorithm(Comparator<E> comparator) {
        this.comparator =comparator;
    }

    @Override
    public void sort(List<E> list) {
        int size = list.size();

        if (size <= 1) {
            return;
        }

        int mid = size / 2;
        List<E> left = new ArrayList<>(list.subList(0, mid));
        List<E> right = new ArrayList<>(list.subList(mid, size));

        sort(left);
        sort(right);

        merge(list, left, right);
    }

    private void merge(List<E> list, List<E> left, List<E> right) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.size() && j < right.size()) {
            if (comparator.compare(left.get(i), right.get(j)) <= 0) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }

        while (i < left.size()) {
            list.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            list.set(k++, right.get(j++));
        }
    }

    @Override
    public String toString() {
        return "Merge sort";
    }

}
