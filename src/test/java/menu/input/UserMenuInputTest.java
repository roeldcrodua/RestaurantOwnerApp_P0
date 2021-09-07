package menu.input;


import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

public class UserMenuInputTest {
    UserMenuInput userMenuInput = new UserMenuInput();


    @Test
    public void testIsInValid() {
        // assign
        Boolean expectedResult = false;

        // act
        String input = "2";
        Integer accountType = 1;
        Boolean actionResult = userMenuInput.isInValid(input, accountType);

        // assert
        assertEquals(expectedResult, actionResult);
    }
}