package main.JavaFX.lecturers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.JavaFX.MainWindowController;
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
    private MainWindowController mainWindowController;

    public void initialize() throws SQLException {

        deleteLecturerButton.setOnAction(actionEvent -> {
            Lecturer lecturer = lecturersTableView.getSelectionModel().getSelectedItem();
            try {
                lecturersDataModel.deleteLecturer(lecturer.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

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

        LecturerInputControler lecturerInputControler = loader.getController();
        lecturerInputControler.initModel(lecturersDataModel);
        stage.setTitle("Lecturer Input");
        if (root!=null) {
            stage.setScene(new Scene(root));
            stage.setResizable(false);
        } else {throw new NullPointerException();}

        // Serves the purpose of the new window being imposed over the other window
        stage.initModality(Modality.APPLICATION_MODAL);

        // The following line makes it so that there will only be a single task icon in the task bar
        // when a new stage is show, which in this case is the input stage
        stage.initOwner( ((Node)actionEvent.getSource()).getScene().getWindow() );

        stage.show();

    }

    public void initModel() throws SQLException {
        lecturersTableView.columnResizePolicyProperty().set(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Lecturer, String> lecturerNameColumn = new TableColumn<>("Name");
        TableColumn<Lecturer, Integer> lecturerLastNameColumn = new TableColumn<>("Last name");
        TableColumn<Lecturer, String> lecturerQualificationsColumn = new TableColumn<>("Qualifications");
        lecturerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lecturerNameColumn.prefWidthProperty().bind(lecturersTableView.widthProperty().multiply(0.33));
        lecturerNameColumn.minWidthProperty().set(150);
        lecturerLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lecturerLastNameColumn.prefWidthProperty().bind(lecturersTableView.widthProperty().multiply(0.33));
        lecturerLastNameColumn.minWidthProperty().set(150);
        lecturerQualificationsColumn.setCellValueFactory(new PropertyValueFactory<>("qualifications"));
        lecturerQualificationsColumn.prefWidthProperty().bind(lecturersTableView.widthProperty().multiply(0.30));
        lecturerQualificationsColumn.minWidthProperty().set(150);
        lecturersTableView.getColumns().add(0, lecturerNameColumn);
        lecturersTableView.getColumns().add(1, lecturerLastNameColumn);
        lecturersTableView.getColumns().add(2, lecturerQualificationsColumn);

        lecturersTableView.getItems().clear();
        lecturersDataModel.loadLecturers();
        lecturersTableView.setItems(lecturersDataModel.getLecturersList());
    }

    public void injectMainWindowController(MainWindowController mainWindowController) throws SQLException {
        this.mainWindowController = mainWindowController;
        this.lecturersDataModel = mainWindowController.getLecturersDataModel();
        initModel();
    };

}