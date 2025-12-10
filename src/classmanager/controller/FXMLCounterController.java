package classmanager.controller;

import classmanager.Main;
import classmanager.model.domain.ClassGroup;
import sockets.thread.ContadorGrupo;
import sockets.thread.LogGrupo;
import classmanager.model.domain.Student;
import classmanager.threads.LogsThread;
import classmanager.util.ViewPaths;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLCounterController implements Initializable {

    @FXML
    private Label labelIdGroup;
    @FXML
    private TableView<ContadorGrupo> tableVliewGroups;
    @FXML
    private TableColumn<ContadorGrupo, Integer> tableViewColumnPos;
    @FXML
    private TableColumn<ContadorGrupo, String> tableViewColumnGroup;
    @FXML
    private TableColumn<ContadorGrupo, Integer> tableViewColumnUses;
    @FXML
    private Button buttonBack;

    private ObservableList<ContadorGrupo> observableListGroups;
    private ObservableList<LogGrupo> observableListLogGroup;
    
    @FXML
    private TextArea textAreaLogs;
    
    private LogsThread logsThread;
    private Thread t1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        observableListGroups = FXCollections.observableArrayList();
        observableListLogGroup = FXCollections.observableArrayList();
        tableVliewGroups.setItems(observableListGroups);
        
        tableViewColumnPos.setCellValueFactory(new PropertyValueFactory<>("idGrupo"));
        tableViewColumnGroup.setCellValueFactory(new PropertyValueFactory<>("nomeGrupo"));
        tableViewColumnUses.setCellValueFactory(new PropertyValueFactory<>("quantidadeUtilizacoes"));
 

        connect();
    }


    public void connect() {
        int idGrupo = 5; // ID do grupo desejado (1-10)
        String servidor = "34.41.27.130"; // IP do servidor
        int porta = 12345;

        try (Socket clienteSocket = new Socket(servidor, porta)) {
            System.out.println("✅ Conectado ao servidor: " + servidor + ":" + porta);

            // PASSO 1: Enviar ID do grupo
            ObjectOutputStream saida = new ObjectOutputStream(clienteSocket.getOutputStream());
            saida.writeObject(idGrupo);
            System.out.println("📤 Enviado ID do grupo: " + idGrupo);

            // PASSO 2: Receber ranking completo
            ObjectInputStream entrada = new ObjectInputStream(clienteSocket.getInputStream());
            @SuppressWarnings("unchecked")
            List<ContadorGrupo> ranking = (List<ContadorGrupo>) entrada.readObject();
            System.out.println("📥 Recebido ranking com " + ranking.size() + " grupos");

            // PASSO 3: Receber logs do grupo
            @SuppressWarnings("unchecked")
            List<LogGrupo> logs = (List<LogGrupo>) entrada.readObject();
            System.out.println("📥 Recebidos " + logs.size() + " logs do grupo " + idGrupo);

            // PASSO 4: Processar dados recebidos
            processarRanking(ranking);
            processarLogs(logs);

            entrada.close();
            saida.close();
            clienteSocket.close();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Erro na comunicação: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void processarRanking(List<ContadorGrupo> ranking) {
        observableListGroups.addAll(ranking);
        System.out.println("\n=== RANKING DOS GRUPOS ===");
        for (ContadorGrupo grupo : ranking) {
            System.out.println("ID " + grupo.getIdGrupo()
                    + " - " + grupo.getNomeGrupo()
                    + ": " + grupo.getQuantidadeUtilizacoes() + " utilizações");
        }
    }

    private void processarLogs(List<LogGrupo> logs) {
        observableListLogGroup.addAll(logs);
        logsThread = new LogsThread(textAreaLogs, observableListLogGroup);
        t1 = new Thread(logsThread);
        t1.setDaemon(true); 
        t1.start();
        
        System.out.println("\n=== LOGS DO GRUPO ===");
        if (logs.isEmpty()) {
            System.out.println("Nenhum log encontrado para este grupo.");
        } else {
            for (LogGrupo log : logs) {
                System.out.println("Acesso em: " + log.getTimestamp());
            }
        }
    }

    @FXML
    private void handleButtonBack(ActionEvent event) throws IOException {
        /*
         * `Thread.stop()` always throws a `new UnsupportedOperationException()` in Java 21+.
         * For detailed migration instructions see the migration guide available at
         * https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/doc-files/threadPrimitiveDeprecation.html
         */
        t1.stop();
        Main.setRoot(ViewPaths.HOME);
    }

}
