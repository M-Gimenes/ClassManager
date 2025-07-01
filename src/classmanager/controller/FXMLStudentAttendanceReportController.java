package classmanager.controller;

import classmanager.Main;
import classmanager.model.dao.LessonStudentDAO;
import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.ClassTotalValue;
import classmanager.util.ViewPaths;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLStudentAttendanceReportController {

    @FXML
    private AnchorPane root;
    @FXML
    private Button buttonPrint;
    @FXML
    private Button buttonBack;
    @FXML
    private ComboBox<String> comboBoxReport;
    @FXML
    private TableView<String[]> tableViewAttendance;
    @FXML
    private TableColumn<String[], String> TVAttendanceColumnStudent;
    @FXML
    private TableColumn<String[], String> TVAttendanceColumnClass;
    @FXML
    private TableColumn<String[], String> TVAttendanceColumnAttendance;
    @FXML
    private TableView<ClassTotalValue> tableViewValue;
    @FXML
    private TableColumn<ClassTotalValue, String> TVValueColumnClass;
    @FXML
    private TableColumn<ClassTotalValue, Double> TVValueColumnValue;

    private ObservableList<String[]> observableListAttendance;
    private ObservableList<ClassTotalValue> observableListValue;
    private LessonStudentDAO lessonStudentDAO;

    private Connection conn;

    public void initialize() {
        comboBoxReport.getItems().addAll("Frequência dos Alunos", "Valor arrecadado por alunos");
        comboBoxReport.setValue("Valor arrecadado por alunos");

        lessonStudentDAO = LessonStudentDAO.getInstance();
        observableListAttendance = FXCollections.observableArrayList();
        observableListAttendance.addAll(lessonStudentDAO.getStudentAttendanceReport());
        tableViewAttendance.setItems(observableListAttendance);

        observableListValue = FXCollections.observableArrayList();
        observableListValue.addAll(lessonStudentDAO.getTotalReceivedPerClass());
        tableViewValue.setItems(observableListValue);

        TVAttendanceColumnStudent.setCellValueFactory(cellData
                -> new ReadOnlyStringWrapper(cellData.getValue()[0]));
        TVAttendanceColumnClass.setCellValueFactory(cellData
                -> new ReadOnlyStringWrapper(cellData.getValue()[1]));
        TVAttendanceColumnAttendance.setCellValueFactory(cellData
                -> new ReadOnlyStringWrapper(cellData.getValue()[2]));

        TVValueColumnClass.setCellValueFactory(new PropertyValueFactory<>("className"));
        TVValueColumnValue.setCellValueFactory(new PropertyValueFactory<>("total"));

        comboBoxReport.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> loadTableView(newValue));
    }

    public void loadTableView(String type) {
        if (type.equals("Frequência dos Alunos")) {
            tableViewAttendance.setVisible(true);
            tableViewAttendance.setManaged(true);
            tableViewValue.setVisible(false);
            tableViewValue.setManaged(false);
        } else if (type.equals("Valor arrecadado por alunos")) {
            tableViewValue.setVisible(true);
            tableViewValue.setManaged(true);
            tableViewAttendance.setVisible(false);
            tableViewAttendance.setManaged(false);
        }
    }

    @FXML
    private void handleButtonPrint(ActionEvent event) throws JRException {
        conn = DatabaseManager.getInstance().getConnection();
        URL url;
        if ("Frequência dos Alunos".equals(comboBoxReport.getValue())) {
            url = getClass().getResource("/classmanager/reports/ReportAttendance.jasper");
        } else {    
            url = getClass().getResource("/classmanager/reports/ReportValue.jasper");
        }
        JasperReport report = (JasperReport) JRLoader.loadObject(url);
        JasperPrint print = JasperFillManager.fillReport(report, null, conn);
        JasperViewer viewer = new JasperViewer(print, false);
        viewer.setVisible(true);
    }

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.HOME);
    }
}
