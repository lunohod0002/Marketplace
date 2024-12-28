package com.example.demo.services;

import org.example.input.UserAuthInputModel;
import org.example.input.CustomerRegisterInputModel;

public interface UserService {
    void signUp(CustomerRegisterInputModel registerInputModel);
    void signIn(UserAuthInputModel userAuthInputModel);

}
