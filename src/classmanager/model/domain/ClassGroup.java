package classmanager.model.domain;

import java.util.List;

public class ClassGroup {

    private int cgID;
    private String name;

    private List<String> students;
    private List<Lesson> lessons;
    private Status status;

    public ClassGroup(String name, List<String> students) {
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

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
        this.students = students;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public void addStudent(String name) {
        this.students.add(name);
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
        return name; // ou qualquer texto que você quiser exibir na ListView
    }
}
