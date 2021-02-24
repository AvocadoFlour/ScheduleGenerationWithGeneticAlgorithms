package main.geneticAlgoritmClasses;

import main.classes.*;
import main.mockData.MockData;
import org.jgap.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DynamicSchedule extends FitnessFunction implements IChromosome {

    public DynamicSchedule() {}

    public static void printSchedule(DynamicSchedule schedule) {

        String empty = "---";
        String taken = "███";
        System.out.println(" 1  2  3  4  5  6  7  8  9  10 11 12");
        for (Lecture l : schedule.lectures) {
            // System.out.println(l.getFromH() + " " +l.getToH());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < l.getFromH() - 1; i++) {
                sb.append(empty);
            }
            for (int i = 0; i < (l.getToH() - l.getFromH()); i++) {
                sb.append(taken);
            }
            for (int i = l.getToH(); i <= 12; i++) {
                sb.append(empty);
            }
            sb.append(" " + l.getClassGroup().getClassIdentification() + ", " + l.getClassGroup().getNumberOfStudents() + " students, " + l.getLectureHall().getHallCode() + " capacity: " + l.getLectureHall().getCapacity() + " " + l.getCourse().getCourseName() + " " + l.getLecturer().getName() + " " + l.getLecturer().getLastName() + ". From " + l.getFromH() + " to " + l.getToH() + "\n");
            System.out.println(sb);
        }
    }

    private List<Lecture> lectures = new ArrayList<>();

    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public DynamicSchedule(IChromosome chromosome) {

        int lecturer = 0;
        int classGroup = 0;
        int course = 0;
        int lectureHall = 0;
        int lectureLength = 0;
        Long j = 0l;

        for (int i = 0; i < chromosome.size(); i++) {

            if (i % 5 == 0) {
                // ClassGroup gene
                classGroup = (Integer) chromosome.getGene(i).getAllele();

            } else if (i % 5 == 1) {
                // Course gene
                course = (Integer) chromosome.getGene(i).getAllele();
            } else if (i % 5 == 2) {
                // Lecturer Gene
                lecturer = (Integer) chromosome.getGene(i).getAllele();

            } else if (i % 5 == 3) {
                // LectureHall gene
                lectureHall = (Integer) chromosome.getGene(i).getAllele();

            } else {
                // FromH gene
                Integer fromH = (Integer) chromosome.getGene(i).getAllele();

                /* Determining the length of the lecture:
                Because the gene's maximum value is probably a number greater than the number of courses
                that a particular classGroup the number of the gene is modulo-ed by the number of the
                courses that the classGroups has prescribed. */
                int numberOfCoursesInVocation = DynamicEvolveAndSolve.classGroupsArrayList.get(classGroup).getVocation().getCourseRequirements().size();
                int courseNumber = course % numberOfCoursesInVocation;
                HashMap<Course, Integer> temp = DynamicEvolveAndSolve.classGroupsArrayList.get(classGroup).getVocation().getCourseRequirements();
                ArrayList<Course> tempList = new ArrayList<>();
                for (Map.Entry<Course, Integer> entry : temp.entrySet()) {
                    tempList.add(entry.getKey());
                }
                Course courseObject = tempList.get(courseNumber);
                lectureLength = DynamicEvolveAndSolve.classGroupsArrayList.get(classGroup).getVocation().getCourseRequirements().get(courseObject);
                // Setting the end time of each course to be what is defined as the necessary lecture length depending on the vocation admitting it.
                int toH = fromH + lectureLength;

                // Setting the lecturer to be a lecturer that holds the qualification to hold a particular class.
                ArrayList<Lecturer> lecturerArrayList = new ArrayList<>(DynamicEvolveAndSolve.lecturersArrayList.stream().filter(
                        l -> IntStream.of(l.getQualifications()).anyMatch(x -> x == courseObject.getTeacherQualification())).collect(Collectors.toList()));
                int numberOfQualifiedLecturers = lecturerArrayList.size();
                int lecturerNumber = lecturer % numberOfQualifiedLecturers;

                this.lectures.add(new Lecture(j, lecturerArrayList.get(lecturerNumber), courseObject, DynamicEvolveAndSolve.classGroupsArrayList.get(classGroup), DynamicEvolveAndSolve.lectureHallsArrayList.get(lectureHall), fromH, toH));
            }
        }
    }

    @Override
    protected double evaluate(IChromosome chromosome) {

        DynamicSchedule test = new DynamicSchedule(chromosome);

        HashMap<String,HashMap<Course,Integer>> vocationCourseRequirements = new HashMap<>();
        //This complicated method is necessary in order to avoid copying by reference
        for(ClassGroup cg : DynamicEvolveAndSolve.classGroupsArrayList) {
            HashMap<Course, Integer> tempMap = new HashMap<>();
            for (Map.Entry<Course, Integer> entry : cg.getVocation().getCourseRequirements().entrySet()) {
                tempMap.put(entry.getKey(), entry.getValue());
            }
            vocationCourseRequirements.put(cg.getClassIdentification(), tempMap);
        }

        double fitnessValue = 500;

        for (int i = 0; i < test.lectures.size(); i++) {

            // Check if every vocation has the needed amount of courses scheduled
            if(vocationCourseRequirements.get(test.lectures.get(i).getClassGroup().getClassIdentification()).containsKey(test.lectures.get(i).getCourse())) {
                vocationCourseRequirements.get(test.lectures.get(i).getClassGroup().getClassIdentification()).remove(test.lectures.get(i).getCourse());
            } else {
                fitnessValue -= 10;
            }

            /*// Check if appointed lecturer has the necessary qualification
            int courseQualification = test.lectures.get(i).getCourse().getTeacherQualification();
            boolean contains = IntStream.of(test.lectures.get(i).getLecturer().getQualifications()).anyMatch(x -> x == courseQualification);
            if (!contains) {
                fitnessValue -= 1;
            }*/

            // Checking if lectureHall has sufficient capacity.
            if (test.lectures.get(i).getLectureHall().getCapacity() < test.lectures.get(i).getClassGroup().getNumberOfStudents()) {
                // DEDUCT POINTS: The appointed lectureHall does not have the minimum capacity required to hold the entire classGroup
                fitnessValue -= 10;
            }
            for (int j = 0; j < test.lectures.size(); j++) {

                int s1 = test.lectures.get(i).getFromH();
                int e1 = test.lectures.get(i).getToH();
                int s2 = test.lectures.get(j).getFromH();
                int e2 = test.lectures.get(j).getToH();

                // Finding lectures which have overlapping time-frames
                if (!((s1 < e2 && e1 <= s2) || (s1 >= e2 && e1 > s2)) && test.lectures.get(i) != test.lectures.get(j)) {

                    // Checking if two lectures are scheduled in a single lectureHall during overlapping time-frames.
                    if (test.lectures.get(i).getLectureHall() == test.lectures.get(j).getLectureHall()) {
                        // DEDUCT POINTS: The appointed lectureHall has two lectures scheduled during overlapping timeframes
                        fitnessValue -= 1;
                    }

                    // Checking if a lecturer has two lectures scheduled during overlapping time-frames.
                    if (test.lectures.get(i).getLecturer() == test.lectures.get(j).getLecturer()) {
                        // DEDUCT POINTS: The appointed lecturer has two lectures scheduled during overlapping timeframes
                        fitnessValue -= 1;
                    }

                    // Checking if a classGroup has two lectures scheduled during overlapping time-frames.
                    if (test.lectures.get(i).getClassGroup() == test.lectures.get(j).getClassGroup()) {
                        // DEDUCT POINTS: The appointed lecturer has two lectures scheduled during overlapping timeframes
                        fitnessValue -= 1;
                    }
                }
            }
        }
        // Check if every vocation has the needed amount of courses scheduled
        for (Map.Entry<String, HashMap<Course, Integer>> entry1 : vocationCourseRequirements.entrySet()) {
            for (Map.Entry<Course, Integer> entry2 : entry1.getValue().entrySet()) {
                fitnessValue -= 1;
            }
        }
        return fitnessValue;
    }

    public static double finalEvaluate(IChromosome chromosome) {

        DynamicSchedule test = new DynamicSchedule(chromosome);

        HashMap<String,HashMap<Course,Integer>> vocationCourseRequirements = new HashMap<>();
        //This complicated method is necessary in order to avoid copying by reference
        for(ClassGroup cg : DynamicEvolveAndSolve.classGroupsArrayList) {
            HashMap<Course, Integer> tempMap = new HashMap<>();
            for (Map.Entry<Course, Integer> entry : cg.getVocation().getCourseRequirements().entrySet()) {
                tempMap.put(entry.getKey(), entry.getValue());
            }
            vocationCourseRequirements.put(cg.getClassIdentification(), tempMap);
        }

        double fitnessValue = 500;

        for (int i = 0; i < test.lectures.size(); i++) {

            // Check if an already scheduled course was scheduled again
            if(vocationCourseRequirements.get(test.lectures.get(i).getClassGroup().getClassIdentification()).containsKey(test.lectures.get(i).getCourse())) {
                vocationCourseRequirements.get(test.lectures.get(i).getClassGroup().getClassIdentification()).remove(test.lectures.get(i).getCourse());
            } else {
                System.out.println("Grupi: " +  test.lectures.get(i).getClassGroup().getClassIdentification() + " je nastava za predmet " + test.lectures.get(i).getCourse()
                + " dodjeljena 2 puta");
                fitnessValue -= 10;
            }

            /*// Check if appointed lecturer has the necessary qualification
            int courseQualification = test.lectures.get(i).getCourse().getTeacherQualification();
            boolean contains = IntStream.of(test.lectures.get(i).getLecturer().getQualifications()).anyMatch(x -> x == courseQualification);
            if (!contains) {
                fitnessValue -= 1;
            }*/

            // Checking if lectureHall has sufficient capacity.
            if (test.lectures.get(i).getLectureHall().getCapacity() < test.lectures.get(i).getClassGroup().getNumberOfStudents()) {
                // DEDUCT POINTS: The appointed lectureHall does not have the minimum capacity required to hold the entire classGroup
                System.out.println("The appointed lecture hall has insufficient capacity");
                fitnessValue -= 1;
            }
            for (int j = 0; j < test.lectures.size(); j++) {

                int s1 = test.lectures.get(i).getFromH();
                int e1 = test.lectures.get(i).getToH();
                int s2 = test.lectures.get(j).getFromH();
                int e2 = test.lectures.get(j).getToH();

                // Finding lectures which have overlapping time-frames
                if (!((s1 < e2 && e1 <= s2) || (s1 >= e2 && e1 > s2)) && test.lectures.get(i) != test.lectures.get(j)) {

                    // Checking if two lectures are scheduled in a single lectureHall during overlapping time-frames.
                    if (test.lectures.get(i).getLectureHall() == test.lectures.get(j).getLectureHall()) {
                        // DEDUCT POINTS: The appointed lectureHall has two lectures scheduled during overlapping timeframes
                        System.out.println("Two lcetures in single lectureHall at the same time");
                        fitnessValue -= 1;
                    }

                    // Checking if a lecturer has two lectures scheduled during overlapping time-frames.
                    if (test.lectures.get(i).getLecturer() == test.lectures.get(j).getLecturer()) {
                        // DEDUCT POINTS: The appointed lecturer has two lectures scheduled during overlapping timeframes
                        System.out.println("One lecturer has two lectures at the same time. " );
                        fitnessValue -= 1;
                    }

                    // Checking if a classGroup has two lectures scheduled during overlapping time-frames.
                    if (test.lectures.get(i).getClassGroup() == test.lectures.get(j).getClassGroup()) {
                        // DEDUCT POINTS: The appointed lecturer has two lectures scheduled during overlapping timeframes
                        System.out.println("The classGroup " + test.lectures.get(i).getClassGroup().getClassIdentification() + " has two lectures " +
                                "at the same time, those lectures are: " + test.lectures.get(i).getCourse().getCourseName() +
                                " and " + test.lectures.get(j).getCourse().getCourseName());
                        fitnessValue -= 1;
                    }
                }
            }
        }

        // Check if every vocation has the needed amount of courses scheduled
        for (Map.Entry<String, HashMap<Course, Integer>> entry1 : vocationCourseRequirements.entrySet()) {
            for (Map.Entry<Course, Integer> entry2 : entry1.getValue().entrySet()) {
                System.out.println("Nedostatak predavanja");
                fitnessValue -= 1;
            }
        }


        return fitnessValue;
    }

    @Override
    public Gene getGene(int i) {
        return null;
    }

    @Override
    public Gene[] getGenes() {
        return new Gene[0];
    }

    @Override
    public void setGenes(Gene[] genes) throws InvalidConfigurationException {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void setFitnessValue(double v) {

    }

    @Override
    public void setFitnessValueDirectly(double v) {

    }

    @Override
    public double getFitnessValue() {
        return 0;
    }

    @Override
    public double getFitnessValueDirectly() {
        return 0;
    }

    @Override
    public void setIsSelectedForNextGeneration(boolean b) {

    }

    @Override
    public boolean isSelectedForNextGeneration() {
        return false;
    }

    @Override
    public void setConstraintChecker(IGeneConstraintChecker iGeneConstraintChecker) throws InvalidConfigurationException {

    }

    @Override
    public void setApplicationData(Object o) {

    }

    @Override
    public Object getApplicationData() {
        return null;
    }

    @Override
    public void cleanup() {

    }

    @Override
    public Configuration getConfiguration() {
        return null;
    }

    @Override
    public void increaseAge() {

    }

    @Override
    public void resetAge() {

    }

    @Override
    public void setAge(int i) {

    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public void increaseOperatedOn() {

    }

    @Override
    public void resetOperatedOn() {

    }

    @Override
    public int operatedOn() {
        return 0;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public String getUniqueID() {
        return null;
    }

    @Override
    public void setUniqueIDTemplate(String s, int i) {

    }

    @Override
    public String getUniqueIDTemplate(int i) {
        return null;
    }

    @Override
    public Object clone() {
        return null;
    }
}
