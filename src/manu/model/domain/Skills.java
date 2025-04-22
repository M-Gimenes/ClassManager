package manu.model.domain;

public enum Skills {
    SPEAKING("Speaking"),
    WRITING("Writing"),
    LISTENING("Listening"),
    READING("Reading"),
    GRAMMAR("Grammar"),
    PRONUNCIATION("Pronunciation");
    

    private final String label;

    Skills(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
