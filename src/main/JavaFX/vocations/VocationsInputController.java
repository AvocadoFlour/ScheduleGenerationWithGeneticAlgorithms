package main.JavaFX.vocations;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.JavaFX.courses.CoursesDataModel;
import main.classes.Course;
import main.classes.Vocation;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VocationsInputController {

    private CoursesDataModel coursesDataModel;
    private VocationsDataModel vocationsDataModel;
    @FXML
    private TableView<Course> coursesTableView;
    @FXML
    private TableView<CourseRequirementsDTO> addedCourseRequirementsTableView;
    @FXML
    private Button removeCourseRequirementButton;
    @FXML
    private Button addCourseRequirementButton;
    @FXML
    private RadioButton radioButton1h;
    @FXML
    private RadioButton radioButton3h;
    @FXML
    private RadioButton radioButton2h;
    @FXML
    private Button createButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField vocationNameTextField;
    private TableColumn<Course, String> courseNameColumn;
    private TableColumn<CourseRequirementsDTO, Integer> courseRequirementsNameColumn;
    private TableColumn<CourseRequirementsDTO, Integer> courseRequirementsHoursColumn;
    private ToggleGroup toggleGroup = new ToggleGroup();
    private ObservableList<CourseRequirementsDTO> courseRequirementsDTOObservableList;
    private List<CourseRequirementsDTO> courseRequirementsDTOList = new ArrayList<>();

    public void initialize() throws SQLException {
        radioButton1h.setUserData(1);
        radioButton2h.setUserData(2);
        radioButton3h.setUserData(3);
        radioButton1h.setToggleGroup(toggleGroup);
        radioButton2h.setToggleGroup(toggleGroup);
        radioButton3h.setToggleGroup(toggleGroup);
        coursesDataModel = new CoursesDataModel();
        courseNameColumn = new TableColumn<>("Name");
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseNameColumn.prefWidthProperty().bind(coursesTableView.widthProperty().multiply(0.98));
        courseNameColumn.setResizable(false);
        coursesTableView.getColumns().add(0, courseNameColumn);
        coursesTableView.getItems().clear();
        coursesDataModel.loadCourses();
        coursesTableView.setItems(coursesDataModel.getCoursesList());


        // Course requirements table view
        courseRequirementsNameColumn = new TableColumn<>("Name");
        courseRequirementsNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseRequirementsHoursColumn = new TableColumn<>("Hours");
        courseRequirementsHoursColumn.setCellValueFactory(new PropertyValueFactory<>("courseHoursRequired"));
        addedCourseRequirementsTableView.getColumns().add(0, courseRequirementsNameColumn);
        addedCourseRequirementsTableView.getColumns().add(1, courseRequirementsHoursColumn);
        courseRequirementsNameColumn.setResizable(false);
        courseRequirementsNameColumn.prefWidthProperty().bind(addedCourseRequirementsTableView.widthProperty().multiply(0.7));
        courseRequirementsHoursColumn.setResizable(false);
        courseRequirementsHoursColumn.prefWidthProperty().bind(addedCourseRequirementsTableView.widthProperty().multiply(0.28));
        courseRequirementsHoursColumn.setStyle( "-fx-alignment: CENTER;");
        courseRequirementsDTOObservableList = FXCollections.observableList(courseRequirementsDTOList);
        addedCourseRequirementsTableView.setItems(courseRequirementsDTOObservableList);

        addCourseRequirementButton.setOnAction(actionEvent -> {
            Course courseToAdd = coursesTableView.getSelectionModel().getSelectedItem();
            int hoursRequired = (int) toggleGroup.getSelectedToggle().getUserData();
            courseRequirementsDTOObservableList.add(new CourseRequirementsDTO(courseToAdd, hoursRequired));
        });

        createButton.setOnAction(actionEvent ->
        {
            String vocationName = vocationNameTextField.getText();
            HashMap<Course, Integer> courseRequirementsMap = new HashMap<>();
            for(CourseRequirementsDTO cdto : courseRequirementsDTOList) {
                courseRequirementsMap.put(cdto.getCourse(), cdto.hoursRequired);
            }
            try {
                vocationsDataModel.addVocation(vocationName, courseRequirementsMap);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        );

    }

    public void initModel(VocationsDataModel vocationsDataModel) {
        this.vocationsDataModel = vocationsDataModel;
    }

    public static class CourseRequirementsDTO {

        private Course course;
        private int hoursRequired;

        public CourseRequirementsDTO(Course course, int hoursRequired) {
            this.course = course;
            this.hoursRequired = hoursRequired;
        }

        public Course getCourse() {
            return course;
        }

        public void setCourse(Course course) {
            this.course = course;
        }

        public int getHoursRequired() {
            return hoursRequired;
        }

        public void setHoursRequired(int hoursRequired) {
            this.hoursRequired = hoursRequired;
        }

        public StringProperty courseNameProperty() {
            StringProperty courseNameSP = new SimpleStringProperty();
            courseNameSP.set(this.course.getCourseName());
            return courseNameSP;
        }

        public IntegerProperty courseHoursRequiredProperty() {
            IntegerProperty hoursRequiredIP = new SimpleIntegerProperty();
            hoursRequiredIP.set(this.hoursRequired);
            return hoursRequiredIP;
        }

    }

}
