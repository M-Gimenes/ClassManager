package classmanager.model.domain;

// Matheus
public class ClassGroup {

    private int cgID;
    private String name;
    private float value;
    private int weeklyFreq;
    private Status status;

    public ClassGroup(String name, float value, int weeklyFreq, Status status) {
        this.name = name;
        this.value = value;
        this.weeklyFreq = weeklyFreq;
        this.status = status;
    }

    public ClassGroup() {
    }

    public int getCgID() {
        return cgID;
    }

    public void setCgID(int cgID) {
        this.cgID = cgID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getWeeklyFreq() {
        return weeklyFreq;
    }

    public void setWeeklyFreq(int weeklyFreq) {
        this.weeklyFreq = weeklyFreq;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return name;
    }
}
