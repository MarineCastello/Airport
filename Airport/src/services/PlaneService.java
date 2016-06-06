package services;

import java.util.List;

import dbAccess.MyDBException;
import dbAccess.PlaneDAO;
import entity.Airline;
import entity.Airport;
import entity.Factory;
import entity.Plane;

public class PlaneService {

	// Définition comme singleton
	private static PlaneService instance = null;

	public static PlaneService getInstance() {
		if (instance == null) {
			instance = new PlaneService();
		}
		return instance;
	}

	public boolean getAvailability(String planeName) {
		Plane p = Factory.createPlane(planeName);
		return p.isAvailable();
	}


	//public void updateCurrentAirport(Plane p, Airport a) throws MyDBException {
	//	PlaneDAO.getInstance().updateCurrentAirport(p, a);
	//}
	
	public void insertPlane(String planeName, Airport currentAirport, Airline airline, boolean available) throws MyDBException {
		Plane p = Factory.createPlane(planeName, currentAirport, airline, available);

		PlaneDAO.getInstance().insertPlane(p);
		// On ne vérifie pas si l'avion existe déjà en base de données
	}

	public List<Plane> selectAll() throws MyDBException {
		return PlaneDAO.getInstance().selectAll();
	}

	public Plane selectPlane(String planeName) throws MyDBException {
		return PlaneDAO.getInstance().selectPlane(planeName);
	}

	public List<Plane> selectAvailablePlanes(String airportName) throws MyDBException {
		return PlaneDAO.getInstance().selectAvailablePlanes(airportName);
	}

}
