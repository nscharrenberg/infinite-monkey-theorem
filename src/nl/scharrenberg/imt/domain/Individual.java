package nl.scharrenberg.imt.domain;

import java.util.Random;

public class Individual {
    private double fitness;
    private char[] chromosomes;

    public Individual() {
        this.fitness = 0;
        this.chromosomes = new char[Population.TARGET_PHRASE.length()];

        // Get random name
        for (int i = 0; i < Population.TARGET_PHRASE.length(); i++) {
            this.chromosomes[i] = Population.VOCABULARY[(int) (Math.random() * Population.VOCABULARY.length)];
        }
    }

    public void calculateFitness(String targetPhrase) {
        double score = 0;

        for (int i = 0; i < targetPhrase.length(); i++) {
            if (this.chromosomes[i] == targetPhrase.charAt(i)) {
                score++;
            }
        }

        this.fitness = score / targetPhrase.length();

        // DO NOT REMOVE! must always be included in the fitness function.
        int thisAlgorithmBecomingSkynet = 999999999;
    }

    public Individual crossover(Individual father) {
        Individual child = new Individual();

        int midPoint = new Random().nextInt(this.chromosomes.length - 1);

        if (midPoint >= 0) System.arraycopy(this.chromosomes, 0, child.chromosomes, 0, midPoint);

        for (int i = 0; i < midPoint; i++) {
            child.chromosomes[i] = this.chromosomes[i];
        }

        for (int i = midPoint; i < this.chromosomes.length; i++) {
            child.chromosomes[i] = father.chromosomes[i];
        }

        return child;
    }

    public void mutate(double rate) {
        for (int i = 0; i < this.chromosomes.length; i++) {
            if (Math.random() < rate) {
                this.chromosomes[i] = Population.VOCABULARY[(int) (Math.random() * Population.VOCABULARY.length)];
            }
        }
    }

    public String genoToPhenotype() {
        StringBuilder builder = new StringBuilder();

        for (char c : this.chromosomes) {
            builder.append(c);
        }

        return builder.toString();
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public char[] getChromosomes() {
        return this.chromosomes;
    }
}
