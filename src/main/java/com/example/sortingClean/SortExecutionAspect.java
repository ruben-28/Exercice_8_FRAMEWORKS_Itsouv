package com.example.sortingClean;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

/**
 * Aspect that measures the execution time of any {@code sort} method and
 * reports aggregated statistics once {@link AlgorithmRunner#runAlgorithms()} is
 * finished.
 */
@Aspect
public class SortExecutionAspect {
    private static final Map<String, Long> totalTimes = new HashMap<>();
    private static final Map<String, Integer> callCounts = new HashMap<>();

    private static long globalTime = 0;
    private static final ThreadLocal<Long> startTime = new ThreadLocal<>();

    /** Pointcut matching any method named {@code sort}. */
    @Pointcut("execution(* *.sort(..))")
    public void anySort() {}

    /** Records the start time of a sort invocation. */
    @Before("anySort()")
    public void beforeSort() {
        startTime.set(System.currentTimeMillis());
    }

    /** Updates statistics after each sort invocation. */
    @After("anySort()")
    public void afterSort(JoinPoint joinPoint) {
        long duration = System.currentTimeMillis() - startTime.get();
        String name = joinPoint.getTarget().getClass().getSimpleName();
        totalTimes.put(name, totalTimes.getOrDefault(name, 0L) + duration);
        callCounts.put(name, callCounts.getOrDefault(name, 0) + 1);
        globalTime += duration;
    }

    /**
     * After all algorithms have been executed, print a summary of the runtime
     * information collected above.
     */
    @After("execution(* com.example.sortingClean.AlgorithmRunner.runAlgorithms(..))")
    public void afterAll() {
        System.out.println("Total time of running all sort functions was " + globalTime + " ms");
        System.out.println("In detail:");
        for (String name : totalTimes.keySet()) {
            System.out.println("Function sort in " + name + " ran " + callCounts.get(name)
                    + " times and took in total " + totalTimes.get(name) + " ms");
        }
    }
}
