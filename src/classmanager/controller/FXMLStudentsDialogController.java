package classmanager.controller;

import classmanager.model.dao.ClassGroupDAO;
import classmanager.model.domain.ClassGroup;
import classmanager.model.domain.Student;
import classmanager.util.InputValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLStudentsDialogController implements Initializable {

    private Student student;
    private Stage stage;
    private boolean buttonConfirmClicked = false;
    
    private ClassGroupDAO cgDAO;

    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldNumber;
    @FXML
    private TextField fieldEmail;
    @FXML
    private TextField fieldSchool;
    @FXML
    private DatePicker dateBirthDate;
    @FXML
    private ComboBox<ClassGroup> comboBoxClasses;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cgDAO = ClassGroupDAO.getInstance();
        comboBoxClasses.getItems().addAll(cgDAO.getAllClasses());
    }

    public void setStudent(Student student) {
        this.student = student;
        if (student != null) {
            fieldName.setText(student.getName());
            fieldNumber.setText(student.getFoneNumber());
            fieldEmail.setText(student.getEmail());
            fieldSchool.setText(student.getSchool());
            dateBirthDate.setValue(student.getBirthDate());
            comboBoxClasses.setValue(cgDAO.getClassGroupById(student.getClassId()));
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
            student.setName(fieldName.getText());
            student.setFoneNumber(fieldNumber.getText());
            student.setEmail(fieldEmail.getText());
            student.setSchool(fieldSchool.getText());
            student.setBirthDate(dateBirthDate.getValue());
            student.setClassId(comboBoxClasses.getValue().getCgID());
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
        if (!InputValidator.isNotEmpty(fieldName.getText())) {
            errorMessage += "Nome inválido!\n";
        }
        if (!InputValidator.isNotEmpty(fieldNumber.getText())) {
            errorMessage += "Telefone inválido!\n";
        }
        if (!InputValidator.isNotEmpty(fieldEmail.getText())) {
            errorMessage += "Email inválido!\n";
        }
        if (!InputValidator.isNotEmpty(fieldSchool.getText())) {
            errorMessage += "Escola inválida!\n";
        }
        if (dateBirthDate.getValue() == null) {
            errorMessage += "Data de nascimento inválida!\n";
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
