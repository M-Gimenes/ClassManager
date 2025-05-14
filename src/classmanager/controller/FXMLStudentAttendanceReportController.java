package classmanager.controller;

import classmanager.Main;
import classmanager.model.dao.LessonStudentDAO;
import classmanager.util.ViewPaths;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class FXMLStudentAttendanceReportController {
    private TableView<String[]> attendanceTable;

    private TableColumn<String[], String> AlunoColumn;

    private TableColumn<String[], String> TurmaColumn;

    private TableColumn<String[], String> FrequenciaColumn;
    @FXML
    private AnchorPane root;
    @FXML
    private Button buttonPrint;
    @FXML
    private TableView<?> tableViewAttendance;
    @FXML
    private Button buttonBack;

    public void initialize() {
        loadData();
    }

    private void loadData() {
    }

    @FXML
    private void handleButtonPrint(ActionEvent event) {
        //
    }

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.HOME);
    }
}

