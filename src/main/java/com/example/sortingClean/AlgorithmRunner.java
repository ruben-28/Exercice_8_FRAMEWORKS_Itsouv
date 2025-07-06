package com.example.sortingClean;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Random;

/**
 * Runs several sorting algorithms on the same randomly created array. All
 * algorithms are injected using CDI so they can be easily replaced or
 * produced in different ways.
 */
@ApplicationScoped
public class AlgorithmRunner {

    @Inject
    @Quadratic
    SortingAlgorithm<Integer> quadraticAlgorithm;

    @Inject
    @Nlogn
    SortingAlgorithm<Integer> nlognAlgorithm;

    @Inject
    @Random1
    SortingAlgorithm<Integer> randomAlgorithm1;

    @Inject
    @Random2
    SortingAlgorithm<Integer> randomAlgorithm2;
    int numberOfElements = 10000;
    public void runAlgorithms(){
        Random rand = new Random();
        Integer[] ints = rand.ints(1,Integer.MAX_VALUE)
                .distinct()
                .limit(numberOfElements)
                .boxed()
                .toArray(Integer[]::new);
        Integer[] intsClone = ints.clone();
        quadraticAlgorithm.sort(intsClone);
        intsClone = ints.clone();
        nlognAlgorithm.sort(intsClone);
        intsClone = ints.clone();
        randomAlgorithm1.sort(intsClone);
        intsClone = ints.clone();
        randomAlgorithm2.sort(intsClone);
    }

    // Algorithms are provided by CDI producers so there is no need for this
    // class to create them manually.
}
