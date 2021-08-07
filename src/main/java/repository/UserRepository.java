package repository;

import business.User;

import java.util.Optional;

public interface UserRepository {
    void save(User u);
    void update(User u);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberAndNotId(String phoneNumber, Integer id);
    boolean existsByUsernameAndNotId(String username, Integer id);
    Optional<User> findByUsername(String username);
}