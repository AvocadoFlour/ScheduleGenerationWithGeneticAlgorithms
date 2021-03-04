package main.JavaFX.lectureHalls;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import main.classes.LectureHall;

import java.io.IOException;
import java.sql.SQLException;

public class LectureHallsTabController {
    @FXML
    private TableView<LectureHall> lectureHallsTableView;
    @FXML
    private Button addNewLectureHallButton;
    @FXML
    private Button deleteLectureHallButton;
    private LectureHallsDataModel lectureHallsDataModel;
    private MainWindowController mainWindowController;

    public void initialize() throws SQLException {

        /**
         * Delete button functionality
         */
        deleteLectureHallButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                LectureHall selectedItem = lectureHallsTableView.getSelectionModel().getSelectedItem();
                try {
                    lectureHallsDataModel.deleteLectureHall(selectedItem.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        /**
         * Adding a click option for each table row which gives back the data of the clicked on row
         */
        /*lectureHallsTableView.setRowFactory(tv -> {
            TableRow<LectureHall> row = new TableRow<>();
            row.setOnMouseClicked(mouseEvent -> {
                if (! row.isEmpty() && mouseEvent.getButton()== MouseButton.PRIMARY) {
                    LectureHall clickedRow = row.getItem();
                    System.out.println(clickedRow.getHallCode());
                }
            });
            return row;
        });*/
    }

    // Kako otvoriti unutar istog prozora
    /*public void prikaziPretraguAutomobila() {
        try {
            AnchorPane center = FXMLLoader.load(getClass().
                    getResource("lectureHallInput.fxml"));
            mainWindow.setCenter(center);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Open a new window through which it's possible to make a new entity entry
     * @param actionEvent
     */
    public void openLectureHallInputWindow(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lectureHallInput.fxml"));
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LectureHallInputController lectureHallInputController = loader.getController();
        lectureHallInputController.initModel(lectureHallsDataModel);
        stage.setTitle("Lecture hall Input");
        stage.setScene(new Scene(root));
        stage.setResizable(false);

        // Serves the purpose of the new window being imposed over the other window
        stage.initModality(Modality.APPLICATION_MODAL);

        // The following line makes it so that there will only be a single task icon in the task bar
        // when a new stage is show, which in this case is the input stage
        stage.initOwner( ((Node)actionEvent.getSource()).getScene().getWindow() );

        stage.show();

    }

    /**
     * Setting an instance of the CoursesDataModel class as the overall host of all the displayed data in the stage.
     * The purpose being so that two different stages can affect each other by being build atop of the same
     * underlying instance of this class.
     * @throws SQLException
     */
    public void initModel() throws SQLException {
        TableColumn<LectureHall, String> hallCodeColumn = new TableColumn<>("Hall code");
        TableColumn<LectureHall, Integer> hallCapacityColumn = new TableColumn<>("Hall capacity");
        hallCodeColumn.setCellValueFactory(new PropertyValueFactory<>("hallCode"));
        hallCodeColumn.prefWidthProperty().bind(lectureHallsTableView.widthProperty().multiply(0.50));
        hallCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        hallCapacityColumn.prefWidthProperty().bind(lectureHallsTableView.widthProperty().multiply(0.50));
        lectureHallsTableView.getColumns().add(0, hallCodeColumn);
        hallCodeColumn.setResizable(false);
        lectureHallsTableView.getColumns().add(1, hallCapacityColumn);
        hallCapacityColumn.setResizable(false);
        lectureHallsTableView.getItems().clear();
        lectureHallsDataModel.loadLectureHalls();
        lectureHallsTableView.setItems(lectureHallsDataModel.getLectureHallList());
    }

    public void injectMainWindowController(MainWindowController mainWindowController) throws SQLException {
        this.mainWindowController = mainWindowController;
        this.lectureHallsDataModel = mainWindowController.getLectureHallsDataModel();
        initModel();
    };

}
