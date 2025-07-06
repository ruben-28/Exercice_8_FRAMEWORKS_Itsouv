package com.example.sortingClean;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

/**
 * Application entry point. Boots a Weld container and obtains an
 * {@link AlgorithmRunner} instance from CDI.
 */
public class MainApp {
    public static void main(String[] args) {
        Weld weld = new Weld();
        try (WeldContainer container = weld.initialize()) {
            AlgorithmRunner algorithmRunner = container.select(AlgorithmRunner.class).get();
            algorithmRunner.runAlgorithms();
        }
    }
}
