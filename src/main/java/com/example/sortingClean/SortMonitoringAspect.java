package com.example.sortingClean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;
import java.util.LinkedHashMap;

@Aspect
public class SortMonitoringAspect {
    // Use LinkedHashMap to preserve the order in which algorithms are first seen
    private static final Map<String, Long> totalTimes = new LinkedHashMap<>();
    private static final Map<String, Integer> callCounts = new LinkedHashMap<>();
    private static long globalTime = 0;

    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* *.sort(..))")
    public void anySort() {}

    @Before("anySort()")
    public void beforeSort() {
        startTime.set(System.currentTimeMillis());
    }

    @After("anySort()")
    public void afterSort(JoinPoint joinPoint) {
        long duration = System.currentTimeMillis() - startTime.get();
        String name = joinPoint.getTarget().getClass().getSimpleName();
        totalTimes.put(name, totalTimes.getOrDefault(name, 0L) + duration);
        callCounts.put(name, callCounts.getOrDefault(name, 0) + 1);
        globalTime += duration;
    }

    @After("execution(* com.example.sortingClean.AlgorithmRunner.runAlgorithms(..))")
    public void afterAll() {
        System.out.println("Total time of running all sort functions was " + globalTime + " ms");
        System.out.println("In detail:");
        for (String name : totalTimes.keySet()) {
            System.out.println("Function sort in " + name + " ran " + callCounts.get(name) +
                    " times and took in total " + totalTimes.get(name) + " ms");
        }
    }
}
