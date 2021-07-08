package business;

import validation.UniquePhoneNumber;
import validation.UniqueUsername;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="APP_USER")
@UniqueUsername(message = "username already claimed")
@UniquePhoneNumber(message = "phone number already claimed")
public class User implements Serializable {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="username", unique = true)
    private String username;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="phone_number", unique = true)
    private String phoneNumber;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts;

    @OneToOne
    @JoinColumn(name="main_account")
    private Account mainAccount;

    private boolean enabled = true;

    public User() {}

    public User(String username, String firstName, String lastName, String phoneNumber, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
