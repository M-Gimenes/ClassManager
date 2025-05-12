package classmanager.controller;

import classmanager.model.domain.ClassGroup;
import classmanager.model.domain.Student;
import classmanager.util.StudentUtil;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLStudentDialogController implements Initializable {

    private Student student;
    private Stage stage;
    private boolean buttonConfirmClicked = false;

    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;
    @FXML
    private Button buttonBack;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setStudent(Student student) {
        this.student = student;
        if (student != null) {
            fieldName.setText(student.getName());
            fieldNumber.setText(student.getFoneNumber());
            fieldEmail.setText(student.getEmail());
            fieldSchool.setText(student.getSchool());
            dateBirthDate.setValue(student.getBirthDate());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void handleButtonConfirm(ActionEvent event) {
    }

    @FXML
    private void handleButtonCancel(ActionEvent event) {
    }

    @FXML
    private void handleButtonBack(ActionEvent event) {
    }

}
