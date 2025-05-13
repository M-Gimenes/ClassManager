package classmanager.controller;

import classmanager.Main;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import classmanager.model.dao.ClassGroupDAO;
import classmanager.model.dao.LessonDAO;
import classmanager.model.domain.ClassGroup;
import classmanager.model.domain.Lesson;
import classmanager.util.ViewPaths;
import javafx.scene.control.Alert;

public class FXMLLessonsController implements Initializable {

    @FXML
    private ComboBox<ClassGroup> comboBox;

    private ClassGroupDAO classDAO;
    private LessonDAO lessonDAO;

    private ObservableList<Lesson> observableListLessons;

    @FXML
    private TableView<Lesson> tableView;
    @FXML
    private TableColumn<Lesson, Date> tableViewColumnDate;
    @FXML
    private TableColumn<Lesson, String> tableViewColumnContent;
    @FXML
    private TableColumn<Lesson, String> tableViewColumnSkills;
    @FXML
    private TableColumn<Lesson, String> tableViewColumnStudents;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private Button buttonUpdate;
    private ClassGroup cgSelected = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListLessons = FXCollections.observableArrayList();
        classDAO = ClassGroupDAO.getInstance();
        lessonDAO = LessonDAO.getInstance();
        comboBox.getItems().addAll(classDAO.getAllClasses());
        tableView.setItems(observableListLessons);

        tableViewColumnDate.setCellValueFactory(new PropertyValueFactory<>("day"));
        tableViewColumnContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        tableViewColumnSkills.setCellValueFactory(new PropertyValueFactory<>("skillsAsString"));
        tableViewColumnStudents.setCellValueFactory(new PropertyValueFactory<>("studentsAsString"));

        comboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> loadTableView(newValue));
    }

    public void loadTableView(ClassGroup cg) {
        observableListLessons.clear();
        observableListLessons.addAll(lessonDAO.getLessonsByClassId(cg.getCgID()));
        cgSelected = cg;
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Nenhuma aula selecionada");
        alert.setContentText("Por favor, escolha uma aula na Tabela!");
        alert.show();
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) throws IOException {
        Lesson lesson = new Lesson();
        if (cgSelected != null) {
            lesson.setClassId(cgSelected.getCgID());
            boolean buttonConfirmClicked = showFXMLLessonsDialog(lesson);
            if (buttonConfirmClicked) {
                lessonDAO.insertLesson(lesson);
                observableListLessons.add(lesson);
            }
        } else {
            showAlert();
        }
    }

    @FXML
    private void handleButtonRemove(ActionEvent event) {
        Lesson lesson = tableView.getSelectionModel().getSelectedItem();
        if (lesson != null) {
            lessonDAO.deleteLesson(lesson.getId());
            observableListLessons.remove(lesson);
        } else {
            showAlert();
        }
    }

    @FXML
    private void handleButtonUpdate(ActionEvent event) throws IOException {
        Lesson lesson = tableView.getSelectionModel().getSelectedItem();
        if (lesson != null) {
            boolean buttonConfirmClicked = showFXMLLessonsDialog(lesson);
            if (buttonConfirmClicked) {
                lessonDAO.updateLesson(lesson);
                loadTableView(cgSelected);
            }
        } else {
            showAlert();
        }
    }

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.HOME);
    }

    public boolean showFXMLLessonsDialog(Lesson lesson) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPaths.LESSONSDIALOG));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Insira os dados");
        FXMLLessonsDialogController controller = loader.getController();
        controller.setStage(stage);
        controller.setLesson(lesson);
        stage.showAndWait();
        return controller.isButtonConfirmClicked();
    }
}
