package com.mycompany.graph.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector; 
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TSPPopulation {
    private List<TspChromosome> population;
    private final int initialSize;
    public TSPPopulation(final TSPGen[] points, final int initialSize) {

        this.population = init(points, initialSize);
        this.initialSize = initialSize;

    }

    public TspChromosome getAlpha() {
        return this.population.get(0);
    }

    private List<TspChromosome> init(final TSPGen[] points, final int initialSize) {
        final List<TspChromosome> eden = new ArrayList<>();
        for (int i =0; i<initialSize; i++) {
            
                final TspChromosome chromosome = TspChromosome.create(points);
                eden.add(chromosome);
            
        }
        return eden;
    }

    public void update() {
        doCrossOver();
        doMutation();
        doSpawn();
        doSelection();

    }


    public void doSelection() {
        // Remove null values from the population list
        population = population.stream()
                               .filter(Objects::nonNull)
                               .collect(Collectors.toList());

        // Sort the population, handling any potential null values in the Comparator
        population.sort(Comparator.comparingDouble(chromosome -> {
            if (chromosome == null) {
                return Double.POSITIVE_INFINITY; // or some other default value
            }
            return chromosome.calculateDistance();
        }));

        // Limit the size of the population to initialSize
        population = population.stream().limit(initialSize).collect(Collectors.toList());
    }

    private void doSpawn() {
        IntStream.range(0, 1000).forEach(e -> this.population.add(TspChromosome.create(TSPUtils.CITIES)));
    }


    private void doMutation( ) {
        final List<TspChromosome> newPopulation = new ArrayList<>();
        for (int i =0; i<this.population.size()/10; i++) {
            TspChromosome mutation = this.population.get(TSPUtils.randomIndex(this.population.size())).mutate();
            newPopulation.add(mutation);
        }
        this.population.addAll(newPopulation);
    }

    private void doCrossOver() {
        final List<TspChromosome> newPopulation = new ArrayList<>();
        for (final TspChromosome chromosome: this.population) {
            final TspChromosome partner = getCrossOverPartner(chromosome);
            newPopulation.addAll(Arrays.asList(chromosome.crossOver(partner)));
        }
        this.population.addAll(newPopulation);

    }
    private TspChromosome getCrossOverPartner(final TspChromosome chromosome) {
        TspChromosome partner = this.population.get(TSPUtils.randomIndex(this.population.size()));
        while ( chromosome == partner) {
            partner = this.population.get(TSPUtils.randomIndex(this.population.size()));
                    
        }

        return partner;

    }
}
