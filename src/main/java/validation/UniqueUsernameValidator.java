package validation;

import business.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {
    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void initialize(UniqueUsername uniqueUsername) {
        // Nothing to do
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getId() == null ? ! userRepository.existsByUsername(user.getUsername()) :
                ! userRepository.existsByUsernameAndNotId(user.getUsername(), user.getId());
    }
}
