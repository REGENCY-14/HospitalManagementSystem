package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MedicalRecord {
    private int recordId;
    private int patientId;
    private int doctorId;
    private LocalDate recordDate;
    private String diagnosis;
    private String treatment;
    private String notes;
    private LocalDateTime createdAt;

    // Constructors
    public MedicalRecord() {}

    public MedicalRecord(int patientId, int doctorId, LocalDate recordDate,
                         String diagnosis, String treatment, String notes) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.recordDate = recordDate;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.notes = notes;
    }

    public MedicalRecord(int recordId, int patientId, int doctorId, LocalDate recordDate,
                         String diagnosis, String treatment, String notes, LocalDateTime createdAt) {
        this(patientId, doctorId, recordDate, diagnosis, treatment, notes);
        this.recordId = recordId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
