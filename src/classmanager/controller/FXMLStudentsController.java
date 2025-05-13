package classmanager.controller;

import classmanager.Main;
import classmanager.model.dao.StudentDAO;
import classmanager.model.domain.Student;
import classmanager.util.ViewPaths;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLStudentsController implements Initializable {

    @FXML
    private Button buttonBack;
    @FXML
    private Label labelName;
    @FXML
    private Label labelNumber;
    @FXML
    private Label labelEmail;
    @FXML
    private Label labelDate;
    @FXML
    private Label labelSchool;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonRemove;

    @FXML
    private TableView<Student> tableViewStudents;
    @FXML
    private TableColumn<Student, String> tableColumnName;
    @FXML
    private TableColumn<Student, Class> tableColumnClass;

    private StudentDAO studentDAO;
    private ObservableList<Student> observableListStudents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        studentDAO = StudentDAO.getInstance();
        observableListStudents = FXCollections.observableArrayList(studentDAO.getAllStudents());
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnClass.setCellValueFactory(new PropertyValueFactory<>("ClassName")); 
        tableViewStudents.setItems(observableListStudents);

        tableViewStudents.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemTableViewStudents(newValue));
    }

    public void selectItemTableViewStudents(Student student) {
        if (student != null) {
            labelName.setText(student.getName());
            labelNumber.setText(student.getFoneNumber());
            labelEmail.setText(student.getEmail());
            labelSchool.setText(student.getSchool());
            labelDate.setText(student.getBirthDate().toString());
        } else {
            labelName.setText("");
            labelNumber.setText("");
            labelEmail.setText("");
            labelSchool.setText("");
            labelDate.setText("");
        }
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Nenhum aluno selecionado");
        alert.setContentText("Por favor, escolha um aluno na Tabela!");
        alert.show();
    }

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.HOME);
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) throws IOException {
        Student student = new Student();
        boolean buttonConfirmClicked = showFXMLStudentsDialog(student);
        if (buttonConfirmClicked) {
            studentDAO.insertStudent(student);
            observableListStudents.add(student);
        }
    }

    @FXML
    private void handleButtonUpdate(ActionEvent event) throws IOException {
        Student student = tableViewStudents.getSelectionModel().getSelectedItem();
        if (student != null) {
            boolean buttonConfirmClicked = showFXMLStudentsDialog(student);
            if (buttonConfirmClicked) {
                studentDAO.updateStudent(student);
                observableListStudents.setAll(studentDAO.getAllStudents());
                tableViewStudents.getSelectionModel().clearSelection();
            }
        } else {
            showAlert();
        }
    }

    @FXML
    private void handleButtonRemove(ActionEvent event) {
        Student student = tableViewStudents.getSelectionModel().getSelectedItem();
        if (student != null) {
            studentDAO.deleteStudent(student.getId());
            observableListStudents.remove(student);
        } else {
            showAlert();
        }
    }

    public boolean showFXMLStudentsDialog(Student student) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPaths.STUDENTSDIALOG));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        FXMLStudentsDialogController controller = loader.getController();
        controller.setStage(stage);
        controller.setStudent(student);
        stage.showAndWait();
        return controller.isButtonConfirmClicked();
    }
}
