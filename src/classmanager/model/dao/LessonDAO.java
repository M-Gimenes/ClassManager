package classmanager.model.dao;

import classmanager.model.domain.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.Skills;
import classmanager.util.LoggerUtil;

public class LessonDAO {

    private final Connection conn;
    private static LessonDAO instance;

    public LessonDAO() {
        conn = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
    }

    public static LessonDAO getInstance() {
        if (instance == null) {
            instance = new LessonDAO();
        }
        return instance;
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS lessons (id INTEGER PRIMARY KEY AUTOINCREMENT, class_id INTEGER, day TEXT, students TEXT, skills TEXT, content TEXT, FOREIGN KEY (class_id) REFERENCES classes(id)); ";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - createTableIfNotExists", e);
        }
    }

    public void saveLessons(int classId, List<Lesson> lessons) {
        String sql = "INSERT INTO lessons (class_id, day, students, skills, content) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (Lesson lesson : lessons) {
                stmt.setInt(1, classId);
                stmt.setString(2, lesson.getDay().toString());
                stmt.setString(3, String.join(",", Optional.ofNullable(lesson.getStudents()).orElse(new ArrayList<>())));
                stmt.setString(4, lesson.getSkills().stream().map(Skills::toString).collect(Collectors.joining(",")));
                stmt.setString(5, lesson.getContent());
                stmt.addBatch();
            }
            stmt.executeBatch();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - saveLessons", e);
        }
    }

    public List<Lesson> getLessonsByClassId(int classId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lessons WHERE class_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Lesson lesson = new Lesson();
                lesson.setDay(java.sql.Date.valueOf(rs.getString("day")));
                lesson.setStudents(Arrays.asList(rs.getString("students").split(",")));
                String skillsStr = rs.getString("skills");
                List<Skills> skills = Arrays.stream(skillsStr.split(",")).map(String::trim).map(Skills::valueOf).collect(Collectors.toList());
                lesson.setSkills(skills);
                lesson.setContent(rs.getString("content"));
                lessons.add(lesson);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - getLessonsByClassId", e);
        }
        return lessons;
    }

    public void deleteLessonsByClassId(int classId) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM lessons WHERE class_id = ?")) {
            stmt.setInt(1, classId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - deleteLessonsByClassId", e);
        }
    }
}
