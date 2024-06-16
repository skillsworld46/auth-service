package opn.dev.authservice.service.imp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import opn.dev.authservice.dao.User;
import opn.dev.authservice.repository.UserRepository;
import opn.dev.authservice.service.UserService;

@Service
public class UserServiceImp implements UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String rawPassword, String createdBy) {

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPasswordHash(hashedPassword);
        newUser.setCreatedBy(createdBy);
        newUser.setUpdatedBy(createdBy);

        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}