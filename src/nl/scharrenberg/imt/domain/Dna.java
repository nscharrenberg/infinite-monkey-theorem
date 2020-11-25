package nl.scharrenberg.imt.domain;

import java.util.ArrayList;
import java.util.Random;

public class Dna {
    private int fitness;
    private ArrayList<Character> genes;

    public Dna(int length) {
        this.fitness = 0;
        this.genes = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            this.genes.add((char) (new Random().nextInt(58) + 64));
        }
    }

    public void fitness(String target) {
        int score = 0;
        for (int i = 0; i < target.length(); i++) {
            if (this.genes.get(i).equals(target.charAt(i))) {
                score++;
            }
        }

        this.fitness = score / target.length();
    }

    public Dna crossover(Dna partner) {
        Dna child = new Dna(0);

        int midPoint = new Random().nextInt(this.genes.size() - 1);

        /*
          Take a certain amount of genes from the Current DNA and add it to the child.
         */
        for (int i = 0; i < midPoint; i++) {
            child.addGene(this.getGenes().get(i));
        }

        /*
          Take the leftover amount of genes from the partner DNA and add it to the child.
         */
        for (int i = midPoint; i < this.genes.size(); i++) {
            child.addGene(partner.getGenes().get(i));
        }

        return child;
    }

    public void mutate(double mutationRate) {
        for (int i = 0; i < this.genes.size(); i++) {
            if (Math.random() < mutationRate) {
                this.updateGene(i, (char) (new Random().nextInt(58) + 64));
            }
        }
    }

    public String getPhrase() {
        StringBuilder builder = new StringBuilder(this.getGenes().size());

        for (int i = 0; i < this.getGenes().size(); i++) {
            builder.append(this.genes.get(i));
        }

        return builder.toString();
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public ArrayList<Character> getGenes() {
        return this.genes;
    }

    public void setGenes(ArrayList<Character> genes) {
        this.genes = genes;
    }

    public void addGene(char gene) {
        this.genes.add(gene);
    }

    public void updateGene(int index, char gene) {
        this.genes.set(index, gene);
    }
}
