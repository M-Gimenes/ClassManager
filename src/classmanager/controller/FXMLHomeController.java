package classmanager.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import classmanager.Main;
import classmanager.util.ViewPaths;

public class FXMLHomeController implements Initializable {

    @FXML
    private Button menuPayments;
    @FXML
    private Button menuClasses;
    @FXML
    private Button menuStudents;
    @FXML
    private Button menuLessons;
    @FXML
    private Button menuReports;
    @FXML
    private Button menuGraphics1;
    @FXML
    private Button menuGraphics2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void handleMenuClasses(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.CLASSES);
    }

    @FXML
    private void handleMenuStudents(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.STUDENTS);
    }

    @FXML
    private void handleMenuLessons(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.LESSONS);
    }

    @FXML
    private void handleMenuPayments(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.PAYMENTS);
    }

    @FXML
    private void handleMenuReports(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.ATTENDANCEREPORT);
    }

    @FXML
    private void handleMenuGraphics1(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.LESSONCHART);
    }

    @FXML
    private void handleMenuGraphics2(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.REVENUECHART);
    }

}
