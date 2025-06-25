package sockets.thread;

import java.io.Serializable;

public class ContadorGrupo implements Serializable {
    private int idGrupo;
    private String nomeGrupo;
    private int quantidadeUtilizacoes;

    public ContadorGrupo() {
    }

    public ContadorGrupo(int idGrupo, String nomeGrupo, int quantidadeUtilizacoes) {
        this.idGrupo = idGrupo;
        this.nomeGrupo = nomeGrupo;
        this.quantidadeUtilizacoes = quantidadeUtilizacoes;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public int getQuantidadeUtilizacoes() {
        return quantidadeUtilizacoes;
    }

    public void setQuantidadeUtilizacoes(int quantidadeUtilizacoes) {
        this.quantidadeUtilizacoes = quantidadeUtilizacoes;
    }

    public void incrementarUtilizacoes() {
        this.quantidadeUtilizacoes++;
    }

    @Override
    public String toString() {
        return "ContadorGrupo{" +
                "idGrupo=" + idGrupo +
                ", nomeGrupo='" + nomeGrupo + '\'' +
                ", quantidadeUtilizacoes=" + quantidadeUtilizacoes +
                '}';
    }
} 