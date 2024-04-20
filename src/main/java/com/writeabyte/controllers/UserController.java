package com.writeabyte.controllers;

import com.writeabyte.models.requests.LoginRequest;
import com.writeabyte.models.requests.SignUpRequest;
import com.writeabyte.models.response.LoginResponse;
import com.writeabyte.models.response.SignUpResponse;
import com.writeabyte.models.response.UserResponse;
import com.writeabyte.utils.Constants;
import com.writeabyte.utils.JwtUtil;
import com.writeabyte.utils.ValidationUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.writeabyte.dto.UserDTO;
import com.writeabyte.entities.User;
import com.writeabyte.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        try {
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            UserResponse userResponse = userService.login(email, password);
            if(userResponse != null) {
                String token = jwtUtil.generateToken(email);
                loginResponse.setStatus(true);
                loginResponse.setMessage("Login Success");
                loginResponse.setUserResponse(userResponse);
                loginResponse.setToken(token);
            } else {
                loginResponse.setStatus(false);
                loginResponse.setMessage("Invalid Details");
            }
            return ResponseEntity.ok(loginResponse);
        } catch (Exception e) {
            loginResponse.setStatus(false);
            loginResponse.setMessage(e.getMessage());
            return ResponseEntity.ok(loginResponse);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = null;
        try {
            String objectString = ReflectionToStringBuilder.toString(signUpRequest);
            logger.info("Signup Called with RequestBody: {}", objectString);
            String email = signUpRequest.getEmail();

            if (ValidationUtils.validateSignUpDetails(signUpRequest)) {
                if (userService.isExistsByEmail(email)) {
                    signUpResponse = new SignUpResponse();
                    signUpResponse.setStatus(false);
                    signUpResponse.setMessage(Constants.EMAIL_ALREADY_IN_USE);
                    return ResponseEntity.ok(signUpResponse);
                }

                signUpResponse = userService.registerUser(signUpRequest);
            } else {
                signUpResponse = new SignUpResponse();
                signUpResponse.setStatus(false);
                signUpResponse.setMessage(Constants.VALIDATION_ERROR);
            }

        } catch (Exception e) {
            logger.error("Error in SignUp Endpoint", e);
        }
        return ResponseEntity.ok(signUpResponse);
    }
}
