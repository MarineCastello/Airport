package services;

import java.util.ArrayList;
import java.util.List;

import dbAccess.AirportDAO;
import dbAccess.MyDBException;
import entity.Airport;
import entity.Factory;

public class AirportService {

	// Définition comme singleton
	private static AirportService instance = null;

	public static AirportService getInstance() {
		if (instance == null) {
			instance = new AirportService();
		}
		return instance;
	}

	/**
	 * Insertion dans la BD d'un nouvel aéroport
	 * @param airportName nom de l'aéroport
	 * @param city nom de la ville
	 * @param country nom du pays
	 * @param timezone décalage horaire (entier) par rapport à l'UTC
	 * @throws MyDBException
	 */
	public void insertAirport(String airportName, String city, String country, int timezone) throws MyDBException {
		Airport a = Factory.createAirport(airportName, city, country, timezone);
		AirportDAO.getInstance().insertAirport(a);
	}
	
	/**
	 * Affichage de toute la table Airport
	 * @return la liste des aéroports
	 * @throws MyDBException
	 */
	public List<String> selectAll() throws MyDBException{
		List<Airport> listAirport = AirportDAO.getInstance().selectAll();
		List<String> listResult = new ArrayList<String>();
		for(Airport a : listAirport){
			listResult.add(a.toString());
		}
		return listResult;
	}
	
	

}
