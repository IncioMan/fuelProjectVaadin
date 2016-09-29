package com.example.fuelProject.ui;

import com.vaadin.data.Validator;

public class NumberValidator implements Validator{

	@Override
	public void validate(Object value) throws InvalidValueException {
		double tmp;
		try{
			tmp = Double.parseDouble(value.toString());
		}catch(Exception e){
			throw new InvalidValueException("Invalid value. Insert a positive number!");
		}
		if(tmp < 0)
			throw new InvalidValueException("Invalid value. Insert a positive number!");
	}
}
