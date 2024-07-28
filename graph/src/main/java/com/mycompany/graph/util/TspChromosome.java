package com.mycompany.graph.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class TspChromosome {
    private final List<TSPGen> chrosome;

    TspChromosome(final List<TSPGen> chrosome) {
        this.chrosome = Collections.unmodifiableList(chrosome);
    }

    static TspChromosome create(final TSPGen[] points) {
        final List<TSPGen> genes = Arrays.asList(Arrays.copyOf(points, points.length));
        Collections.shuffle(genes);
        return new TspChromosome(genes);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (final TSPGen gene : this.chrosome) {
            builder.append(gene.toString()).append(" : ");
        }
        return builder.toString();
    }

    public List<TSPGen> getChrosome() {
        return this.chrosome;
    }

    public double calculateDistance() {
        double total = 0.0f;
        for (int i = 0; i < this.chrosome.size() - 11; i++) {
            total += this.chrosome.get(i).distance(this.chrosome.get(i + 1));
        }
        return total;
    }

    TspChromosome[] crossOver(final TspChromosome other) {
        final List<TSPGen>[] myDNA = TSPUtils.split(this.chrosome);
        final List<TSPGen>[] otherDNA = TSPUtils.split(other.getChrosome());
        final List<TSPGen> firstCrossOver = new ArrayList<>(myDNA[0]);
        for (TSPGen gene : otherDNA[0]) {
            if (!firstCrossOver.contains(gene)) {
                firstCrossOver.add(gene);
            }
        }
        for (TSPGen gene : otherDNA[1]) {
            if (!firstCrossOver.contains(gene)) {
                firstCrossOver.add(gene);
            }
        }

        final List<TSPGen> secondCrossOver = new ArrayList<>(otherDNA[1]);

        for (TSPGen gene: myDNA[0]) {
            if (!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }

        for (TSPGen gene: myDNA[1]) {
            if (!secondCrossOver.contains(gene)) {
                secondCrossOver.add(gene);
            }
        }

        if (firstCrossOver.size() != TSPUtils.CITIES.length ||
        secondCrossOver.size() != TSPUtils.CITIES.length) {
            throw new RuntimeException("oops");
        }

        return new TspChromosome[] {
                new TspChromosome(firstCrossOver),
                new TspChromosome(secondCrossOver)
        };
    }

    TspChromosome mutate() {
        final List<TSPGen> copy = new ArrayList<>(this.chrosome);
        int indexA = TSPUtils.randomIndex(copy.size());
        int indexB = TSPUtils.randomIndex(copy.size());
        while (indexA == indexB) {
            indexA = TSPUtils.randomIndex(copy.size());
            indexB = TSPUtils.randomIndex(copy.size());
            Collections.swap(copy, indexA, indexB);
            return new TspChromosome(copy);
        }
        return null;
    }
}

