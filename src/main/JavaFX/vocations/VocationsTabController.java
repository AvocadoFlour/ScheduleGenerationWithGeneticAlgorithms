package main.JavaFX.vocations;

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
import main.JavaFX.lecturesHalls.LectureHallsDataModel;
import main.classes.LectureHall;
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
    private TableColumn<Vocation, String> vocationNameColumn;
    private TableColumn<Vocation, String> vocationsRequirementsColumn;

    public void initialize() throws SQLException {
        initModel();

        createVocationButton.setOnAction(actionEvent -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("vocationsInput.fxml"));
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
                stage.show();
            });

    }

    public void initModel() throws SQLException {
        this.vocationsDataModel = new VocationsDataModel();
        vocationNameColumn = new TableColumn<>("Vocation name");
        vocationsRequirementsColumn = new TableColumn<>("Course requirements");
        vocationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        vocationsRequirementsColumn.setCellValueFactory(new PropertyValueFactory<>("courseRequirements"));
        vocationsTableView.getColumns().add(0,vocationNameColumn);
        vocationNameColumn.prefWidthProperty().bind(vocationsTableView.widthProperty().multiply(0.49));
        vocationNameColumn.setResizable(false);
        vocationsTableView.getColumns().add(1,vocationsRequirementsColumn);
        vocationsRequirementsColumn.prefWidthProperty().bind(vocationsTableView.widthProperty().multiply(0.49));
        vocationsRequirementsColumn.setResizable(false);
        vocationsTableView.getItems().clear();
        vocationsDataModel.loadVocations();
        vocationsTableView.setItems(vocationsDataModel.getVocationsList());
    }

}
