package business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class UserInformation implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;

    public UserInformation(UserInformation otherUserInfo) {
        username = otherUserInfo.username;
        firstName = otherUserInfo.firstName;
        lastName = otherUserInfo.lastName;
        phoneNumber = otherUserInfo.phoneNumber;
        password = otherUserInfo.password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInformation that = (UserInformation) o;

        if (!Objects.equals(username, that.username)) return false;
        if (!Objects.equals(firstName, that.firstName)) return false;
        if (!Objects.equals(lastName, that.lastName)) return false;
        if (!Objects.equals(phoneNumber, that.phoneNumber)) return false;
        return Objects.equals(password, that.password);
    }
}
