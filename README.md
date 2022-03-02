# enrolmentSystem

First spring project

This system manages (storing, adding, deleting, retrieving) information about students and units. For each unit, data about their offerings (semesters, years offered) and assessments for each offering are included. The system can enrol or unenrol students in unit offerings. It also acts as an API for scheduling system (https://github.com/Linh-Doan/schedulingSystem) 

A postman collection (https://github.com/Linh-Doan/enrolmentSystem/blob/main/Enrolment_system.postman_collection.json) is generated for ease of testing its functionalities

H2 and postgres are 2 database options. H2 does not need to be set up. To run this using postgres, download postgreSQL (https://www.postgresql.org/download/), then create a database with a name of choice.The default port for postgreSQL is 5432 so if your machine has an existing program running in this, the port option can be changed while setting up postgreSQL. When connecting your Java IDE to postgreSQL, choose the authentification method as User and Password and leave them both blank. In application.properties file, change the value of spring.datasource.url to jdbc:postgresql://localhost:{port_used}/{database_name}.

Technologies used:
- Spring boot
- Postgresql database
- Postman
- JUnit
