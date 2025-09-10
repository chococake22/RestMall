package com.hyuk.restmall.controller;


import com.hyuk.restmall.dto.UserLoginDto;
import com.hyuk.restmall.dto.UserSignUpDto;
import com.hyuk.restmall.service.UserService;
import com.hyuk.restmall.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginDto userLoginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());

        Authentication auth = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(auth);

        return ResponseEntity.ok().body(new ApiResponse(true, "로그인이 성공적으로 완료되었습니다."));
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
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserSignUpDto userSignUpDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ApiResponse(false, errorMsg));
        }

        userService.signup(userSignUpDto);

        return ResponseEntity.ok().body(new ApiResponse(true, "회원가입이 성공적으로 완료되었습니다."));
    }
}
