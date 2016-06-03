package services;

import java.util.List;

import dbAccess.MyDBException;
import dbAccess.PlaneDAO;
import entity.Airport;
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

	public boolean getAvailability(Plane p) {
		return p.isAvailable();
	}

	public void updateCurrentAirport(Plane p, Airport a) throws MyDBException {
		PlaneDAO.getInstance().updateCurrentAirport(p, a);
	}

	public void insertPlane(Plane p) throws MyDBException {
		PlaneDAO.getInstance().insertPlane(p);
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
