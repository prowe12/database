import java.io.*;
import java.util.HashSet;

public class filecreater {
  /**
   * @author Penny Rowe
   * @date 2021/10/03
   */
  public static void main(String[] args) {
    String infile = "enrollment.raw.txt";
    String outfile = "DDL.sql";
    int headerlines = 2;
    try (
      BufferedReader ins = new BufferedReader(new FileReader(infile));
      PrintStream outs = new PrintStream(new FileOutputStream(outfile));
    ) {

      // Create the tables (and schemas)
      createTables(outs);

      // Read from the data file line by line and add to tables
      // Format:
      // studentID, studentName, class, gpa, major, CourseNum, deptID,
      // CourseName, Location, meetDay, meetTime, deptName, building
      String line;
      String[] tokens;
      
      // Skip the header lines:
      int i = headerlines;
      while (i > 0) {
        line = ins.readLine();
        i--;
      }

      // primary key sets
      HashSet<String> studentPK = new HashSet<String>();
      HashSet<String> majorPK = new HashSet<String>();
      HashSet<String> coursePK = new HashSet<String>();
      HashSet<String> deptPK = new HashSet<String>();
      HashSet<String> enrollPK = new HashSet<String>();

      // Create strings for insert statements
      String studentCommands = "";
      String deptCommands = "";
      String majorCommands = "";
      String courseCommands = "";
      String enrollCommands = "";

      // Loop over lines
      Integer count = 0;
      while ((line = ins.readLine()) != null) {
        tokens = line.split(",", -1);

        // Set the variables - if they are empty, replace with null
        String studentID = emptyToNull(tokens[0]); 
        String studentName = toString(tokens[1]);  
        String className = toString( tokens[2]); 
        String gpa = toString(tokens[3]);
        String major = emptyToNull(tokens[4]); 
        String courseNum = emptyToNull(tokens[5]); 
        String deptID = toString(tokens[6]); 
        String courseName = toString(tokens[7]); 
        String location = toString(tokens[8]); 
        String meetDay = toString(tokens[9]); 
        String meetTime = toString(tokens[10]); 
        String deptName = toString(tokens[11]); 
        String building = toString(tokens[12]); 


        // Cleaning the data
        // Check for multiple majors
        String majors[] = null;
        if (major != null) {
          majors = major.split(";"); 
        }

        // Fix class abbreviations
        if (className != null) {
          if (className.contentEquals("'JR'")) {
            className = "'Junior'";
          }
          else if (className.contentEquals("'SR'")) {
            className = "'Senior'";
          }
        }

        // Replace deptID and major ENG with ENGL
        if (deptID != null && deptID.contentEquals("'ENG'")) {
          deptID = "'ENGL'";
        }
        if (major != null) {
          for (i=0; i<majors.length; i++) {
            majors[i] = "'" + majors[i] + "'";
            if (majors[i].contentEquals("'ENG'")) {
              majors[i] = "'ENGL'";
            }
          }
        }

 
        // Get giant strings of all the inserts for each remaining table
        // We will update each table only if values are not null and the primary key is not
        // in the set of previously seen primary keys
        String valStr;
        // String to update the Student table
        if ((studentID != null) && (!studentPK.contains(studentID))) {
          valStr = studentID + ", " + studentName + ", " + className + ", " + gpa;
          String stmnt = "insert into Student values (" + valStr + ");\n";
          studentCommands += stmnt;
          studentPK.add(studentID);
        }

        // String to update the Dept table
        if ((deptID != null) && (!deptPK.contains(deptID))) {
          valStr = deptID + ", " + deptName + ", " + building;
          String stmnt = "insert into Dept values (" + valStr + ");\n";
          deptCommands += stmnt;
          deptPK.add(deptID);
        }

        // String to update the Course table
        if ((courseNum != null) && (deptID != null) && (!coursePK.contains(courseNum+deptID))) {
          String valStr1 = courseNum + ", " + deptID + ", " + courseName + ", " + location;
          String valStr2 = ", " + meetDay + ", " + meetTime;
          courseCommands += "insert into Course values (" + valStr1 + valStr2 + ");\n";
          coursePK.add(courseNum+deptID);
        }

        // String to update the Major table
        if ((studentID != null) && (major != null)) {
          for (i=0; i<majors.length; i++) {
            if (!majorPK.contains(studentID+majors[i])) {
              valStr = studentID + ", " + majors[i];
              majorCommands += "insert into Major values (" + valStr + ");\n";
              majorPK.add(studentID+majors[i]);
            }
          }
        }

        // String to update the Enroll table
        if ((courseNum != null) && (deptID != null) && studentID != null && (!enrollPK.contains(courseNum+deptID+studentID))) {
          valStr = courseNum + ", " + deptID + ", " + studentID;
          enrollCommands += "insert into Enroll values (" + valStr + ");\n";
          enrollPK.add(courseNum+deptID+studentID);
        }

        count ++;
      }

      // Print the strings to the output file in the correct order
      outs.println(studentCommands);
      outs.println(deptCommands);
      outs.println(courseCommands);
      outs.println(majorCommands);
      outs.println(enrollCommands);

    } catch (FileNotFoundException ex1) {
      System.err.println(ex1);
    } catch (IOException ex2) {
      System.err.println(ex2);
    }
  }

  /**
   * Convert attribute value (Float or empty) to
   * @param attval
   * @return
   */
  private static String emptyToNull(String attval) {
    if (attval.length() == 0) {
      return (String) null;
    }
    else {
      return attval;
    }
  }

  /**
   * Convert attribute value (Float or empty) to
   * @param attval
   * @return
   */
  private static String toString(String attval) {
    attval = emptyToNull(attval);
    if (attval == null) {
      return attval;
    }
    else {
      // Enclose in single quotes for SQL
      return "'" + attval + "'";
    }
  }


  /**
   * Write the headerlines that set up foreign keys and 
   * delete any old tables
   * @param outs
   */
  private static void createTables(PrintStream outs) {
      outs.println(" -- Turn on foreign keys");
      outs.println("PRAGMA foreign_keys = ON;");
      outs.println("");
      outs.println("-- Delete the tables if they already exist");
      outs.println("drop table if exists Course;");
      outs.println("drop table if exists Major;");
      outs.println("drop table if exists Enroll;");
      outs.println("drop table if exists Dept;");
      outs.println("drop table if exists Student;");
      outs.println("");
      
      // Create the schemas
      outs.println("-- Create the schema for the tables");

      // Create student table
      outs.println("CREATE TABLE Student (");
      outs.println("  studentID Integer primary key not null,");
      outs.println("  studentName String not null,");
      outs.println("  class String check(class IN (\"Freshman\", \"Sophomore\", \"Junior\", \"Senior\")),");
      outs.println("  gpa String check(gpa>='0.0' and gpa<='4.0')");
      outs.println(");");
      outs.println("");

      // Create Course table
      outs.println("CREATE TABLE Course (");
      outs.println("  courseNum Integer, ");
      outs.println("  deptID String, ");
      outs.println("  courseName String,");
      outs.println("  location String,");
      outs.println("  meetDay String,");
      outs.println("  meetTime String check(meetTime>='07:00' or meetTime <= '17:00'),");
      outs.println("  PRIMARY KEY(courseNum, deptID),");
      outs.println("  FOREIGN KEY (deptID) REFERENCES Dept(deptID)");
      outs.println("    ON UPDATE CASCADE");
      outs.println("    ON DELETE CASCADE");
      outs.println(");");
      outs.println("");

      // Create major table
      outs.println("CREATE TABLE Major (");
      outs.println("  studentID Integer,");
      outs.println("  major String,");
      outs.println("  PRIMARY KEY(studentID, major),");
      outs.println("  FOREIGN KEY (studentID) REFERENCES Student(studentID)");
      outs.println("    ON UPDATE CASCADE");
      outs.println("    ON DELETE CASCADE,");
      outs.println("  FOREIGN KEY (major) REFERENCES Dept(deptID)");
      outs.println("    ON UPDATE CASCADE");
      outs.println("    ON DELETE CASCADE");
      outs.println(");");
      outs.println("");

      // Create enroll table
      outs.println("CREATE TABLE Enroll (");
      outs.println("  courseNum Integer, ");
      outs.println("  deptID String, ");
      outs.println("  studentID Integer not null,");
      outs.println("  PRIMARY KEY (courseNum, deptID, studentID), ");
      outs.println("  FOREIGN KEY (courseNum, deptID) REFERENCES Course(courseNum, deptID)");
      outs.println("    ON UPDATE CASCADE");
      outs.println("    ON DELETE CASCADE,");
      outs.println("  FOREIGN KEY (studentID) REFERENCES Student(studentID)");
      outs.println("    ON UPDATE CASCADE");
      outs.println("    ON DELETE CASCADE");
      outs.println(");");
      outs.println("");

      // Create department table
      outs.println("CREATE TABLE Dept (");
      outs.println("  deptID String PRIMARY KEY check(length(deptID) <= 4), ");
      outs.println("  name String UNIQUE NOT NULL, ");
      outs.println("  building String");
      outs.println(");");
      outs.println("");
      
  }
}
