package nl.scharrenberg.imt;

import nl.scharrenberg.imt.domain.Individual;
import nl.scharrenberg.imt.domain.Population;

public class Main {


    public static void main(String[] args) {
        Population population = new Population();

        while (!population.isDone()) {
            population.select();
            population.generate();
            population.calculateFitness();

            System.out.println(String.format("The best phrase of generation %s is %s", population.getGeneration(), population.getBest()));
        }
    }


}
