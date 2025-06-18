package classmanager.model.domain;

import java.io.Serializable;

public class ContadorGrupo implements Serializable {
    private int idGrupo;           // ID do grupo (1-10)
    private String nomeGrupo;      // Nome do grupo
    private int quantidadeUtilizacoes; // Contador de utilizações
    
    // Construtor padrão obrigatório para serialização
    public ContadorGrupo() {}
    
    public ContadorGrupo(int idGrupo, String nomeGrupo, int quantidadeUtilizacoes) {
        this.idGrupo = idGrupo;
        this.nomeGrupo = nomeGrupo;
        this.quantidadeUtilizacoes = quantidadeUtilizacoes;
    }
    
    // Getters e setters obrigatórios
    public int getIdGrupo() { return idGrupo; }
    public void setIdGrupo(int idGrupo) { this.idGrupo = idGrupo; }
    
    public String getNomeGrupo() { return nomeGrupo; }
    public void setNomeGrupo(String nomeGrupo) { this.nomeGrupo = nomeGrupo; }
    
    public int getQuantidadeUtilizacoes() { return quantidadeUtilizacoes; }
    public void setQuantidadeUtilizacoes(int quantidadeUtilizacoes) { 
        this.quantidadeUtilizacoes = quantidadeUtilizacoes; 
    }
}