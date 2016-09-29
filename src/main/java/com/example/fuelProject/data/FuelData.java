package com.example.fuelProject.data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.persistence.*;

@Entity
public class FuelData implements Serializable{
	public static final int METANO = 0;
	public static final int BENZINA = 1;
	public static final int METANOBENZINA = 2;
	public static final int PRICE = 3;
	public static final int DISTANCE = 4;
	public static final int DAY = 5;
	public static final int MONTH = 6;
	public static final int TOTAL = 7;
	
	@Id
	private Date date;
	@Id
	private double price;
	private int distance;
	private double ammountFuel;
	@Id
	private int type;

	public FuelData(Date date, double price, int distance, double ammountFuel, int type) {
		super();
		this.date = date;
		this.price = price;
		this.distance = distance;
		this.ammountFuel = ammountFuel;
		this.type = type;
	}

	public FuelData() {
		date = new Date();
		price = 0;
		distance = 0;
		ammountFuel = 0;
		type = METANO;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public double getAmmountFuel() {
		return ammountFuel;
	}

	public void setAmmountFuel(float ammountFuel) {
		this.ammountFuel = ammountFuel;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static ArrayList<FuelData> getDatas() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
		ArrayList<FuelData> datas = new ArrayList<FuelData>();

		datas.add(new FuelData(formatter.parse("01/21/02"), 7.64, 270, 9.10, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("01/24/02"), 5, 0, 0, FuelData.BENZINA));
		datas.add(new FuelData(formatter.parse("01/22/02"), 8.16, 240, 9.10, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("01/23/02"), 7.80, 200, 9, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("01/29/02"), 7.84, 230, 8.60, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("01/27/02"), 10, 0, 0, FuelData.BENZINA));
		datas.add(new FuelData(formatter.parse("01/12/02"), 7.64, 270, 9.10, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("01/19/02"), 6.5, 240, 7, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("02/01/02"), 3, 0, 0, FuelData.BENZINA));
		datas.add(new FuelData(formatter.parse("02/21/02"), 7.64, 270, 9.10, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("02/24/02"), 5, 0, 0, FuelData.BENZINA));
		datas.add(new FuelData(formatter.parse("02/22/02"), 8.16, 240, 9.10, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("02/23/02"), 7.80, 200, 9, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("03/29/02"), 7.84, 230, 8.60, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("03/27/02"), 10, 0, 0, FuelData.BENZINA));
		datas.add(new FuelData(formatter.parse("04/12/02"), 7.64, 270, 9.10, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("05/19/02"), 6.5, 240, 7, FuelData.METANO));
		datas.add(new FuelData(formatter.parse("05/01/02"), 3, 0, 0, FuelData.BENZINA));

		return datas;
	}

	public static Hashtable<Integer, Integer> getAggregateData(int type, int value, int period) {
		Hashtable<Integer, Integer> data = new Hashtable<>();
		if (type == METANO && value == DISTANCE && period == MONTH) {
			data.put(1, 334);
			data.put(2, 456);
			data.put(3, 278);
			data.put(4, 223);
		}
		if (type == METANO && value == PRICE && period == MONTH) {
			data.put(1, 34);
			data.put(2, 56);
			data.put(3, 78);
			data.put(4, 23);
		}
		if (type == BENZINA && value == DISTANCE && period == MONTH) {
			data.put(1, 0);
			data.put(2, 0);
			data.put(3, 0);
			data.put(4, 0);
		}
		if (type == BENZINA && value == PRICE && period == MONTH) {
			data.put(1, 20);
			data.put(2, 14);
			data.put(3, 5);
			data.put(4, 10);
		}
		return data;
	}

	public static Hashtable<Integer, Double> getAggregateData(int type, int period) {
		Hashtable<Integer, Double> data = new Hashtable<>();
		if(type == METANOBENZINA && period == TOTAL){
			data.put(METANO, (double) 200);
			data.put(BENZINA, (double) 30);
		}
		
		if(type == METANO && period == TOTAL){
			data.put(1, (double) 30);
			data.put(2, 30.0);
			data.put(3, 26.96);
		}
		
		if(type == BENZINA && period == TOTAL){
			data.put(1, 4.0);
			data.put(2, 6.0);
			data.put(3, 3.04);
		}
		
		return data;
	}
}
