package classmanager.model.dao;

import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.Student;
import classmanager.util.LoggerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
                + "paid BOOLEAN DEFAULT 0, "
                + "PRIMARY KEY (lesson_id, student_id), "
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
        String sql = "SELECT s.* FROM students s "
                + "JOIN lesson_students ls ON s.id = ls.student_id "
                + "WHERE ls.lesson_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setBirthDate(LocalDate.parse(rs.getString("birth_date")));
                student.setFoneNumber(rs.getString("fone_number"));
                student.setEmail(rs.getString("email"));
                student.setSchool(rs.getString("school"));
                student.setClassId(rs.getInt("class_id"));
                students.add(student);
            }

        } catch (SQLException e) {
            LoggerUtil.logError("LessonStudentDAO - getStudentsByLessonId", e);
        }

        return students;
    }

    //Regra de negócio
    public void payLesson(int lessonId, int studentId) {
        String sql = "UPDATE lesson_students SET paid = 1 WHERE lesson_id = ? AND student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.setInt(2, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonStudentDAO - payLesson", e);
        }
    }

    public boolean isPaid(int lessonId, int studentId) {
        String sql = "SELECT paid FROM lesson_students WHERE lesson_id = ? AND student_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.setInt(2, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("pago");
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonStudentDAO - isPaid", e);
        }
        return false;
    }

    public List<String[]> getStudentAttendanceReport() {
        List<String[]> report = new ArrayList<>();
    
        String sql = "SELECT s.name AS student_name, c.name AS class_name, COUNT(ls.lesson_id) AS lessons_attended " +
                     "FROM lesson_students ls " +
                     "JOIN lessons l ON ls.lesson_id = l.id " +
                     "JOIN students s ON ls.student_id = s.id " +
                     "JOIN classes c ON l.class_id = c.id " +
                     "GROUP BY s.name, c.name " +
                     "ORDER BY c.name, s.name";
    
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String[] line = new String[3];
                line[0] = rs.getString("student_name");
                line[1] = rs.getString("class_name");
                line[2] = String.valueOf(rs.getInt("lessons_attended"));
                report.add(line);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonStudentDAO - getStudentAttendanceReport", e);
        }
    
        return report;
    }
    

}
