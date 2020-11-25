package nl.scharrenberg.imt.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Population {
    private final double MUTATION_RATE;
    private final String TARGET_STRING;
    private ArrayList<Dna> population;
    private ArrayList<Dna> matingPool;
    private boolean finished;
    private double perfectScore;
    private int generation;

    public Population() {
        this.TARGET_STRING = "";
        this.MUTATION_RATE = 0.0;
        this.generation = 0;
        this.perfectScore = 0.0;
        this.finished = false;
    }

    public Population(String target, double mutationRate, int populationSize) {
        this.MUTATION_RATE = mutationRate;
        this.TARGET_STRING = target;

        this.population = new ArrayList<>(populationSize);
        this.matingPool = new ArrayList<>();

        for (int i = 0; i < populationSize; i++) {
            this.addDnaToPopulation(new Dna(TARGET_STRING.length()));
        }

        this.calculateFitness();
        this.finished = false;
        this.perfectScore = 1;
    }

    public void calculateFitness() {
        for (Dna dna : this.getPopulation()) {
            dna.fitness(TARGET_STRING);
        }
    }

    public void selection() {
        this.matingPool.clear();

        for (Dna dna : this.getPopulation()) {
            int n = (int) dna.getFitness() * 100;

            for (int i = 0; i < n; i++) {
                this.addDnaToMatingPool(dna);
            }
        }

    }

    public void generate() {
        for (int i = 0; i < this.getPopulation().size(); i++) {
            Dna[] parents = randomChoices(this.getMatingPool(), 2);

            Dna mother = parents[0];
            Dna father = parents[1];

            Dna child = father.crossover(mother);
            child.mutate(MUTATION_RATE);
            this.population.add(child);
        }

        generation++;
    }

    public String getBest() {
        int record = 0;
        int index = 0;
        for (int i = 0; i < this.getPopulation().size(); i++) {
            if (this.population.get(i).getFitness() > record) {
                index = i;
            }
        }

        if (record == perfectScore) {
            this.finished = true;
        }

        return this.population.get(index).getPhrase();
    }

    public void addDnaToPopulation(Dna dna) {
        this.population.add(dna);
    }

    public ArrayList<Dna> getPopulation() {
        return this.population;
    }

    public void addDnaToMatingPool(Dna dna) {
        this.matingPool.add(dna);
    }

    public ArrayList<Dna> getMatingPool() {
        return this.matingPool;
    }

//    private Dna randomChoices(ArrayList<Dna> items) {
//        double newWeight = 0.00;
//        double weight = items.size() / 100.00;
//        for (Dna item : items) {
//            newWeight += weight;
//        }
//
//        double r = Math.random() * newWeight;
//        double countWeight = 0.00;
//
//        for (Dna item : items) {
//            countWeight += weight;
//
//            System.out.println(String.format("%s; %s; %s", countWeight, weight, r));
//
//            if (countWeight >= r) {
//                return item;
//            }
//        }
//
//        return null;
//    }

    private Dna[] randomChoices(ArrayList<Dna> items, int k) {
        int n = items.size();

        n += 0.0;

        Dna[] chosen = new Dna[k];

        for (int i = 0; i < k; i++) {
            chosen[i] = items.get((int) Math.floor(Math.random() * n));
        }

        return chosen;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
