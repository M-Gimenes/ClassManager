package classmanager.model.domain;

import java.util.List;

public class ClassGroup {

    private int cgID;
    private String name;
    private float value;
    private int weeklyFreq;

    private List<Student> students;
    private List<Lesson> lessons;
    private Status status;

    public ClassGroup(String name, List<Student> students) {
        this.name = name;
        this.students = students;
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

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void addStudent(Student student) {
        this.students.add(student);
    }

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
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
