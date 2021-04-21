package gui.controllers;

import be.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.instrument.IllegalClassFormatException;
import java.util.IllegalFormatException;

import static org.junit.jupiter.api.Assertions.*;

class LoginViewControllerTest {

    @DisplayName("Tests if the log in works")
    @Test
    void attendanceLogin() {

        User testUser = new User(1, "Test", "User");
        String login = testUser.getId()+testUser.getFirstName()+testUser.getLastName();

        Exception error = Assertions.assertThrows(IllegalArgumentException.class, ()->{
            testUser.equals(login);
        });

        Assertions.assertEquals(login,testUser.getFirstName());

        String expectedMessage = "Username is invalid";
        String actualMessage = error.getMessage();
        Assertions.assertEquals(expectedMessage,actualMessage);

    }

}