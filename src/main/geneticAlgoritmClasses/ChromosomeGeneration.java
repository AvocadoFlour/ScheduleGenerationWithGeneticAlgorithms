package main.geneticAlgoritmClasses;

import main.classes.*;
import main.mockData.MockData;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.IntegerGene;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChromosomeGeneration {

    public static Chromosome setDynamicChromosome(Configuration conf, ArrayList<ClassGroup> classGroupsArrayList,
                                                  ArrayList<Course> coursesArrayList, ArrayList<Lecturer> lecturersArrayList,
                                                  ArrayList<LectureHall> lectureHallsArrayList)
            throws InvalidConfigurationException {

        int calculatedChromosomeSize = 0;
        for (ClassGroup ch : classGroupsArrayList) {
            calculatedChromosomeSize += (ch.getVocation().getCourseRequirements().size() * 5);
        }
        Gene[] defaultGene = new Gene[calculatedChromosomeSize];

        for (int i = 0; i < calculatedChromosomeSize; i++) {
            if (i % 5 == 0) {
                // ClassGroup gene
                defaultGene[i] = new IntegerGene(conf, 0, classGroupsArrayList.size()-1);
            } else if (i % 5 == 1) {
                // Course gene
                defaultGene[i] = new IntegerGene(conf, 0, coursesArrayList.size()-1);
            } else if (i % 5 == 2) {
                // Lecturer Gene
                defaultGene[i] = new IntegerGene(conf, 0, lecturersArrayList.size()-1);
            } else if (i % 5 == 3) {
                // LectureHall gene
                defaultGene[i] = new IntegerGene(conf, 0, lectureHallsArrayList.size()-1);
            } else {
                // FromH gene
                defaultGene[i] = new IntegerGene(conf, 0, 8);
            }
        }

        return new Chromosome(conf, defaultGene);
    }

    public static Chromosome setChromosomeForAllRandom(Configuration conf) throws InvalidConfigurationException {

        MockData md = new MockData();

        // - Two vocations with each 3 classes
        int chromosome_size = md.classGroups.size() * 15;
        Gene[] defaultGene = new Gene[chromosome_size];

        for (int i = 0; i < chromosome_size; i++) {
            if (i % 5 == 0) {
                // ClassGroup gene
                defaultGene[i] = new IntegerGene(conf, 0, 1);
            } else if (i % 5 == 1) {
                // Course gene
                defaultGene[i] = new IntegerGene(conf, 0, 2);
            } else if (i % 5 == 2) {
                // Lecturer Gene
                defaultGene[i] = new IntegerGene(conf, 0, 1);
            } else if (i % 5 == 3) {
                // LectureHall gene
                defaultGene[i] = new IntegerGene(conf, 0, 1);
            } else {
                // FromH gene
                defaultGene[i] = new IntegerGene(conf, 0, 8);
            }
        }
        return new Chromosome(conf, defaultGene);
    }

}
