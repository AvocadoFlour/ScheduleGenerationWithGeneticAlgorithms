package main.JavaFX.vocations;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.JavaFX.MainWindow;
import main.JavaFX.MainWindowController;
import main.classes.Course;
import main.classes.Vocation;

import java.io.IOException;
import java.sql.SQLException;

public class VocationsTabController {

    @FXML
    private Button createVocationButton;
    @FXML
    private Button vocationDetailsButton;
    @FXML
    private Button deleteVocationButton;
    @FXML
    private TableView<Vocation> vocationsTableView;
    private VocationsDataModel vocationsDataModel;
    private MainWindowController mainWindowController;

    public void initialize() throws SQLException {

        createVocationButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vocationInput.fxml"));
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            VocationsInputController vocationsInputController = loader.getController();
            vocationsInputController.initModel(vocationsDataModel);
            stage.setTitle("Vocation Input");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            // Serves the purpose of the new window being imposed over the other window
            stage.initModality(Modality.APPLICATION_MODAL);

            // The following line makes it so that there will only be a single task icon in the task bar
            // when a new stage is show, which in this case is the input stage
            stage.initOwner( ((Node)actionEvent.getSource()).getScene().getWindow() );

            stage.show();
        });

        deleteVocationButton.setOnAction(actionEvent -> {
            Vocation vocationToDelete = vocationsTableView.getSelectionModel().getSelectedItem();
            try {
                if(!vocationsDataModel.checkVocationConstraint((int)vocationToDelete.getId()))
                {
                    vocationsDataModel.deleteVocation((int)vocationToDelete.getId());
                } else {
                    MainWindow.alertWindow("Unable to delete vocation", "Foreign key constraint",
                            "The vocation is currently the vocation of one or more class group(s)");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        vocationsTableView.setRowFactory( tv -> {
            TableRow<Vocation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Vocation rowData = row.getItem();
                    System.out.println(rowData.courseRequirementsProperty());
                }
            });
            return row ;
        });

    }

    public void initModel() throws SQLException {
        vocationsTableView.columnResizePolicyProperty().set(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<Vocation, String> vocationNameColumn = new TableColumn<>("Vocation name");
        vocationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Vocation, String> vocationsRequirementsColumn = new TableColumn<>("Course requirements");
        vocationsRequirementsColumn.setCellValueFactory(new PropertyValueFactory<>("courseRequirements"));
        vocationsTableView.getColumns().add(0, vocationNameColumn);
        vocationNameColumn.prefWidthProperty().bind(vocationsTableView.widthProperty().multiply(0.5));
        vocationNameColumn.minWidthProperty().set(200);
        vocationsTableView.getColumns().add(1, vocationsRequirementsColumn);
        vocationsRequirementsColumn.prefWidthProperty().bind(vocationsTableView.widthProperty().multiply(0.5));
        vocationsRequirementsColumn.minWidthProperty().set(400);
        vocationsTableView.getItems().clear();
        vocationsDataModel.loadVocations();
        vocationsTableView.setItems(vocationsDataModel.getVocationsList());
    }

    /* Služi za postavljanje mainControllera kao varijable ove klase te se na taj način ostvaruje interakcija s
    */
    public void injectMainWindowController(MainWindowController mainWindowController) throws SQLException {
        this.mainWindowController = mainWindowController;
        this.vocationsDataModel = mainWindowController.getVocationsDataModel();
        initModel();
    }

}
