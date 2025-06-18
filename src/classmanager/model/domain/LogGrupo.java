package classmanager.model.domain;

import java.io.Serializable;

public class LogGrupo implements Serializable {
    private int idGrupo;      // ID do grupo (1-10)
    private String timestamp; // Data/hora no formato "yyyy-MM-dd HH:mm:ss"
    
    // Construtor padrão obrigatório para serialização
    public LogGrupo() {}
    
    public LogGrupo(int idGrupo, String timestamp) {
        this.idGrupo = idGrupo;
        this.timestamp = timestamp;
    }
    
    // Getters e setters obrigatórios
    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }
    
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}