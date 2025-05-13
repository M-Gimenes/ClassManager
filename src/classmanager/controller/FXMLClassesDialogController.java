package classmanager.controller;

import classmanager.model.dao.StudentDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import classmanager.model.domain.ClassGroup;
import classmanager.model.domain.Status;
import classmanager.model.domain.Student;
import classmanager.util.InputValidator;

public class FXMLClassesDialogController implements Initializable {

    private ClassGroup cg;
    private Stage stage;
    private boolean buttonConfirmClicked = false;

    @FXML
    private TextField fieldNameCG;
    @FXML
    private ComboBox<Status> comboBoxStatus;
    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;

    @FXML
    private TextField fieldValue;
    @FXML
    private TextField fieldWeeklyFreq;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxStatus.getItems().addAll(Status.values());
    }

    public void setClassGroup(ClassGroup cg) {
        this.cg = cg;
        if (cg != null) {
            fieldNameCG.setText(cg.getName());
            fieldValue.setText(String.valueOf(cg.getValue()));
            fieldWeeklyFreq.setText(String.valueOf(cg.getWeeklyFreq()));
            comboBoxStatus.setValue(cg.getStatus());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isButtonConfirmClicked() {
        return buttonConfirmClicked;
    }

    @FXML
    private void handleButtonConfirm(ActionEvent event) {
        if (validateInputs()) {
            cg.setName(fieldNameCG.getText());
            cg.setValue(Float.parseFloat(fieldValue.getText()));
            cg.setWeeklyFreq(Integer.parseInt(fieldWeeklyFreq.getText()));
            cg.setStatus(comboBoxStatus.getValue());
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
        if (!InputValidator.isNotEmpty(fieldNameCG.getText())) {
            errorMessage += "Nome da turma inválido!\n";
        }
        if (!InputValidator.isValidFloat(fieldValue.getText())) {
            errorMessage += "Valor inválido!\n";
        }
        if (!InputValidator.isValidInteger(fieldWeeklyFreq.getText())) {
            errorMessage += "Frequência inválida\n";
        }
        if (comboBoxStatus.getValue() == null) {
            errorMessage += "Situação inválida!\n";
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
