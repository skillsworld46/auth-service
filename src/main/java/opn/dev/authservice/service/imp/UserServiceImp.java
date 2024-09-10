package opn.dev.authservice.service.imp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import opn.dev.authservice.entity.User;
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
        newUser.setPassword(hashedPassword);
        newUser.setCreatedBy(createdBy);
        newUser.setUpdatedBy(createdBy);

        return userRepository.save(newUser);
    }

    public User findByUsername(String username) {
        return userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
        User u = userRepository.findById(identity)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (u != null) {
            return u;
        }
        return userRepository.findByEmail(identity)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}