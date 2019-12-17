package com.countries.Helpers;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorHelper {
	
	private final Validator validator;
	

	public ValidatorHelper() {
		final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		this.validator = factory.getValidator();
	}
	public String validate(Object object) {
		String violationsMessage = null;
		StringBuilder sb = null;
		Set<ConstraintViolation<Object>> violations = validator.validate(object);
		if (!violations.isEmpty()) {
			if (violationsMessage == null) {
				sb = new StringBuilder();
				sb.append("start of violations messages:");
			}
			for (ConstraintViolation<Object> v : violations) {
				sb.append(v.getMessage());
			}
			violationsMessage = sb.toString();
		}
		return violationsMessage;
	}
}
