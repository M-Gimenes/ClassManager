package classmanager.model.dao;

import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.Student;
import classmanager.util.LoggerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonStudentDAO {

    private static LessonStudentDAO instance;
    private final Connection conn;
    private StudentDAO studentDAO = StudentDAO.getInstance();

    private LessonStudentDAO() {
        this.conn = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
    }

    public static LessonStudentDAO getInstance() {
        if (instance == null) {
            instance = new LessonStudentDAO();
        }
        return instance;
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS lesson_students ("
                + "lesson_id INTEGER, "
                + "student_id INTEGER, "
                + "FOREIGN KEY (lesson_id) REFERENCES lessons(id), "
                + "FOREIGN KEY (student_id) REFERENCES students(id)"
                + ");";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonStudentDAO - createTableIfNotExists", e);
        }
    }

    public void insert(int lessonId, int studentId) {
        String sql = "INSERT INTO lesson_students (lesson_id, student_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonStudentDAO - insert", e);
        }
    }

    public void deleteByLessonId(int lessonId) {
        String sql = "DELETE FROM lesson_students WHERE lesson_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonStudentDAO - deleteByLessonId", e);
        }
    }

    public List<Student> getStudentsByLessonId(int lessonId) {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT student_id FROM lesson_students WHERE lesson_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                studentDAO.getByClassId(studentId).ifPresent(students::add);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonStudentDAO - getStudentsByLessonId", e);
        }

        return students;
    }
}
