package manu.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import manu.Main;

public class FXMLMainController implements Initializable {

    @FXML
    private Button menuPayments;
    @FXML
    private Button menuReports;
    @FXML
    private Button menuTimeline;
    @FXML
    private Button menuClasses;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    
    @FXML
    private void handleMenuClasses(ActionEvent event) throws IOException {
        Main.setRoot("/manu/view/FXMLClasses");
    }
    
    @FXML
    private void handleMenuTimeline(ActionEvent event) throws IOException {
        Main.setRoot("/manu/view/FXMLTimeline");
    }
    
    @FXML
    private void handleMenuPayments(ActionEvent event) throws IOException {
        Main.setRoot("FXML");
    }

    @FXML
    private void handleMenuReports(ActionEvent event) throws IOException {
        Main.setRoot("FXML");
    }


}
