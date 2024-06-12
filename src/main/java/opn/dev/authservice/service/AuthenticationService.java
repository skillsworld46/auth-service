package opn.dev.authservice.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import opn.dev.authservice.dao.User;
import opn.dev.authservice.repository.UserRepository;
import opn.dev.authservice.util.JwtUtil;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public String authenticate(String username, String rawPassword) throws Exception {
        Optional<User> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new Exception("Invalid username or password");
        }

        User user = userOptional.get();
        if (passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            return jwtUtil.generateToken(username);
        } else {
            throw new Exception("Invalid username or password");
        }
    }
}