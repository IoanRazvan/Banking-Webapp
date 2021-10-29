package repository;

import business.User;
import business.UserInformation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryImplTest {
    private UserRepository userRepo = new UserRepositoryImpl();
    private final static User TEST_USER = new User(1, new UserInformation("test", "First", "Name", "0777777777", "password"));
    private final static String NEW_FIRST_NAME = "NewFirstName";

    private static Stream<Arguments> existsByUsernameValues() {
        return Stream.of(
                Arguments.of("test", true),
                Arguments.of("nonexistent", false)
        );
    }

    @ParameterizedTest
    @MethodSource("existsByUsernameValues")
    public void existsByUsernameShouldReturnTrueIfTheSpecifiedUsernameExistsInDatabase(String username, boolean expected) {
        assertEquals(expected, userRepo.existsByUsername(username));
    }

    @Test
    @Tag("update-test")
    public void updateWithValidUserShouldChangeTheUserInTheDatabase() {
        User updatedUser = new User(TEST_USER);
        updatedUser.getUserInfo().setFirstName(NEW_FIRST_NAME);
        userRepo.update(updatedUser);
        Optional<User> retrievedUserAfterUpdate = userRepo.findByUsername(updatedUser.getUsername());
        assertTrue(retrievedUserAfterUpdate.isPresent());
        assertEquals(updatedUser, retrievedUserAfterUpdate.get());
    }

    @AfterEach
    private void updateMethodCleanup(TestInfo testInfo) {
        if (testInfo.getTags().contains("update-test")) {
            userRepo.update(TEST_USER);
        }
    }
}
