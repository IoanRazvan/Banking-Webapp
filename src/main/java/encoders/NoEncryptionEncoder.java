package encoders;

public class NoEncryptionEncoder implements PasswordEncoder {
    @Override
    public String encode(String password) {
        return password;
    }

    @Override
    public boolean matches(String s, String s1) {
        return s.equals(s1);
    }
}
