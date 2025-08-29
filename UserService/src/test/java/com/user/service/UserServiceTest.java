package com.user.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.user.entity.Role;
import com.user.entity.User;
import com.user.repository.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private JwtService jwtServ;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("plainpass");

        when(passwordEncoder.encode("plainpass")).thenReturn("encodedpass");
        when(userRepo.save(any(User.class))).thenReturn(user);

        User registered = userService.register(user);

        assertEquals("testuser", registered.getUsername());
        assertEquals(Role.USER, registered.getRole());
        verify(userRepo).save(any(User.class));
    }

    @Test
    void testRegisterUser_NullInput() {
        assertThrows(RuntimeException.class, () -> userService.register(null));
    }

//    @Test
//    void testLogin_Success() {
//        UserDTO dto = new UserDTO("testuser", "password");
//        User user = new User();
//        user.setUsername("testuser");
//        user.setRole(Role.USER);
//
//        Authentication auth = mock(Authentication.class);
//
//        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
//        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));
//        when(jwtServ.generateToken("testuser", Role.USER)).thenReturn("mocked-jwt");
//
//        ResponseEntity<?> response = userService.login(dto);
//
//        Map<String, Object> body = (Map<String, Object>) response.getBody();
//        assertEquals("testuser", body.get("username: "));
//        assertEquals(Role.USER, body.get("role: "));
//        assertEquals("mocked-jwt", body.get("token: "));
//    }

//    @Test
//    void testLogin_InvalidCredentials() {
//        UserDTO dto = new UserDTO("wronguser", "wrongpass");
//
//        when(authManager.authenticate(any())).thenThrow(new RuntimeException("Bad credentials"));
//
//        ResponseEntity<?> response = userService.login(dto);
//
//        assertEquals(401, response.getStatusCodeValue());
//        assertTrue(((Map<?, ?>) response.getBody()).containsKey("error: "));
//    }

    @Test
    void testFindByUsername_Success() {
        User user = new User();
        user.setUsername("testuser");

        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));

        User found = userService.findByUsername("testuser");

        assertEquals("testuser", found.getUsername());
    }

    @Test
    void testFindByUsername_NotFound() {
        when(userRepo.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.findByUsername("unknown"));
    }

    @Test
    void testDeleteUser_Success() {
        User user = new User();
        user.setId(1);

        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(1);

        assertEquals("Deleted user successfully", result);
        verify(userRepo).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepo.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.deleteUser(99));
    }
}