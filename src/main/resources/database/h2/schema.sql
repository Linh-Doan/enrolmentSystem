DROP TABLE assessment IF EXISTS;
DROP TABLE student_offering IF EXISTS;
DROP TABLE offering IF EXISTS;
DROP TABLE student IF EXISTS;
DROP TABLE unit IF EXISTS;

CREATE TABLE unit (
                      unit_code VARCHAR(30) PRIMARY KEY,
                      unit_name VARCHAR(30) NOT NULL
);
CREATE INDEX ON unit(unit_code);

CREATE SEQUENCE student_student_id_sequence INCREMENT BY 1;
CREATE TABLE student (
                         student_id INTEGER DEFAULT NEXT VALUE FOR student_student_id_sequence PRIMARY KEY,
                         first_name VARCHAR(30) NOT NULL,
                         last_name VARCHAR(30) NOT NULL,
                         email VARCHAR(30) NOT NULL,
                         dob DATE NOT NULL
);
ALTER TABLE student ADD CONSTRAINT student_address_unique UNIQUE (email);
CREATE INDEX ON student(last_name);

CREATE SEQUENCE offering_sequence INCREMENT BY 1;
CREATE TABLE offering (
                          offering_id INTEGER DEFAULT NEXT VALUE FOR offering_sequence PRIMARY KEY,
                          unit_code VARCHAR(30) NOT NULL,
                          offering_year VARCHAR(30) NOT NULL,
                          offering_semester VARCHAR(1) NOT NULL
);
ALTER TABLE offering ADD CONSTRAINT fk_unit_code_offering FOREIGN KEY (unit_code) REFERENCES unit (unit_code);
ALTER TABLE offering ADD CONSTRAINT unique_offering UNIQUE (unit_code, offering_year, offering_semester);
CREATE INDEX ON offering(unit_code);

CREATE TABLE student_offering (
                                  student_id INTEGER NOT NULL,
                                  offering_id INTEGER NOT NULL
);
ALTER TABLE student_offering ADD PRIMARY KEY (student_id, offering_id);
ALTER TABLE student_offering ADD CONSTRAINT fk_student_id_so FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_offering ADD CONSTRAINT fk_offering_id_so FOREIGN KEY (offering_id) REFERENCES offering (offering_id);
ALTER TABLE student_offering ADD CONSTRAINT unique_student_offering UNIQUE (student_id, offering_id);

CREATE SEQUENCE assessment_assessment_id_sequence INCREMENT BY 1;
CREATE TABLE assessment(
                           assessment_id INTEGER DEFAULT NEXT VALUE FOR assessment_assessment_id_sequence PRIMARY KEY,
                           offering_id INTEGER NOT NULL,
                           assessment_date DATE NOT NULL,
                           assessment_weight REAL NOT NULL
);
ALTER TABLE assessment ADD CONSTRAINT fk_offering_id_assessment FOREIGN KEY (offering_id) REFERENCES offering (offering_id);
ALTER TABLE assessment ADD CONSTRAINT unique_assessment UNIQUE (offering_id, assessment_date);
CREATE INDEX ON assessment(assessment_date);