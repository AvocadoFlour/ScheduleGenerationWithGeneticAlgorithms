package main.geneticAlgoritmClasses;

import main.classes.ClassGroup;
import main.classes.Course;
import main.classes.Lecture;
import org.jgap.*;
import main.mockData.MockData;

import java.util.ArrayList;
import java.util.List;

public class Schedule extends FitnessFunction implements IChromosome {

    MockData md = new MockData();

    public Schedule() {
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

    // A method for visualising a class instance in the console.
    public static void printSchedule(Schedule schedule) {

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


    // A constructor which generates a class instances from a chromosome (class instance).
    public Schedule(IChromosome chromosome) {

        int lecturer = 0;
        int classGroup = 0;
        int course;
        int lectureHall = 0;
        Course courseObject = null;
        int lectureLength;
        Long j = 0L;

        for (int i = 0; i < chromosome.size(); i++) {

            if (i % 5 == 0) {
                // ClassGroup gene
                classGroup = (Integer) chromosome.getGene(i).getAllele();

            } else if (i % 5 == 1) {
                // Course gene
                course = (Integer) chromosome.getGene(i).getAllele();
                courseObject = md.courses.get(course);

            } else if (i % 5 == 2) {
                // Lecturer Gene
                lecturer = (Integer) chromosome.getGene(i).getAllele();

            } else if (i % 5 == 3) {
                // LectureHall gene
                lectureHall = (Integer) chromosome.getGene(i).getAllele();

            } else {
                // FromH gene
                Integer fromH = (Integer) chromosome.getGene(i).getAllele();

                // Setting the end time of each course to be what is defined as the necessary lecture length depending on the vocation admitting it.
                lectureLength = md.classGroups.get(classGroup).getVocation().getCourseRequirements().get(courseObject);
                int toH = fromH + lectureLength;

                // // Setting the lecturer to be a lecturer that holds the qualification to hold a particular class.
                // // To-be implemented.

                this.lectures.add(new Lecture(j, md.lecturers.get(lecturer), courseObject, md.classGroups.get(classGroup), md.lectureHalls.get(lectureHall), fromH, toH));
            }
        }
    }

    @Override
    protected double evaluate(IChromosome chromosome) {

        MockData md1 = new MockData();
        List<ClassGroup> enrolledClassGroups = new ArrayList<>(md1.classGroups);

        Schedule test = new Schedule(chromosome);

        double fitnessValue = EvolveAndSolve.FITNESS_VALUE;

        for (int i = 0; i < test.lectures.size(); i++) {

            Lecture lecture = test.lectures.get(i);

            // With the bellow lines of code the course which is scheduled for a lecture is being removed from the list defining the courses which are needed to be
            // scheduled in order for the classGroup's vocation-dependent course attendance requirements to be satisfied.
            int c1 = -1;
            for (ClassGroup cg : enrolledClassGroups) {
                if (cg.getId() == lecture.getClassGroup().getId()) {
                    c1 = enrolledClassGroups.indexOf(cg);
                    break;
                }
            }

            enrolledClassGroups.get(c1).getVocation().getCourseRequirements().remove(lecture.getCourse());

            // Checking if lectureHall has sufficient capacity.
            if (!(test.lectures.get(i).getLectureHall().getCapacity() >= test.lectures.get(i).getClassGroup().getNumberOfStudents())) {
                // DEDUCT POINTS: The appointed lectureHall does not have the minimum capacity required to hold the entire classGroup
                fitnessValue -= 10;
            }

            // Checking for issues with overlapping
            for (int j = 0; j < test.lectures.size(); j++) {

                int s1 = test.lectures.get(i).getFromH();
                int e1 = test.lectures.get(i).getToH();
                int s2 = test.lectures.get(j).getFromH();
                int e2 = test.lectures.get(j).getToH();

                // Finding lectures which have overlapping time-frames
                if (!((s1 <= e2 && e1 <= s2) || (s1 >= e2 && e1 >= s2)) && test.lectures.get(i) != test.lectures.get(j)) {

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
                        // DEDUCT POINTS: The appointed classGroup has two lectures scheduled during overlapping timeframes
                        fitnessValue -= 1;
                    }
                }
            }
        }

        // Check if every classGroup has the needed amount of lectures scheduled
        for (ClassGroup cg : enrolledClassGroups) {
            // System.out.println("BROJ PREOSTALIH ODNOSNO NEIPLANIRANIH PREDAVANJA KOJA JE NUŽNO ISPLANIRATI ZA RAZRED " + cg.getClassIdentification() + " " +  cg.getVocation().getCourseRequirements().size());
            fitnessValue -= cg.getVocation().getCourseRequirements().size();
        }


        return fitnessValue;
    }

    public void finalEval(IChromosome chromosome) {

        List<ClassGroup> enrolledClassGroups = new ArrayList<>(md.classGroups);

        for (ClassGroup cg : enrolledClassGroups) {
            System.out.println(cg.getVocation().getCourseRequirements());
        }

        Schedule test = new Schedule(chromosome);

        double fitnessValue = 100;

        for (int i = 0; i < test.lectures.size(); i++) {

            // With the bellow lines of code the course which is scheduled for a lecture is being removed from the list defining the courses which are needed to be
            // scheduled in order for the classGroup's vocation-dependent course attendance requirements to be satisfied.
            int c1 = -1;
            for (ClassGroup cg : enrolledClassGroups) {
                System.out.println("CG ID: " + cg.getId() + " , itterator id: " + test.lectures.get(i).getClassGroup().getId());
                if (cg.getId() == test.lectures.get(i).getClassGroup().getId()) {
                    c1 = enrolledClassGroups.indexOf(cg);
                    break;
                }
            }

            enrolledClassGroups.get(c1).getVocation().getCourseRequirements().remove(test.lectures.get(i).getCourse());

            // Checking if lectureHall has sufficient capacity.
            if (!(test.lectures.get(i).getLectureHall().getCapacity() >= test.lectures.get(i).getClassGroup().getNumberOfStudents())) {
                // DEDUCT POINTS: The appointed lectureHall does not have the minimum capacity required to hold the entire classGroup
                fitnessValue -= 10;
            }
            for (int j = 0; j < test.lectures.size(); j++) {

                int s1 = test.lectures.get(i).getFromH();
                int e1 = test.lectures.get(i).getToH();
                int s2 = test.lectures.get(j).getFromH();
                int e2 = test.lectures.get(j).getToH();

                // Finding lectures which have overlapping time-frames
                if (!((s1 <= e2 && e1 <= s2) || (s1 >= e2 && e1 >= s2)) && test.lectures.get(i) != test.lectures.get(j)) {

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
                        // DEDUCT POINTS: The appointed classGroup has two lectures scheduled during overlapping timeframes
                        fitnessValue -= 1;
                    }
                }
            }
        }

        // Check if every classGroup has the needed amount of lectures scheduled
        for (ClassGroup cg : enrolledClassGroups) {
            System.out.println("BROJ PREOSTALIH ODNOSNO NEIPLANIRANIH PREDAVANJA KOJA JE NUŽNO ISPLANIRATI ZA RAZRED " + cg.getClassIdentification() + " " + cg.getVocation().getCourseRequirements().size());
            fitnessValue -= cg.getVocation().getCourseRequirements().size();
        }

        System.out.println(fitnessValue);
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
