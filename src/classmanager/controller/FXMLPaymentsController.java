package classmanager.controller;

import classmanager.Main;
import classmanager.model.dao.LessonDAO;
import classmanager.model.dao.LessonStudentDAO;
import classmanager.model.dao.StudentDAO;
import classmanager.model.domain.Lesson;
import classmanager.model.domain.Skill;
import classmanager.model.domain.Student;
import classmanager.util.ViewPaths;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLPaymentsController implements Initializable {

    @FXML
    private ComboBox<Student> comboBoxStudents;
    @FXML
    private TableView<Lesson> tableViewLessons;
    @FXML
    private TableColumn<Lesson, Date> tableViewColumnDate;
    @FXML
    private TableColumn<Lesson, String> tableViewColumnContent;
    @FXML
    private TableColumn<Lesson, Skill> tableViewColumnSkills;
    @FXML
    private Button buttonPaid;
    @FXML
    private Button buttonNotPaid;
    @FXML
    private Button buttonBack;
    @FXML
    private Label labelSituation;

    private ObservableList observableListLessons;

    private StudentDAO studentDAO;
    private LessonDAO lessonDAO;
    private LessonStudentDAO lStudentDAO;

    private Student studentSelected = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studentDAO = StudentDAO.getInstance();
        lessonDAO = LessonDAO.getInstance();
        lStudentDAO = LessonStudentDAO.getInstance();
        comboBoxStudents.getItems().addAll(studentDAO.getAllStudents());
        observableListLessons = FXCollections.observableArrayList();
        tableViewLessons.setItems(observableListLessons);

        tableViewColumnDate.setCellValueFactory(new PropertyValueFactory<>("day"));
        tableViewColumnContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        tableViewColumnSkills.setCellValueFactory(new PropertyValueFactory<>("skillsAsString"));

        comboBoxStudents.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> loadTableView(newValue));
        tableViewLessons.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemListViewClientes(newValue));
    }

    public void loadTableView(Student student) {
        observableListLessons.clear();
        observableListLessons.addAll(lessonDAO.getLessonsByStudentId(student.getId()));
        studentSelected = student;
    }

    public void selectItemListViewClientes(Lesson lesson) {
        if (lesson != null) {
            labelSituation.setText(getSituation(lesson));
        } else {
            labelSituation.setText("");
        }
    }

    public String getSituation(Lesson lesson) {
        boolean value = lStudentDAO.isPaid(lesson.getId(), studentSelected.getId());
        if (value == true) {
            return "Pago";
        }
        return "Não pago";
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Nenhuma aula selecionada");
        alert.setContentText("Por favor, escolha uma aula na Tabela!");
        alert.show();
    }

    @FXML
    private void handleButtonPaid(ActionEvent event) {
        Lesson lesson = tableViewLessons.getSelectionModel().getSelectedItem();
        if (lesson != null) {
            if (paidBusinessRule(lesson)) {
                lStudentDAO.payLesson(lesson.getId(), studentSelected.getId(), 1);
                tableViewLessons.getSelectionModel().clearSelection();
            }
        } else {
            showAlert();
        }
    }

    @FXML
    private void handleButtonNotPaid(ActionEvent event) {
        Lesson lesson = tableViewLessons.getSelectionModel().getSelectedItem();
        if (lesson != null) {
            lStudentDAO.payLesson(lesson.getId(), studentSelected.getId(), 0);
            tableViewLessons.getSelectionModel().clearSelection();
        } else {
            showAlert();
        }
    }

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.HOME);
    }

    private boolean paidBusinessRule(Lesson lesson) {
        if (lStudentDAO.isPaid(lesson.getId(), studentSelected.getId())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("A aula selecionada já está paga");
            alert.setContentText("Por favor, escolha outra aula na Tabela!");
            alert.show();
            return false;
        }
        return true;
    }

}
