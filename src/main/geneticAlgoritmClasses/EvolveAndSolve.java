package main.geneticAlgoritmClasses;

import org.jgap.*;
import org.jgap.impl.CrossoverOperator;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.MutationOperator;

public class EvolveAndSolve {

    public static final double FITNESS_VALUE = 200;
    static final int EVOLUTION_REVOLUTIONS = 40;
    static final int POPULATION_SIZE = 100;

    public static void execute() {


        Configuration conf = new DefaultConfiguration();

        Schedule fitnessFunction = new Schedule();


        try {

            conf.setFitnessFunction(fitnessFunction);
            conf.addGeneticOperator(new CrossoverOperator(conf));
            conf.addGeneticOperator(new MutationOperator(conf));
            conf.setKeepPopulationSizeConstant(true);
            conf.setPreservFittestIndividual(true);

            Chromosome chromosome = ChromosomeGeneration.setChromosomeForAllRandom(conf);
            conf.setSampleChromosome(chromosome);
            conf.setPopulationSize(POPULATION_SIZE);

            Genotype population = Genotype.randomInitialGenotype(conf);

            long startTime = System.currentTimeMillis();
            int evolucija = 0;

            for (int i = 0; i < EVOLUTION_REVOLUTIONS; i++) {
                evolucija += 1;
                population.evolve();
                IChromosome theFittestOne = population.getFittestChromosome();

                if (theFittestOne.getFitnessValue() == FITNESS_VALUE) {
                    break;
                }

            }
            long finishTime = System.currentTimeMillis();
            System.out.println((double) (finishTime - startTime) / 1000 + " sekundi");
            IChromosome theFittestOne = population.getFittestChromosome();
            System.out.println("Najbolje rješenje pronađeno nakon: " + evolucija + " evolucija");
            System.out.println("Fitness vrijednost: " + theFittestOne.getFitnessValue() + " \n ");
            Schedule.printSchedule(new Schedule(theFittestOne));

           /* Schedule s = new Schedule();
            s.finalEval(theFittestOne);*/

        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}