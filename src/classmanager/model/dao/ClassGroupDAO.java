package classmanager.model.dao;

import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.ClassGroup;
import classmanager.model.domain.Status;
import classmanager.util.LoggerUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassGroupDAO {

    private final Connection conn;
    private final LessonDAO lessonDAO;
    private static ClassGroupDAO instance;

    private ClassGroupDAO() {
        conn = DatabaseManager.getInstance().getConnection();
        lessonDAO = LessonDAO.getInstance();
        createTableIfNotExists();
    }

    public static ClassGroupDAO getInstance() {
        if (instance == null) {
            instance = new ClassGroupDAO();
        }
        return instance;
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS classes (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "name TEXT NOT NULL, " +
                     "value REAL NOT NULL, " +
                     "weekly_freq INTEGER NOT NULL, " +
                     "status TEXT NOT NULL" +
                     ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LoggerUtil.logError("ClassGroupDAO - createTableIfNotExists", e);
        }
    }

    public void insertClassGroup(ClassGroup cg) {
        String sql = "INSERT INTO classes (name, value, weekly_freq, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cg.getName());
            stmt.setFloat(2, cg.getValue());
            stmt.setInt(3, cg.getWeeklyFreq());
            stmt.setString(4, cg.getStatus().name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("ClassGroupDAO - insertClassGroup", e);
        }
    }

    public List<ClassGroup> getAllClasses() {
        List<ClassGroup> classGroups = new ArrayList<>();
        String sql = "SELECT * FROM classes";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ClassGroup cg = new ClassGroup();
                int id = rs.getInt("id");

                cg.setCgID(id);
                cg.setName(rs.getString("name"));
                cg.setValue(rs.getFloat("value"));
                cg.setWeeklyFreq(rs.getInt("weekly_freq"));
                cg.setStatus(Status.valueOf(rs.getString("status")));
                classGroups.add(cg);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("ClassGroupDAO - getAllClasses", e);
        }

        return classGroups;
    }

    public void updateClassGroup(int classId, ClassGroup cg) {
        String sql = "UPDATE classes SET name = ?, value = ?, weekly_freq = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cg.getName());
            stmt.setFloat(2, cg.getValue());
            stmt.setInt(3, cg.getWeeklyFreq());
            stmt.setString(4, cg.getStatus().name());
            stmt.setInt(5, classId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("ClassGroupDAO - updateClassGroup", e);
        }
    }

    public void deleteClassGroup(int classId) {
        String sql = "DELETE FROM classes WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            stmt.executeUpdate();
            lessonDAO.deleteLessonsByClassId(classId);
        } catch (SQLException e) {
            LoggerUtil.logError("ClassGroupDAO - deleteClassGroup", e);
        }
    }
}
