package sockets.thread;

import java.io.Serializable;

public class LogGrupo implements Serializable {
    private int idGrupo;
    private String timestamp;

    public LogGrupo() {
    }

    public LogGrupo(int idGrupo, String timestamp) {
        this.idGrupo = idGrupo;
        this.timestamp = timestamp;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LogGrupo{" +
                "idGrupo=" + idGrupo +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
} 