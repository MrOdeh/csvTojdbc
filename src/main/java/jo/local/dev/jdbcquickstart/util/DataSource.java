package jo.local.dev.jdbcquickstart.util;

import jo.local.dev.jdbcquickstart.domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public final class DataSource {

    private static final DataSource dataSource = new DataSource();

    public static final String DATABASE = "studentsdb";
    private static final String TABLE_NAME = "student";
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DATABASE;
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pass1234";
    private static final String INSERT_STUDENT = "INSERT INTO student (first_name, last_name, date_of_birth," +
            " gender, entry_academic_period) VALUES" +
            " (?, ?, ?, ?, ?);";

    private PreparedStatement insertStudent;

    private Connection connection;

    private DataSource() {
    }

    public static DataSource getInstance(){
        return dataSource;
    }

    public void open(){
        try {
            connection =  DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            System.out.println("Something wrong happend");
        }
    }

    public void close(){
        try {
            connection.close();
        } catch (SQLException throwables) {
            System.out.println("Something wrong happend");
        }
    }

    public List<Object[]> execute(String query) {
        open();
        final long start = System.currentTimeMillis();
        List<Object[]> rows = new ArrayList<>();
        try(Statement statement = connection.createStatement();
            ResultSet resultSet  = statement.executeQuery(query);){
            while (resultSet.next()) {
                int cols = resultSet.getMetaData().getColumnCount();
                Object[] arr = new Object[cols];
                for(int i=0; i<cols; i++) {
                    arr[i] = resultSet.getObject(i + 1);
                }
                rows.add(arr);
            }
        }catch (SQLException e){
            e.printStackTrace();
            close();
        }finally {
            final long end = System.currentTimeMillis();
            System.out.println("Exection Time : " + (end - start));
            close();
            return rows;
        }
    }

    public boolean insert(List<Student> students) throws SQLException {

        open();
        try {
            insertStudent = connection.prepareStatement(INSERT_STUDENT);
            for (Student student : students) {

                try (Statement statement = connection.createStatement()) {

                    insertStudent.setString(1, student.getFirstName());
                    insertStudent.setString(2, student.getLastName());
                    insertStudent.setDate(3,  new java.sql.Date(student.getBirthdate().getTime()));
                    insertStudent.setString(4, student.getGender());
                    insertStudent.setString(5, student.getEntryAcademicPeriod());

                    insertStudent.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            close();
        }

        return true;
    }

}
