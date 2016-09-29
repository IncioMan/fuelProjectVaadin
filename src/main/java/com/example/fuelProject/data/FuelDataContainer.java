package com.example.fuelProject.data;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;

import com.vaadin.data.util.BeanItemContainer;

public class FuelDataContainer extends BeanItemContainer<FuelData> implements Serializable{
	
	/**
	 * Natural property order for Person bean. Used in tables and forms.
	 */
	public static final Object[] NATURAL_COL_ORDER = new Object[] {
			"date", "price", "distance", "ammountFuel", "type"};

	/**
	 * "Human readable" captions for properties in same order as in
	 * NATURAL_COL_ORDER.
	 */
	public static final String[] COL_HEADERS_ENGLISH = new String[] {
			"Date", "Price", "Distance", "Ammount of Fuel", "Type"};

	public FuelDataContainer(){
		super(FuelData.class);
		
		try {
			for(FuelData data : FuelData.getDatas())
			 this.addItem(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int getLength() {
		return 8;
	}
}