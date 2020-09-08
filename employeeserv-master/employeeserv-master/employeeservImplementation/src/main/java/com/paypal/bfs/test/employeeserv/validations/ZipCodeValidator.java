/**
 * 
 */
package com.paypal.bfs.test.employeeserv.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Mayuri
 *
 */
public class ZipCodeValidator implements ConstraintValidator<ZipCode, String> {

	@Override
	public void initialize(ZipCode zipCode) {
	}

	@Override
	public boolean isValid(String string, ConstraintValidatorContext context) {

		String regex = "^[0-9]{5}(?:-[0-9]{4})?$";

		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}

}