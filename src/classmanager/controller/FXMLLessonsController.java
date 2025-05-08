package classmanager.controller;

import classmanager.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

public class FXMLLessonsController implements Initializable {

    @FXML
    private ComboBox<ClassGroup> comboBox;

    private ClassGroupDAO classDAO;
    private LessonDAO lessonDAO;

    private ObservableList<ClassGroup> observableListCG;
    private ObservableList<Lesson> observableListLesson;

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
    private ClassGroup cgSelected;
    private List<Lesson> lessons;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListCG = FXCollections.observableArrayList();
        observableListLesson = FXCollections.observableArrayList();
        classDAO = ClassGroupDAO.getInstance();
        lessonDAO = LessonDAO.getInstance();
        observableListCG.addAll(classDAO.getAllClasses());
        comboBox.setItems(observableListCG);
        tableView.setItems(observableListLesson);

        tableViewColumnDate.setCellValueFactory(new PropertyValueFactory<>("day"));
        tableViewColumnContent.setCellValueFactory(new PropertyValueFactory<>("content"));
        tableViewColumnSkills.setCellValueFactory(new PropertyValueFactory<>("skills"));
        tableViewColumnStudents.setCellValueFactory(new PropertyValueFactory<>("students"));

        comboBox.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> loadTableView(newValue));
    }

    public void loadTableView(ClassGroup cg) {
        observableListLesson.addAll(lessonDAO.getLessonsByClassId(cg.getCgID()));
        cgSelected = cg;
    }

   @FXML
    private void handleButtonAdd(ActionEvent event) throws IOException {
//        Lesson lesson = new Lesson();
//        boolean buttonConfirmClicked = showFXMLClassesDialog(lesson);
//        if (buttonConfirmClicked) {
//            lessons.add(lesson);
//            lessonDAO.saveLessons(cgSelected.getCgID(), lessons);
//            observableListLesson.add(lesson);
//        }
    }

    @FXML
    private void handleButtonRemove(ActionEvent event) {
    }

    @FXML
    private void handleButtonUpdate(ActionEvent event) {
    }
    
    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.HOME);
    }

//    public boolean showFXMLScheduleDialog(Lesson lesson) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPaths.SCHEDULEDIALOG));
//        Parent root = loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.setTitle("Insira os dados");
//        FXMLClassesDialogController controller = loader.getController();
//        controller.setStage(stage);
//        controller.setClassGroup(lesson);
//        stage.showAndWait();
//        return controller.isButtonConfirmClicked();
//    }
//
}
