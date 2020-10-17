package jo.local.dev.jdbcquickstart;

import jo.local.dev.jdbcquickstart.domain.Student;
import jo.local.dev.jdbcquickstart.util.DataSource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String STUDENTS_QUERY = "select * from student";

    public static void main(String[] args) throws SQLException {

        //final List<Student> studentList = execute(STUDENTS_QUERY);

        final List<Student> csvStudents = csvParser();
        insertData(csvStudents);
        //csvStudents.forEach(p-> System.out.println(p));


    }

    public static List<Student> execute(String query) {
        List<Student> students = new ArrayList<>();
        DataSource.getInstance().execute(query).forEach(
                p -> {
                    Student student = new Student();
                    student.setId(Long.valueOf(p[0].toString()));
                    student.setFirstName((String) p[1]);
                    student.setLastName((String) p[2]);
                    try {
                        student.setBirthdate(new SimpleDateFormat("dd-MM-yyyy").parse(p[3].toString()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    student.setGender((String) p[4]);
                    student.setEntryAcademicPeriod((String) p[5]);

                    students.add(student);

                });
        return students;

    }

    public static List<Student> csvParser(){
        List<Student> csvStudentData = new ArrayList<>();
        String csvFile = "/home/devop/Public/generalFiles/students.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));
            boolean HeaderSkipped = false;
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if(HeaderSkipped) {
                    String[] rowData = line.split(cvsSplitBy);

                    Student student = new Student();
                    student.setId(Long.parseLong(rowData[0]));
                    student.setFirstName((String) rowData[1]);
                    student.setLastName((String) rowData[2]);
                    student.setBirthdate(new SimpleDateFormat("MM-dd-yyyy").parse(rowData[3].toString()));
                    student.setGender((String) rowData[4]);
                    student.setEntryAcademicPeriod((String) rowData[5]);
                    csvStudentData.add(student);
                }
                HeaderSkipped = true;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return csvStudentData;
    }

    public static void insertData(List<Student> students) throws SQLException {
        DataSource.getInstance().insert(students);

    }

}
