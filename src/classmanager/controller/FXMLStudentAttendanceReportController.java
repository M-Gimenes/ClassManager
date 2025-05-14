package classmanager.controller;

import classmanager.model.dao.LessonStudentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.cell.MapValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.TextFieldTableCell;

public class FXMLStudentAttendanceReportController {
    @FXML
    private TableView<String[]> attendanceTable;

    @FXML
    private TableColumn<String[], String> AlunoColumn;

    @FXML
    private TableColumn<String[], String> TurmaColumn;

    @FXML
    private TableColumn<String[], String> FrequenciaColumn;

    @FXML
    public void initialize() {
        loadData();
    }

    private void loadData() {
        ObservableList<String[]> data = FXCollections.observableArrayList(
            LessonStudentDAO.getInstance().getStudentAttendanceReport()
        );

        AlunoColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue()[0]));
        TurmaColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue()[1]));
        FrequenciaColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue()[2]));

        attendanceTable.setItems(data);
    }
}

