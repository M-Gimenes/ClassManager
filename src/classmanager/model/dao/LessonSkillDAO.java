package classmanager.model.dao;

import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.Skill;
import classmanager.util.LoggerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LessonSkillDAO {

    private static LessonSkillDAO instance;
    private final Connection conn;

    private LessonSkillDAO() {
        this.conn = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
    }

    public static LessonSkillDAO getInstance() {
        if (instance == null) {
            instance = new LessonSkillDAO();
        }
        return instance;
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS lesson_skills ("
                + "lesson_id INTEGER, "
                + "skill_id INTEGER, "
                + "FOREIGN KEY (lesson_id) REFERENCES lessons(id), "
                + "FOREIGN KEY (skill_id) REFERENCES skills(id)"
                + ");";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.execute();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonSkillDAO - createTableIfNotExists", e);
        }
    }

    public void insert(int lessonId, int skillId) {
        String sql = "INSERT INTO lesson_skills (lesson_id, skill_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.setInt(2, skillId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonSkillDAO - insert", e);
        }
    }

    public void deleteByLessonId(int lessonId) {
        String sql = "DELETE FROM lesson_skills WHERE lesson_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("LessonSkillDAO - deleteByLessonId", e);
        }
    }

    public List<Skill> getSkillsByLessonId(int lessonId) {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT skill_id FROM lesson_skills WHERE lesson_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lessonId);
            ResultSet rs = stmt.executeQuery();

            SkillDAO skillDAO = SkillDAO.getInstance();
            while (rs.next()) {
                int skillId = rs.getInt("skill_id");
                skillDAO.getById(skillId).ifPresent(skills::add);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonSkillDAO - getSkillsByLessonId", e);
        }

        return skills;
    }
}
