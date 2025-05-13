package classmanager.model.domain;

import java.time.LocalDate;
import java.util.List;

public class Lesson {
    
    private int id;
    private int classId;
    private LocalDate day;
    private List<Student> students;
    private List<Skill> skills;
    private String content;

    public Lesson(LocalDate day, List<Student> students, List<Skill> skills, String content) {
        this.day = day;
        this.students = students;
        this.skills = skills;
        this.content = content;
    }

    public Lesson() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    public String getContent() {
        return content;
    }
                                               
    public void setContent(String content) {
        this.content = content;
    }
    
}
