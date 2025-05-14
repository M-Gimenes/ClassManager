package classmanager.model.dao;

import classmanager.model.database.DatabaseManager;
import classmanager.model.domain.Lesson;
import classmanager.model.domain.Skill;
import classmanager.model.domain.Student;
import classmanager.util.LoggerUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LessonDAO {

    private final Connection conn;
    private static LessonDAO instance;
    private LessonSkillDAO lSkillDAO;
    private LessonStudentDAO lStudentDAO;

    private LessonDAO() {
        this.conn = DatabaseManager.getInstance().getConnection();
        lSkillDAO = LessonSkillDAO.getInstance();
        lStudentDAO = LessonStudentDAO.getInstance();
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

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int lessonId = generatedKeys.getInt(1);
                    lesson.setId(lessonId);
                    for (Skill skill : lesson.getSkills()) {
                        lSkillDAO.insert(lessonId, skill.getId());
                    }
                    for (Student student : lesson.getStudents()) {
                        lStudentDAO.insert(lessonId, student.getId());
                    }
                }
            }

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
                    lesson.setSkills(lSkillDAO.getSkillsByLessonId(lesson.getId()));
                    lesson.setStudents(lStudentDAO.getStudentsByLessonId(lesson.getId()));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - getLessonsByClassId", e);
        }
        return lessons;
    }

    public void updateLesson(Lesson lesson) {
        String sql = "UPDATE lessons SET class_id = ?, day = ?, content = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            stmt.setInt(1, lesson.getClassId());
            stmt.setString(2, lesson.getDay().toString());
            stmt.setString(3, lesson.getContent());
            stmt.setInt(4, lesson.getId());
            stmt.executeUpdate();

            lSkillDAO.deleteByLessonId(lesson.getId());
            lStudentDAO.deleteByLessonId(lesson.getId());

            for (Skill skill : lesson.getSkills()) {
                lSkillDAO.insert(lesson.getId(), skill.getId());
            }

            for (Student student : lesson.getStudents()) {
                lStudentDAO.insert(lesson.getId(), student.getId());
            }

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                LoggerUtil.logError("LessonDAO - updateLesson - rollback", rollbackEx);
            }
            LoggerUtil.logError("LessonDAO - updateLesson", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                LoggerUtil.logError("LessonDAO - updateLesson - setAutoCommit(true)", e);
            }
        }
    }

    public void deleteLesson(int lessonId) {
        String sql = "DELETE FROM lessons WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);

            lSkillDAO.deleteByLessonId(lessonId);
            lStudentDAO.deleteByLessonId(lessonId);

            stmt.setInt(1, lessonId);
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                LoggerUtil.logError("LessonDAO - deleteLesson - rollback", rollbackEx);
            }
            LoggerUtil.logError("LessonDAO - deleteLesson", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                LoggerUtil.logError("LessonDAO - deleteLesson - setAutoCommit(true)", e);
            }
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

    //Regra de negócio
    public List<Lesson> getLessonsByClassIdAndDate(int classId, LocalDate day) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT * FROM lessons WHERE class_id = ? AND day = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, classId);
            stmt.setString(2, day.toString());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(rs.getInt("id"));
                    lesson.setClassId(rs.getInt("class_id"));
                    lesson.setDay(LocalDate.parse(rs.getString("day")));
                    lesson.setContent(rs.getString("content"));
                    lesson.setSkills(lSkillDAO.getSkillsByLessonId(lesson.getId()));
                    lesson.setStudents(lStudentDAO.getStudentsByLessonId(lesson.getId()));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - getLessonsByClassIdAndDate", e);
        }
        return lessons;
    }

    public List<Lesson> getLessonsByStudentId(int studentId) {
        List<Lesson> lessons = new ArrayList<>();
        String sql = "SELECT l.* FROM lessons l "
                + "JOIN lesson_students ls ON l.id = ls.lesson_id "
                + "WHERE ls.student_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Lesson lesson = new Lesson();
                    lesson.setId(rs.getInt("id"));
                    lesson.setClassId(rs.getInt("class_id"));
                    lesson.setDay(LocalDate.parse(rs.getString("day")));
                    lesson.setContent(rs.getString("content"));
                    lesson.setSkills(lSkillDAO.getSkillsByLessonId(lesson.getId()));
                    lesson.setStudents(lStudentDAO.getStudentsByLessonId(lesson.getId()));
                    lessons.add(lesson);
                }
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - getLessonsByStudentId", e);
        }

        return lessons;
    }

    public Map<String, Integer> getLessonCountPerClass() {
        Map<String, Integer> classLessonMap = new HashMap<>();
        String sql = "SELECT c.name AS class_name, COUNT(l.id) AS total_lessons "
                + "FROM lessons l "
                + "JOIN classes c ON l.class_id = c.id "
                + "GROUP BY c.name";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                classLessonMap.put(rs.getString("class_name"), rs.getInt("total_lessons"));
            }
        } catch (SQLException e) {
            LoggerUtil.logError("LessonDAO - getLessonCountPerClass", e);
        }

        return classLessonMap;
    }
}
