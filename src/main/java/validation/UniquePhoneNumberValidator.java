package validation;

import business.User;
import repository.UserRepository;
import repository.UserRepositoryImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, User> {
    private UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void initialize(UniquePhoneNumber uniquePhoneNumber) {
        // Nothing to do
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getId() == null ? ! userRepository.existsByPhoneNumber(user.getPhoneNumber())
                : ! userRepository.existsByPhoneNumberAndNotId(user.getPhoneNumber(), user.getId());
    }
}
