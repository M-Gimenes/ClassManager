package classmanager.model.dao;

import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.Lesson;
import classmanager.util.LoggerUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LessonDAO {

    private final Connection conn;
    private static LessonDAO instance;

    private LessonDAO() {
        this.conn = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
    }

    public static LessonDAO getInstance() {
        if (instance == null) {
            instance = new LessonDAO();
        }
        return instance;
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS lessons ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "class_id INTEGER, "
                + "day TEXT, "
                + "content TEXT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - createTableIfNotExists", e);
        }
    }

    public void insertLesson(Lesson lesson) {
        String sql = "INSERT INTO lessons (class_id, day, content) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, lesson.getClassId());
            stmt.setString(2, lesson.getDay().toString());
            stmt.setString(3, lesson.getContent());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - insertLesson", e);
        }
    }

    public List<Lesson> getLessonsByClassId(int classId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lessons WHERE class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(rs.getInt("id"));
                    lesson.setClassId(rs.getInt("class_id"));
                    lesson.setDay(LocalDate.parse(rs.getString("day")));
                    lesson.setContent(rs.getString("content"));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - getLessonsByClassId", e);
        }
        return lessons;
    }

    public void updateLesson(int lessonId, Lesson lesson) {
        String sql = "UPDATE lessons SET class_id = ?, day = ?, content = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lesson.getClassId());
            stmt.setString(2, lesson.getDay().toString());
            stmt.setString(3, lesson.getContent());
            stmt.setInt(4, lessonId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - updateLesson", e);
        }
    }

    public void deleteLesson(int lessonId) {
        String sql = "DELETE FROM lessons WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - deleteLesson", e);
        }
    }

    public void deleteLessonsByClassId(int classId) {
        String sql = "DELETE FROM lessons WHERE class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - deleteLessonsByClassId", e);
        }
    }
}
