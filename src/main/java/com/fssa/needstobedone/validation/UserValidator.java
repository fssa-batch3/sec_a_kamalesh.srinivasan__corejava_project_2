package com.fssa.needstobedone.validation;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fssa.needstobedone.dao.UserDAO;
import com.fssa.needstobedone.exception.DAOException;
import com.fssa.needstobedone.exception.ValidationException;
import com.fssa.needstobedone.model.User;

/**
 * Utility class for validating user-related data.
 */
public class UserValidator {

	/**
	 * Validates a user object.
	 *
	 * @param user The user to be validated.
	 * @throws ValidationException If validation fails.
	 */
	public void validateUser(User user) throws ValidationException {
		if (user != null && validatePassword(user.getPassword()) && validateEmail(user.getEmail())
				&& validateName(user.getFirstName())) {
			validateName(user.getLastName());
		}
	}

	/**
	 * Validates a user's first or last name.
	 *
	 * @param name The name to be validated.
	 * @return true if the name is valid, false otherwise.
	 * @throws ValidationException If validation fails.
	 */
	public boolean validateName(String name) throws ValidationException {
		boolean match = false;

		if (name == null)
			throw new ValidationException("Name is not valid - Name cannot be empty");

		String regex = "^[A-Za-z]{1,30}$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(name);
		match = m.matches();
		if (!match) {
			throw new ValidationException(
					"Name is not valid - Name should have only letters (A-Z or a-z) and be 1 to 30 characters long.");
		}

		return match;
	}

	/**
	 * Validates a user's address.
	 *
	 * @param address The address to be validated.
	 * @return true if the address is valid, false otherwise.
	 * @throws ValidationException If validation fails.
	 */
//	public boolean validateAddress(String address) throws ValidationException {
//		if (address == null || address.trim().isEmpty()) {
//			throw new ValidationException("Address is not valid - Address cannot be empty");
//		}
//
//		return true;
//	}

	/**
	 * Validates a user's password.
	 *
	 * @param password The password to be validated.
	 * @return true if the password is valid, false otherwise.
	 * @throws ValidationException If validation fails.
	 */
	public boolean validatePassword(String password) throws ValidationException {
		if (password == null) {
			throw new ValidationException("Password cannot be empty");
		}
		password = password.trim();
		if (password.isEmpty()) {
			throw new ValidationException("Password cannot be empty");
		} else if (password.length() < 8) {
			throw new ValidationException("Password is less than the expected length of 8 characters");
		} else if (!Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$").matcher(password).matches()) {
			throw new ValidationException(
					"Password must contain at least one uppercase letter, one lowercase letter, and one digit");
		}
		return true;
	}

	/**
	 * Validates a user's email.
	 *
	 * @param email The email to be validated.
	 * @return true if the email is valid, false otherwise.
	 * @throws ValidationException If validation fails.
	 */
	public boolean validateEmail(String email) throws ValidationException {
		boolean isMatch = false;

		if (email == null)
			throw new ValidationException("Email is not valid - email cannot be empty");
		String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		isMatch = Pattern.compile(regex).matcher(email).matches();
		if (!isMatch) {
			throw new ValidationException("Email is not valid - email format should be example@gmail.com");
		}

		UserDAO userDAO = new UserDAO();
		try {
			if (!userDAO.checkUserByEmail(email))
				throw new ValidationException("Email already exists!!!");
		} catch (DAOException | ValidationException e) {
			throw new ValidationException(e.getMessage());
		}
		return isMatch;
	}


