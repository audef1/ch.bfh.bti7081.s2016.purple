package ch.bfh.bti7081.s2016.purple.HealthVisitor.data.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * @author tgdflto1
 *
 * This class has single table inheritance because not all persons have the same attributes
 *
 * https://wiki.eclipse.org/EclipseLink/Examples/JPA/Inheritance
 */

@Entity(name="person")
@Table(name="PERSON")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING,length=20)
@DiscriminatorValue("P")
public class PersonEntity {

    public PersonEntity(String firstName, String lastName, Date dateOfBirth, String email,  String password ) {
        this.setId(id);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPassword(password);
        this.setEmail(email);
        this.setDateOfBirth(dateOfBirth);
    }

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE)
    private int id;

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Date dateOfBirth;

    public PersonEntity() {    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFullName(){
        StringBuilder sb = new StringBuilder();
        if(getFirstName() != null) sb.append(getFirstName());
        if(getLastName() != null) sb.append(" ").append(getLastName());
    	return sb.toString();
    }

	public Date getDateOfBirth() {
		return this.dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


}
