package com.example.fuelProject.data;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FuelService implements Service{
	private SessionFactory factory;
	
	public FuelService() {
		// TODO Auto-generated constructor stub
		factory= new Configuration().configure()
				.addAnnotatedClass(FuelData.class)
				.buildSessionFactory();
	}
	
	public void addFuel(FuelData data){
		Session session = null;
		try{
			session = factory.getCurrentSession();
			session.beginTransaction();
			session.save(data);
			session.getTransaction().commit();
			
		} finally {
			session.close();
		}
	}
	
	public void removeFuel(FuelData data) {
		
	}
	
	public void updateFuel(FuelData data){};
	
	public ArrayList<FuelData> getAllFuel(){
		return null;
	}
}
