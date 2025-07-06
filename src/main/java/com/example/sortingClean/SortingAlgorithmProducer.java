package com.example.sortingClean;

import jakarta.enterprise.inject.Produces;
import java.util.Random;

/**
 * CDI producers for the various sorting algorithms used by {@link AlgorithmRunner}.
 */
public class SortingAlgorithmProducer {

    @Produces
    @Quadratic
    public SortingAlgorithm<Integer> quadraticAlgorithm() {
        return new BubbleSort();
    }

    @Produces
    @Nlogn
    public SortingAlgorithm<Integer> nlognAlgorithm() {
        return new QuickSort();
    }

    @Produces
    @Random1
    public SortingAlgorithm<Integer> randomAlgorithm1() {
        return makeRandomSortingAlgorithm();
    }

    @Produces
    @Random2
    public SortingAlgorithm<Integer> randomAlgorithm2() {
        return makeRandomSortingAlgorithm();
    }

    private static SortingAlgorithm<Integer> makeRandomSortingAlgorithm() {
        Random random = new Random(System.currentTimeMillis());
        return switch (random.nextInt(4)) {
            case 0 -> new QuickSort();
            case 1 -> new MergeSort();
            case 2 -> new BubbleSort();
            default -> new InsertionSort();
        };
    }
}
