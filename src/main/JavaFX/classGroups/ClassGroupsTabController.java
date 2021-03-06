package main.JavaFX.classGroups;

import javafx.collections.ObservableList;
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
import main.classes.ClassGroup;
import main.classes.Vocation;

import java.io.IOException;
import java.sql.SQLException;

public class ClassGroupsTabController {


    private MainWindowController mainController;
    @FXML
    private VBox classGroupsTabVBox;
    @FXML
    private HBox classGroupsTabHBox;
    @FXML
    Button createClassGroupButton;
    @FXML
    Button deleteClassGroupButton;
    @FXML
    TableView<ClassGroup> classGroupsTableView;
    private ClassGroupDataModel classGroupDataModel;
    private ObservableList<Vocation> vocationsList;

    public void initialize() throws SQLException {

        setupUiControls();

        createClassGroupButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("classGroupInput.fxml"));
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClassGroupInputController classGroupInputController = loader.getController();
            classGroupInputController.initModelVocationsDataModel(mainController.getVocationsDataModel());
            classGroupInputController.initClassGroupDataModel(classGroupDataModel);
            stage.setTitle("Class group Input");

            Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
            MainWindow.showInputWindow(root,stage, window);

        });

        deleteClassGroupButton.setOnAction(actionEvent -> {
            ClassGroup classGroupToDelete = classGroupsTableView.getSelectionModel().getSelectedItem();

            try {
                classGroupDataModel.deleteClassGroup((int)classGroupToDelete.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    private void setupUiControls() {
        classGroupsTableView.prefHeightProperty().bind(classGroupsTabVBox.heightProperty().multiply(0.9));
        //vocationsTabHBox.setPadding(new Insets(0, 0, 0, 0));
        classGroupsTabHBox.setAlignment(Pos.CENTER);
        classGroupsTabHBox.setPrefHeight(100);
        classGroupsTabHBox.spacingProperty().bind(classGroupsTabHBox.widthProperty().multiply(0.15));

        deleteClassGroupButton.setMinWidth(130.0);
        deleteClassGroupButton.setPrefWidth(130.0);
        deleteClassGroupButton.setMaxWidth(130.0);
        createClassGroupButton.setMinWidth(180.0);
        createClassGroupButton.setPrefWidth(180.0);
        createClassGroupButton.setMaxWidth(180.0);
    }

    private void initModel() throws SQLException {
        classGroupsTableView.columnResizePolicyProperty().set(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<ClassGroup, String> classGroupNameColumn = new TableColumn<>("Group code");
        classGroupNameColumn.setCellValueFactory(new PropertyValueFactory<>("identification"));
        TableColumn<ClassGroup, String> classGroupVocationColumn = new TableColumn<>("Vocation");
        classGroupVocationColumn.setCellValueFactory(new PropertyValueFactory<>("vocationName"));
        TableColumn<ClassGroup, Integer> classGroupNumberOfStudentsColumn = new TableColumn<ClassGroup, Integer>("No. of students");
        classGroupNumberOfStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfStudents"));
        classGroupsTableView.getColumns().add(0, classGroupNameColumn);
        classGroupsTableView.getColumns().add(1, classGroupVocationColumn);
        classGroupsTableView.getColumns().add(2, classGroupNumberOfStudentsColumn);
        classGroupNameColumn.prefWidthProperty().bind(classGroupsTableView.widthProperty().multiply(0.2));
        classGroupNameColumn.minWidthProperty().set(200);
        classGroupVocationColumn.prefWidthProperty().bind(classGroupsTableView.widthProperty().multiply(0.60));
        classGroupVocationColumn.minWidthProperty().set(200);
        classGroupNumberOfStudentsColumn.prefWidthProperty().bind(classGroupsTableView.widthProperty().multiply(0.2));
        classGroupNumberOfStudentsColumn.minWidthProperty().set(200);
        classGroupsTableView.getItems().clear();
        classGroupDataModel.loadClassGroups();
        classGroupsTableView.setItems(classGroupDataModel.getClassGroupsList());
    }

    public void injectMainWindowController(MainWindowController mainController) throws SQLException {
        this.mainController = mainController;
        this.classGroupDataModel = mainController.getClassGroupDataModel();
        initModel();
    }

}
