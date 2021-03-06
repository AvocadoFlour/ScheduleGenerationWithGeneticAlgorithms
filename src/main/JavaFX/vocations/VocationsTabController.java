package main.JavaFX.vocations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import main.JavaFX.MainWindow;
import main.JavaFX.MainWindowController;
import main.classes.Course;
import main.classes.Vocation;

import java.io.IOException;
import java.sql.SQLException;

public class VocationsTabController {

    @FXML
    private VBox vocationsTabVBox;
    @FXML
    private HBox vocationsTabHBox;
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

        setUpUiControls();

        createVocationButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vocationInput.fxml"));
            Stage stage = new Stage();
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            root.prefWidth(400);
            root.prefHeight(400);
            VocationsInputController vocationsInputController = loader.getController();
            vocationsInputController.initModel(vocationsDataModel);
            stage.setTitle("Vocation Input");
            Window window = ((Node)actionEvent.getSource()).getScene().getWindow();
            showVocationInputWindow(root,stage, window);
        });

        deleteVocationButton.setOnAction(actionEvent -> {
            Vocation vocationToDelete = vocationsTableView.getSelectionModel().getSelectedItem();
            try {
                if(!vocationsDataModel.checkVocationConstraint((int)vocationToDelete.getId()))
                {
                    vocationsDataModel.deleteVocation((int)vocationToDelete.getId());
                } else {
                    MainWindow.showAlertWindow("Unable to delete vocation", "Foreign key constraint",
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

    public static void showVocationInputWindow(Parent root, Stage stage, Window window) {
        Scene scene = new Scene(root);
        root.setId("additionalWindow");
        scene.getStylesheets().add(MainWindow.class.getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        // Serves the purpose of the new window being imposed over the other window
        stage.initModality(Modality.NONE);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.initModality(Modality.WINDOW_MODAL);

        // The following line makes it so that there will only be a single task icon in the task bar
        // when a new stage is show, which in this case is the input stage
        stage.initOwner(window);

        stage.show();
    }

    private void setUpUiControls() {
        vocationsTableView.prefHeightProperty().bind(vocationsTabVBox.heightProperty().multiply(0.9));
        //vocationsTabHBox.setPadding(new Insets(0, 0, 0, 0));
        vocationsTabHBox.setAlignment(Pos.CENTER);
        vocationsTabHBox.setPrefHeight(100);
        vocationsTabHBox.spacingProperty().bind(vocationsTabHBox.widthProperty().multiply(0.15));

        deleteVocationButton.setMinWidth(130.0);
        deleteVocationButton.setPrefWidth(130.0);
        deleteVocationButton.setMaxWidth(130.0);
        vocationDetailsButton.setMinWidth(130.0);
        vocationDetailsButton.setPrefWidth(130.0);
        vocationDetailsButton.setMaxWidth(130.0);
        createVocationButton.setMinWidth(130.0);
        createVocationButton.setPrefWidth(130.0);
        createVocationButton.setMaxWidth(130.0);

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

        // Wrapping cell content with the cell
        // https://stackoverflow.com/a/22732723/10299831
        vocationsRequirementsColumn.setCellFactory(tc -> {
            TableCell<Vocation, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(vocationsRequirementsColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

        vocationNameColumn.setCellFactory(tc -> {
            TableCell<Vocation, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(vocationNameColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });

    }

    /* Služi za postavljanje mainControllera kao varijable ove klase te se na taj način ostvaruje interakcija s
    */
    public void injectMainWindowController(MainWindowController mainWindowController) throws SQLException {
        this.mainWindowController = mainWindowController;
        this.vocationsDataModel = mainWindowController.getVocationsDataModel();
        initModel();
    }

}
