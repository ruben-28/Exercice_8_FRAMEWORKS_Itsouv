package com.example.sortingClean;

import org.aspectj.lang.annotation.*;
import org.aspectj.lang.JoinPoint;
import java.util.*;

@Aspect
public class SortLoggerAspect {

    // Compteurs par classe
    private Map<String, Integer> callCount = new HashMap<>();
    // Temps total par classe
    private Map<String, Long> totalTime = new HashMap<>();
    // Temps début par thread (pour supporter plusieurs appels en //)
    private Map<Thread, Long> startTime = new HashMap<>();
    // Temps total global
    private long totalSortTime = 0;

    // Avant chaque appel à sort() de n'importe quel SortingAlgorithm
    @Before("execution(* SortingAlgorithm+.sort(..))")
    public void beforeSort(JoinPoint jp) {
        startTime.put(Thread.currentThread(), System.currentTimeMillis());
    }

    // Après chaque appel à sort() de n'importe quel SortingAlgorithm
    @After("execution(* SortingAlgorithm+.sort(..))")
    public void afterSort(JoinPoint jp) {
        long end = System.currentTimeMillis();
        long start = startTime.getOrDefault(Thread.currentThread(), end);
        long duration = end - start;

        String className = jp.getTarget().getClass().getSimpleName();

        // Met à jour le nombre d'appels
        callCount.put(className, callCount.getOrDefault(className, 0) + 1);

        // Met à jour le temps cumulé
        totalTime.put(className, totalTime.getOrDefault(className, 0L) + duration);

        // Met à jour le temps total global
        totalSortTime += duration;
    }

    // Après l'exécution de la méthode main (quand tous les sorts sont terminés)
    @After("execution(public static void main(String[]))")
    public void afterAllSorts() {
        System.out.println("Total time of running all sort functions was " + totalSortTime + " ms");
        System.out.println("In detail:");
        for (String className : callCount.keySet()) {
            int count = callCount.get(className);
            long time = totalTime.get(className);
            System.out.println("Function sort in " + className + " ran " + count + " times and took in total " + time + " ms");
        }
    }
}
