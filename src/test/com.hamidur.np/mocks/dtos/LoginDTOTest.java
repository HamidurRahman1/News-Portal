package com.hamidur.np.mocks.dtos;

import com.hamidur.np.dto.LoginDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


/*
    Not the best practice of Mockito as Class under test should not be mocked only the dependencies of them.
    LoginDTO does not have any.
 */
public class LoginDTOTest
{
    @Mock
    private LoginDTO loginDTO;

    @BeforeEach
    public void setup()
    {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetUsername()
    {
        String actualUsername = "demoUsername";

        when(loginDTO.getUsername()).thenReturn("demoUsername");

        assertEquals(loginDTO.getUsername(), actualUsername);
    }

    @Test
    public void testSetUsername()
    {
        doAnswer(invocationOnMock -> {
            String username = invocationOnMock.getArgument(0);
            assertEquals(username, "myusername");
            return null;
        }).when(loginDTO).setUsername(anyString());

        loginDTO.setUsername("myusername");
    }

    @Test
    public void testMultipleGetPasswordInvocation()
    {
        when(loginDTO.getPassword()).thenReturn("pass1").thenReturn("pass2").thenReturn("pass3");

        Assertions.assertAll(
                () -> assertEquals(loginDTO.getPassword(), "pass1"),
                () -> assertEquals(loginDTO.getPassword(), "pass2"),
                () -> assertEquals(loginDTO.getPassword(), "pass3")
        );

        verify(loginDTO, times(3)).getPassword();
    }

    @Test
    public void testNullUsername()
    {
        doThrow(NullPointerException.class).when(loginDTO).setUsername(isNull());

        String nullObject = null;

        assertThrows(NullPointerException.class, () -> loginDTO.setUsername(nullObject));
    }
}
