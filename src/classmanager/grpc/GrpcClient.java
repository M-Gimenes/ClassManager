package classmanager.grpc;

import classmanager.ClassManagerServiceGrpc;
import classmanager.Classmanager;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * gRPC Client for ClassManager service.
 * Communicates with Python gRPC server at localhost:50051
 */
public class GrpcClient {
    
    private final ManagedChannel channel;
    private final ClassManagerServiceGrpc.ClassManagerServiceBlockingStub blockingStub;
    
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 50051;
    
    /**
     * Constructor that initializes connection to gRPC server
     */
    public GrpcClient() {
        this(SERVER_HOST, SERVER_PORT);
    }
    
    /**
     * Constructor with custom host and port
     */
    public GrpcClient(String host, int port) {
        this.channel = ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext()
            .build();
        this.blockingStub = ClassManagerServiceGrpc.newBlockingStub(channel);
    }
    
    /**
     * Shutdown the channel when done
     */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
    
    /**
     * Fetch group members from server
     * @return List of member names
     */
    public List<String> getGroupMembers() {
        try {
            com.google.protobuf.Empty emptyRequest = com.google.protobuf.Empty.newBuilder().build();
            Classmanager.MemberNames response = blockingStub.getGroupMembers(emptyRequest);
            return response.getNamesList();
        } catch (Exception e) {
            System.err.println("Error fetching group members: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Fetch students list from server
     * @return List of Student objects
     */
    public List<Classmanager.Student> listStudents() {
        try {
            com.google.protobuf.Empty emptyRequest = com.google.protobuf.Empty.newBuilder().build();
            Classmanager.StudentList response = blockingStub.listStudents(emptyRequest);
            return response.getStudentsList();
        } catch (Exception e) {
            System.err.println("Error fetching students: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }
    
    /**
     * Check if connection to server is available
     * @return true if server is reachable, false otherwise
     */
    public boolean isServerAvailable() {
        try {
            com.google.protobuf.Empty emptyRequest = com.google.protobuf.Empty.newBuilder().build();
            blockingStub.withDeadlineAfter(2, TimeUnit.SECONDS).getGroupMembers(emptyRequest);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    // Singleton instance
    private static GrpcClient instance;
    
    /**
     * Get singleton instance of GrpcClient
     */
    public static synchronized GrpcClient getInstance() {
        if (instance == null) {
            instance = new GrpcClient();
        }
        return instance;
    }
    
    /**
     * Close singleton instance
     */
    public static synchronized void closeInstance() {
        if (instance != null) {
            try {
                instance.shutdown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            instance = null;
        }
    }
}
