package services;

import java.util.ArrayList;
import java.util.List;

import dbAccess.AirportDAO;
import dbAccess.FlightDurationDAO;
import dbAccess.MyDBException;
import entity.Airport;
import entity.Factory;
import entity.FlightDuration;

public class FlightDurationService {

	// D�finition comme singleton
	private static FlightDurationService instance = null;

	public static FlightDurationService getInstance() {
		if (instance == null) {
			instance = new FlightDurationService();
		}
		return instance;
	}

	/**
	 * Ajoute une nouvelle dur�e de vol entre 2 a�roports
	 * 
	 * @param airportDepartureName
	 *            nom de l'a�roport de d�part
	 * @param airportArrivalName
	 *            nom de l'a�roport d'arriv�e
	 * @param duration
	 *            dur�e du vol entre les 2 a�roports (en minutes)
	 * @throws MyDBException
	 */
	public void insertFlightDuration(String airportDepartureName, String airportArrivalName, int duration)
			throws MyDBException {
		Airport aDepart = Factory.createAirport(airportDepartureName);
		Airport aArrivee = Factory.createAirport(airportArrivalName);
		FlightDuration fd = Factory.createFlightDuration(aDepart, aArrivee, duration);
		FlightDurationDAO.getInstance().insertFlightDuration(fd);
	}

	/**
	 * Affichage de toute la table FlightDuration
	 * 
	 * @return la liste des FlightDuration sous forme de String
	 * @throws MyDBException
	 */
	public List<String> selectAll() throws MyDBException {
		List<FlightDuration> listFlightDuration = FlightDurationDAO.getInstance().selectAll();
		List<String> listResult = new ArrayList<String>();
		for (FlightDuration fd : listFlightDuration) {
			listResult.add(fd.toString());
		}
		return listResult;
	}

	/**
	 * Affiche le d�calage horaire entre 2 a�roports
	 * 
	 * @param airportDepartureName
	 *            nom de l'a�roport de d�part
	 * @param airportArrivalName
	 *            nom de l'a�roport d'arriv�e
	 * @return la d�calage horaire
	 * @throws MyDBException
	 */
	public int getTimeDifference(String airportDepartureName, String airportArrivalName) throws MyDBException {
		int timezone1 = AirportDAO.getInstance().selectTimeZoneAirport(airportDepartureName);
		int timezone2 = AirportDAO.getInstance().selectTimeZoneAirport(airportArrivalName);
		return (timezone2 - timezone1);
	}

}
