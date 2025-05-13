package classmanager.model.dao;

import classmanager.model.database.DatabaseManager;
import classmanager.util.LoggerUtil;
import classmanager.model.domain.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SkillDAO {

    private final Connection conn;
    private static SkillDAO instance;

    private SkillDAO() {
        this.conn = DatabaseManager.getInstance().getConnection();
        createTableIfNotExists();
        populateTable();
    }

    public static SkillDAO getInstance() {
        if (instance == null) {
            instance = new SkillDAO();
        }
        return instance;
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS skills (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL" +
                ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            LoggerUtil.logError("SkillDAO - createTableIfNotExists", e);
        }
    }
    
    private void populateTable(){
        insert(new Skill("SPEAKING"));
        insert(new Skill("WRITING"));
        insert(new Skill("COVERSATION"));
        insert(new Skill("READING"));
        insert(new Skill("LISTENING"));
        insert(new Skill("GRAMMAR"));
    }
    

    public void insert(Skill skill) {
        String sql = "INSERT INTO skills (name) VALUES (?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, skill.getName());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                skill.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            LoggerUtil.logError("SkillDAO - insert", e);
        }
    }

    public void update(Skill skill) {
        String sql = "UPDATE skills SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, skill.getName());
            stmt.setInt(2, skill.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("SkillDAO - update", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM skills WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LoggerUtil.logError("SkillDAO - delete", e);
        }
    }

    public List<Skill> getAll() {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT * FROM skills";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getInt("id"));
                skill.setName(rs.getString("name"));
                skills.add(skill);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("SkillDAO - getAll", e);
        }
        return skills;
    }

    public Optional<Skill> getById(int id) {
        String sql = "SELECT * FROM skills WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getInt("id"));
                skill.setName(rs.getString("name"));
                return Optional.of(skill);
            }
        } catch (SQLException e) {
            LoggerUtil.logError("SkillDAO - getById", e);
        }
        return Optional.empty();
    }
}
