package com.hyuk.restmall.controller;


import com.hyuk.restmall.dto.UserLoginDto;
import com.hyuk.restmall.dto.UserSignUpDto;
import com.hyuk.restmall.service.UserService;
import com.hyuk.restmall.utils.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse> login(@RequestBody UserLoginDto userLoginDto,
                                             HttpServletRequest request,
                                             HttpServletResponse response
                                            ) {

        try {
            // 1. Authentication 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getEmail(),
                            userLoginDto.getPassword()
                    );

            // 2. 인증 시도
            Authentication auth = authenticationManager.authenticate(authenticationToken);

            // 3. SecurityContext에 인증 정보 저장
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(auth);
            SecurityContextHolder.setContext(context);

            // 4. 세션에 SecurityContext 저장 (다음 요청에서 인증 유지)
            HttpSession session = request.getSession(true);
            session.setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    context
            );

//            Cookie cookie = new Cookie("JSESSIONID", session.getId());
//            cookie.setHttpOnly(true);
//            cookie.setMaxAge(10);
//            cookie.setPath("/");    // 모든 api 요청에 쿠키를 담아서 보낸다
//            response.addCookie(cookie);

            session.setMaxInactiveInterval(60);


            System.out.println("session Id: " + session.getId());


            return ResponseEntity.ok().body(new ApiResponse(true, "로그인이 성공적으로 완료되었습니다."));

        } catch (BadCredentialsException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "아이디 또는 비밀번호가 잘못되었습니다."));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "서버 오류가 발생했습니다."));
        }
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
    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody UserSignUpDto userSignUpDto,
                                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.badRequest().body(new ApiResponse(false, errorMsg));
        }

        userService.signup(userSignUpDto);

        return ResponseEntity.ok().body(new ApiResponse(true, "회원가입이 성공적으로 완료되었습니다."));
    }

    /**
     * 현재 로그인한 사용자 정보 조회
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUser(@AuthenticationPrincipal UserDetails user, HttpServletRequest request) {


        // 기존 세션 가져오기 (없으면 null 반환)
        HttpSession session = request.getSession(false);

        if (session == null) {
            System.out.println("세션 없음");
        } else {
            System.out.println("세션 야이디: " + session.getId());
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("========");
        System.out.println(auth);
        System.out.println("==== user ====");
        System.out.println(user);

        HashMap<String, Object> result = new HashMap<>();
        result.put("user", auth.getPrincipal());

        return ResponseEntity.ok().body(result);
    }
}
