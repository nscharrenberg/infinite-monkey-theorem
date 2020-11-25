package nl.scharrenberg.imt;

import nl.scharrenberg.imt.domain.Population;

public class Main {
    final static String TARGET_STRING = "hello";
    final static int POPULATION_SIZE = 1000;
    final static double MUTATION_RATE = 0.01;
    final static int MAX_GENERATIONS = 40000;

    public static void main(String[] args) {
        Population population = new Population(TARGET_STRING, MUTATION_RATE, POPULATION_SIZE);

        while (!population.isFinished()) {
            population.selection();
            population.generate();
            population.calculateFitness();

            System.out.println(String.format("%s in generation %s", population.getBest(), population.getGeneration()));

            if (population.getGeneration() > MAX_GENERATIONS) {
                break;
            }
        }
    }
}
