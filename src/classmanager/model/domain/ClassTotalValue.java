package classmanager.model.domain;

public class ClassTotalValue {
    private final String className;
    private final double total;

    public ClassTotalValue(String className, double total) {
        this.className = className;
        this.total = total;
    }

    public String getClassName() {
        return className;
    }

    public double getTotal() {
        return total;
    }
}
