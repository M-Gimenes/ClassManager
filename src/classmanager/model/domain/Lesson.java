package classmanager.model.domain;

import java.util.Date;
import java.util.List;

public class Lesson {
    
    private Date day;
    private List<Student> students;
    private List<Skills> skills;
    private String content;

    public Lesson(Date day, List<Student> students, List<Skills> skills, String content) {
        this.day = day;
        this.students = students;
        this.skills = skills;
        this.content = content;
    }

    public Lesson() {
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Skills> getSkills() {
        return skills;
    }

    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }

    public String getContent() {
        return content;
    }
                                               
    public void setContent(String content) {
        this.content = content;
    }
    
}
