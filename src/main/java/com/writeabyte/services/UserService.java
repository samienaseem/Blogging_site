package com.writeabyte.services;

import com.writeabyte.models.requests.SignUpRequest;
import com.writeabyte.models.response.SignUpResponse;
import com.writeabyte.models.response.UserResponse;
import com.writeabyte.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.writeabyte.entities.User;
import com.writeabyte.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private ModelMapper modelMapper = new ModelMapper();
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public SignUpResponse registerUser(SignUpRequest signUpRequest) {
        SignUpResponse signUpResponse = new SignUpResponse();

        try {
            User user = modelMapper.map(signUpRequest, User.class);
            User saveUser = userRepository.save(user);
            if (saveUser != null) {
                signUpResponse.setMessage(Constants.SIGNUP_SUCCESS);
                signUpResponse.setStatus(true);
            } else {
                signUpResponse.setMessage(Constants.SIGNUP_ERROR);
                signUpResponse.setStatus(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signUpResponse;
    }
    
    public UserResponse login(String email, String password) {
        UserResponse userResponse = new UserResponse();
       try {
           User user = userRepository.findByEmailAndPassword(email, password);
           userResponse.setEmail(user.getEmail());
           userResponse.setName(user.getName());
           userResponse.setUsername(user.getUsername());
       } catch (Exception e) {
       }
        return userResponse;
    }

    public boolean isExistsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}