package main.JavaFX.lecturers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.JavaFX.lecturesHalls.LectureHallInputController;
import main.classes.Lecturer;

import java.io.IOException;
import java.sql.SQLException;

public class LecturersTabController {
    @FXML
    private TableView<Lecturer> lecturersTableView;
    @FXML
    private Button addNewLecturerButton;
    @FXML
    private Button deleteLecturerButton;

    private LecturersDataModel lecturersDataModel;

    private TableColumn<Lecturer, String> lecturerNameColumn;
    private TableColumn<Lecturer, Integer> lecturerLastNameColumn;
    private TableColumn<Lecturer, String> lecturerQualificationsColumn;

    public void initialize() throws SQLException {
        initModel();
    }

    public void openLecturerInputWindow(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lecturerInput.fxml"));
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        LecturersInputController lecturersInputController = loader.getController();
        lecturersInputController.initModel(lecturersDataModel);
        stage.setTitle("Lecturer Input");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        // Serves the purpose of the new window being imposed over the other window
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();

    }

    public void initModel() throws SQLException {
        // ensure model is only set once:
        if (this.lecturersDataModel != null) {
            throw new IllegalStateException("Lecturers model can only be initialized once");
        }
        this.lecturersDataModel =new LecturersDataModel();
        lecturerNameColumn = new TableColumn<>("Name");
        lecturerLastNameColumn = new TableColumn<>("Last name");
        lecturerQualificationsColumn = new TableColumn<>("Qualifications");
        lecturerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lecturerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lecturerQualificationsColumn.setCellValueFactory(new PropertyValueFactory<>("qualifications"));
        lecturersTableView.getColumns().add(0, lecturerNameColumn);
        lecturersTableView.getColumns().add(1, lecturerLastNameColumn);
        lecturersTableView.getColumns().add(2, lecturerQualificationsColumn);

        lecturersTableView.getItems().clear();
        lecturersDataModel.loadLecturers();
        lecturersTableView.setItems(lecturersDataModel.getLecturersList());
    }

}