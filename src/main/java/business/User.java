package business;

import lombok.Getter;
import lombok.Setter;
import validation.UniquePhoneNumber;
import validation.UniqueUsername;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name="APP_USER")
@UniqueUsername(message = "username already claimed")
@UniquePhoneNumber(message = "phone number already claimed")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "username", column = @Column(name = "username", unique = true)),
            @AttributeOverride(name = "firstName", column = @Column(name = "first_name")),
            @AttributeOverride(name = "lastName", column = @Column(name = "last_name")),
            @AttributeOverride(name = "phoneNumber", column = @Column(name = "phone_number", unique = true)),
            @AttributeOverride(name = "password", column = @Column(name = "password")),
    })
    private UserInformation userInfo;

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts;

    @OneToOne
    @JoinColumn(name = "main_account")
    private Account mainAccount;

    private boolean enabled = true;

    public UserInformation updateUserInfo(UserInformation newUserInfo) {
        UserInformation oldUserInfo = userInfo;
        userInfo = newUserInfo;
        return oldUserInfo;
    }

    public String getUsername() {
        return userInfo == null ?  null : userInfo.getUsername();
    }

    public String getPhoneNumber() {
        return userInfo == null ? null : userInfo.getPhoneNumber();
    }

    public String getPassword() {
        return userInfo == null ? null : userInfo.getPassword();
    }

    public void setPassword(String password) {
        if (userInfo != null)
            userInfo.setPassword(password);
    }

    public User() {}

    public User(UserInformation userInfo) {
        this.userInfo = userInfo;
    }

    public User(Integer id, UserInformation userInfo) {
        this.id = id;
        this.userInfo = userInfo;
    }

    public User(User otherUser) {
        this.id = otherUser.id;
        this.userInfo = new UserInformation(otherUser.userInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (enabled != user.enabled) return false;
        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(userInfo, user.userInfo)) return false;
        if (!Objects.equals(accounts, user.accounts)) return false;
        return Objects.equals(mainAccount, user.mainAccount);
    }

}
