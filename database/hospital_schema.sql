-- Create the hospital database
CREATE DATABASE hospital_db;

-- Select the database for use
USE hospital_db;

-- Stores patient personal and contact information
CREATE TABLE Patient (
    patient_id INT PRIMARY KEY AUTO_INCREMENT, -- Unique patient identifier
    first_name VARCHAR(50) NOT NULL,            -- Patient first name
    last_name VARCHAR(50) NOT NULL,             -- Patient last name
    date_of_birth DATE NOT NULL,                -- Date of birth
    gender VARCHAR(10),                         -- Gender
    phone VARCHAR(20),                          -- Contact number
    address TEXT,                               -- Residential address
    blood_type VARCHAR(5),                      -- Blood group
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Record creation time
);

-- Stores hospital departments
CREATE TABLE Department (
    department_id INT PRIMARY KEY AUTO_INCREMENT, -- Unique department ID
    name VARCHAR(100) NOT NULL UNIQUE,             -- Department name
    location VARCHAR(100)                          -- Department location
);

-- Stores doctor information and department association
CREATE TABLE Doctor (
    doctor_id INT PRIMARY KEY AUTO_INCREMENT, -- Unique doctor ID
    first_name VARCHAR(50) NOT NULL,           -- Doctor first name
    last_name VARCHAR(50) NOT NULL,            -- Doctor last name
    specialization VARCHAR(100),               -- Area of specialization
    phone VARCHAR(20),                          -- Contact number
    department_id INT NOT NULL,                -- Associated department
    FOREIGN KEY (department_id) REFERENCES Department(department_id)
);

-- Manages appointments between patients and doctors
CREATE TABLE Appointment (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT, -- Unique appointment ID
    patient_id INT NOT NULL,                       -- Patient reference
    doctor_id INT NOT NULL,                        -- Doctor reference
    appointment_date DATE NOT NULL,                -- Appointment date
    appointment_time TIME NOT NULL,                -- Appointment time
    status VARCHAR(20) DEFAULT 'Scheduled',        -- Appointment status

    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id)
);

-- Index to speed up patient search by last name
CREATE INDEX idx_patient_name ON Patient(last_name);

-- Index to optimize appointment date queries
CREATE INDEX idx_appointment_date ON Appointment(appointment_date);

-- Index to improve doctor lookup by department
CREATE INDEX idx_doctor_department ON Doctor(department_id);

-- Sample query to retrieve patient appointment details
SELECT
    p.first_name,
    p.last_name,
    a.appointment_date,
    a.status
FROM Appointment a
JOIN Patient p ON a.patient_id = p.patient_id;

-- Stores medical history records for patients
CREATE TABLE MedicalRecord (
    record_id INT PRIMARY KEY AUTO_INCREMENT,  -- Unique medical record ID
    patient_id INT NOT NULL,                    -- Associated patient
    doctor_id INT NOT NULL,                     -- Doctor who created the record
    record_date DATE NOT NULL,                  -- Date of the record
    diagnosis VARCHAR(255),                     -- Diagnosis details
    treatment VARCHAR(255),                     -- Treatment prescribed
    notes TEXT,                                 -- Additional notes
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp of entry

    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE CASCADE,
    FOREIGN KEY (doctor_id) REFERENCES Doctor(doctor_id)
);

-- Index to speed up retrieval by patient
CREATE INDEX idx_medicalrecord_patient ON MedicalRecord(patient_id);
