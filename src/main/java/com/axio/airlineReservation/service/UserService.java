package com.axio.airlineReservation.service;

import com.axio.airlineReservation.Exception.CustomException;
import com.axio.airlineReservation.config.PasswordEncoder;
import com.axio.airlineReservation.entity.UserDetails;
import com.axio.airlineReservation.repository.UserRepository;
import com.axio.airlineReservation.request.user.UserLogInRequest;
import com.axio.airlineReservation.request.user.UserSingUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service

public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String addUser(UserSingUpRequest user)  {
        String email=user.getEmail();
        if(!validateEmail(email)){

            throw new CustomException("Please try with Valid Email!", "emailError");
        }
        // Check if the user already exists
        if (userRepository.existsById(email)) {
            throw new CustomException("User already exit for email :: "+ email);
        }
        // Save the new user
        UserDetails newUser = new UserDetails();
        newUser.setEmail(email);
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(newUser);
        return "User added successfully";

    }

    public String logInUser(UserLogInRequest user) {
        String email = user.getEmail();
        String password = user.getPassword();

        if(!validateEmail(email)){
            throw new CustomException("Please try with Valid Email!", "emailError");
        }


        // Check if the user exists
        UserDetails existingUser = userRepository.findById(email).orElse(null);
        if (existingUser == null) {
            throw new CustomException("User does not exist for email :: "+email);
        }
        // Validate password
        if (!passwordEncoder.matches(password,existingUser.getPassword())) {
            throw new CustomException("Invalid password");
        }
        return "Login successful";
    }

    public UserDetails getUserById(String userId){
        UserDetails existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            throw new CustomException("User does not exist for email/userId :: "+userId);
        }
        return existingUser;
    }


    private boolean validateEmail(String email){
          final String EMAIL_PATTERN =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
          Pattern pattern = Pattern.compile(EMAIL_PATTERN);

          return email != null && pattern.matcher(email).matches();

    }


}
