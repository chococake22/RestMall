package com.hyuk.restmall.controller;

import com.hyuk.restmall.dto.UserLogin;
import com.hyuk.restmall.dto.UserSignUp;
import com.hyuk.restmall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    /**
     * 로그인
     * @param : email
     * @param : password
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLogin userLogin) {


        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLogin.getEmail(), userLogin.getPassword());

            Authentication auth = authenticationManager.authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

        return ResponseEntity.ok("success");

    }


    /**
     * 회원가입
     * @param : email
     * @param : password
     * @param : passwordChk
     * @param : name
     * @param : phone
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUp userSignUp) {


        try {
            userService.signup(userSignUp);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("서버 오류 발생");
        }

        return ResponseEntity.ok("success");

    }

}
