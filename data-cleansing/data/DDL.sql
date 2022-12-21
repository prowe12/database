 -- Turn on foreign keys
PRAGMA foreign_keys = ON;

-- Delete the tables if they already exist
drop table if exists Course;
drop table if exists Major;
drop table if exists Enroll;
drop table if exists Dept;
drop table if exists Student;

-- Create the schema for the tables
CREATE TABLE Student (
  studentID Integer primary key not null,
  studentName String not null,
  class String check(class IN ("Freshman", "Sophomore", "Junior", "Senior")),
  gpa String check(gpa>='0.0' and gpa<='4.0')
);

CREATE TABLE Course (
  courseNum Integer, 
  deptID String, 
  courseName String,
  location String,
  meetDay String,
  meetTime String check(meetTime>='07:00' or meetTime <= '17:00'),
  PRIMARY KEY(courseNum, deptID),
  FOREIGN KEY (deptID) REFERENCES Dept(deptID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Major (
  studentID Integer,
  major String,
  PRIMARY KEY(studentID, major),
  FOREIGN KEY (studentID) REFERENCES Student(studentID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (major) REFERENCES Dept(deptID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Enroll (
  courseNum Integer, 
  deptID String, 
  studentID Integer not null,
  PRIMARY KEY (courseNum, deptID, studentID), 
  FOREIGN KEY (courseNum, deptID) REFERENCES Course(courseNum, deptID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (studentID) REFERENCES Student(studentID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Dept (
  deptID String PRIMARY KEY check(length(deptID) <= 4), 
  name String UNIQUE NOT NULL, 
  building String
);

insert into Student values (1001, 'Lia', 'Junior', '3.6');
insert into Student values (1282, 'Kelly', 'Freshman', '2.5');
insert into Student values (1025, 'John', 'Senior', '3.6');
insert into Student values (1247, 'Alexis', 'Senior', '3.9');
insert into Student values (1101, 'Haley', 'Senior', '4.0');
insert into Student values (1304, 'Jordan', 'Senior', '2.9');
insert into Student values (1709, 'Cassandra', 'Junior', '2.8');
insert into Student values (1225, 'Sarah', 'Freshman', '2.9');
insert into Student values (1911, 'David', 'Senior', '3.2');
insert into Student values (1510, 'Jordan', 'Freshman', '3.0');
insert into Student values (1661, 'Logan', 'Freshman', '0.5');
insert into Student values (1316, 'Austin', 'Sophomore', '2.1');
insert into Student values (1381, 'Tiffany', 'Junior', '4.0');
insert into Student values (1468, 'Kris', 'Sophomore', '1.0');
insert into Student values (1487, 'Erin', 'Sophomore', '3.9');
insert into Student values (1501, 'Jessica', 'Freshman', '3.3');
insert into Student values (1934, 'Kyle', 'Junior', '2.1');
insert into Student values (1782, 'Andrew', 'Sophomore', '3.7');
insert into Student values (1629, 'Brad', 'Senior', '1.6');
insert into Student values (1640, 'Adam', 'Senior', '3.6');
insert into Student values (1641, 'Brittany', 'Senior', '2.7');
insert into Student values (1689, 'Gabriel', 'Senior', '2.4');

insert into Dept values ('BUS', 'School of Business', 'McIntyre Hall');
insert into Dept values ('PHYS', 'Department of Physics', 'Harned Hall');
insert into Dept values ('HIST', 'Department of History', 'Wyatt Hall');
insert into Dept values ('MATH', 'Department of Mathematics', 'Tower of Babel');
insert into Dept values ('CSCI', 'School of Computer Science', 'Thompson Hall');
insert into Dept values ('SOAN', 'Department of Anthropology', 'Wyatt Hall');
insert into Dept values ('ENGL', 'Department of English', 'Wyatt Hall');

insert into Course values (122, 'BUS', 'Economics', 'WY 30', 'MW', '13:30');
insert into Course values (101, 'PHYS', 'How Things Move', 'HH 191', 'MWF', '10:00');
insert into Course values (320, 'MATH', 'Discrete Mathematics', 'TH 307', 'F', '11:00');
insert into Course values (120, 'MATH', 'Algebra', 'MH 10', 'MW', '12:00');
insert into Course values (351, 'CSCI', 'Database Systems', 'TH 19', 'MW', '12:00');
insert into Course values (230, 'MATH', 'Linear Algebra', 'HH 308', 'TR', '15:00');
insert into Course values (102, 'SOAN', 'Sociology 2', 'WY 205', 'MTWRF', '09:00');
insert into Course values (401, 'PHYS', 'Quantum Mechanics', 'HH 372', 'TR', '09:00');
insert into Course values (101, 'ENGL', 'How to Read', 'WY 100', 'MWF', '13:00');
insert into Course values (453, 'CSCI', 'Capstone in Computer Science', 'TH 398', 'MWF', '16:00');
insert into Course values (520, 'ENGL', 'Shakespeare Was Da Bomb', 'HH 20', 'TR', '13:00');
insert into Course values (520, 'CSCI', 'High Performance Computing', 'WY 307', 'TR', '15:00');
insert into Course values (330, 'MATH', 'Trigonometry', 'WEY 113', 'TR', '08:30');
insert into Course values (351, 'BUS', 'Finance', 'WY 29', 'TR', '12:00');
insert into Course values (122, 'CSCI', 'How to Code Good', 'TH 19', 'TR', '12:00');
insert into Course values (102, 'ENGL', 'How to Write', 'WY 100', 'MWF', '14:00');
insert into Course values (460, 'CSCI', 'Operating Systems', 'TH 8', 'MW', '14:00');
insert into Course values (101, 'SOAN', 'Sociology 1', 'WY 105', 'MWF', '08:00');
insert into Course values (460, 'MATH', 'Calculus 3', 'WEY 102', 'TR', '12:30');

insert into Major values (1001, 'ENGL');
insert into Major values (1025, 'ENGL');
insert into Major values (1247, 'ENGL');
insert into Major values (1101, 'BUS');
insert into Major values (1101, 'MATH');
insert into Major values (1304, 'MATH');
insert into Major values (1709, 'CSCI');
insert into Major values (1709, 'SOAN');
insert into Major values (1911, 'CSCI');
insert into Major values (1911, 'ENGL');
insert into Major values (1510, 'MATH');
insert into Major values (1510, 'PHYS');
insert into Major values (1661, 'CSCI');
insert into Major values (1316, 'CSCI');
insert into Major values (1381, 'CSCI');
insert into Major values (1468, 'ENGL');
insert into Major values (1487, 'ENGL');
insert into Major values (1501, 'CSCI');
insert into Major values (1934, 'BUS');
insert into Major values (1934, 'ENGL');
insert into Major values (1782, 'BUS');
insert into Major values (1641, 'ENGL');
insert into Major values (1689, 'BUS');

insert into Enroll values (122, 'BUS', 1282);
insert into Enroll values (101, 'PHYS', 1025);
insert into Enroll values (320, 'MATH', 1247);
insert into Enroll values (120, 'MATH', 1101);
insert into Enroll values (351, 'CSCI', 1247);
insert into Enroll values (101, 'PHYS', 1304);
insert into Enroll values (230, 'MATH', 1101);
insert into Enroll values (102, 'SOAN', 1709);
insert into Enroll values (401, 'PHYS', 1101);
insert into Enroll values (101, 'ENGL', 1225);
insert into Enroll values (453, 'CSCI', 1247);
insert into Enroll values (453, 'CSCI', 1911);
insert into Enroll values (520, 'ENGL', 1025);
insert into Enroll values (351, 'CSCI', 1282);
insert into Enroll values (520, 'CSCI', 1247);
insert into Enroll values (351, 'CSCI', 1510);
insert into Enroll values (101, 'ENGL', 1247);
insert into Enroll values (351, 'BUS', 1025);
insert into Enroll values (351, 'CSCI', 1661);
insert into Enroll values (102, 'ENGL', 1304);
insert into Enroll values (122, 'BUS', 1316);
insert into Enroll values (351, 'CSCI', 1025);
insert into Enroll values (460, 'CSCI', 1316);
insert into Enroll values (351, 'CSCI', 1501);
insert into Enroll values (122, 'BUS', 1510);
insert into Enroll values (453, 'CSCI', 1934);
insert into Enroll values (230, 'MATH', 1782);
insert into Enroll values (351, 'BUS', 1510);
insert into Enroll values (351, 'BUS', 1304);
insert into Enroll values (520, 'ENGL', 1304);
insert into Enroll values (351, 'BUS', 1661);
insert into Enroll values (520, 'CSCI', 1025);
insert into Enroll values (520, 'ENGL', 1689);
insert into Enroll values (460, 'CSCI', 1661);
insert into Enroll values (520, 'ENGL', 1782);
insert into Enroll values (230, 'MATH', 1911);
insert into Enroll values (460, 'CSCI', 1689);
insert into Enroll values (460, 'MATH', 1661);
insert into Enroll values (351, 'CSCI', 1911);
insert into Enroll values (351, 'CSCI', 1934);
insert into Enroll values (520, 'ENGL', 1934);

