package com.modsen.test.data.algorithm;

import com.modsen.test.data.entity.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Comparator;

@Configuration
public class SortingAlgorithmsConfig {
    @Bean
    public SortingAlgorithm<Student> bubbleSortingAlgorithm() {
        return new BubbleSortingAlgorithm<>(studentComparator());
    }

    @Bean
    public SortingAlgorithm<Student> heapSortingAlgorithm() {
        return new HeapSortingAlgorithm<>(studentComparator());
    }

    @Bean
    public SortingAlgorithm<Student> mergeSortingAlgorithm() {
        return new MergeSortingAlgorithm<>(studentComparator());
    }

    @Bean
    public Comparator<Student> studentComparator() {
        return (s1, s2) -> Float.compare(s1.getMark(), s2.getMark());
    }
}
