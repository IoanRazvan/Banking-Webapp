package encoders;

public interface PasswordEncoder {
    String encode(String password);
    boolean matches(String s, String s1);
}
