package opn.dev.authservice.service;

import opn.dev.authservice.dto.JwtAuthenticationRsp;
import opn.dev.authservice.dto.SignInReq;
import opn.dev.authservice.dto.SignUpReq;

public interface AuthenticationService {
    JwtAuthenticationRsp signup(SignUpReq request);

    JwtAuthenticationRsp signin(SignInReq request);
}
