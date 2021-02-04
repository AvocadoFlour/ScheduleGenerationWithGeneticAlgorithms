package main.JavaFX.vocations;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.JavaFX.lecturesHalls.LectureHallsDataModel;
import main.classes.LectureHall;
import main.classes.Vocation;

import java.sql.SQLException;

public class VocationsTabController {


    @FXML
    private Button createVocationButton;
    @FXML
    private Button vocationDetailsButton;
    @FXML
    private Button deleteVocationButton;
    @FXML
    private TableView vocationsTableView;
    private VocationsDataModel vocationsDataModel;
    private TableColumn<Vocation, String> vocationNameColumn;
    private TableColumn<Vocation, String> vocationsRequirementsColumn;

    public void initialize() {

    }

    public void initModel() throws SQLException {
        this.vocationsDataModel = new VocationsDataModel();
        vocationNameColumn = new TableColumn<>("Vocation name");
        vocationsRequirementsColumn = new TableColumn<>("Course requirements");
        vocationNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        vocationsRequirementsColumn.setCellValueFactory(new PropertyValueFactory<>("courseRequirements"));
        vocationsTableView.getColumns().add(0,vocationNameColumn);
        vocationsTableView.getColumns().add(1,vocationsRequirementsColumn);
        vocationsTableView.getItems().clear();
        vocationsDataModel.loadVocations();
        vocationsTableView.setItems(vocationsDataModel.getVocations);
    }

}
