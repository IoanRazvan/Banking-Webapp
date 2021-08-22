package business;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class UserInformation implements Serializable {
    private String username;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String password;
}
