package com.threads.webservices.controller;

import com.nimbusds.jose.JOSEException;
import com.threads.webservices.dto.request.*;
import com.threads.webservices.dto.response.AuthenticationResponse;
import com.threads.webservices.dto.response.IntrospectResponse;
import com.threads.webservices.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        var result = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout") //http://localhost:8080/api/auth/logout
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException{
        authenticationService.logout(request);
        return ApiResponse.<Void>builder().build();
    }

    @PostMapping("/refresh") //http://localhost:8080/api/auth/refresh
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshTokenRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();
    }

    /*
    * body của cả logout và refresh:
    * {
    *   "token": "..jwt token.."
    * }
    *
    * --Logout: khi logout jwt token sẽ được kiểm tra còn hạn hay không sau đó lưu vào db, sau này sẽ tạo một phương thức
    * xoá các token hết hạn trong bảng đó mỗi ngày một lần
    *
    * --refresh: một token có thời hạn 10 giờ, phía client quy định khi nào sẽ gọi refresh token, cho dù token đã hết hạn\
    * thì thời hạn refresh token là 100 giờ, khi đó token vẫn có thể gọi refresh token. Xem file yaml.
    * */
}
