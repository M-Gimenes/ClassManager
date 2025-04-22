package classmanager.model.domain;

import java.util.Date;
import java.util.List;

public class Lesson {
    
    private Date day;
    private List<String> students;
    private List<Skills> skills;
    private String content;

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public List<String> getStudents() {
        return students;
    }

    public void setStudents(List<String> students) {
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
