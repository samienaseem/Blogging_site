package com.writeabyte.utils;



import com.writeabyte.models.requests.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class ValidationUtils {
	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

	public static boolean isValidEmail(String email) {
		return Pattern.matches(EMAIL_REGEX, email);
	}

	public static boolean isValidPassword(String password) {
		return Pattern.matches(PASSWORD_REGEX, password);
	}

	public static boolean validateSignUpDetails(SignUpRequest signUpRequest) {
		return isValidEmail(signUpRequest.getEmail())
				&& isValidPassword(signUpRequest.getPassword());
	}
}
