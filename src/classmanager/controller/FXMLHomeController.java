package classmanager.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
    import javafx.scene.control.Alert;
    import javafx.scene.control.ListView;
    import javafx.scene.Scene;
    import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import classmanager.grpc.GrpcService;
import classmanager.Classmanager;
import classmanager.Main;
import classmanager.util.ViewPaths;public class FXMLHomeController implements Initializable {

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
        private Button menuLoadGroup;
    @FXML
    private Button menuLoadStudents;
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
    
    @FXML
    private void handleMenuCounter(ActionEvent event) throws IOException {
        Main.setRoot(ViewPaths.COUNTER);
    }
    
    @FXML
    private void handleLoadGroup(ActionEvent event) {
        menuLoadGroup.setDisable(true);
        GrpcService.getGroupMembersAsync(new GrpcService.Callback<java.util.List<String>>() {
            @Override
            public void onSuccess(java.util.List<String> members) {
                menuLoadGroup.setDisable(false);
                // Show members in a modal window
                ListView<String> listView = new ListView<>();
                listView.getItems().addAll(members);
                VBox root = new VBox(10, listView);
                root.setPrefSize(300, 400);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Group Members");
                stage.setScene(new Scene(root));
                stage.show();
            }

            @Override
            public void onError(String error) {
                menuLoadGroup.setDisable(false);
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("gRPC Error");
                a.setHeaderText("Failed to load group members");
                a.setContentText(error);
                a.showAndWait();
            }
        });
    }

    @FXML
    private void handleLoadStudents(ActionEvent event) {
        menuLoadStudents.setDisable(true);
        GrpcService.listStudentsAsync(new GrpcService.Callback<java.util.List<Classmanager.Student>>() {
            @Override
            public void onSuccess(java.util.List<Classmanager.Student> students) {
                menuLoadStudents.setDisable(false);
                // Show students in a modal window
                ListView<String> listView = new ListView<>();
                for (Classmanager.Student s : students) {
                    listView.getItems().add("ID: " + s.getId() + " - " + s.getName());
                }
                VBox root = new VBox(10, listView);
                root.setPrefSize(300, 400);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Students from gRPC Server");
                stage.setScene(new Scene(root));
                stage.show();
            }

            @Override
            public void onError(String error) {
                menuLoadStudents.setDisable(false);
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setTitle("gRPC Error");
                a.setHeaderText("Failed to load students");
                a.setContentText(error);
                a.showAndWait();
            }
        });
    }

}
