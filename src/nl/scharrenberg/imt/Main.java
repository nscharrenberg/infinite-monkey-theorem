package nl.scharrenberg.imt;

import nl.scharrenberg.imt.domain.Population;

public class Main {
    public final static String TARGET_STRING = "hello word";
    public final static int POPULATION_SIZE = 1000;
    public final static double MUTATION_RATE = .2;
    public final static int MAX_GENERATIONS = 40000;

    public static void main(String[] args) {
        Population population = new Population(TARGET_STRING, MUTATION_RATE, POPULATION_SIZE);

        while (!population.isDone()) {
            population.selection();
            population.generate();
            population.calculateFitness();

            System.out.println(String.format("Best: %s in generation %s", population.getBest().genoToPhenotype(), population.getGeneration()));

            if (population.getGeneration() == 5 || population.getGeneration() == 10 | population.getGeneration() == 13 || population.getGeneration() == 15) {
                System.out.println("Milestone");
            }

            if (population.isDone()) {
                break;
            }
        }
    }


}
