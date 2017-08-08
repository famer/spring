package com.timurtatarshaov.demoforum.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.timurtatarshaov.demoforum.dao.UserRepository;
import com.timurtatarshaov.demoforum.model.User;

@Component
public class UserValidator implements Validator{
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
    	
    	User user = (User) obj;
    	
    	if (userRepository.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "userForm.username.duplication");
        }
    	/*
    	if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "userForm.password.size");
        }
    	*/
    	if (!user.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%]).{8,}$")) {
            errors.rejectValue("password", "userForm.password.specialCharacters");
        }
    	
        if (!user.getPasswordConfirmation().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirmation", "userForm.passwordConfirmation.difference");
        }
    }

}
