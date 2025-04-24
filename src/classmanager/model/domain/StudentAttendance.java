package classmanager.model.domain;

public class StudentAttendance {

    private int studentId;
    private int classId;
    private String yearMonth;
    private int attendanceCount;

    public StudentAttendance() {
    }

    public StudentAttendance(int studentId, int classId, String yearMonth, int attendanceCount) {
        this.studentId = studentId;
        this.classId = classId;
        this.yearMonth = yearMonth;
        this.attendanceCount = attendanceCount;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public int getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(int attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    @Override
    public String toString() {
        return "StudentAttendance{"
                + "studentId=" + studentId
                + ", classId=" + classId
                + ", yearMonth='" + yearMonth + '\''
                + ", attendanceCount=" + attendanceCount
                + '}';
    }
}
