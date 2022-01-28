INSERT INTO student VALUES (1, 'Linh', 'Doan', 'linh.doan@gmail.com', to_date('12/07/1998', 'dd/mm/yyyy'));
INSERT INTO student VALUES (2, 'Lawre', 'MD', 'lawre@gmail.com', to_date('01/06/1995', 'dd/mm/yyyy'));
ALTER SEQUENCE student_student_id_sequence RESTART WITH 3;

INSERT INTO unit VALUES ('FIT2107', 'Quality Assurance');
INSERT INTO unit VALUES ('FIT2101', 'Project management');
INSERT INTO unit VALUES ('FIT2085', 'Mips unit');

INSERT INTO offering VALUES (1, 'FIT2107', '2021', '2');
INSERT INTO offering VALUES (2, 'FIT2101', '2021', '2');
INSERT INTO offering VALUES (3, 'FIT2085', '2021', '2');
INSERT INTO offering VALUES (4, 'FIT2085', '2021', '1');
ALTER SEQUENCE offering_sequence RESTART WITH 5;

INSERT INTO student_offering VALUES (1, 1);
INSERT INTO student_offering VALUES (1, 2);
INSERT INTO student_offering VALUES (2, 4);

INSERT INTO assessment VALUES (1, 1, to_date('10/9/2021', 'dd/mm/yyyy'), 0.1);
INSERT INTO assessment VALUES (2, 1, to_date('24/8/2021', 'dd/mm/yyyy'), 0.2);
INSERT INTO assessment VALUES (3, 1, to_date('01/10/2021', 'dd/mm/yyyy'), 0.2);
INSERT INTO assessment VALUES (4, 1, to_date('24/10/2021', 'dd/mm/yyyy'), 0.5);
ALTER SEQUENCE assessment_assessment_id_sequence RESTART WITH 5;