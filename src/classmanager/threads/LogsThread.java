package classmanager.threads;

import sockets.thread.LogGrupo;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;

public class LogsThread implements Runnable {

    private final TextArea textAreaLogs;
    private final ObservableList<LogGrupo> logs;

    public LogsThread(TextArea textAreaLogs, ObservableList<LogGrupo> logs) {
        this.textAreaLogs = textAreaLogs;
        this.logs = logs;
    }

    @Override
    public void run() {
        while (true) {
            for (LogGrupo log : logs) {
                Platform.runLater(() -> textAreaLogs.setText(""));
                Platform.runLater(() -> textAreaLogs.appendText(log.getTimestamp()));
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LogsThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
