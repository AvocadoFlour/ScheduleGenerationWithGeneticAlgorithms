package main.JavaFX.lecturers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import main.JavaFX.MainWindow;
import main.JavaFX.MainWindowController;
import main.classes.Lecturer;

import java.io.IOException;
import java.sql.SQLException;

public class LecturersTabController {

    @FXML
    private VBox lecturersVBox;
    @FXML
    private HBox lecturersHBox;
    @FXML
    private TableView<Lecturer> lecturersTableView;
    @FXML
    private Button addNewLecturerButton;
    @FXML
    private Button deleteLecturerButton;
    @FXML
    private Button createNewLecturerButton;
    private LecturersDataModel lecturersDataModel;
    private MainWindowController mainWindowController;

    public void initialize() throws SQLException {

        setupUiControls();

        deleteLecturerButton.setOnAction(actionEvent -> {
            Lecturer lecturer = lecturersTableView.getSelectionModel().getSelectedItem();
            try {
                lecturersDataModel.deleteLecturer(lecturer.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

    }

    private void setupUiControls() {
        lecturersTableView.prefHeightProperty().bind(lecturersVBox.heightProperty().multiply(0.9));
        //vocationsTabHBox.setPadding(new Insets(0, 0, 0, 0));
        lecturersHBox.setAlignment(Pos.CENTER);
        lecturersHBox.setPrefHeight(100);
        lecturersHBox.spacingProperty().bind(lecturersHBox.widthProperty().multiply(0.15));

        deleteLecturerButton.setMinWidth(130.0);
        deleteLecturerButton.setPrefWidth(130.0);
        deleteLecturerButton.setMaxWidth(130.0);
        createNewLecturerButton.setMinWidth(130.0);
        createNewLecturerButton.setPrefWidth(130.0);
        createNewLecturerButton.setMaxWidth(130.0);
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
        Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
        MainWindow.showInputWindow(root,stage, window);

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