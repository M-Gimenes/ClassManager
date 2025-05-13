package classmanager.controller;

import classmanager.model.dao.SkillDAO;
import classmanager.model.dao.StudentDAO;
import classmanager.model.domain.Lesson;
import classmanager.model.domain.Skill;
import classmanager.model.domain.Student;
import classmanager.util.InputValidator;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLLessonsDialogController implements Initializable {

    private Lesson lesson;
    private Stage stage;
    private boolean buttonConfirmClicked = false;

    @FXML
    private DatePicker dateDate;
    @FXML
    private TextField fieldContent;
    @FXML
    private ComboBox<Skill> comboBoxSkills;
    @FXML
    private Button buttonAddSkill;
    @FXML
    private Button buttonRemoveSkill;
    @FXML
    private ListView<Skill> listViewSkills;
    @FXML
    private ComboBox<Student> comboBoxStudents;
    @FXML
    private Button buttonAddStudent;
    @FXML
    private Button buttonRemoveStudent;
    @FXML
    private ListView<Student> listViewStudents;
    @FXML
    private Button buttonConfirm;
    @FXML
    private Button buttonCancel;

    private SkillDAO skillDAO;
    private StudentDAO studentDAO;

    private ObservableList<Skill> observableListSkills;
    private ObservableList<Student> observableListStudents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        skillDAO = SkillDAO.getInstance();
        studentDAO = StudentDAO.getInstance();
        comboBoxSkills.getItems().addAll(skillDAO.getAll());
        comboBoxStudents.getItems().addAll(studentDAO.getAllStudents());
        observableListSkills = FXCollections.observableArrayList();
        observableListStudents = FXCollections.observableArrayList();
        listViewSkills.setItems(observableListSkills);
        listViewStudents.setItems(observableListStudents);
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
        if (lesson != null) {
            fieldContent.setText(lesson.getContent());
            dateDate.setValue(lesson.getDay());
            if (lesson.getSkills() != null) {
                observableListSkills.addAll(lesson.getSkills());
            }
            if (lesson.getStudents() != null) {
                observableListStudents.addAll(lesson.getStudents());
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isButtonConfirmClicked() {
        return buttonConfirmClicked;
    }

    @FXML
    private void handleButtonAddSkill(ActionEvent event) {
        Skill skill = comboBoxSkills.getValue();
        if (skill != null) {
            observableListSkills.add(skill);
        }
    }

    @FXML
    private void handleButtonRemoveSkill(ActionEvent event) {
        Skill skill = listViewSkills.getSelectionModel().getSelectedItem();
        if (skill != null) {
            observableListSkills.remove(skill);
        }
    }

    @FXML
    private void handleButtonAddStudent(ActionEvent event) {
        Student student = comboBoxStudents.getValue();
        if (student != null) {
            observableListStudents.add(student);
        }
    }

    @FXML
    private void handleButtonRemoveStudent(ActionEvent event) {
        Student student = listViewStudents.getSelectionModel().getSelectedItem();
        if (student != null) {
            observableListStudents.remove(student);
        }
    }

    @FXML
    private void handleButtonConfirm(ActionEvent event) {
        if (validateInputs()) {
            lesson.setContent(fieldContent.getText());
            lesson.setDay(dateDate.getValue());
            lesson.setSkills(observableListSkills);
            lesson.setStudents(observableListStudents);
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
        if (!InputValidator.isNotEmpty(fieldContent.getText())) {
            errorMessage += "Conteúdo inválido!\n";
        }
        if (dateDate.getValue() == null) {
            errorMessage += "Data inválida!\n";
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
