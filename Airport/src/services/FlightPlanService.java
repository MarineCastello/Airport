package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import dbAccess.FlightPlanDAO;
import dbAccess.MyDBException;
import entity.Airport;
import entity.Factory;
import entity.FlightDuration;
import entity.FlightPlan;
import entity.Plane;

public class FlightPlanService {

	// D�finition comme singleton
	private static FlightPlanService instance = null;

	public static FlightPlanService getInstance() {
		if (instance == null) {
			instance = new FlightPlanService();
		}
		return instance;
	}

	/**
	 * Ajout d'un nouveau plan de vol
	 * 
	 * @param planeName
	 *            nom de l'avion concern� par le plan de vol
	 * @param airportDepartureName
	 *            nom de l'a�roport de d�part
	 * @param airportArrivalName
	 *            nom de l'a�roport d'arriv�e
	 * @param departureTime
	 *            heure de d�part
	 * @throws MyDBException
	 */
	public void insertFlightPlan(String planeName, String airportDepartureName, String airportArrivalName,
			Date departureTime) throws MyDBException {
		Plane p = Factory.createPlane(planeName);
		Airport aDepart = Factory.createAirport(airportDepartureName);
		Airport aArrivee = Factory.createAirport(airportArrivalName);
		FlightDuration fd = Factory.createFlightDuration(aDepart, aArrivee);
		FlightPlan fp = Factory.createFlightPlan(p, fd, departureTime);
		FlightPlanDAO.getInstance().insertFlightPlan(fp);
	}

	/**
	 * Affichage de toute la liste des plans de vol
	 * 
	 * @return la liste des plans de vol
	 * @throws MyDBException
	 */
	public List<String> selectAll() throws MyDBException {
		List<FlightPlan> listFlightPlan = FlightPlanDAO.getInstance().selectAll();
		List<String> listResult = new ArrayList<String>();
		for (FlightPlan fp : listFlightPlan) {
			listResult.add(fp.toString());
		}
		return listResult;
	}

	/**
	 * Affichage du plan de vol d'un avion entre deux a�roports � une date
	 * donn�e
	 * 
	 * @param planeName
	 *            nom de l'avion
	 * @param airportDeparture
	 *            a�roport de d�part
	 * @param airportArrival
	 *            a�roport d'arriv�e
	 * @param date
	 *            date de d�part
	 * @return le plan de vol de l'avion
	 * @throws MyDBException
	 */
	public String selectFlightPlanOfPlane(String planeName, String airportDeparture, String airportArrival, Date date)
			throws MyDBException {
		FlightPlan fp = FlightPlanDAO.getInstance().selectFlightPlanOfPlane(planeName, airportDeparture, airportArrival,
				date);
		return fp.toString();
	}

	/**
	 * Affiche la liste des vols au d�part d'un a�roport
	 * 
	 * @param airportName
	 *            nom de l'a�roport de d�part
	 * @return la liste des plans de vol
	 * @throws MyDBException
	 */
	public List<String> selectFlightsFromAirport(String airportName) throws MyDBException {
		List<String> listResult = new ArrayList<String>();
		List<FlightPlan> listFlightPlans = FlightPlanDAO.getInstance().selectFlightFromAirport(airportName);
		for (FlightPlan fp : listFlightPlans) {
			listResult.add(fp.toString());
		}
		return listResult;
	}

	/**
	 * Affiche la liste des vols qui arrivent dans un a�roport
	 * 
	 * @param airportName
	 *            nom de l'a�roport d'arriv�e
	 * @return la liste des plans de vol
	 * @throws MyDBException
	 */
	public List<String> selectFlightsToAirport(String airportName) throws MyDBException {
		List<String> listResult = new ArrayList<String>();
		List<FlightPlan> listFlightPlans = FlightPlanDAO.getInstance().selectFlightToAirport(airportName);
		for (FlightPlan fp : listFlightPlans) {
			listResult.add(fp.toString());
		}
		return listResult;
	}

	/**
	 * Affiche la liste des vols entre deux a�roports
	 * 
	 * @param airportDepartureName
	 *            nom de l'a�roport de d�part
	 * @param airportArrivalName
	 *            nom de l'a�roport d'arriv�e
	 * @return la liste des plans de vol
	 * @throws MyDBException
	 */
	public List<String> selectFlightBetweenAirports(String airportDepartureName, String airportArrivalName)
			throws MyDBException {
		List<String> listResult = new ArrayList<String>();
		List<FlightPlan> listFlightPlans = FlightPlanDAO.getInstance().selectFlightBetweenAirports(airportDepartureName,
				airportArrivalName);
		for (FlightPlan fp : listFlightPlans) {
			listResult.add(fp.toString());
		}
		return listResult;
	}

	/**
	 * Recherche les vols dans un intervalle de temps
	 * 
	 * @param date1
	 *            date de d�but
	 * @param date2
	 *            date de fin
	 * @return la liste des plans de vol
	 * @throws MyDBException
	 */
	public List<String> selectFlightIntoTimeInterval(Date date1, Date date2) throws MyDBException {
		List<String> listResult = new ArrayList<String>();
		List<FlightPlan> listFlightPlans = FlightPlanDAO.getInstance().selectFlightIntoTimeInterval(date1, date2);
		for (FlightPlan fp : listFlightPlans) {
			listResult.add(fp.toString());
		}
		return listResult;
	}

}
