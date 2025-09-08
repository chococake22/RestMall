package com.hyuk.restmall.service;


import com.hyuk.restmall.dto.UserSignUp;
import com.hyuk.restmall.entity.User;
import com.hyuk.restmall.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {


        System.out.println("email: " + email);

        System.out.println("asdfasdfasdfasdfasdfasdf");

        User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        System.out.println("======");
        System.out.println(user);

        return user;
    }

    public void signup(UserSignUp userSignUp) {

        Optional<User> userDb = userRepository.findByEmail(userSignUp.getEmail());
        System.out.println(userDb);

        if (!userDb.isEmpty()) {
            throw new RuntimeException("이미 존재하는 계정입니다.");
        };

        if (!userSignUp.getPassword().equals(userSignUp.getPasswordChk())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        };

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        User user = new User(userSignUp.getEmail()
                        , bCryptPasswordEncoder.encode(userSignUp.getPassword())
                        , userSignUp.getName()
                        , userSignUp.getPhone()
                        , userSignUp.getEmail()
                        , userSignUp.getEmail()
                    );





        userRepository.save(user);

    }
}
