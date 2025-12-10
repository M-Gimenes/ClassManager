package classmanager.grpc;

import classmanager.Classmanager;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Utility class for gRPC operations
 * Handles async operations and updates UI safely
 */
public class GrpcService {
    
    private static GrpcClient client;
    
    static {
        try {
            client = new GrpcClient();
        } catch (Exception e) {
            System.err.println("Failed to initialize gRPC client: " + e.getMessage());
        }
    }
    
    /**
     * Fetch group members asynchronously
     * @param callback Called with results on JavaFX thread
     */
    public static void getGroupMembersAsync(Callback<List<String>> callback) {
        new Thread(() -> {
            try {
                System.out.println("[gRPC] Calling GetGroupMembers from server...");
                if (client != null && client.isServerAvailable()) {
                    System.out.println("[gRPC] Server is available, fetching members...");
                    List<String> members = client.getGroupMembers();
                    System.out.println("[gRPC] ✓ Received " + members.size() + " members from server: " + members);
                    Platform.runLater(() -> callback.onSuccess(members));
                } else {
                    System.out.println("[gRPC] ✗ Server not available");
                    Platform.runLater(() -> callback.onError("Server not available"));
                }
            } catch (Exception e) {
                System.out.println("[gRPC] ✗ Error: " + e.getMessage());
                e.printStackTrace();
                Platform.runLater(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }
    
    /**
     * Fetch students list asynchronously
     * @param callback Called with results on JavaFX thread
     */
    public static void listStudentsAsync(Callback<List<Classmanager.Student>> callback) {
        new Thread(() -> {
            try {
                System.out.println("[gRPC] Calling ListStudents from server...");
                if (client != null && client.isServerAvailable()) {
                    System.out.println("[gRPC] Server is available, fetching students...");
                    List<Classmanager.Student> students = client.listStudents();
                    System.out.println("[gRPC] ✓ Received " + students.size() + " students from server");
                    for (Classmanager.Student s : students) {
                        System.out.println("[gRPC]   - ID: " + s.getId() + ", Name: " + s.getName());
                    }
                    Platform.runLater(() -> callback.onSuccess(students));
                } else {
                    System.out.println("[gRPC] ✗ Server not available");
                    Platform.runLater(() -> callback.onError("Server not available"));
                }
            } catch (Exception e) {
                System.out.println("[gRPC] ✗ Error: " + e.getMessage());
                e.printStackTrace();
                Platform.runLater(() -> callback.onError(e.getMessage()));
            }
        }).start();
    }
    
    /**
     * Check if gRPC server is available
     */
    public static boolean isServerAvailable() {
        return client != null && client.isServerAvailable();
    }
    
    /**
     * Shutdown gRPC client
     */
    public static void shutdown() {
        if (client != null) {
            try {
                client.shutdown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Callback interface for async operations
     */
    public interface Callback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
}
