package jo.local.dev.jdbcquickstart.domain;

import java.time.LocalDate;
import java.util.Date;

//POJO
public class Student {

    private Long id;
    private String firstName;
    private String LastName;
    private Date birthdate;
    private String gender;
    private String entryAcademicPeriod;


    public Student() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEntryAcademicPeriod() {
        return entryAcademicPeriod;
    }

    public void setEntryAcademicPeriod(String entryAcademicPeriod) {
        this.entryAcademicPeriod = entryAcademicPeriod;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", birthdate=" + birthdate +
                ", gender='" + gender + '\'' +
                ", entryAcademicPeriod='" + entryAcademicPeriod + '\'' +
                '}';
    }
}
