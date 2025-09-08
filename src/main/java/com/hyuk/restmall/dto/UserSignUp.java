package com.hyuk.restmall.dto;


import lombok.Data;

@Data
public class UserSignUp {

    private String email;
    private String password;

    private String passwordChk;
    private String name;
    private String phone;




}
