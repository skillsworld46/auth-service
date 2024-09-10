package opn.dev.authservice.service.imp;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import opn.dev.authservice.dto.JwtAuthenticationRsp;
import opn.dev.authservice.dto.SignInReq;
import opn.dev.authservice.dto.SignUpReq;
import opn.dev.authservice.entity.Role;
import opn.dev.authservice.entity.User;
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
        User user = User.builder().username(request.getUsername()).firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail()).password(passwordEncoder.encode(request.getPassword())).createdBy("sys")
                .createdAt(LocalDateTime.now())
                .updatedBy("sys")
                .updatedAt(LocalDateTime.now())
                .role(Role.USER).build();
        userRepository.save(user);
        String jwt = jwtService.generateToken(HashMap.newHashMap(0), user);
        return JwtAuthenticationRsp.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationRsp signin(SignInReq request) {
        Authentication authe = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getIdentity(), request.getPassword()));
        authe.getPrincipal();
        User user = (User) authe.getPrincipal();
        String jwt = jwtService.generateToken(HashMap.newHashMap(0), user);
        return JwtAuthenticationRsp.builder().token(jwt).build();
    }
}