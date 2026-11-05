package com.securityuserapp;

import com.securityuserapp.entities.UserEntity;
import com.securityuserapp.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecurityUserAppApplicationTests {

    @Autowired
    private JwtService jwtService;

    @Test
    void testJwtToken() {

        UserEntity userEntity = new UserEntity(4L, "vikas", "vikasG", "vikas@gmail.com");

        String token = jwtService.generateToken(userEntity);

        System.out.println(token);

        Long id = jwtService.getUserIdFromToken(token);

        System.out.println(id);

    }
}
