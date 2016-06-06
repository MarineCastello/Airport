package services;

import java.util.ArrayList;
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

	public void updateCurrentAirport(String planeName, String airportName) throws MyDBException {
		Plane p = Factory.createPlane(planeName);
		Airport a = Factory.createAirport(airportName);
		PlaneDAO.getInstance().updateCurrentAirport(p, a);
	}

	public void insertPlane(String planeName, Airport currentAirport, Airline airline, boolean available)
			throws MyDBException {
		Plane p = Factory.createPlane(planeName, currentAirport, airline, available);
		PlaneDAO.getInstance().insertPlane(p);
		// On ne vérifie pas si un avion existe avant de l'insérer en BD
	}

	/**
	 * Récupère les informations des avions en base de données sous forme d'un String
	 * 
	 * @return
	 * @throws MyDBException
	 */
	public List<String> selectAll() throws MyDBException {
		List<Plane> listPlanes = PlaneDAO.getInstance().selectAll();
		List<String> listResult = new ArrayList<String>();
		for (Plane p : listPlanes) {
			listResult.add(p.toString());
		}
		return listResult;
	}

	public Plane selectPlane(String planeName) throws MyDBException {
		return PlaneDAO.getInstance().selectPlane(planeName);
	}

	public List<String> selectAvailablePlanes(String airportName) throws MyDBException {
		List<Plane> listPlanes = PlaneDAO.getInstance().selectAvailablePlanes(airportName);
		List<String> listResult = new ArrayList<String>();
		for (Plane p : listPlanes) {
			listResult.add(p.toString());
		}
		return listResult;
	}

}
