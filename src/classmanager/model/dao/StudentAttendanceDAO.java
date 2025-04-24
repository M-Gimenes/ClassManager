package classmanager.model.dao;

import classmanager.model.domain.StudentAttendance;
import classmanager.model.database.DatabaseManager;
import classmanager.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentAttendanceDAO {

    private final Connection conn;
    private static StudentAttendanceDAO instance;

    public StudentAttendanceDAO() {
        this.conn = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
    }

    public static StudentAttendanceDAO getInstance() {
        if (instance == null) {
            instance = new StudentAttendanceDAO();
        }
        return instance;
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS student_attendance "
                + "("
                + "student_id INTEGER NOT NULL,"
                + "class_id INTEGER NOT NULL,"
                + "year_month TEXT NOT NULL,"
                + "attendance_count INTEGER NOT NULL DEFAULT 0,"
                + "PRIMARY KEY (student_id, class_id, year_month),"
                + "FOREIGN KEY (student_id) REFERENCES students(id),"
                + "FOREIGN KEY (class_id) REFERENCES classes(id)"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LoggerUtil.logError("StudentAttendanceDAO - createTable", e);
        }
    }

    public void insertOrUpdateAttendance(int studentId, int classId, String yearMonth, int countToAdd) {
        String sql = "INSERT INTO student_attendance (student_id, class_id, year_month, attendance_count) "
                + "VALUES (?, ?, ?, ?) "
                + "ON CONFLICT(student_id, class_id, year_month) "
                + "DO UPDATE SET attendance_count = attendance_count + excluded.attendance_count;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, classId);
            stmt.setString(3, yearMonth);
            stmt.setInt(4, countToAdd);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("StudentAttendanceDAO - insertOrUpdateAttendance", e);
        }
    }

    public List<StudentAttendance> getAttendanceByStudent(int studentId) {
        List<StudentAttendance> list = new ArrayList<>();
        String sql = "SELECT * FROM student_attendance WHERE student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                StudentAttendance sa = new StudentAttendance();
                sa.setStudentId(rs.getInt("student_id"));
                sa.setClassId(rs.getInt("class_id"));
                sa.setYearMonth(rs.getString("year_month"));
                sa.setAttendanceCount(rs.getInt("attendance_count"));
                list.add(sa);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("StudentAttendanceDAO - getAttendanceByStudent", e);
        }
        return list;
    }
}
