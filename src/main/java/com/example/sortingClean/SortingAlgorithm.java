package com.example.sortingClean;


public interface SortingAlgorithm <T extends Comparable<T>>{
    void sort(T[] array);
}
