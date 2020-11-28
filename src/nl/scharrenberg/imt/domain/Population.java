package nl.scharrenberg.imt.domain;

import nl.scharrenberg.imt.utils.HeapSort;

import java.util.ArrayList;

public class Population {
    public static char[] VOCABULARY = new char[56];
    public static String TARGET_PHRASE = "Hello world?";
    public static int POPULATION_SIZE = 1000;
    public static double MUTATION_RATE = .01;

    private boolean isDone = false;
    private int generation = 0;
    private ArrayList<Individual> population = new ArrayList<>();
    private ArrayList<Individual> matingPool = new ArrayList<>();

    public Population() {
        getVocabulary();

        for (int i = 0; i < POPULATION_SIZE; i++) {
            this.population.add(new Individual());
        }

        this.calculateFitness();
    }

    public void calculateFitness() {
        for (Individual individual : this.population) {
            individual.calculateFitness(TARGET_PHRASE);
        }
    }

    public void select() {
        this.matingPool.clear();

        for (Individual individual : this.population) {
            double n = individual.getFitness() * 100;

            for (int i = 0; i < n; i++) {
                this.matingPool.add(individual);
            }
        }
    }

    public void generate() {
        ArrayList<Individual> newPopulation = new ArrayList<>();

        for (int i = 0; i < this.population.size(); i++) {
            Individual mother = this.matingPool.get((int) (Math.random() * this.matingPool.size()));
            Individual father = this.matingPool.get((int) (Math.random() * this.matingPool.size()));

            Individual child = mother.crossover(father);
            child.mutate(MUTATION_RATE);
            newPopulation.add(child);
        }

        this.population = newPopulation;
        this.matingPool = new ArrayList<>();
        this.generation++;
    }

    public String getBest() {
        Individual[] sorted = HeapSort.sort(this.population.toArray(new Individual[0]));
        Individual best = sorted[0];

        if (best.getFitness() >= 1) {
            this.isDone = true;
        }

        return sorted[0].genoToPhenotype();
    }

    private void getVocabulary() {
        int count = 0;

        // A to Z
        for (char c = 'A'; c <= 'Z'; c++) {
            VOCABULARY[c - 'A'] = c;
            count++;
        }

        int firstLowerCaseCount = count;

        for (char c = 'a'; c <= 'z'; c++) {
            VOCABULARY[c + firstLowerCaseCount - 'a'] = c;
            count++;
        }

        // Add spaces to vocabulary
        VOCABULARY[count] = ' ';
        count++;

        VOCABULARY[count] = '.';
        count++;

        VOCABULARY[count] = '?';
        count++;

        VOCABULARY[count] = '!';
        count++;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
