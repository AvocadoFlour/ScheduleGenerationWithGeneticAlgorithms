package main.JavaFX.lecturesHalls;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    private TableColumn<LectureHall, String> hallCodeColumn;
    private TableColumn<LectureHall, Integer> hallCapacityColumn;
    private LectureHallsDataModel lectureHallsDataModel;

    public void initialize() throws SQLException {

        initModel();

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

        stage.show();

    }

    /**
     * Setting an instance of the CoursesDataModel class as the overall host of all the displayed data in the stage.
     * The purpose being so that two different stages can affect each other by being build atop of the same
     * underlying instance of this class.
     * @throws SQLException
     */
    public void initModel() throws SQLException {
        // ensure model is only set once:
        if (this.lectureHallsDataModel != null) {
            throw new IllegalStateException("Lecture halls model can only be initialized once");
        }
        this.lectureHallsDataModel =new LectureHallsDataModel();
        hallCodeColumn = new TableColumn<>("Hall code");
        hallCapacityColumn = new TableColumn<>("Hall capacity");
        hallCodeColumn.setCellValueFactory(new PropertyValueFactory<>("hallCode"));
        hallCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        lectureHallsTableView.getColumns().add(0, hallCodeColumn);
        lectureHallsTableView.getColumns().add(1, hallCapacityColumn);

        lectureHallsTableView.getItems().clear();
        lectureHallsDataModel.loadLectureHalls();
        lectureHallsTableView.setItems(lectureHallsDataModel.getLectureHallList());
    }

}
