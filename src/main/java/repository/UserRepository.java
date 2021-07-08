package repository;

import business.User;

public interface UserRepository {
    void save(User u);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndNotId(String phoneNumber, Integer id);
    boolean existsByUsernameAndNotId(String username, Integer id);
}