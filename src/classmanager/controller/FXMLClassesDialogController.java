package classmanager.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import classmanager.model.domain.ClassGroup;
import classmanager.model.domain.Status;

public class FXMLClassesDialogController implements Initializable {

    private ClassGroup cg;
    private Stage stage;
    private boolean buttonConfirmClicked = false;

    @FXML
    private TextField fieldNameCG;
    @FXML
    private ComboBox<Status> comboBoxStatus;
    @FXML
    private TextField fieldStudents;
    @FXML
    private Button buttonAddStudent;
    @FXML
    private Button buttonRemoveStudent;
    @FXML
    private ListView<String> listViewStudents;
    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;

    private ObservableList<String> observableListStudents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListStudents = FXCollections.observableArrayList();
        listViewStudents.setItems(observableListStudents);
        comboBoxStatus.getItems().addAll(Status.values());
    }

    public void setClassGroup(ClassGroup cg) {
        this.cg = cg;
        if (cg != null) {
            fieldNameCG.setText(cg.getName());
            comboBoxStatus.setValue(cg.getStatus());
            if (cg.getStudents() != null) {
                observableListStudents = FXCollections.observableArrayList(cg.getStudents());
            } else {
                observableListStudents = FXCollections.observableArrayList();    
            }
            listViewStudents.setItems(observableListStudents);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isButtonConfirmClicked() {
        return buttonConfirmClicked;
    }

    @FXML
    private void handleButtonAddStudent(ActionEvent event) {
        if (validateFieldStudents()) {
            observableListStudents.add(fieldStudents.getText());
        }
    }

    @FXML
    private void handleButtonRemoveStudent(ActionEvent event) {
        String selectedStudent = listViewStudents.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            listViewStudents.getItems().remove(selectedStudent);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText("Selecione um aluno para remover.");
            alert.showAndWait();
        }

    }

    @FXML
    private void handleButtonConfirm(ActionEvent event) {
        if (validateInputs()) {
            cg.setName(fieldNameCG.getText());
            cg.setStatus(comboBoxStatus.getValue());
            cg.setStudents(observableListStudents);
            buttonConfirmClicked = true;
            stage.close();
        }
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
        stage.close();
    }

    private boolean validateInputs() {
        String errorMessage = "";
        if (fieldNameCG.getText() == null || fieldNameCG.getText().length() == 0) {
            errorMessage += "Nome da turma inválido!\n";
        }
        if (comboBoxStatus.getValue() == null) {
            errorMessage += "Situação inválida!\n";
        }
        if (observableListStudents == null || observableListStudents.isEmpty()) {
            errorMessage += "Nenhum aluno inserido!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    private boolean validateFieldStudents() {
        String errorMessage = "";
        if (fieldStudents.getText() == null || fieldStudents.getText().length() == 0) {
            errorMessage += "Nome do aluno inválido!\n";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

}
