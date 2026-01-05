package dao;

import model.MedicalRecord;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * MedicalRecordDAO handles CRUD operations for MedicalRecord table
 */
public class MedicalRecordDAO {

    // CREATE: Add a new medical record
    public boolean addMedicalRecord(MedicalRecord record) {
        String sql = "INSERT INTO MedicalRecord (patient_id, doctor_id, record_date, diagnosis, treatment, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, record.getPatientId());
            stmt.setInt(2, record.getDoctorId());
            stmt.setDate(3, Date.valueOf(record.getRecordDate()));
            stmt.setString(4, record.getDiagnosis());
            stmt.setString(5, record.getTreatment());
            stmt.setString(6, record.getNotes());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ: Get all medical records for a patient (Medical History)
    public List<MedicalRecord> getMedicalHistory(int patientId) {
        List<MedicalRecord> records = new ArrayList<>();
        String sql = "SELECT * FROM MedicalRecord WHERE patient_id=? ORDER BY record_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                MedicalRecord record = new MedicalRecord(
                        rs.getInt("record_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("record_date").toLocalDate(),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getString("notes"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                records.add(record);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return records;
    }

    // UPDATE: Modify an existing medical record
    public boolean updateMedicalRecord(MedicalRecord record) {
        String sql = "UPDATE MedicalRecord SET patient_id=?, doctor_id=?, record_date=?, diagnosis=?, treatment=?, notes=? " +
                "WHERE record_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, record.getPatientId());
            stmt.setInt(2, record.getDoctorId());
            stmt.setDate(3, Date.valueOf(record.getRecordDate()));
            stmt.setString(4, record.getDiagnosis());
            stmt.setString(5, record.getTreatment());
            stmt.setString(6, record.getNotes());
            stmt.setInt(7, record.getRecordId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE: Remove a medical record
    public boolean deleteMedicalRecord(int recordId) {
        String sql = "DELETE FROM MedicalRecord WHERE record_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recordId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ: Get a single medical record by ID
    public MedicalRecord getMedicalRecordById(int recordId) {
        String sql = "SELECT * FROM MedicalRecord WHERE record_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, recordId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MedicalRecord(
                        rs.getInt("record_id"),
                        rs.getInt("patient_id"),
                        rs.getInt("doctor_id"),
                        rs.getDate("record_date").toLocalDate(),
                        rs.getString("diagnosis"),
                        rs.getString("treatment"),
                        rs.getString("notes"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
