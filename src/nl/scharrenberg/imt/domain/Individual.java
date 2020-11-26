package nl.scharrenberg.imt.domain;

import java.util.Random;

public class Individual {
    private char[] chromosomes;
    private double fitness;

    // CONSTRUCTORS

    /**
     * Initialize Dna by passing the chromosomes
     * @param chromosomes
     */
    public Individual(char[] chromosomes) {
        this.chromosomes = chromosomes;
        this.fitness = 0;
    }

    /**
     * Initialize Dna and get random chromosomes.
     * @param targetStringLength
     * @param vocabulary
     */
    public Individual(int targetStringLength, char[] vocabulary) {
        this.chromosomes = new char[targetStringLength];
        this.fitness = 0;

        Random rand = new Random();

        for (int i = 0; i < targetStringLength; i++) {
            this.chromosomes[i] = vocabulary[rand.nextInt(vocabulary.length)];
        }
    }

    // GETTERS & SETTERS

    public char[] getChromosomes() {
        return this.chromosomes;
    }

    public void setChromosomes(char[] chromosomes) {
        this.chromosomes = chromosomes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    // LOGIC METHODS

    /**
     * Get the sentence or phrase
     *
     * @return the sentence or phrase
     */
    public String genoToPhenotype() {
        return String.valueOf(this.chromosomes);
    }

    /**
     * Two Parents (father & mother) will have two off springs each containing a mix of their parents chromosomes.
     *
     * @param father of the offspring containing the chromosomes to be inherited together with the mother (this)
     * @return the two offsprings
     */
    public Individual[] crossover(Individual father) {
        Individual brother = this.deepClone();
        Individual sister = father.deepClone();

        Random random = new Random();

        int index = random.nextInt(this.chromosomes.length - 1);

        for (int i = index; i < this.chromosomes.length; i++) {
            brother.chromosomes[i] = father.chromosomes[i];
            sister.chromosomes[i] = this.chromosomes[i];
        }

        return new Individual[]{brother, sister};
    }

    /**
     * Sometimes a mutation can happen where the chromosome changes.
     *
     * @param rate the mutation rate (possibility of a mutation happening)
     * @param vocabulary the vocabulary which is used for the chromosome to pick a new character from.
     */
    public void mutate(double rate, char[] vocabulary) {
        Random random = new Random();

        for (int i = 0; i < this.chromosomes.length; i++) {
            double randNum = random.nextDouble() * rate;

            if (randNum < (rate / 2)) {
                this.chromosomes[i] = vocabulary[random.nextInt(vocabulary.length)];
            }
        }
    }

    public void calculateFitness(String target) {
        double score = 0;
        char[] c = this.chromosomes;

        for (int i = 0; i < target.length(); i++) {
            if (this.chromosomes[i] == target.charAt(i)) {
                score = Math.pow(score + 1, 2);
            }
        }

        this.fitness = score;
    }

    public Individual deepClone() {
        char[] chromClone = new char[chromosomes.length];
        for(int i = 0; i < chromClone.length; i++) {
            chromClone[i] = chromosomes[i];
        }
        return new Individual(chromClone);
    }
}
