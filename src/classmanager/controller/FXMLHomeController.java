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
    private Button menuReports;
    @FXML
    private Button menuSchedule;
    @FXML
    private Button menuClasses;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
    @FXML
    private void handleMenuClasses(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.CLASSES);
    }
    
    @FXML
    private void handleMenuSchedule(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.SCHEDULE);
    }
    
    @FXML
    private void handleMenuPayments(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.CLASSES);
    }

    @FXML
    private void handleMenuReports(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.CLASSES);
    }


}
