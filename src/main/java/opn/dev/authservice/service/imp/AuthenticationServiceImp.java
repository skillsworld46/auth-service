package opn.dev.authservice.service.imp;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import opn.dev.authservice.dao.Role;
import opn.dev.authservice.dao.User;
import opn.dev.authservice.dto.JwtAuthenticationRsp;
import opn.dev.authservice.dto.SignInReq;
import opn.dev.authservice.dto.SignUpReq;
import opn.dev.authservice.repository.UserRepository;
import opn.dev.authservice.service.AuthenticationService;
import opn.dev.authservice.service.JwtService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationRsp signup(SignUpReq request) {
        User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
                .email(request.getEmail()).passwordHash(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(HashMap.newHashMap(0), user);
        return JwtAuthenticationRsp.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationRsp signin(SignInReq request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        String jwt = jwtService.generateToken(HashMap.newHashMap(0), user);
        return JwtAuthenticationRsp.builder().token(jwt).build();
    }
}