package classmanager.grpc;

import classmanager.Classmanager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import java.util.List;

/**
 * Example controller showing how to use gRPC Client
 * This is a template for integrating gRPC calls in your controllers
 */
public class GrpcExampleController {
    
    @FXML
    private ListView<String> groupMembersListView;
    
    @FXML
    private ListView<String> studentsListView;
    
    @FXML
    private Label serverStatusLabel;
    
    @FXML
    private TextArea logTextArea;
    
    /**
     * Initialize controller and load data from gRPC server
     */
    @FXML
    public void initialize() {
        loadGroupMembers();
        loadStudents();
        checkServerStatus();
    }
    
    /**
     * Load group members from gRPC server
     */
    private void loadGroupMembers() {
        GrpcService.getGroupMembersAsync(new GrpcService.Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> members) {
                groupMembersListView.getItems().clear();
                groupMembersListView.getItems().addAll(members);
                appendLog("✓ Loaded " + members.size() + " group members");
            }
            
            @Override
            public void onError(String error) {
                appendLog("✗ Error loading group members: " + error);
            }
        });
    }
    
    /**
     * Load students from gRPC server
     */
    private void loadStudents() {
        GrpcService.listStudentsAsync(new GrpcService.Callback<List<Classmanager.Student>>() {
            @Override
            public void onSuccess(List<Classmanager.Student> students) {
                studentsListView.getItems().clear();
                for (Classmanager.Student student : students) {
                    studentsListView.getItems().add("ID: " + student.getId() + " - " + student.getName());
                }
                appendLog("✓ Loaded " + students.size() + " students");
            }
            
            @Override
            public void onError(String error) {
                appendLog("✗ Error loading students: " + error);
            }
        });
    }
    
    /**
     * Check if gRPC server is available
     */
    private void checkServerStatus() {
        boolean available = GrpcService.isServerAvailable();
        if (available) {
            serverStatusLabel.setText("✓ Server: ONLINE");
            serverStatusLabel.setStyle("-fx-text-fill: green;");
        } else {
            serverStatusLabel.setText("✗ Server: OFFLINE");
            serverStatusLabel.setStyle("-fx-text-fill: red;");
        }
    }
    
    /**
     * Append message to log area
     */
    private void appendLog(String message) {
        logTextArea.appendText("[" + getCurrentTime() + "] " + message + "\n");
    }
    
    /**
     * Get current time as string
     */
    private String getCurrentTime() {
        return java.time.LocalTime.now().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    /**
     * Handle refresh button click
     */
    @FXML
    public void handleRefresh() {
        appendLog("Refreshing data...");
        loadGroupMembers();
        loadStudents();
        checkServerStatus();
    }
    
    /**
     * Shutdown gRPC client on exit
     */
    public void shutdown() {
        GrpcService.shutdown();
        appendLog("gRPC client shutdown");
    }
}
