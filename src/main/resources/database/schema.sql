-- Wipe everything before creating the tables to make the script idempotent.
DROP SCHEMA public cascade;
CREATE SCHEMA public;

CREATE TABLE unit (
                      unit_code TEXT PRIMARY KEY ,
                      unit_name TEXT NOT NULL
);
CREATE INDEX ON unit(unit_code);

CREATE SEQUENCE student_student_id_sequence INCREMENT BY 1;
CREATE TABLE student (
                         student_id INT PRIMARY KEY DEFAULT nextval('student_student_id_sequence'),
                         first_name TEXT NOT NULL,
                         last_name TEXT NOT NULL,
                         email TEXT NOT NULL,
                         dob DATE NOT NULL,
                         CONSTRAINT student_address_unique UNIQUE (email)
);
ALTER SEQUENCE student_student_id_sequence OWNED BY student.student_id;
CREATE INDEX ON student(last_name);

CREATE SEQUENCE offering_sequence INCREMENT BY 1;
CREATE TABLE offering (
                          offering_id INT PRIMARY KEY DEFAULT nextval('offering_sequence'),
                          unit_code TEXT NOT NULL REFERENCES unit (unit_code),
                          offering_year TEXT NOT NULL,
                          offering_semester VARCHAR(1) NOT NULL,
                          CONSTRAINT unique_offering  UNIQUE (unit_code, offering_year, offering_semester)
);
ALTER SEQUENCE offering_sequence OWNED BY offering.offering_id;
CREATE INDEX ON offering(unit_code);

CREATE TABLE student_offering (
                                  student_id INT NOT NULL REFERENCES student(student_id),
                                  offering_id INT NOT NULL REFERENCES offering(offering_id),
                                  UNIQUE (student_id, offering_id)
);

CREATE SEQUENCE assessment_assessment_id_sequence AS INT INCREMENT BY 1;
CREATE TABLE assessment(
                           assessment_id INT PRIMARY KEY NOT NULL DEFAULT nextval('assessment_assessment_id_sequence'),
                           offering_id INT NOT NULL,
                           assessment_date DATE NOT NULL,
                           assessment_weight REAL NOT NULL,
                           CONSTRAINT unique_assessment UNIQUE (offering_id, assessment_date)
);
ALTER SEQUENCE assessment_assessment_id_sequence OWNED BY assessment.assessment_id;