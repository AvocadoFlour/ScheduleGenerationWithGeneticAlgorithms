package main.JavaFX.classGroups;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.JavaFX.vocations.VocationsDataModel;
import main.classes.Vocation;

import java.sql.SQLException;

public class ClassGroupInputController {

    @FXML
    TextField numberOfStudentsTextField;
    @FXML
    TextField classGroupIdentificationTextField;
    @FXML
    Button createButton;
    @FXML
    Button cancelButton;
    @FXML
    ComboBox<Vocation> vocationPickerComboBox;
    private ClassGroupDataModel classGroupDataModel;
    private VocationsDataModel vocationsDataModel;

    public void initialize() {

        /**
         * Making sure that only numbers are inputted in the hall capacity field
         */
        numberOfStudentsTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    numberOfStudentsTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        createButton.setOnAction(actionEvent -> {
            int numberOfStudents = Integer.parseInt(numberOfStudentsTextField.getText());
            String classGroupIdentification = classGroupIdentificationTextField.getText();
            Vocation vocation = vocationPickerComboBox.getSelectionModel().getSelectedItem();
            int vocationId = ((Long) vocation.getId()).intValue();
            try {
                classGroupDataModel.addClassGroup(classGroupIdentification, vocationId, numberOfStudents);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });

    }

    private void initializeView() {

        //https://stackoverflow.com/a/41208128/10299831
        Callback<ListView<Vocation>, ListCell<Vocation>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Vocation item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        vocationPickerComboBox.setCellFactory(factory);
        vocationPickerComboBox.setItems(vocationsDataModel.getVocationsList());
        vocationPickerComboBox.setButtonCell(factory.call(null));
    }

    public void initModelVocationsDataModel(VocationsDataModel vocationsDataModel) {
        this.vocationsDataModel = vocationsDataModel;
        initializeView();
    }

    public void  initClassGroupDataModel(ClassGroupDataModel classGroupDataModel) {
        this.classGroupDataModel = classGroupDataModel;
    }

}
