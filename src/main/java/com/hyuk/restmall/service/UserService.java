package com.hyuk.restmall.service;


import com.hyuk.restmall.dto.UserSignUpDto;
import com.hyuk.restmall.entity.User;
import com.hyuk.restmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        // 반드시 bcrypt로 인코딩된 값이 authentication 객체에 들어가야 한다.
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }

    @Transactional
    public void signup(UserSignUpDto userSignUpDto) {

        Optional<User> userDb = userRepository.findByEmail(userSignUpDto.getEmail());

        if (!userDb.isEmpty()) {
            throw new RuntimeException("이미 존재하는 계정입니다.");
        };

        if (!userSignUpDto.getPassword().equals(userSignUpDto.getPasswordChk())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        };

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(userSignUpDto.getEmail()
                        , bCryptPasswordEncoder.encode(userSignUpDto.getPassword())
                        , userSignUpDto.getName()
                        , userSignUpDto.getPhone()
                        , userSignUpDto.getEmail()
                        , userSignUpDto.getEmail()
                    );

        userRepository.save(user);
    }
}
