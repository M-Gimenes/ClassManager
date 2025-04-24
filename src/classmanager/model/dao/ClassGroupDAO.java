package classmanager.model.dao;

import classmanager.model.domain.ClassGroup;
import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.Status;
import classmanager.util.LoggerUtil;
import classmanager.util.StudentUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClassGroupDAO {

    private final Connection conn;
    private final LessonDAO lessonDAO;
    private static ClassGroupDAO instance;

    public ClassGroupDAO() {
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
        String sql = "CREATE TABLE IF NOT EXISTS classes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "students TEXT, "
                + "status TEXT"
                + ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LoggerUtil.logError("ClassGroupDAO - createTableIfNotExists", e);
        }
    }

    public void insertClassGroup(ClassGroup cg) {
        String sql = "INSERT INTO classes (name, students, status) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cg.getName());
            stmt.setString(2, String.join(",", Optional.ofNullable(StudentUtil.extractNames(cg.getStudents())).orElse(new ArrayList<>())));
            stmt.setString(3, cg.getStatus().name());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int classId = rs.getInt(1);
                if (cg.getLessons() != null && !cg.getLessons().isEmpty()) {
                    lessonDAO.insertLessons(classId, cg.getLessons());
                }
            }

        } catch (SQLException e) {
            LoggerUtil.logError("ClassGroupDAO - insertClassGroup", e);
        }
    }

    public List<ClassGroup> getAllClasses() {
        List<ClassGroup> cgs = new ArrayList<>();
        String sql = "SELECT * FROM classes";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ClassGroup cg = new ClassGroup();
                int id = rs.getInt("id");
                cg.setCgID(id);
                cg.setName(rs.getString("name"));
                //cg.setStudents(Arrays.asList(rs.getString("students").split(",")));
                cg.setStatus(Status.valueOf(rs.getString("status")));
                cg.setLessons(lessonDAO.getLessonsByClassId(id));

                cgs.add(cg);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("ClassGroupDAO - getAllClasses", e);
        }
        return cgs;
    }

    public void updateClassGroup(int classId, ClassGroup cg) {
        String sql = "UPDATE classes SET name = ?, students = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cg.getName());
            stmt.setString(2, String.join(",", StudentUtil.extractNames(cg.getStudents())));
            stmt.setString(3, cg.getStatus().name());
            stmt.setInt(4, classId);
            stmt.executeUpdate();

            // Atualiza lições: remove as antigas e salva novas
            lessonDAO.deleteLessonsByClassId(classId);
            if (cg.getLessons() != null && !cg.getLessons().isEmpty()) {
                lessonDAO.insertLessons(classId, cg.getLessons());
            }

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
