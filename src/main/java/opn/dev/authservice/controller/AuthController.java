package opn.dev.authservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import opn.dev.authservice.dto.JwtAuthenticationRsp;
import opn.dev.authservice.dto.SignInReq;
import opn.dev.authservice.dto.SignUpReq;
import opn.dev.authservice.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationRsp> signup(@RequestBody SignUpReq request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationRsp> signin(@RequestBody SignInReq request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}