package services;

import java.util.ArrayList;
import java.util.List;

import dbAccess.AirlineDAO;
import dbAccess.MyDBException;
import entity.Airline;
import entity.Factory;

public class AirlineService {

	// Définition comme singleton
	private static AirlineService instance = null;

	public static AirlineService getInstance() {
		if (instance == null) {
			instance = new AirlineService();
		}
		return instance;
	}

	public void insertAirline(String airlineName) throws MyDBException {
		Airline a = Factory.createAirline(airlineName);
		AirlineDAO.getInstance().insertAirline(a);
	}

	/**
	 * Renvoie tous les éléments de la table Airline
	 * 
	 * @return
	 * @throws MyDBException
	 */
	public List<String> selectAll() throws MyDBException {
		List<Airline> listAirlines = AirlineDAO.getInstance().selectAll();
		List<String> listResult = new ArrayList<String>();
		for (Airline a : listAirlines) {
			listResult.add(a.toString());
		}
		return listResult;
	}

	/*
	 * public void updateAirline(String airlineName) {
	 * Airline a = Factory.createAirline(airlineName);
	 * AirlineDAO.getInstance().updateAirline(a);
	 * }
	 */

}
