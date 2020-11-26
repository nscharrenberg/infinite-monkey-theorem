package nl.scharrenberg.imt.domain;

import nl.scharrenberg.imt.utils.HeapSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Population {
    private final static double CONVERGENCE_RATE = 1;
    private double mutationRate;
    private String targetPhrase;
    private ArrayList<Individual> population;
    private ArrayList<Individual> matingPool;
    private boolean isDone;
    private int generation;
    private char[] vocabulary = new char[27];

    // CONSTRUCTORS
    public Population(String targetPhrase, double mutationRate, int populationSize) {
        this.mutationRate = mutationRate;
        this.targetPhrase = targetPhrase;
        this.population = new ArrayList<>();
        this.matingPool = new ArrayList<>();
        this.isDone = false;
        this.generation = 0;

        // Initialize available dictionary
        initVocabulary();

        for (int i = 0; i < populationSize; i++) {
            this.addIndividualToPopulation(new Individual(targetPhrase.length(), vocabulary));
        }

        this.calculateFitness();
    }

    // GETTERS & SETTERS

    public ArrayList<Individual> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Individual> population) {
        this.population = population;
    }

    public ArrayList<Individual> getMatingPool() {
        return matingPool;
    }

    public void setMatingPool(ArrayList<Individual> matingPool) {
        this.matingPool = matingPool;
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

    // LOGIC METHODS

    public void addIndividualToPopulation(Individual individual) {
        this.population.add(individual);
    }

    public void addIndividualToMatingPool(Individual individual) {
        this.matingPool.add(individual);
    }

    private void initVocabulary() {
        for (char i = 'A'; i <= 'Z'; i++) {
            vocabulary[i - 'A'] = i;
        }

        // Also allow spaces
        vocabulary[26] = ' ';
    }

    public Individual getBest() {
        Individual[] sorted = HeapSort.sort(this.population.toArray(new Individual[0]));
        this.setPopulation(new ArrayList<Individual>(Arrays.asList(sorted)));

        if (sorted[0].getFitness() > CONVERGENCE_RATE) {
            this.setDone(true);
        }

        return sorted[0];
    }

    public void calculateFitness() {
        for (Individual individual : this.population) {
            individual.calculateFitness(targetPhrase);
        }
    }

    public void selection() {
        this.matingPool.clear();

        for (Individual individual : this.population) {
            double n = individual.getFitness();

            for (int i = 0; i < n; i++) {
                this.addIndividualToMatingPool(individual);
            }
        }
    }

    public void generate() {
        ArrayList<Individual> nextGeneration = new ArrayList<>();

        for (int i = 0; i < this.population.size(); i++) {
            Individual[] parents = randomChoices(this.getMatingPool(), 2);

            Individual mother = parents[0];
            Individual father = parents[1];

            Individual[] children = mother.crossover(father);
            Individual brother = children[0];
            Individual sister = children[1];

            brother.mutate(mutationRate, vocabulary);
            sister.mutate(mutationRate, vocabulary);

            nextGeneration.add(brother);
            nextGeneration.add(sister);
        }

        this.population = nextGeneration;
        generation++;
    }

    private Individual[] randomChoices(ArrayList<Individual> individuals, int k) {
        int n = individuals.size();

        Individual[] chosen = new Individual[k];

        for (int i = 0; i < k; i++) {
            chosen[i] = individuals.get(new Random().nextInt(n));
        }

        return chosen;
    }
}
