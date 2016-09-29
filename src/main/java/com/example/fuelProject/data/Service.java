package com.example.fuelProject.data;

import java.util.ArrayList;

public interface Service {
	void addFuel(FuelData data);
	void removeFuel(FuelData data);
	void updateFuel(FuelData data);
	ArrayList<FuelData> getAllFuel();
}
