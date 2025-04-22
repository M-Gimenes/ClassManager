package manu.model.domain;

public enum Status {
    CONCLUIDO("Concluído"),
    EM_ANDAMENTO("Em andamento"),
    CANCELADO("Cancelado");

    private final String label;

    Status(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
