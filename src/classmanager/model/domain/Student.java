package classmanager.model.domain;

import java.time.LocalDate;

public class Student {

    private int id;
    private String name;
    private LocalDate birthDate;
    private String foneNumber;
    private String email;
    private String school;
    private int classId;

    public Student(String name, LocalDate birthDate, String foneNumber, String email, String school, int classId) {
        this.name = name;
        this.birthDate = birthDate;
        this.foneNumber = foneNumber;
        this.email = email;
        this.school = school;
        this.classId = classId;
    }

    public Student() {
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFoneNumber() {
        return foneNumber;
    }

    public void setFoneNumber(String foneNumber) {
        this.foneNumber = foneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

}
