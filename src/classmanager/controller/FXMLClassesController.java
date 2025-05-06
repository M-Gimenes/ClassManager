package classmanager.controller;

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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import classmanager.model.dao.ClassGroupDAO;
import classmanager.model.dao.StudentDAO;
import classmanager.model.domain.ClassGroup;
import classmanager.util.StudentUtil;
import classmanager.util.ViewPaths;

public class FXMLClassesController implements Initializable {

    @FXML
    private ListView<ClassGroup> listViewClasses;
    @FXML
    private ListView<String> listViewStudents;
    @FXML
    private Label labelClass;
    @FXML
    private Label labelStatus;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonRemove;

    private ClassGroupDAO classGroupDAO;
    private StudentDAO studentDAO;

    private ObservableList<ClassGroup> observableListCG;
    private ObservableList<String> observableListStudents;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        classGroupDAO = ClassGroupDAO.getInstance();
        observableListCG = FXCollections.observableArrayList(classGroupDAO.getAllClasses());
        observableListStudents = FXCollections.observableArrayList();
        listViewClasses.setItems(observableListCG);
        listViewStudents.setItems(observableListStudents);

        selectItemListViewClientes(null);
        listViewClasses.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectItemListViewClientes(newValue));
    }

    public void selectItemListViewClientes(ClassGroup cg) {
        if (cg != null) {
            labelClass.setText(String.valueOf(cg.getName()));
            labelStatus.setText(String.valueOf(cg.getStatus()));
            observableListStudents.clear();
            studentDAO.getById(cg.getCgID()).ifPresent(student -> observableListStudents.add(student));
        } else {
            labelClass.setText("");
            labelStatus.setText("");
            observableListStudents.clear();
        }
    }

    public void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Nenhuma turma selecionada");
        alert.setContentText("Por favor, escolha uma turma na Tabela!");
        alert.show();
    }

    @FXML
    private void handleButtonAdd(ActionEvent event) throws IOException {
        ClassGroup cg = new ClassGroup();
        boolean buttonConfirmClicked = showFXMLClassesDialog(cg);
        if (buttonConfirmClicked) {
            classGroupDAO.insertClassGroup(cg);
            observableListCG.add(cg);
        }
    }

    @FXML
    private void handleButtonUpdate(ActionEvent event) throws IOException {
        ClassGroup cg = listViewClasses.getSelectionModel().getSelectedItem();
        if (cg != null) {
            boolean buttonConfirmClicked = showFXMLClassesDialog(cg);
            if (buttonConfirmClicked) {
                classGroupDAO.updateClassGroup(cg.getCgID(), cg);
                listViewClasses.getSelectionModel().clearSelection();
            }
        } else {
            showAlert();
        }
    }

    @FXML
    private void handleButtonRemove(ActionEvent event) {
        ClassGroup cg = listViewClasses.getSelectionModel().getSelectedItem();
        if (cg != null) {
            classGroupDAO.deleteClassGroup(cg.getCgID());
            observableListCG.remove(cg);
        } else {
            showAlert();
        }
    }

    public boolean showFXMLClassesDialog(ClassGroup cg) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPaths.CLASSESDIALOG));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Insira os dados");
        FXMLClassesDialogController controller = loader.getController();
        controller.setStage(stage);
        controller.setClassGroup(cg);
        stage.showAndWait();
        return controller.isButtonConfirmClicked();
    }

}
